@param String userId

<div id="activity-log" hx-get="api/activity-log/${userId}" hx-trigger="load">
</div>
<hr>
<div>
    <form method="POST" id="activity-form" hx-post="api/activity-log" hx-ext="json-enc-custom" hx-target="#activity-log" hx-swap="beforeend">
        <input type="hidden" name="user[id]" value="${userId}">
        <label for="activity" hx-get="api/activity" hx-trigger="load" hx-target="#activity"></label>
        <select id="activity" name="activity[id]">
        </select>
        <label for="duration"></label>
        <input type="number" id="duration" name="duration" placeholder="Duration in seconds"/>
    </form>
</div>
