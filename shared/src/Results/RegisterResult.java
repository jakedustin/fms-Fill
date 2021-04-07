package Results;

import Models.*;

public class RegisterResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    private boolean success;

    public RegisterResult(String errorMessage) {
        this.message = errorMessage;
        this.success = false;
    }

    public RegisterResult(User user, AuthenticationToken authenticationToken) {
        this.authtoken = authenticationToken.getAuthtoken();
        this.username = user.getUsername();
        this.personID = user.getPersonID();
        this.success = true;
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
