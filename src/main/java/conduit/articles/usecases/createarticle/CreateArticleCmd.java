package conduit.articles.usecases.createarticle;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

record CreateArticleCmd(
        @NotEmpty(message = "{title.required}") String title,
        @NotEmpty(message = "{description.required}") String description,
        @NotEmpty(message = "{body.required}") String body,
        List<String> tagList) {}
