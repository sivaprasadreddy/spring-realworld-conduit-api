package conduit.users.usecases.unfollowuser;

import static conduit.jooq.models.tables.UserFollower.USER_FOLLOWER;
import static conduit.jooq.models.tables.Users.USERS;
import static org.jooq.impl.DSL.select;

import conduit.users.usecases.shared.models.LoginUser;
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

    public void unfollow(LoginUser loginUser, String username) {
        dsl.deleteFrom(USER_FOLLOWER)
                .where(USER_FOLLOWER
                        .FROM_ID
                        .eq(loginUser.id())
                        .and(USER_FOLLOWER.TO_ID.in(select(USERS.ID).from(USERS).where(USERS.USERNAME.eq(username)))))
                .execute();
    }
}
