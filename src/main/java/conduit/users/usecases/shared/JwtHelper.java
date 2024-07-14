package conduit.users.usecases.shared;

import static java.nio.charset.StandardCharsets.UTF_8;

import conduit.ApplicationProperties;
import conduit.users.usecases.shared.models.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
    private static final Logger log = LoggerFactory.getLogger(JwtHelper.class);
    private final ApplicationProperties properties;

    public JwtHelper(ApplicationProperties properties) {
        this.properties = properties;
    }

    public Boolean validateToken(String token, LoginUser loginUser) {
        Claims claims = this.parseToken(token);
        String email = claims.getSubject();
        Date expiration = claims.getExpiration();
        return email != null && email.equals(loginUser.email()) && expiration.after(new Date());
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = this.parseToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public String generateToken(String email) {
        String secretString = properties.jwt().secret();
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(UTF_8));
        Date issuedAt = new Date();
        Date expiration = generateExpirationDate(issuedAt);
        return Jwts.builder()
                .issuer(properties.jwt().issuer())
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    private Claims parseToken(String token) {
        String secretString = properties.jwt().secret();
        SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(UTF_8));
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Date generateExpirationDate(Date date) {
        return new Date(date.getTime() + properties.jwt().expiresIn() * 1000);
    }
}
