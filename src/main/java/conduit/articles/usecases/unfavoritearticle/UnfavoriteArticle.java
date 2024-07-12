package conduit.articles.usecases.unfavoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UnfavoriteArticle {
    private final UnfavoriteArticleRepo unfavoriteArticleRepo;
    private final FindArticleBySlugRepo findArticleBySlug;

    UnfavoriteArticle(UnfavoriteArticleRepo unfavoriteArticleRepo, FindArticleBySlugRepo findArticleBySlug) {
        this.unfavoriteArticleRepo = unfavoriteArticleRepo;
        this.findArticleBySlug = findArticleBySlug;
    }

    public Optional<Article> execute(LoginUser loginUser, String slug) {
        unfavoriteArticleRepo.unfavoriteArticle(loginUser, slug);
        return findArticleBySlug.findArticleBySlug(loginUser, slug);
    }
}
