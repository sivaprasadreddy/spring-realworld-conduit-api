package conduit.users.usecases.shared.models;

public record UserResponse(UserInfo user) {

    public static UserResponse from(LoginUser loginUser) {
        return new UserResponse(new UserInfo(
                loginUser.email(), loginUser.getToken(), loginUser.username(), loginUser.bio(), loginUser.image()));
    }

    public static UserResponse from(User user, String token) {
        return new UserResponse(new UserInfo(user.email(), token, user.username(), user.bio(), user.image()));
    }

    record UserInfo(String email, String token, String username, String bio, String image) {}
}
