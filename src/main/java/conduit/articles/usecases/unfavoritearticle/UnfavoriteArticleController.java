package conduit.articles.usecases.unfavoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UnfavoriteArticleController {
    private final UnfavoriteArticle unfavoriteArticle;
    private final AuthService authService;

    UnfavoriteArticleController(UnfavoriteArticle unfavoriteArticle, AuthService authService) {
        this.unfavoriteArticle = unfavoriteArticle;
        this.authService = authService;
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    ResponseWrapper<Article> favoriteArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = unfavoriteArticle.execute(loginUser, slug).orElseThrow();
        return new ResponseWrapper<>("article", article);
    }
}
