@import ch.pa5.greenfit.repository.entity.ActivityEntity
@import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity
@import ch.pa5.greenfit.repository.entity.ActivityTrackerEntity.ActivityTimerType
@import java.time.Duration
@import java.time.Instant
@import java.util.Optional

@param ch.pa5.greenfit.repository.entity.UserEntity user
@param ActivityTrackerEntity activeTracker

<div id="activity-log" hx-get="api/activity-log/${user.getId()}" hx-trigger="load">
</div>
<hr>
<div>
    <form method="POST" id="activity-form" hx-post="api/activity-log" hx-ext="json-enc-custom" hx-target="#activity-log"
          hx-swap="beforeend">
        <input id="userId" type="hidden" name="user[id]" value="${user.getId()}">
        <label for="activity" hx-get="api/activity" hx-trigger="load" hx-target="#activity"
               hx-vals='{"selectedActivity": ${Optional.ofNullable(activeTracker).map(ActivityTrackerEntity::getActivity).map(ActivityEntity::getId).orElse(null)}}'></label>
        <select id="activity" name="activity[id]">
        </select>
        <label for="duration"></label>
        <input type="number" id="duration" name="duration" placeholder="Duration in seconds"/>
        @if(activeTracker == null)
        <button>Log manually</button>
            <p>or</p>
        @endif
    </form>
    @if(activeTracker == null|| activeTracker.getType() == ActivityTimerType.TIMER)
        @if(activeTracker == null)
        <button hx-post="api/activity-timer/start" hx-target="#content" hx-include="#userId,#activity"
                hx-ext="json-enc-custom">Timer
        </button>
        @else
        <p>Tracker running for <span
                    id="time">${Duration.between(activeTracker.getStartUtc(), Instant.now()).getSeconds()}</span>
            Seconds</p>
        <button hx-post="api/activity-timer/stop"  hx-ext="json-enc-custom" hx-target="#content"
                hx-include="#userId,#activity">Stop Timer
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
        @endif
    @endif
    @if (activeTracker == null || activeTracker.getType() == ActivityTrackerEntity.ActivityTimerType.GPS)
        <button>GPS</button>
    @endif
</div>
