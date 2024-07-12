package conduit.articles.usecases.listarticles;

import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Tags.TAGS;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.multiset;
import static org.jooq.impl.DSL.select;

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
class FindArticlesRepo {
    private final DSLContext dsl;

    FindArticlesRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public MultipleArticles findArticles(LoginUser loginUser, ArticlesFilterCriteria filters) {
        List<Condition> conditions = new ArrayList<>();
        // TODO; apply filters

        var articlesCount = dsl.selectCount().from(ARTICLES).where(conditions).fetchOneInto(Integer.class);

        var records = dsl.select(
                        ARTICLES.ID,
                        ARTICLES.TITLE,
                        ARTICLES.SLUG,
                        ARTICLES.CONTENT,
                        ARTICLES.DESCRIPTION,
                        ARTICLES.AUTHOR_ID,
                        USERS.USERNAME,
                        USERS.BIO,
                        USERS.IMAGE,
                        ARTICLES.CREATED_AT,
                        ARTICLES.UPDATED_AT,
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
                .where(conditions)
                .orderBy(ARTICLES.CREATED_AT.desc())
                .limit(filters.limit())
                .offset(filters.offset())
                .fetch(record -> new Article(
                        record.get(ARTICLES.SLUG),
                        record.get(ARTICLES.TITLE),
                        record.get(ARTICLES.DESCRIPTION),
                        record.get(ARTICLES.CONTENT),
                        record.value12(),
                        record.get(ARTICLES.CREATED_AT),
                        record.get(ARTICLES.UPDATED_AT),
                        false,
                        0,
                        new Profile(
                                record.get(USERS.USERNAME),
                                record.get(USERS.BIO),
                                record.get(USERS.IMAGE),
                                // TODO; implement logic to check if loginUser follows this profile(user)
                                false)));

        return new MultipleArticles(records, articlesCount);
    }
}
