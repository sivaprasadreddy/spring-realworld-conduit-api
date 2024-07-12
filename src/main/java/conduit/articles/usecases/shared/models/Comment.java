package conduit.articles.usecases.shared.models;

import conduit.users.usecases.shared.models.Profile;
import java.time.LocalDateTime;

public record Comment(Long id, String body, LocalDateTime createdAt, LocalDateTime updatedAt, Profile author) {
    public Comment withProfile(Profile profile) {
        return new Comment(id, body, createdAt, updatedAt, profile);
    }
}
