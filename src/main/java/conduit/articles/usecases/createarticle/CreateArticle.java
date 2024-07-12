package conduit.articles.usecases.createarticle;

import conduit.articles.usecases.shared.models.Article;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepo;
import conduit.shared.StringUtils;
import conduit.users.usecases.shared.models.LoginUser;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class CreateArticle {
    private final CreateArticleRepo createArticleRepo;
    private final FindArticleBySlugRepo findArticleBySlugRepo;

    CreateArticle(CreateArticleRepo createArticleRepo, FindArticleBySlugRepo findArticleBySlugRepo) {
        this.createArticleRepo = createArticleRepo;
        this.findArticleBySlugRepo = findArticleBySlugRepo;
    }

    public Article execute(LoginUser loginUser, CreateArticleCmd cmd) {
        String slug = StringUtils.toSlug(cmd.title());
        Article article = new Article(
                slug,
                cmd.title(),
                cmd.description(),
                cmd.body(),
                cmd.tagList(),
                LocalDateTime.now(),
                null,
                false,
                0,
                null);
        createArticleRepo.createArticle(loginUser, article);
        return findArticleBySlugRepo.findArticleBySlug(loginUser, slug).orElseThrow();
    }
}
