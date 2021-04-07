package Results;

import Models.Person;

public class PersonResult {

    public PersonResult(Person[] data) {
        this.data = data;
        success = true;
    }

    public PersonResult(Person person) {
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
        this.success = true;
    }

    public PersonResult(String message) {
        this.message = message;
        success = false;
    }

    String associatedUsername;
    String personID;
    String firstName;
    String lastName;
    String gender;
    String fatherID;
    String motherID;
    String spouseID;
    Person[] data;
    String message;
    boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getPersonID() {
        return personID;
    }

    public String getMessage() {
        return message;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public Person[] getData() {
        return data;
    }
}
