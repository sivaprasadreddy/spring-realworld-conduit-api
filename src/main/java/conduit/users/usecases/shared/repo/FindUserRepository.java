package conduit.users.usecases.shared.repo;

import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.Records.mapping;

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
        return dsl.select(USERS.ID, USERS.EMAIL, USERS.PASSWORD, USERS.USERNAME, USERS.BIO, USERS.IMAGE)
                .from(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptional(mapping(User::new));
    }

    public boolean emailExists(String email) {
        return dsl.fetchExists(USERS.where(USERS.EMAIL.equalIgnoreCase(email)));
    }

    public boolean usernameExists(String username) {
        return dsl.fetchExists(USERS.where(USERS.USERNAME.equalIgnoreCase(username)));
    }
}
