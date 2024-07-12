package conduit.config;

import java.util.List;

record ApiErrors(Errors errors) {

    public static ApiErrors from(List<String> body) {
        return new ApiErrors(new Errors(body));
    }
}

record Errors(List<String> body) {}
