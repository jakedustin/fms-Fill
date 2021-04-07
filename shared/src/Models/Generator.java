package Models;

import java.util.UUID;

public class Generator {

    public static String randomStringUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
