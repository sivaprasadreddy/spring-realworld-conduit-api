package conduit.articles.usecases.feedarticles;

import conduit.articles.usecases.shared.models.MultipleArticles;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FeedArticlesController {
    private final FindFeedArticlesRepo feedArticles;
    private final AuthService authService;

    FeedArticlesController(FindFeedArticlesRepo feedArticles, AuthService authService) {
        this.feedArticles = feedArticles;
        this.authService = authService;
    }

    @GetMapping("/api/articles/feed")
    MultipleArticles getArticle(
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var filters = new ArticlesFeedFilterCriteria(limit, offset);
        return feedArticles.findFeedArticles(loginUser, filters);
    }
}
