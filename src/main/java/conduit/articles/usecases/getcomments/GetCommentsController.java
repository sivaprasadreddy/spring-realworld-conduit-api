package conduit.articles.usecases.getcomments;

import conduit.users.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetCommentsController {
    private final AuthService authService;
    private final GetCommentsRepo getComments;

    GetCommentsController(AuthService authService, GetCommentsRepo getComments) {
        this.authService = authService;
        this.getComments = getComments;
    }

    @GetMapping("/api/articles/{slug}/comments")
    MultipleComments getComments(@PathVariable String slug) {
        var loginUser = authService.getCurrentUser();
        return getComments.getComments(loginUser, slug);
    }
}
