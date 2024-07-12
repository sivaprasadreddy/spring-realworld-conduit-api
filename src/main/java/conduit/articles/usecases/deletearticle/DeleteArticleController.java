package conduit.articles.usecases.deletearticle;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteArticleController {
    private final DeleteArticleRepo deleteArticle;
    private final AuthService authService;

    DeleteArticleController(DeleteArticleRepo deleteArticle, AuthService authService) {
        this.deleteArticle = deleteArticle;
        this.authService = authService;
    }

    @DeleteMapping("/api/articles/{slug}")
    void delete(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        deleteArticle.deleteArticle(loginUser, slug);
    }
}
