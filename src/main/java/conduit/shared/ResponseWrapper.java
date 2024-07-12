package conduit.shared;

import java.util.HashMap;

public class ResponseWrapper<T> extends HashMap<String, T> {
    public ResponseWrapper(String key, T value) {
        put(key, value);
    }
}
