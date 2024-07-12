package conduit.users.usecases.followuser;

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
class FollowUserRepo {
    private final DSLContext dsl;

    FollowUserRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Profile follow(LoginUser loginUser, String username) {
        var result = dsl.select(USERS.ID, USERS.USERNAME, USERS.BIO, USERS.IMAGE)
                .from(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne();
        if (result == null) {
            throw new BadRequestException("Invalid username " + username);
        }
        Long loginUserId = loginUser.id();
        Long profileUserId = result.get(USERS.ID);
        dsl.insertInto(USER_FOLLOWER)
                .set(USER_FOLLOWER.FROM_ID, loginUserId)
                .set(USER_FOLLOWER.TO_ID, profileUserId)
                .onConflictDoNothing()
                .execute();
        return new Profile(result.get(USERS.USERNAME), result.get(USERS.BIO), result.get(USERS.IMAGE), true);
    }
}
