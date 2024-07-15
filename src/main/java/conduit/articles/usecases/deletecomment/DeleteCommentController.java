package conduit.articles.usecases.deletecomment;

import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteCommentController {
    private final AuthService authService;
    private final DeleteCommentRepository deleteComment;
    private final FindArticleBySlugRepository findArticleBySlug;

    DeleteCommentController(
            DeleteCommentRepository deleteComment,
            AuthService authService,
            FindArticleBySlugRepository findArticleBySlug) {
        this.deleteComment = deleteComment;
        this.authService = authService;
        this.findArticleBySlug = findArticleBySlug;
    }

    @DeleteMapping("/api/articles/{slug}/comments/{commentId}")
    @Operation(summary = "Delete Comment", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    void delete(@PathVariable String slug, @PathVariable Long commentId) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var articleId = findArticleBySlug.getArticleMetadataBySlugOrThrow(slug).articleId();
        deleteComment.deleteComment(loginUser, articleId, commentId);
    }
}
