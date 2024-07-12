package conduit.articles.usecases.favoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.Articles.ARTICLES;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class FavoriteArticleRepo {
    private final DSLContext dsl;

    FavoriteArticleRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void favoriteArticle(LoginUser loginUser, String slug) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        dsl.insertInto(ARTICLE_FAVORITE)
                .set(ARTICLE_FAVORITE.ARTICLE_ID, articleId)
                .set(ARTICLE_FAVORITE.USER_ID, loginUser.id())
                .set(ARTICLE_FAVORITE.CREATED_AT, LocalDateTime.now())
                .execute();
    }
}
