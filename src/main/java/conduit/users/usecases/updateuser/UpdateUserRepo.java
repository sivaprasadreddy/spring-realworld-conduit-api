package conduit.users.usecases.updateuser;

import static conduit.jooq.models.tables.Users.USERS;

import conduit.jooq.models.tables.records.UsersRecord;
import conduit.shared.BadRequestException;
import conduit.users.usecases.shared.models.LoginUser;
import org.jooq.DSLContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
class UpdateUserRepo {
    private final DSLContext dsl;

    UpdateUserRepo(DSLContext dsl) {
        this.dsl = dsl;
    }

    public String updateUser(LoginUser loginUser, UpdateUserCmd cmd) {
        UsersRecord usersRecord =
                dsl.selectFrom(USERS).where(USERS.EMAIL.eq(loginUser.email())).fetchOne();
        if (usersRecord == null) {
            throw new BadRequestException("Invalid user update request");
        }
        String email = loginUser.email();
        if (cmd.email() != null && !cmd.email().isEmpty()) {
            email = cmd.email();
            usersRecord.set(USERS.EMAIL, cmd.email());
        }
        if (cmd.username() != null && !cmd.username().isEmpty()) {
            usersRecord.set(USERS.USERNAME, cmd.username());
        }
        if (cmd.password() != null && !cmd.password().isEmpty()) {
            usersRecord.set(USERS.PASSWORD, cmd.password());
        }
        if (cmd.bio() != null) {
            usersRecord.set(USERS.BIO, cmd.bio());
        }
        if (cmd.image() != null) {
            usersRecord.set(USERS.IMAGE, cmd.image());
        }
        try {
            dsl.executeUpdate(usersRecord);
        } catch (DataIntegrityViolationException e) {
            // TODO: Check if there is any better way to find out which constraint is violated
            String message = e.getMessage();
            if (message.contains("uk_users_username")) {
                throw new BadRequestException("Username already exists");
            } else if (message.contains("uk_users_email")) {
                throw new BadRequestException("Email already exists");
            } else throw new BadRequestException("User details could not be updated");
        }
        return email;
    }
}
