package conduit.articles.usecases.createcomment;

import com.fasterxml.jackson.annotation.JsonRootName;
import conduit.articles.usecases.shared.models.Comment;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.UserService;
import conduit.users.usecases.shared.models.LoginUser;
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
    private final CreateCommentRepo createComment;

    CreateCommentController(AuthService authService, UserService userService, CreateCommentRepo createComment) {
        this.authService = authService;
        this.userService = userService;
        this.createComment = createComment;
    }

    @PostMapping("/api/articles/{slug}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseWrapper<Comment> addComment(
            @PathVariable("slug") String slug, @RequestBody @Valid CreatedCommentPayload payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var comment = createComment.createComment(loginUser, new CreatedCommentCmd(slug, payload.body()));
        var profile = userService.getProfile(loginUser, loginUser.username());
        return new ResponseWrapper<>("comment", comment.withProfile(profile));
    }

    @JsonRootName("comment")
    record CreatedCommentPayload(@NotEmpty(message = "{body.required}") String body) {}
}
