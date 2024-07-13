package conduit.articles.usecases.deletecomment;

import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteCommentRepo {
    private final DSLContext dsl;
    private final FindArticleIdBySlugRepo findArticleIdBySlugRepo;

    DeleteCommentRepo(DSLContext dsl, FindArticleIdBySlugRepo findArticleIdBySlugRepo) {
        this.dsl = dsl;
        this.findArticleIdBySlugRepo = findArticleIdBySlugRepo;
    }

    @Transactional
    public void deleteComment(LoginUser loginUser, String slug, Long commentId) {
        Long articleId = findArticleIdBySlugRepo.getRequiredArticleIdBySlug(slug);
        dsl.deleteFrom(COMMENTS)
                .where(COMMENTS.ID.eq(commentId).and(COMMENTS.ARTICLE_ID.eq(articleId)))
                .execute();
    }
}
