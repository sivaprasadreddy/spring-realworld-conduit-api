package conduit.articles.usecases.shared.repo;

import static conduit.jooq.models.tables.Articles.ARTICLES;

import conduit.articles.usecases.shared.models.ArticleAuthorPair;
import conduit.shared.ResourceNotFoundException;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class FindArticleIdBySlugRepository {
    private final DSLContext dsl;

    public FindArticleIdBySlugRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public ArticleAuthorPair getRequiredArticleIdBySlug(String slug) {
        ArticleAuthorPair articleAuthorPair = dsl.select(ARTICLES.ID, ARTICLES.AUTHOR_ID)
                .from(ARTICLES)
                .where(ARTICLES.SLUG.eq(slug))
                .fetchOne(r -> new ArticleAuthorPair(r.get(ARTICLES.ID), r.get(ARTICLES.AUTHOR_ID)));
        if (articleAuthorPair == null) {
            throw new ResourceNotFoundException("Article with slug '" + slug + "' does not exist");
        }
        return articleAuthorPair;
    }
}
