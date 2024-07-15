package conduit.articles.usecases.deletearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteArticleRepository {
    private static final Logger log = LoggerFactory.getLogger(DeleteArticleRepository.class);
    private final DSLContext dsl;

    DeleteArticleRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void deleteArticle(LoginUser loginUser, Long articleId) {
        log.info("Deleting article with articleId {} by userId:{}", articleId, loginUser.id());
        dsl.delete(ARTICLE_TAG).where(ARTICLE_TAG.ARTICLE_ID.eq(articleId)).execute();
        dsl.delete(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId))
                .execute();
        dsl.delete(COMMENTS).where(COMMENTS.ARTICLE_ID.eq(articleId)).execute();
        dsl.delete(ARTICLES).where(ARTICLES.ID.eq(articleId)).execute();
    }
}
