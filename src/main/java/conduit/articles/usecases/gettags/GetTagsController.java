package conduit.articles.usecases.gettags;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetTagsController {
    private final GetTagsRepository getTagsRepository;

    GetTagsController(GetTagsRepository getTagsRepository) {
        this.getTagsRepository = getTagsRepository;
    }

    @GetMapping("/api/tags")
    @Operation(summary = "Get all Tags", tags = "Article API Endpoints")
    Tags getTags() {
        return new Tags(getTagsRepository.findAllTags());
    }
}
