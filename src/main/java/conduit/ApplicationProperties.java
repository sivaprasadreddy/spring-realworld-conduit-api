package conduit;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "conduit")
public record ApplicationProperties(OpenAPIProperties openapi, CorsProperties cors, JwtProperties jwt) {

    public record JwtProperties(
            @DefaultValue("Conduit") String issuer,
            @DefaultValue("Authorization") String header,
            @DefaultValue("604800") Long expiresIn,
            @NotEmpty String secret) {}

    public record OpenAPIProperties(
            @DefaultValue("Spring Boot RealWorld Conduit API") String title,
            @DefaultValue("Spring Boot RealWorld Conduit API Swagger Documentation") String description,
            @DefaultValue("v1.0.0") String version,
            Contact contact) {

        public record Contact(
                @DefaultValue("SivaLabs") String name, @DefaultValue("support@sivalabs.in") String email) {}
    }

    public record CorsProperties(
            @DefaultValue("/api/**") String pathPattern,
            @DefaultValue("*") String allowedOrigins,
            @DefaultValue("*") String allowedMethods,
            @DefaultValue("*") String allowedHeaders) {}
}
