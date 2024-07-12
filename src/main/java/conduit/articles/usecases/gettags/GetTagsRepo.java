package conduit.articles.usecases.gettags;

import static conduit.jooq.models.tables.Tags.TAGS;

import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
class GetTagsRepo {
    private final DSLContext dsl;

    GetTagsRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<String> findAllTags() {
        return dsl.select(TAGS.NAME).from(TAGS).fetch(TAGS.NAME);
    }
}
