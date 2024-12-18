@import ch.pa5.greenfit.repository.entity.ArticleEntity
@import java.util.List

@param List<ArticleEntity> articles

@for(ArticleEntity article: articles)

    @template.fragment.food-option(article = article)

@endfor
