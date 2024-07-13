package conduit.articles.usecases.getarticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepo;
import conduit.shared.ResourceNotFoundException;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetArticleController {
    private final FindArticleBySlugRepo getArticle;
    private final AuthService authService;

    GetArticleController(FindArticleBySlugRepo getArticle, AuthService authService) {
        this.getArticle = getArticle;
        this.authService = authService;
    }

    @GetMapping("/api/articles/{slug}")
    ResponseWrapper<Article> getArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = getArticle
                .findArticleBySlug(loginUser, slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with slug: " + slug));
        return new ResponseWrapper<>("article", article);
    }
}
