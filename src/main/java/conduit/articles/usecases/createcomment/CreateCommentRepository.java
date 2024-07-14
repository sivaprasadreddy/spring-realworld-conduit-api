package conduit.articles.usecases.createcomment;

import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.articles.usecases.shared.models.Comment;
import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class CreateCommentRepository {
    private final DSLContext dsl;
    private final FindArticleIdBySlugRepository findArticleIdBySlugRepository;

    CreateCommentRepository(DSLContext dsl, FindArticleIdBySlugRepository findArticleIdBySlugRepository) {
        this.dsl = dsl;
        this.findArticleIdBySlugRepository = findArticleIdBySlugRepository;
    }

    public Comment createComment(LoginUser loginUser, CreatedCommentCmd cmd) {
        Long articleId = findArticleIdBySlugRepository
                .getRequiredArticleIdBySlug(cmd.articleSlug())
                .articleId();
        return dsl.insertInto(COMMENTS)
                .set(COMMENTS.ARTICLE_ID, articleId)
                .set(COMMENTS.AUTHOR_ID, loginUser.id())
                .set(COMMENTS.CONTENT, cmd.body())
                .set(COMMENTS.CREATED_AT, LocalDateTime.now())
                .returning(COMMENTS.ID, COMMENTS.CONTENT, COMMENTS.CREATED_AT)
                .fetchOne(r -> new Comment(r.getId(), r.getContent(), r.getCreatedAt(), null, null));
    }
}
