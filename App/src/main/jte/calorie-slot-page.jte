@param String selectedDate
@param String previousDate
@param String previousDateLabel

<h1 class="mb-10 text-3xl font-bold">Konsum 🍎</h1>

<div class="w-3/5">

    @template.fragment.date-navigation(selectedDate = selectedDate, previousDate = previousDate, previousDateLabel = previousDateLabel)

    <div class="flex flex-col space-y-2"
         hx-get="/api/calories-by-slot" hx-trigger="load, date-changed from:body"
         hx-include="#selectedDate"></div>
</div>
