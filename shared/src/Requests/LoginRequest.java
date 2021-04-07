package Requests;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(String _username, String _password) {
        this.username = _username;
        this.password = _password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
