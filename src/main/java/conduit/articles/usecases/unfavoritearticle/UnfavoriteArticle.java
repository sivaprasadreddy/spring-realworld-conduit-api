package conduit.articles.usecases.unfavoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UnfavoriteArticle {
    private final UnfavoriteArticleRepository unfavoriteArticleRepository;
    private final FindArticleBySlugRepository findArticleBySlug;

    UnfavoriteArticle(
            UnfavoriteArticleRepository unfavoriteArticleRepository, FindArticleBySlugRepository findArticleBySlug) {
        this.unfavoriteArticleRepository = unfavoriteArticleRepository;
        this.findArticleBySlug = findArticleBySlug;
    }

    public Optional<Article> execute(LoginUser loginUser, String slug) {
        unfavoriteArticleRepository.unfavoriteArticle(loginUser, slug);
        return findArticleBySlug.findArticleBySlug(loginUser, slug);
    }
}
