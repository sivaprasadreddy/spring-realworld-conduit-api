package conduit.articles.usecases.unfavoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class UnfavoriteArticleRepository {
    private final FindArticleIdBySlugRepository findArticleIdBySlugRepository;
    private final DSLContext dsl;

    UnfavoriteArticleRepository(FindArticleIdBySlugRepository findArticleIdBySlugRepository, DSLContext dsl) {
        this.findArticleIdBySlugRepository = findArticleIdBySlugRepository;
        this.dsl = dsl;
    }

    public void unfavoriteArticle(LoginUser loginUser, String slug) {
        Long articleId = findArticleIdBySlugRepository.getRequiredArticleIdBySlug(slug);
        dsl.deleteFrom(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId).and(ARTICLE_FAVORITE.USER_ID.eq(loginUser.id())))
                .execute();
    }
}
