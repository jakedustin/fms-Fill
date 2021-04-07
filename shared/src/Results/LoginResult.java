package Results;

public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private boolean success;

    public LoginResult(String authtoken, String username, String personID, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public LoginResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
