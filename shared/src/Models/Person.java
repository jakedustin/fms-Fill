package Models;

import java.util.Objects;

public class Person implements iModel {
    public Person(String _personID,
                  String _associatedUsername,
                  String _firstName,
                  String _lastName,
                  String _gender,
                  String _fatherID,
                  String _motherID,
                  String _spouseID) {
        this.associatedUsername = _associatedUsername;
        this.personID = _personID;
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.gender = _gender;
        this.fatherID = _fatherID;
        this.motherID = _motherID;
        this.spouseID = _spouseID;
    }
    public Person(String _associatedUsername,
                  String _firstName,
                  String _lastName,
                  String _gender,
                  String _fatherID,
                  String _motherID,
                  String _spouseID) {
        this.associatedUsername = _associatedUsername;
        this.personID = Generator.randomStringUUID();
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.gender = _gender;
        this.fatherID = _fatherID;
        this.motherID = _motherID;
        this.spouseID = _spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getAssociatedUsername().equals(person.getAssociatedUsername()) && getPersonID().equals(person.getPersonID()) && getFirstName().equals(person.getFirstName()) && getLastName().equals(person.getLastName()) && getGender().equals(person.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssociatedUsername(), getPersonID(), getFirstName(), getLastName(), getGender());
    }

    public Person(String associatedUsername,
                  String gender) {
        this.associatedUsername = associatedUsername;
        this.personID = Generator.randomStringUUID();
        this.gender = gender;
        if (gender.equalsIgnoreCase("m")) {
            this.firstName = Library.getMNameData().getRandomName();
        }
        else {
            this.firstName = Library.getFNameData().getRandomName();
        }
        this.lastName = Library.getSNameData().getRandomName();
    }

    // ------------------------------ //
    // properties may not be null --- //
    private String associatedUsername;
    private String personID;            // unique identifier
    private String firstName;
    private String lastName;
    private String gender;

    // properties may be null
    private String fatherID;
    private String motherID;
    private String spouseID;
    // ------------------------------ //
    // ------------------------------ //

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public String getUniqueID() {
        return getPersonID();
    }

    @Override
    public String toString() {
        return "\nPerson{" +
                "\nassociatedUsername='" + associatedUsername + '\'' +
                ",\n personID='" + personID + '\'' +
                ",\n firstName='" + firstName + '\'' +
                ",\n lastName='" + lastName + '\'' +
                ",\n gender='" + gender + '\'' +
                ",\n fatherID='" + fatherID + '\'' +
                ",\n motherID='" + motherID + '\'' +
                ",\n spouseID='" + spouseID + '\'' +
                "\n}";
    }
}
