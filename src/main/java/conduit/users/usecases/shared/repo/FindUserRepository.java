package conduit.users.usecases.shared.repo;

import static conduit.jooq.models.tables.Users.USERS;

import conduit.users.usecases.shared.models.User;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class FindUserRepository {
    private final DSLContext dsl;

    FindUserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<User> findUserByEmail(String email) {
        return dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(user -> new User(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getUsername(),
                        user.getBio(),
                        user.getImage()));
    }

    public boolean emailExists(String email) {
        return dsl.fetchExists(USERS.where(USERS.EMAIL.equalIgnoreCase(email)));
    }

    public boolean usernameExists(String username) {
        return dsl.fetchExists(USERS.where(USERS.USERNAME.equalIgnoreCase(username)));
    }
}
