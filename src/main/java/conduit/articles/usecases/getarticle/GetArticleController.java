package conduit.articles.usecases.getarticle;

import conduit.articles.usecases.shared.models.SingleArticleResponse;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
import conduit.shared.ResourceNotFoundException;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetArticleController {
    private final AuthService authService;
    private final FindArticleBySlugRepository findArticleBySlugRepository;

    GetArticleController(AuthService authService, FindArticleBySlugRepository findArticleBySlugRepository) {
        this.authService = authService;
        this.findArticleBySlugRepository = findArticleBySlugRepository;
    }

    @GetMapping("/api/articles/{slug}")
    @Operation(summary = "Get Article by Slug", tags = "Article API Endpoints")
    SingleArticleResponse getArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUser();
        var article = findArticleBySlugRepository
                .findArticleBySlug(loginUser, slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with slug: " + slug));
        return new SingleArticleResponse(article);
    }
}
