package conduit.articles.usecases.favoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class FavoriteArticle {
    private final FavoriteArticleRepo favoriteArticleRepo;
    private final FindArticleBySlugRepo findArticleBySlug;

    public FavoriteArticle(FavoriteArticleRepo favoriteArticleRepo, FindArticleBySlugRepo findArticleBySlug) {
        this.favoriteArticleRepo = favoriteArticleRepo;
        this.findArticleBySlug = findArticleBySlug;
    }

    public Optional<Article> execute(LoginUser loginUser, String slug) {
        favoriteArticleRepo.favoriteArticle(loginUser, slug);
        return findArticleBySlug.findArticleBySlug(loginUser, slug);
    }
}
