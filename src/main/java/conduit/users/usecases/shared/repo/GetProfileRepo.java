package conduit.users.usecases.shared.repo;

import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.selectCount;

import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class GetProfileRepo {
    private final DSLContext dsl;

    GetProfileRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<Profile> findProfile(LoginUser loginUser, String username) {
        Long loginUserId = loginUser != null ? loginUser.id() : -1;

        return dsl.select(
                        USERS.ID,
                        USERS.USERNAME,
                        USERS.BIO,
                        USERS.IMAGE,
                        field(selectCount()
                                        .from(USER_FOLLOWER.where(USER_FOLLOWER
                                                .FROM_ID
                                                .eq(loginUserId)
                                                .and(USER_FOLLOWER.TO_ID.eq(USERS.ID)))))
                                .as("FOLLOWING"))
                .from(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOptional(result -> new Profile(
                        result.get(USERS.USERNAME),
                        result.get(USERS.BIO),
                        result.get(USERS.IMAGE),
                        result.get("FOLLOWING", Integer.class) > 0));
    }
}
