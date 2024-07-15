package conduit.articles.usecases.favoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class FavoriteArticle {
    private final FavoriteArticleRepository favoriteArticleRepository;
    private final FindArticleBySlugRepository findArticleBySlug;
    private final FindArticleIdBySlugRepository findArticleIdBySlug;

    public FavoriteArticle(
            FavoriteArticleRepository favoriteArticleRepository,
            FindArticleBySlugRepository findArticleBySlug,
            FindArticleIdBySlugRepository findArticleIdBySlug) {
        this.favoriteArticleRepository = favoriteArticleRepository;
        this.findArticleBySlug = findArticleBySlug;
        this.findArticleIdBySlug = findArticleIdBySlug;
    }

    public Optional<Article> execute(LoginUser loginUser, String slug) {
        Long articleId = findArticleIdBySlug.getRequiredArticleIdBySlug(slug).articleId();
        favoriteArticleRepository.favoriteArticle(loginUser, articleId);
        return findArticleBySlug.findArticleBySlug(loginUser, slug);
    }
}
