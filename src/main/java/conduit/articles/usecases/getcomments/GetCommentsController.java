package conduit.articles.usecases.getcomments;

import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.users.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetCommentsController {
    private final AuthService authService;
    private final GetCommentsRepository getCommentsRepository;
    private final FindArticleBySlugRepository findArticleBySlug;

    GetCommentsController(
            AuthService authService,
            GetCommentsRepository getCommentsRepository,
            FindArticleBySlugRepository findArticleBySlug) {
        this.authService = authService;
        this.getCommentsRepository = getCommentsRepository;
        this.findArticleBySlug = findArticleBySlug;
    }

    @GetMapping("/api/articles/{slug}/comments")
    @Operation(summary = "Get Comments of an Article", tags = "Comments")
    MultipleComments getComments(@PathVariable String slug) {
        var loginUser = authService.getCurrentUser();
        var articleId = findArticleBySlug.getArticleMetadataBySlugOrThrow(slug).articleId();

        return getCommentsRepository.getComments(loginUser, articleId);
    }
}
