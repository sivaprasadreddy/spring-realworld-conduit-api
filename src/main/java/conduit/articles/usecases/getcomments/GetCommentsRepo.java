package conduit.articles.usecases.getcomments;

import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Comments.COMMENTS;
import static conduit.jooq.models.tables.Users.USERS;

import conduit.articles.usecases.shared.models.Comment;
import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class GetCommentsRepo {
    private final DSLContext dsl;

    GetCommentsRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public MultipleComments getComments(LoginUser loginUser, String slug) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        var comments = dsl.select(
                        COMMENTS.ID,
                        COMMENTS.CONTENT,
                        COMMENTS.CREATED_AT,
                        COMMENTS.UPDATED_AT,
                        USERS.USERNAME,
                        USERS.BIO,
                        USERS.IMAGE)
                .from(COMMENTS.join(USERS).on(COMMENTS.AUTHOR_ID.eq(USERS.ID)))
                .where(COMMENTS.ARTICLE_ID.eq(articleId))
                .fetch(r -> new Comment(
                        r.get(COMMENTS.ID),
                        r.get(COMMENTS.CONTENT),
                        r.get(COMMENTS.CREATED_AT),
                        r.get(COMMENTS.UPDATED_AT),
                        // TODO; get 'following' value
                        new Profile(r.get(USERS.USERNAME), r.get(USERS.BIO), r.get(USERS.IMAGE), false)));
        return new MultipleComments(comments);
    }
}
