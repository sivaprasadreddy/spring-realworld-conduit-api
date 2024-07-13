package conduit.articles.usecases.favoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FavoriteArticleController {
    private final FavoriteArticle favoriteArticle;
    private final AuthService authService;

    FavoriteArticleController(FavoriteArticle favoriteArticle, AuthService authService) {
        this.favoriteArticle = favoriteArticle;
        this.authService = authService;
    }

    @PostMapping("/api/articles/{slug}/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseWrapper<Article> favoriteArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = favoriteArticle.execute(loginUser, slug).orElseThrow();
        return new ResponseWrapper<>("article", article);
    }
}
