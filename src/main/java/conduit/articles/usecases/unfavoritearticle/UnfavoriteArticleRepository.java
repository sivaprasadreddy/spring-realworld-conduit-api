package conduit.articles.usecases.unfavoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;

import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class UnfavoriteArticleRepository {
    private final DSLContext dsl;

    UnfavoriteArticleRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void unfavoriteArticle(LoginUser loginUser, Long articleId) {
        dsl.deleteFrom(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId).and(ARTICLE_FAVORITE.USER_ID.eq(loginUser.id())))
                .execute();
    }
}
