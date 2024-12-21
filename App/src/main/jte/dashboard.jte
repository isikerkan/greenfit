@param String selectedDate
@param String previousDate
@param String previousDateLabel

<h1 class="mb-10 text-3xl font-bold">Deine Leistungen 💪</h1>

<div class="w-1/2">

    @template.fragment.date-navigation(selectedDate = selectedDate, previousDate = previousDate, previousDateLabel = previousDateLabel)

    <div class="space-y-8 w-500px flex flex-col">
        <div class="">
            <h2>Kalorienansicht</h2>
            <div class="border-2 shadow-md rounded p-3">
                <p>Kalorien <span hx-get="api/get-calorie-intake" hx-trigger="load, change from:#selectedDate, date-changed from:body"
                                  hx-include="#selectedDate"></span>kcal</p>
            </div>
        </div>
        <div>
            <h2>Aktivitätenübersicht</h2>
            <div class="border-2 shadow-md rounded flex items-center justify-evenly ">
                <div class="p-3 flex items-center">
                    <image class="size-16 mr-3" src="icon/workout.svg"></image>
                    <div class="" hx-get="/api/activity-short-list" hx-trigger="load, change from:#selectedDate, date-changed from:body" hx-include="#selectedDate"></div>
                </div>
                <div class="p-3 flex items-center">
                    <image class="size-16 mr-3" src="icon/scale.svg"></image>
                    <p><span hx-get="api/get-calorie-burn" hx-trigger="load, change from:#selectedDate, date-changed from:body"
                             hx-include="#selectedDate"></span> kcal</p>
                </div>
            </div>
        </div>
        <div class="group    p-2 size-24 self-end">
            <div class="flex items-center justify-center rounded-full p-3 absolute">
                <image class="size-16" src="icon/plus.svg"></image>
            </div>
            <div class="flex items-center justify-center rounded-full pl-6 pr-10 py-2 absolute transition-all duration-[0.2s] ease-out scale-y-0 group-hover:scale-y-100 group-hover:-translate-x-28 bg-blue-100">
                <a class="w-12" href="/activities">Aktivitäten erfassen</a>
            </div>
            <div class="flex items-center justify-center rounded-full pl-6 pr-8 py-2 absolute transition-all duration-[0.2s] ease-out scale-x-0 group-hover:scale-x-100 group-hover:-translate-y-16 bg-blue-100  ">
                <a class="w-12" href="/calories">Kalorien erfassen</a>
            </div>
        </div>
    </div>
</div>

