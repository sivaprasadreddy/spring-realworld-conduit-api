package conduit.articles.usecases.createcomment;

import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.articles.usecases.shared.models.Comment;
import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class CreateCommentRepo {
    private final DSLContext dsl;

    CreateCommentRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Comment createComment(LoginUser loginUser, CreatedCommentCmd cmd) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(cmd.articleSlug()))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + cmd.articleSlug() + "' does not exist");
        }
        return dsl.insertInto(COMMENTS)
                .set(COMMENTS.ARTICLE_ID, articleId)
                .set(COMMENTS.AUTHOR_ID, loginUser.id())
                .set(COMMENTS.CONTENT, cmd.body())
                .set(COMMENTS.CREATED_AT, LocalDateTime.now())
                .returning(COMMENTS.ID, COMMENTS.CONTENT, COMMENTS.CREATED_AT)
                .fetchOne(r -> new Comment(r.getId(), r.getContent(), r.getCreatedAt(), null, null));
    }
}
