@import ch.pa5.greenfit.repository.entity.ArticleEntity

@param ArticleEntity article


<div class="flex flex-col border-2 shadow-xs"
     hx-get="/api/add-food?articleId=${article.getId()}"
     hx-swap="outerHTML"
>
    <p class="pl-2 pt-2 text-xl">${article.getName()}</p>
    <span class="self-end pb-2 pr-2"><image class="size-8" src="/icon/plus.svg"></image></span>
</div>
