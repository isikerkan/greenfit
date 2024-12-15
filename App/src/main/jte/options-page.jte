@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user

<div>
    <form hx-post="api/options" hx-ext="json-enc-custom" hx-target="#content">
        <input id="userId" type="hidden" name="user[id]" value="${user.getId()}">
        <label for="age">
            <input id="age" type="number" name="age" placeholder="Age" value="${user.getOptions().getAge()}">
        </label>
        <label for="height">
            <input id="height" type="number" name="height_cm" placeholder="Height in cm" value="${user.getOptions().getHeight()}">
        </label>
        <label for="weight">
            <input id="weight" type="number" name="weight_kg" placeholder="Weight in kg" value="${user.getOptions().getWeight()}">
        </label>

        <button type="submit">Save</button>
    </form>
</div>
