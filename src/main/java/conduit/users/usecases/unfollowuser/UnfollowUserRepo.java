package conduit.users.usecases.unfollowuser;

import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;

import conduit.shared.BadRequestException;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
class UnfollowUserRepo {
    private final DSLContext dsl;

    UnfollowUserRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Profile unfollow(LoginUser loginUser, String username) {
        var result = dsl.select(USERS.ID, USERS.USERNAME, USERS.BIO, USERS.IMAGE)
                .from(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne();
        if (result == null) {
            throw new BadRequestException("Invalid username " + username);
        }
        Long loginUserId = loginUser.id();
        Long profileUserId = result.get(USERS.ID);
        dsl.deleteFrom(USER_FOLLOWER)
                .where(USER_FOLLOWER.FROM_ID.eq(loginUserId).and(USER_FOLLOWER.TO_ID.eq(profileUserId)))
                .execute();
        return new Profile(result.get(USERS.USERNAME), result.get(USERS.BIO), result.get(USERS.IMAGE), false);
    }
}
