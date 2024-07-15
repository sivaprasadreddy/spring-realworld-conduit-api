package conduit.articles.usecases.createcomment;

import conduit.articles.usecases.shared.models.Comment;
import conduit.articles.usecases.shared.repo.FindArticleBySlugRepository;
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
    private final FindArticleBySlugRepository findArticleBySlug;

    CreateCommentController(
            AuthService authService,
            UserService userService,
            CreateCommentRepository createComment,
            FindArticleBySlugRepository findArticleBySlug) {
        this.authService = authService;
        this.userService = userService;
        this.createComment = createComment;
        this.findArticleBySlug = findArticleBySlug;
    }

    @PostMapping("/api/articles/{slug}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Comment", tags = "Comments")
    @SecurityRequirement(name = "Token")
    CreatedCommentResponse createComment(
            @PathVariable("slug") String slug, @RequestBody @Valid CreatedCommentPayloadWrapper payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        Long articleId = findArticleBySlug.getArticleMetadataBySlugOrThrow(slug).articleId();
        var comment =
                createComment.createComment(loginUser, articleId, new CreatedCommentCmd(slug, payload.comment.body()));
        var profile = userService.getProfile(loginUser, loginUser.username());
        return new CreatedCommentResponse(comment.withProfile(profile));
    }

    record CreatedCommentPayloadWrapper(@Valid CreatedCommentPayload comment) {}

    record CreatedCommentPayload(@NotEmpty(message = "{body.required}") String body) {}

    record CreatedCommentResponse(Comment comment) {}
}
