package conduit.users.usecases.updateuser;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("user")
record UpdateUserCmd(String email, String username, String password, String bio, String image) {}
