package conduit.users.usecases.shared.repo;

import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;

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
        var result = dsl.select(USERS.ID, USERS.USERNAME, USERS.BIO, USERS.IMAGE)
                .from(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne();
        if (result == null) {
            return Optional.empty();
        }
        boolean following = false;
        if (loginUser != null) {
            Long loginUserId = loginUser.id();
            Long profileUserId = result.get(USERS.ID);
            following = dsl.fetchExists(USER_FOLLOWER.where(
                    USER_FOLLOWER.FROM_ID.eq(loginUserId).and(USER_FOLLOWER.TO_ID.eq(profileUserId))));
        }
        return Optional.of(
                new Profile(result.get(USERS.USERNAME), result.get(USERS.BIO), result.get(USERS.IMAGE), following));
    }
}
