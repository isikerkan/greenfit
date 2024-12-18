@param String selectedDate
@param String previousDate
@param String previousDateLabel

<input id="previousDate" hx-swap-oob="true" name="date" type="hidden" value="${previousDate}">
<span id="previousDateLabel" hx-swap-oob="true">${previousDateLabel}</span>
<input id="selectedDate" hx-swap-oob="true" name="date" type="date" value="${selectedDate}"/>
