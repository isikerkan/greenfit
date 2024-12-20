@import ch.pa5.greenfit.repository.entity.ActivityEntity
@import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity
@import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity.ActivityTimerType
@import java.time.Duration
@import java.time.Instant
@import java.util.Optional

@param ch.pa5.greenfit.repository.entity.UserEntity user
@param ActivityTrackerEntity activeTracker

<h1 class="mb-10 text-3xl font-bold">Auswahl Aktivität</h1>

<div class="w-3/4 ">
    <form method="POST" id="activity-form" hx-post="api/activity-log" hx-ext="json-enc-custom" hx-swap="none">
        <div class="flex flex-col w-3/4 ">
            <input id="userId" type="hidden" name="user[id]" value="${user.getId()}">
            <label for="activity" hx-get="api/activity" hx-trigger="load" hx-target="#activity"
                   hx-swap="innerHTML"
                   hx-vals='{"selectedActivity": ${Optional.ofNullable(activeTracker).map(ActivityTrackerEntity::getActivity).map(ActivityEntity::getId).orElse(-1L)}}'></label>
            <select id="activity" name="activity[id]" class="p-1" disabled="${activeTracker != null}">
            </select>
            <p class="mt-20 text-l font-bold">Dauer</p>
            @if(activeTracker == null)
            <div class="p-1">
                <label class="" for="duration">Minuten</label>
                <input class="w-28 pl-4" type="number" id="duration" name="duration" placeholder="00"/>
                    <button type="submit" class="rounded px-3 py-1 bg-blue-400 ml-6">Speichern</button>
            </div>
            @endif
        </div>
    </form>
    @if(activeTracker == null|| activeTracker.getType() == ActivityTimerType.TIMER)
        @if(activeTracker == null)
            <div class="flex item-center p-1">
                <label for="timer">Timer</label>
                <button id="timer" hx-post="api/activity-timer/start" hx-target="#content"
                        hx-include="#userId,#activity"
                        hx-ext="json-enc-custom"
                        class="px-3">
                    <image class="size-4" src="/icon/play.svg"></image>
                </button>
            </div>
        @else
            <div class="flex item-center p-1">
                <label for="timer">Timer</label>
                <span id="time"
                      class="px-2">${Duration.between(activeTracker.getStartUtc(), Instant.now()).getSeconds()}</span>
                <button hx-post="api/activity-timer/stop" hx-ext="json-enc-custom" hx-swap="none"
                        hx-include="#userId,#activity" >
                    <image class="size-6" src="/icon/stop.svg"></image>
                </button>
                <script>
                  (function () {
                    setInterval(() => {
                      const time = document.getElementById("time")
                      const parsedTime = parseInt(time.innerHTML) + 1
                      time.innerHTML = "" + parsedTime
                    }, 1000);
                  })()
                </script>
            </div>
        @endif
    @endif
</div>
