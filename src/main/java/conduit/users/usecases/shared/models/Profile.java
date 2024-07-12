package conduit.users.usecases.shared.models;

public record Profile(String username, String bio, String image, boolean following) {

    public Profile withFollowing(boolean following) {
        return new Profile(username, bio, image, following);
    }
}
