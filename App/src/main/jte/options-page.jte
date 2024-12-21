@import ch.pa5.greenfit.repository.entity.UserEntity

@param UserEntity user

<div class=" h-screen flex flex-col">
    <h1 class="mb-10 text-3xl font-bold">Deine Angaben ⚙️</h1>

    <div class="flex-grow">
        <p class="text-xl mb-4">Bitte teile deine persönlichen Informationen</p>
        <form hx-post="api/options" hx-ext="json-enc-custom" hx-target="#content" >
            <div class="flex flex-col items-center justify-center">
                <div class="p-3">
                    <input id="userId" type="hidden" name="id" value="${user.getOptions().getId()}">
                    <label for="age">
                        <input id="age" type="number" name="age" placeholder="Alter"
                               value="${user.getOptions().getAge()}" class="border p-1">
                    </label>
                    <label for="height">
                        <input id="height" type="number" name="height" placeholder="Grösse in cm"
                               value="${user.getOptions().getHeight()}" class="border p-1">
                    </label>
                    <label for="weight">
                        <input id="weight" type="number" name="weight" placeholder="Gewicht in kg"
                               value="${user.getOptions().getWeight()}" class="border p-1">
                    </label>
                </div>
                <button class="px-3 py-1 bg-blue-400 rounded mt-4" type="submit">Speichern</button>
            </div>
        </form>
    </div>
    <div class="flex justify-between w-1/2  ">
        <p class="cursor-pointer" hx-delete="/api/user-data"  hx-target="#content" hx-indicator="#spinner">Userdaten löschen</p>
        <p class="cursor-pointer" hx-delete="/api/user-account" hx-swap="none">Useraccount löschen</p>
    </div>
</div>
