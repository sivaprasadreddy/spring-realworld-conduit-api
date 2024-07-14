package conduit.articles.usecases.shared.repo;

import static conduit.jooq.models.tables.Articles.ARTICLES;

import conduit.shared.ResourceNotFoundException;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class FindArticleIdBySlugRepository {
    private final DSLContext dsl;

    public FindArticleIdBySlugRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Long getRequiredArticleIdBySlug(String slug) {
        Long articleId = dsl.select(ARTICLES.ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(ARTICLES.ID);
        if (articleId == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        return articleId;
    }
}
