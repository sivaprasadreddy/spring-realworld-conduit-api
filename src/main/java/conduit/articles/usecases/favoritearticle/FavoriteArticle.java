package conduit.articles.usecases.favoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class FavoriteArticle {
    private final FavoriteArticleRepository favoriteArticleRepository;
    private final FindArticleBySlugRepository findArticleBySlug;

    public FavoriteArticle(
            FavoriteArticleRepository favoriteArticleRepository, FindArticleBySlugRepository findArticleBySlug) {
        this.favoriteArticleRepository = favoriteArticleRepository;
        this.findArticleBySlug = findArticleBySlug;
    }

    public Optional<Article> execute(LoginUser loginUser, String slug) {
        favoriteArticleRepository.favoriteArticle(loginUser, slug);
        return findArticleBySlug.findArticleBySlug(loginUser, slug);
    }
}
