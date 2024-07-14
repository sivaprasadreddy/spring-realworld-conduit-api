package conduit.articles.usecases.shared.repo;

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
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class FindArticleBySlugRepository {
    private final DSLContext dsl;

    public FindArticleBySlugRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Article> findArticleBySlug(LoginUser loginUser, String articleSlug) {
        Long loginUserId = loginUser != null ? loginUser.id() : -1;
        var record = dsl.select(
                        ARTICLES.ID,
                        ARTICLES.TITLE,
                        ARTICLES.SLUG,
                        ARTICLES.CONTENT,
                        ARTICLES.DESCRIPTION,
                        ARTICLES.AUTHOR_ID,
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
                .from(ARTICLES.join(USERS).on(ARTICLES.AUTHOR_ID.eq(USERS.ID)))
                .where(ARTICLES.SLUG.eq(articleSlug))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }
        var articleId = record.getValue(ARTICLES.ID);

        boolean favourited = dsl.fetchExists(ARTICLE_FAVORITE.where(
                ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId).and(ARTICLE_FAVORITE.USER_ID.eq(loginUserId))));
        int favoritesCount = dsl.fetchCount(ARTICLE_FAVORITE.where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId)));

        return Optional.of(new Article(
                record.get(ARTICLES.SLUG),
                record.get(ARTICLES.TITLE),
                record.get(ARTICLES.DESCRIPTION),
                record.get(ARTICLES.CONTENT),
                record.value13(),
                record.get(ARTICLES.CREATED_AT),
                record.get(ARTICLES.UPDATED_AT),
                favourited,
                favoritesCount,
                new Profile(
                        record.get(USERS.USERNAME),
                        record.get(USERS.BIO),
                        record.get(USERS.IMAGE),
                        record.get("FOLLOWING", Integer.class) > 0)));
    }
}
