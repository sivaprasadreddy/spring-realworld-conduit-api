package conduit.articles.usecases.deletecomment;

import static conduit.jooq.models.tables.Comments.COMMENTS;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class DeleteCommentRepository {
    private final FindArticleIdBySlugRepository findArticleIdBySlugRepository;
    private final DSLContext dsl;

    DeleteCommentRepository(FindArticleIdBySlugRepository findArticleIdBySlugRepository, DSLContext dsl) {
        this.findArticleIdBySlugRepository = findArticleIdBySlugRepository;
        this.dsl = dsl;
    }

    public void deleteComment(LoginUser loginUser, String slug, Long commentId) {
        Long articleId = findArticleIdBySlugRepository.getRequiredArticleIdBySlug(slug);
        dsl.deleteFrom(COMMENTS)
                .where(COMMENTS.ID.eq(commentId).and(COMMENTS.ARTICLE_ID.eq(articleId)))
                .execute();
    }
}
