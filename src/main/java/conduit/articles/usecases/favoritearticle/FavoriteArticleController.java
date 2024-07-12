package conduit.articles.usecases.favoritearticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.UserService;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FavoriteArticleController {
    private final FavoriteArticle favoriteArticle;
    private final AuthService authService;
    private final UserService userService;

    FavoriteArticleController(FavoriteArticle favoriteArticle, AuthService authService, UserService userService) {
        this.favoriteArticle = favoriteArticle;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/api/articles/{slug}/favorite")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseWrapper<Article> favoriteArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = favoriteArticle.execute(loginUser, slug).orElseThrow();
        Profile profile = userService.getProfile(loginUser, article.author().username());
        return new ResponseWrapper<>("article", article.withProfile(profile));
    }
}
