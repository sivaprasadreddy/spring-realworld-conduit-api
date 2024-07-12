package conduit.articles.usecases.deletearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteArticleRepo {
    private final DSLContext dsl;

    DeleteArticleRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void deleteArticle(LoginUser loginUser, String slug) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        dsl.delete(ARTICLE_TAG).where(ARTICLE_TAG.ARTICLE_ID.eq(articleId)).execute();
        dsl.delete(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId))
                .execute();
        dsl.delete(COMMENTS).where(COMMENTS.ARTICLE_ID.eq(articleId)).execute();
        dsl.delete(ARTICLES).where(ARTICLES.ID.eq(articleId)).execute();
    }
}
