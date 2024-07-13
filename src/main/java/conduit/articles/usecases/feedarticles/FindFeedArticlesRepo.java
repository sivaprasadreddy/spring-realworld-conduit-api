package conduit.articles.usecases.feedarticles;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Tags.TAGS;
import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectCount;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.models.MultipleArticles;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
class FindFeedArticlesRepo {
    private final DSLContext dsl;

    FindFeedArticlesRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public MultipleArticles findFeedArticles(LoginUser loginUser, ArticlesFeedFilterCriteria filters) {
        List<Long> followingUserIds = dsl.select(USER_FOLLOWER.TO_ID)
                .from(USER_FOLLOWER)
                .where(USER_FOLLOWER.FROM_ID.eq(loginUser.id()))
                .fetch(USER_FOLLOWER.TO_ID);
        if (followingUserIds.isEmpty()) {
            return new MultipleArticles(List.of(), 0);
        }

        var articlesCount = dsl.selectCount()
                .from(ARTICLES)
                .where(ARTICLES.AUTHOR_ID.in(followingUserIds))
                .fetchOneInto(Integer.class);

        var records = dsl.select(
                        ARTICLES.ID,
                        ARTICLES.TITLE,
                        ARTICLES.SLUG,
                        ARTICLES.CONTENT,
                        ARTICLES.DESCRIPTION,
                        ARTICLES.AUTHOR_ID,
                        field(selectCount()
                                        .from(ARTICLE_FAVORITE.where(ARTICLE_FAVORITE
                                                .ARTICLE_ID
                                                .eq(ARTICLES.ID)
                                                .and(ARTICLE_FAVORITE.USER_ID.eq(loginUser.id())))))
                                .as("FAVORITED"),
                        field(selectCount().from(ARTICLE_FAVORITE.where(ARTICLE_FAVORITE.ARTICLE_ID.eq(ARTICLES.ID))))
                                .as("FAVORITE_COUNT"),
                        ARTICLES.CREATED_AT,
                        ARTICLES.UPDATED_AT,
                        USERS.USERNAME,
                        USERS.BIO,
                        USERS.IMAGE,
                        field(selectCount()
                                        .from(USER_FOLLOWER.where(USER_FOLLOWER
                                                .FROM_ID
                                                .eq(loginUser.id())
                                                .and(USER_FOLLOWER.TO_ID.eq(USERS.ID)))))
                                .as("FOLLOWING"),
                        multiset(select(TAGS.NAME)
                                        .from(TAGS)
                                        .join(ARTICLE_TAG)
                                        .on(ARTICLE_TAG.TAG_ID.eq(TAGS.ID))
                                        .where(ARTICLE_TAG.ARTICLE_ID.eq(ARTICLES.ID)))
                                .as("tagsList")
                                .convertFrom(r -> r.getValues(TAGS.NAME)))
                .from(ARTICLES)
                .leftJoin(USERS)
                .on(ARTICLES.AUTHOR_ID.eq(USERS.ID))
                .where(ARTICLES.AUTHOR_ID.in(followingUserIds))
                .orderBy(ARTICLES.CREATED_AT.desc())
                .limit(filters.limit())
                .offset(filters.offset())
                .fetch(r -> new Article(
                        r.get(ARTICLES.SLUG),
                        r.get(ARTICLES.TITLE),
                        r.get(ARTICLES.DESCRIPTION),
                        r.get(ARTICLES.CONTENT),
                        r.value15(),
                        r.get(ARTICLES.CREATED_AT),
                        r.get(ARTICLES.UPDATED_AT),
                        r.get("FAVORITED", Integer.class) > 0,
                        r.get("FAVORITE_COUNT", Integer.class),
                        new Profile(
                                r.get(USERS.USERNAME),
                                r.get(USERS.BIO),
                                r.get(USERS.IMAGE),
                                r.get("FOLLOWING", Integer.class) > 0)));
        return new MultipleArticles(records, articlesCount);
    }
}
