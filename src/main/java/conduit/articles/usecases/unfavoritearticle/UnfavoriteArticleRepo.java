package conduit.articles.usecases.unfavoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.Articles.ARTICLES;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class UnfavoriteArticleRepo {
    private final DSLContext dsl;

    UnfavoriteArticleRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void unfavoriteArticle(LoginUser loginUser, String slug) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        dsl.deleteFrom(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId).and(ARTICLE_FAVORITE.USER_ID.eq(loginUser.id())))
                .execute();
    }
}
