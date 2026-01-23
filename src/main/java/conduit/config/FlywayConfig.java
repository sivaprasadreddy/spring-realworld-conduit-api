package conduit.config;

import java.util.HashMap;
import java.util.Map;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

@Configuration
class FlywayConfig {
    private static final Logger log = LoggerFactory.getLogger(FlywayConfig.class);

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy(Environment env) {
        boolean isLocalProfile = env.acceptsProfiles(Profiles.of("local"));

        Map<String, String> overrides = new HashMap<>();
        if (isLocalProfile) {
            overrides.put("flyway.cleanDisabled", "false");
        }
        return flywayOld -> {
            final Flyway flyway = Flyway.configure()
                    .configuration(flywayOld.getConfiguration())
                    .configuration(overrides)
                    .load();
            try {
                flyway.migrate();
            } catch (Exception e) {
                log.error("Flyway migration failed", e);
                if (isLocalProfile) {
                    log.warn(
                            "Application is running in 'local' profile. Cleaning up database and applying migrations again");
                    flyway.clean();
                    flyway.migrate();
                } else {
                    throw e;
                }
            }
        };
    }
}
