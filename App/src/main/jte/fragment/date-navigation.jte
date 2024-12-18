@param String selectedDate
@param String previousDate
@param String previousDateLabel

<div id="daterow" class="flex justify-between mb-5" hx-get="/api/change-date" hx-include="#selectedDate" hx-trigger="change from:#selectedDate" hx-swap="outerHTML">
    <input id="previousDate" name="date" type="hidden" value="${previousDate}">
    <p class="cursor-pointer" hx-get="/api/change-date" hx-include="#previousDate" hx-swap="outerHTML" hx-target="#daterow" ><
        <span id="previousDateLabel">${previousDateLabel}</span>
    </p>
    <span><input id="selectedDate" name="date" type="date" value="${selectedDate}"/></span>
</div>
