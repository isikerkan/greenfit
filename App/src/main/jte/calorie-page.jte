@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user

<div id="calorie-log" hx-get="api/calorie-log/${user.getId()}" hx-trigger="load">
</div>
<hr>
<div>
    <form method="POST" id="calorie-form" hx-post="api/calorie-log" hx-ext="json-enc-custom" hx-target="#calorie-log" hx-swap="beforeend">
        <input type="hidden" name="user[id]" value="${user.getId()}">
        <label for="food" hx-get="api/get-foods" hx-trigger="load" hx-target="#food"></label>
        <select id="food" name="recipe[id]">
        </select>
        <label for="weight"></label>
        <input type="number" id="weight" name="size[amount]" placeholder="g"/>
        <input type="hidden" id="size_type" name="size[type]" value="GRAMM"/>
    </form>
</div>
