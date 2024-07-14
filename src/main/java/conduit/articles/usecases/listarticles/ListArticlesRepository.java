package conduit.articles.usecases.listarticles;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;
import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Tags.TAGS;
import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.condition;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectCount;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.models.MultipleArticles;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
class ListArticlesRepository {
    private final DSLContext dsl;

    ListArticlesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public MultipleArticles findArticles(LoginUser loginUser, ArticlesFilterCriteria filters) {
        Long loginUserId = loginUser != null ? loginUser.id() : -1;
        List<Condition> conditions = new ArrayList<>();

        if (filters.author() != null && !filters.author().isEmpty()) {
            conditions.add(condition(USERS.USERNAME.eq(filters.author())));
        }
        if (filters.favoritedBy() != null && !filters.favoritedBy().isEmpty()) {
            conditions.add(condition(ARTICLE_FAVORITE.USER_ID.eq(
                    select(USERS.ID).from(USERS).where(USERS.USERNAME.eq(filters.favoritedBy())))));
        }

        if (filters.tag() != null && !filters.tag().isEmpty()) {
            conditions.add(
                    condition(ARTICLE_TAG.TAG_ID.eq(select(TAGS.ID).from(TAGS).where(TAGS.NAME.eq(filters.tag())))));
        }

        var articlesCount = dsl.selectCount()
                .from(dsl.selectDistinct(ARTICLES.ID)
                        .from(ARTICLES)
                        .join(USERS)
                        .on(ARTICLES.AUTHOR_ID.eq(USERS.ID))
                        .leftJoin(ARTICLE_FAVORITE)
                        .on(ARTICLE_FAVORITE.ARTICLE_ID.eq(ARTICLES.ID))
                        .leftJoin(ARTICLE_TAG)
                        .on(ARTICLE_TAG.ARTICLE_ID.eq(ARTICLES.ID))
                        .where(conditions))
                .fetchOneInto(Integer.class);

        var records = dsl.selectDistinct(
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
                                                .and(ARTICLE_FAVORITE.USER_ID.eq(loginUserId)))))
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
                                                .eq(loginUserId)
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
                .join(USERS)
                .on(ARTICLES.AUTHOR_ID.eq(USERS.ID))
                .leftJoin(ARTICLE_FAVORITE)
                .on(ARTICLE_FAVORITE.ARTICLE_ID.eq(ARTICLES.ID))
                .leftJoin(ARTICLE_TAG)
                .on(ARTICLE_TAG.ARTICLE_ID.eq(ARTICLES.ID))
                .where(conditions)
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
