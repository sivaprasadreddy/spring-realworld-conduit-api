package conduit.articles.usecases.updatearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepo;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UpdateArticle {
    private final FindArticleBySlugRepo findArticleBySlug;
    private final UpdateArticleRepo updateArticle;

    UpdateArticle(FindArticleBySlugRepo findArticleBySlug, UpdateArticleRepo updateArticle) {
        this.findArticleBySlug = findArticleBySlug;
        this.updateArticle = updateArticle;
    }

    public Article execute(LoginUser loginUser, UpdateArticleCmd cmd) {
        String currentSlug = updateArticle.updateArticle(loginUser, cmd);
        return findArticleBySlug.findArticleBySlug(loginUser, currentSlug).orElseThrow();
    }
}
