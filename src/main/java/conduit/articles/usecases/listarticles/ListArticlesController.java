package conduit.articles.usecases.listarticles;

import conduit.articles.usecases.shared.models.MultipleArticles;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ListArticlesController {
    private final FindArticlesRepo listArticles;
    private final AuthService authService;

    ListArticlesController(FindArticlesRepo listArticles, AuthService authService) {
        this.listArticles = listArticles;
        this.authService = authService;
    }

    @GetMapping("/api/articles")
    MultipleArticles getArticle(
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "favorited", required = false) String favorited,
            @RequestParam(name = "limit", defaultValue = "20") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        LoginUser loginUser = authService.getCurrentUser();
        var filters = new ArticlesFilterCriteria(tag, author, favorited, limit, offset);
        return listArticles.findArticles(loginUser, filters);
    }
}
