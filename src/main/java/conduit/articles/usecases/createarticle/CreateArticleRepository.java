package conduit.articles.usecases.createarticle;

import static conduit.jooq.models.tables.ArticleTag.ARTICLE_TAG;
import static conduit.jooq.models.tables.Articles.ARTICLES;
import static conduit.jooq.models.tables.Tags.TAGS;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.models.Tag;
import conduit.shared.BadRequestException;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.DSLContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
class CreateArticleRepository {
    private final DSLContext dsl;

    CreateArticleRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void createArticle(LoginUser loginUser, Article article) {
        Long articleId;
        try {
            articleId = dsl.insertInto(ARTICLES)
                    .set(ARTICLES.SLUG, article.slug())
                    .set(ARTICLES.TITLE, article.title())
                    .set(ARTICLES.DESCRIPTION, article.description())
                    .set(ARTICLES.CONTENT, article.body())
                    .set(ARTICLES.AUTHOR_ID, loginUser.id())
                    .set(ARTICLES.CREATED_AT, LocalDateTime.now())
                    .returning(ARTICLES.ID)
                    .fetchOne(ARTICLES.ID);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("uk_articles_title")) {
                throw new BadRequestException("Article with title '" + article.title() + "' already exists");
            }
            if (e.getMessage().contains("uk_articles_slug")) {
                throw new BadRequestException("Article with slug '" + article.slug() + "' already exists");
            }
            throw new BadRequestException("Cannot create article");
        }
        if (article.tagList() != null && !article.tagList().isEmpty()) {
            Map<String, Tag> tagMap = findTags(article.tagList());

            for (String tag : article.tagList()) {
                Long tagId = tagMap.computeIfAbsent(tag, this::createTag).id();
                dsl.insertInto(ARTICLE_TAG)
                        .set(ARTICLE_TAG.ARTICLE_ID, articleId)
                        .set(ARTICLE_TAG.TAG_ID, tagId)
                        .set(ARTICLE_TAG.CREATED_AT, LocalDateTime.now())
                        .execute();
            }
        }
    }

    private Tag createTag(String tagName) {
        Long tagId = dsl.insertInto(TAGS)
                .set(TAGS.NAME, tagName)
                .set(TAGS.CREATED_AT, LocalDateTime.now())
                .returning(TAGS.ID)
                .fetchOne(TAGS.ID);
        return new Tag(tagId, tagName);
    }

    private Map<String, Tag> findTags(List<String> tagNames) {
        List<Tag> tags = dsl.select(TAGS.ID, TAGS.NAME)
                .from(TAGS)
                .where(TAGS.NAME.in(tagNames))
                .fetch(r -> new Tag(r.value1(), r.value2()));
        Map<String, Tag> tagMap = new HashMap<>();
        for (Tag tag : tags) {
            tagMap.put(tag.name(), tag);
        }
        return tagMap;
    }
}
