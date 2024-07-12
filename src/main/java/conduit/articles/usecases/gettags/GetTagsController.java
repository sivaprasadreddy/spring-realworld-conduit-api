package conduit.articles.usecases.gettags;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetTagsController {
    private final GetTagsRepo getTags;

    GetTagsController(GetTagsRepo getTags) {
        this.getTags = getTags;
    }

    @GetMapping("/api/tags")
    Tags getTags() {
        return new Tags(getTags.findAllTags());
    }
}
