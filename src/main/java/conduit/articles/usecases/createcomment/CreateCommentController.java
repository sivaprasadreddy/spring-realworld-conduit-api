package conduit.articles.usecases.createcomment;

import conduit.articles.usecases.shared.models.Comment;
import conduit.articles.usecases.shared.repo.FindArticleIdBySlugRepository;
import conduit.users.AuthService;
import conduit.users.UserService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreateCommentController {
    private final AuthService authService;
    private final UserService userService;
    private final CreateCommentRepository createComment;
    private final FindArticleIdBySlugRepository findArticleIdBySlug;

    CreateCommentController(
            AuthService authService,
            UserService userService,
            CreateCommentRepository createComment,
            FindArticleIdBySlugRepository findArticleIdBySlug) {
        this.authService = authService;
        this.userService = userService;
        this.createComment = createComment;
        this.findArticleIdBySlug = findArticleIdBySlug;
    }

    @PostMapping("/api/articles/{slug}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Comment", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    CreatedCommentResponse createComment(
            @PathVariable("slug") String slug, @RequestBody @Valid CreatedCommentPayloadWrapper payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        Long articleId = findArticleIdBySlug.getRequiredArticleIdBySlug(slug).articleId();
        var comment =
                createComment.createComment(loginUser, articleId, new CreatedCommentCmd(slug, payload.comment.body()));
        var profile = userService.getProfile(loginUser, loginUser.username());
        return new CreatedCommentResponse(comment.withProfile(profile));
    }

    record CreatedCommentPayloadWrapper(@Valid CreatedCommentPayload comment) {}

    record CreatedCommentPayload(@NotEmpty(message = "{body.required}") String body) {}

    record CreatedCommentResponse(Comment comment) {}
}
