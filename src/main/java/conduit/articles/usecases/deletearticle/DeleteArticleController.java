package conduit.articles.usecases.deletearticle;

import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Objects;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteArticleController {
    private final AuthService authService;
    private final DeleteArticleRepository deleteArticle;
    private final FindArticleIdBySlugRepository findArticleIdBySlug;

    DeleteArticleController(
            AuthService authService,
            DeleteArticleRepository deleteArticle,
            FindArticleIdBySlugRepository findArticleIdBySlug) {
        this.authService = authService;
        this.deleteArticle = deleteArticle;
        this.findArticleIdBySlug = findArticleIdBySlug;
    }

    @DeleteMapping("/api/articles/{slug}")
    @Operation(summary = "Delete Article", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    void delete(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var articleAuthor = findArticleIdBySlug.getRequiredArticleIdBySlug(slug);
        Long articleId = articleAuthor.articleId();
        if (!Objects.equals(articleAuthor.authorId(), loginUser.id())) {
            throw new AccessDeniedException("Access Denied");
        }
        deleteArticle.deleteArticle(loginUser, articleId);
    }
}
