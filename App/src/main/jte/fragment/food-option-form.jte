@import ch.pa5.greenfit.repository.entity.ArticleEntity

@param ArticleEntity article


<div id="foodCard" class="flex flex-col border-2 shadow-xs"
     hx-get="/api/food?articleId=${article.getId()}" hx-swap="outerHTML" hx-trigger="click target:#foodCard"
>
    <p class="pl-2 pt-2 text-xl">${article.getName()}</p>
    <div class="flex items-center justify-between">
        <form method="POST" id="calorie-form"
              class="mx-auto"
              hx-post="/api/calorie-log" hx-ext="json-enc-custom" hx-swap="none" hx-include="#date,#slotId,#userId"
        >
            <input id="articleId" type="hidden" name="article[id]" value="${article.getId()}">
            <label for="weight"></label>
            <input type="number" id="weight" name="portion[size][amount]" placeholder="Menge"/>
            <select id="size_type" name="portion[size][type]">
                <option value="${article.getUnitOfMeasure().getUnit()}">${article.getUnitOfMeasure().getUnit()}</option>
            </select>
        </form>
        <button class="self-end pb-2 pr-2" type="submit"
                hx-post="/api/calorie-log" hx-ext="json-enc-custom" hx-swap="none"
                hx-include="#calorie-form,#date,#slotId,#userId"
        >
            <image class="size-8" src="/icon/plus.svg"></image>
        </button>
    </div>
</div>

