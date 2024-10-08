package conduit.articles.usecases.deletecomment;

import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Objects;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteCommentRepository {
    private static final Logger log = LoggerFactory.getLogger(DeleteCommentRepository.class);

    private final DSLContext dsl;

    DeleteCommentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void deleteComment(LoginUser loginUser, Long articleId, Long commentId) {
        log.info("Deleting comment with id {} by userId:{}", commentId, loginUser.id());
        var record = dsl.select(COMMENTS.ID, COMMENTS.AUTHOR_ID)
                .from(COMMENTS)
                .where(COMMENTS.ID.eq(commentId))
                .fetchOne();
        if (record == null) {
            throw new ResourceNotFoundException("Comment with id " + commentId + " not found");
        }
        if (!Objects.equals(record.get(COMMENTS.AUTHOR_ID), loginUser.id())) {
            throw new AccessDeniedException("Access Denied");
        }
        dsl.deleteFrom(COMMENTS)
                .where(COMMENTS.ID.eq(commentId).and(COMMENTS.ARTICLE_ID.eq(articleId)))
                .execute();
    }
}
