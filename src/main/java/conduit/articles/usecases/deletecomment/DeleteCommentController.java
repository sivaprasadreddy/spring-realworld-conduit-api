package conduit.articles.usecases.deletecomment;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DeleteCommentController {
    private final DeleteCommentRepo deleteComment;
    private final AuthService authService;

    DeleteCommentController(DeleteCommentRepo deleteComment, AuthService authService) {
        this.deleteComment = deleteComment;
        this.authService = authService;
    }

    @DeleteMapping("/api/articles/{slug}/comments/{commentId}")
    void delete(@PathVariable String slug, @PathVariable Long commentId) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        deleteComment.deleteComment(loginUser, slug, commentId);
    }
}
