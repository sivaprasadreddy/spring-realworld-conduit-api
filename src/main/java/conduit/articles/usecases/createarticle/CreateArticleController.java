package conduit.articles.usecases.createarticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreateArticleController {
    private final CreateArticle createArticle;
    private final AuthService authService;

    CreateArticleController(CreateArticle createArticle, AuthService authService) {
        this.createArticle = createArticle;
        this.authService = authService;
    }

    @PostMapping("/api/articles")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseWrapper<Article> create(@RequestBody @Valid CreateArticleCmd cmd) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = createArticle.execute(loginUser, cmd);
        return new ResponseWrapper<>("article", article);
    }
}
