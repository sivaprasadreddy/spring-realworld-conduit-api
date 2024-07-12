package conduit.users.usecases.shared.models;

public record UserResponse(String email, String token, String username, String bio, String image) {

    public static UserResponse from(LoginUser loginUser) {
        return new UserResponse(
                loginUser.email(), loginUser.getToken(), loginUser.username(), loginUser.bio(), loginUser.image());
    }

    public static UserResponse from(User user, String token) {
        return new UserResponse(user.email(), token, user.username(), user.bio(), user.image());
    }
}
