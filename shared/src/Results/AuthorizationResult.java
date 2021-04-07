package Results;

public class AuthorizationResult {
    private boolean success;
    private String username;

    public AuthorizationResult(boolean success, String username) {
        this.success = success;
        this.username = username;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getUsername() {
        return username;
    }
}
