package conduit.articles.usecases.deletearticle;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteArticleController {
    private final AuthService authService;
    private final DeleteArticleRepository deleteArticle;

    DeleteArticleController(AuthService authService, DeleteArticleRepository deleteArticle) {
        this.authService = authService;
        this.deleteArticle = deleteArticle;
    }

    @DeleteMapping("/api/articles/{slug}")
    @Operation(summary = "Delete Article", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    void delete(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        deleteArticle.deleteArticle(loginUser, slug);
    }
}
