package Models;

import java.util.Objects;

public class AuthenticationToken implements iModel {

    public AuthenticationToken(String _username) {
        this.authtoken = Generator.randomStringUUID();
        this.username = _username;
    }

    public AuthenticationToken(String _username, String _authtoken) {
        this.authtoken = _authtoken;
        this.username = _username;
    }

    // properties are non-null
    private String authtoken;         // unique identifier
    private String username;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUniqueID() {
        return getAuthtoken();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationToken that = (AuthenticationToken) o;
        return getAuthtoken().equals(that.getAuthtoken()) && getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthtoken(), getUsername());
    }
}
