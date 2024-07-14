package conduit.articles.usecases.getcomments;

import conduit.users.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetCommentsController {
    private final AuthService authService;
    private final GetCommentsRepository getCommentsRepository;

    GetCommentsController(AuthService authService, GetCommentsRepository getCommentsRepository) {
        this.authService = authService;
        this.getCommentsRepository = getCommentsRepository;
    }

    @GetMapping("/api/articles/{slug}/comments")
    @Operation(summary = "Get Comments of an Article", tags = "Article API Endpoints")
    MultipleComments getComments(@PathVariable String slug) {
        var loginUser = authService.getCurrentUser();
        return getCommentsRepository.getComments(loginUser, slug);
    }
}
