package Models;

import java.util.Objects;

public class User implements iModel {

    public User(String _username,
                String _password,
                String _email,
                String _firstName,
                String _lastName,
                String _gender,
                String _personID) {
        this.username = _username;
        this.password = _password;
        this.email = _email;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.gender = _gender;
        this.personID = _personID;
    }

    public User(String _username,
                String _password,
                String _email,
                String _firstName,
                String _lastName,
                String _gender) {
        this.username = _username;
        this.password = _password;
        this.email = _email;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.gender = _gender;
        this.personID = new Person(_username, _firstName, _lastName, _gender,
                null, null, null).getPersonID();
    }

    // ------------------------------ //
    // properties may not be null --- //
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    // must be 'm' or 'f'
    private String gender;
    private String personID;
    // ------------------------------ //
    // ------------------------------ //

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() { return personID; }

    @Override
    public String getUniqueID() {
        return getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername())
                && getPassword().equals(user.getPassword())
                && getEmail().equals(user.getEmail())
                && getFirstName().equals(user.getFirstName())
                && getLastName().equals(user.getLastName())
                && getGender().equals(user.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(),
                getPassword(),
                getEmail(),
                getFirstName(),
                getLastName(),
                getGender());
    }
}
