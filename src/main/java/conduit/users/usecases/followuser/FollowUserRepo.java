package conduit.users.usecases.followuser;

import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.select;

import conduit.users.usecases.shared.models.LoginUser;
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

    public void follow(LoginUser loginUser, String username) {
        dsl.insertInto(USER_FOLLOWER)
                .set(USER_FOLLOWER.FROM_ID, loginUser.id())
                .set(USER_FOLLOWER.TO_ID, select(USERS.ID).from(USERS).where(USERS.USERNAME.eq(username)))
                .onConflictDoNothing()
                .execute();
    }
}
