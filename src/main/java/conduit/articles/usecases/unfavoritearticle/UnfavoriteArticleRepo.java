package conduit.articles.usecases.unfavoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class UnfavoriteArticleRepo {
    private final DSLContext dsl;
    private final FindArticleIdBySlugRepo findArticleIdBySlugRepo;

    UnfavoriteArticleRepo(DSLContext dsl, FindArticleIdBySlugRepo findArticleIdBySlugRepo) {
        this.dsl = dsl;
        this.findArticleIdBySlugRepo = findArticleIdBySlugRepo;
    }

    public void unfavoriteArticle(LoginUser loginUser, String slug) {
        Long articleId = findArticleIdBySlugRepo.getRequiredArticleIdBySlug(slug);
        dsl.deleteFrom(ARTICLE_FAVORITE)
                .where(ARTICLE_FAVORITE.ARTICLE_ID.eq(articleId).and(ARTICLE_FAVORITE.USER_ID.eq(loginUser.id())))
                .execute();
    }
}
