package conduit.articles.usecases.updatearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UpdateArticle {
    private final FindArticleBySlugRepository findArticleBySlug;
    private final UpdateArticleRepository updateArticleRepository;

    UpdateArticle(FindArticleBySlugRepository findArticleBySlug, UpdateArticleRepository updateArticleRepository) {
        this.findArticleBySlug = findArticleBySlug;
        this.updateArticleRepository = updateArticleRepository;
    }

    public Article execute(LoginUser loginUser, UpdateArticleCmd cmd) {
        String currentSlug = updateArticleRepository.updateArticle(loginUser, cmd);
        return findArticleBySlug.findArticleBySlug(loginUser, currentSlug).orElseThrow();
    }
}
