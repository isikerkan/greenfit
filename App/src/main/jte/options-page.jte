@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user

<h1 class="mb-10 text-3xl font-bold">Deine Angaben ⚙️</h1>

<div>
    <p class="text-xl mb-4">Bitte teile deine persönlichen Informationen</p>
    <form hx-post="api/options" hx-ext="json-enc-custom" hx-target="#content">
        <div class="flex flex-col items-center justify-center">
            <div class="p-3">
        <input id="userId" type="hidden" name="id" value="${user.getOptions().getId()}">
        <label for="age">
            <input id="age" type="number" name="age" placeholder="Alter" value="${user.getOptions().getAge()}">
        </label>
        <label for="height">
            <input id="height" type="number" name="height" placeholder="Grösse in cm" value="${user.getOptions().getHeight()}">
        </label>
        <label for="weight">
            <input id="weight" type="number" name="weight" placeholder="Gewicht in kg" value="${user.getOptions().getWeight()}">
        </label>
            </div>
        <button class="px-3 py-1 bg-blue-400 rounded mt-4" type="submit">Speichern</button>
        </div>
    </form>
</div>
