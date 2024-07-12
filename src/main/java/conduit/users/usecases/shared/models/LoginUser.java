package conduit.users.usecases.shared.models;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoginUser implements UserDetails {
    public static final LoginUser ANONYMOUS = new LoginUser(null, null, null, null, null, null);

    private final Long id;
    private final String email;
    private final String password;
    private final String username;
    private final String bio;
    private final String image;
    private String token;

    public LoginUser(Long id, String email, String password, String username, String bio, String image) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }

    public String username() {
        return username;
    }

    public String bio() {
        return bio;
    }

    public String image() {
        return image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
