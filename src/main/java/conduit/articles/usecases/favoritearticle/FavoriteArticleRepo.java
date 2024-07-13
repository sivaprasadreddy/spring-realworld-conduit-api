package conduit.articles.usecases.favoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class FavoriteArticleRepo {
    private final DSLContext dsl;
    private final FindArticleIdBySlugRepo findArticleIdBySlugRepo;

    FavoriteArticleRepo(DSLContext dsl, FindArticleIdBySlugRepo findArticleIdBySlugRepo) {
        this.dsl = dsl;
        this.findArticleIdBySlugRepo = findArticleIdBySlugRepo;
    }

    public void favoriteArticle(LoginUser loginUser, String slug) {
        Long articleId = findArticleIdBySlugRepo.getRequiredArticleIdBySlug(slug);
        dsl.insertInto(ARTICLE_FAVORITE)
                .set(ARTICLE_FAVORITE.ARTICLE_ID, articleId)
                .set(ARTICLE_FAVORITE.USER_ID, loginUser.id())
                .set(ARTICLE_FAVORITE.CREATED_AT, LocalDateTime.now())
                .execute();
    }
}
