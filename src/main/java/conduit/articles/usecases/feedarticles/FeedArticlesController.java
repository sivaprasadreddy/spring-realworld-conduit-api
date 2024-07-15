package conduit.articles.usecases.feedarticles;

import conduit.articles.usecases.shared.models.MultipleArticles;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FeedArticlesController {
    private final AuthService authService;
    private final FindFeedArticlesRepository feedArticlesRepo;

    FeedArticlesController(AuthService authService, FindFeedArticlesRepository feedArticlesRepo) {
        this.authService = authService;
        this.feedArticlesRepo = feedArticlesRepo;
    }

    @GetMapping("/api/articles/feed")
    @Operation(summary = "Get Login User Articles Feed", tags = "Articles")
    @SecurityRequirement(name = "Token")
    MultipleArticles getArticlesFeed(
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var filters = new ArticlesFeedFilterCriteria(limit, offset);
        return feedArticlesRepo.findFeedArticles(loginUser, filters);
    }
}
