package conduit.articles.usecases.getcomments;

import static conduit.jooq.models.tables.Comments.COMMENTS;
import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.selectCount;

import conduit.articles.usecases.shared.models.Comment;
import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class GetCommentsRepo {
    private final DSLContext dsl;
    private final FindArticleIdBySlugRepo findArticleIdBySlugRepo;

    GetCommentsRepo(DSLContext dsl, FindArticleIdBySlugRepo findArticleIdBySlugRepo) {
        this.dsl = dsl;
        this.findArticleIdBySlugRepo = findArticleIdBySlugRepo;
    }

    public MultipleComments getComments(LoginUser loginUser, String slug) {
        Long articleId = findArticleIdBySlugRepo.getRequiredArticleIdBySlug(slug);
        Long loginUserId = loginUser != null ? loginUser.id() : -1;
        var comments = dsl.select(
                        COMMENTS.ID,
                        COMMENTS.CONTENT,
                        COMMENTS.CREATED_AT,
                        COMMENTS.UPDATED_AT,
                        USERS.USERNAME,
                        USERS.BIO,
                        USERS.IMAGE,
                        field(selectCount()
                                        .from(USER_FOLLOWER.where(USER_FOLLOWER
                                                .FROM_ID
                                                .eq(loginUserId)
                                                .and(USER_FOLLOWER.TO_ID.eq(USERS.ID)))))
                                .as("FOLLOWING"))
                .from(COMMENTS.join(USERS).on(COMMENTS.AUTHOR_ID.eq(USERS.ID)))
                .where(COMMENTS.ARTICLE_ID.eq(articleId))
                .fetch(r -> new Comment(
                        r.get(COMMENTS.ID),
                        r.get(COMMENTS.CONTENT),
                        r.get(COMMENTS.CREATED_AT),
                        r.get(COMMENTS.UPDATED_AT),
                        new Profile(
                                r.get(USERS.USERNAME),
                                r.get(USERS.BIO),
                                r.get(USERS.IMAGE),
                                r.get("FOLLOWING", Integer.class) > 0)));
        return new MultipleComments(comments);
    }
}
