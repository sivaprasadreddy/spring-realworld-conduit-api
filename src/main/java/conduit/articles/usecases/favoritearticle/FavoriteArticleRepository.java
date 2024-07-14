package conduit.articles.usecases.favoritearticle;

import static conduit.jooq.models.tables.ArticleFavorite.ARTICLE_FAVORITE;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
class FavoriteArticleRepository {
    private final FindArticleIdBySlugRepository findArticleIdBySlugRepository;
    private final DSLContext dsl;

    FavoriteArticleRepository(FindArticleIdBySlugRepository findArticleIdBySlugRepository, DSLContext dsl) {
        this.findArticleIdBySlugRepository = findArticleIdBySlugRepository;
        this.dsl = dsl;
    }

    public void favoriteArticle(LoginUser loginUser, String slug) {
        Long articleId =
                findArticleIdBySlugRepository.getRequiredArticleIdBySlug(slug).articleId();
        dsl.insertInto(ARTICLE_FAVORITE)
                .set(ARTICLE_FAVORITE.ARTICLE_ID, articleId)
                .set(ARTICLE_FAVORITE.USER_ID, loginUser.id())
                .set(ARTICLE_FAVORITE.CREATED_AT, LocalDateTime.now())
                .execute();
    }
}
