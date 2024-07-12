package conduit.articles.usecases.deletecomment;

import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteCommentRepo {
    private final DSLContext dsl;

    DeleteCommentRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void deleteComment(LoginUser loginUser, String slug, Long commentId) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        dsl.deleteFrom(COMMENTS)
                .where(COMMENTS.ID.eq(commentId).and(COMMENTS.ARTICLE_ID.eq(articleId)))
                .execute();
    }
}
