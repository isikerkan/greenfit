@param String slotId
@param String date
@param ch.pa5.greenfit.repository.entity.UserEntity user

<h1 class="mb-10 text-3xl font-bold">Konsum hinzufügen 📈</h1>

<div class="w-3/5">
    <input id="userId" type="hidden" name="user[id]" value="${user.getId()}">
    <input id="slotId" type="hidden" name="slot[id]" value="${slotId}">
    <input id="date" type="hidden" name="date" value="${date}">

    <div class="flex flex-col space-y-2"
         hx-get="/api/get-foods"
         hx-trigger="load"
    >
    </div>

</div>

