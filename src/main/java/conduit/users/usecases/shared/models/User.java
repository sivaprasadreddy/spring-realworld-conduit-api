package conduit.users.usecases.shared.models;

public record User(Long id, String email, String password, String username, String bio, String image) {
    public User withId(Long id) {
        return new User(id, email, password, username, bio, image);
    }
}
