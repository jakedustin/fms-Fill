package Models;

import java.util.Objects;

public class Event implements iModel {
    // constructor to build a deep copy of an event, designed for use with secondary marriage events
    public Event(Event event,
                 Person person) {
        this.associatedUsername = person.getAssociatedUsername();
        this.eventID = Generator.randomStringUUID();
        this.personID = person.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }

    // constructor designed for birth/death/initial marriage events where location does not matter
    public Event(Person person,
                 String eventType,
                 int year) {
        Location location = Library.getLocations().getRandomLocation();
        this.associatedUsername = person.getAssociatedUsername();
        this.eventID = Generator.randomStringUUID();
        this.personID = person.getPersonID();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.country = location.getCountry();
        this.city = location.getCity();
        this.eventType = eventType;
        this.year = year;
    }

    public Event(String _eventID,
                 String _associatedUsername,
                 String _personID,
                 float _latitude,
                 float _longitude,
                 String _country,
                 String _city,
                 String _eventType,
                 int _year) {
        this.associatedUsername = _associatedUsername;
        this.eventID = _eventID;
        this.personID = _personID;
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.country = _country;
        this.city = _city;
        this.eventType = _eventType;
        this.year = _year;
    }

    public Event(String _associatedUsername,
                 String _personID,
                 float _latitude,
                 float _longitude,
                 String _country,
                 String _city,
                 String _eventType,
                 int _year) {
        this.associatedUsername = _associatedUsername;
        this.eventID = Generator.randomStringUUID();
        this.personID = _personID;
        this.latitude = _latitude;
        this.longitude = _longitude;
        this.country = _country;
        this.city = _city;
        this.eventType = _eventType;
        this.year = _year;
    }
    //
    // properties may not be null
    private String associatedUsername;
    private String eventID;                 // unique identifier
    private String personID;

    private float latitude;
    private float longitude;
    private String country;
    private String city;

    private String eventType;
    private int year;
    //
    //

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String getUniqueID() {
        return getEventID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Float.compare(event.getLatitude(), getLatitude()) == 0 && Float.compare(event.getLongitude(), getLongitude()) == 0 && getYear() == event.getYear() && Objects.equals(getAssociatedUsername(), event.getAssociatedUsername()) && Objects.equals(getEventID(), event.getEventID()) && Objects.equals(getPersonID(), event.getPersonID()) && Objects.equals(getCountry(), event.getCountry()) && Objects.equals(getCity(), event.getCity()) && Objects.equals(getEventType(), event.getEventType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssociatedUsername(), getEventID(), getPersonID(), getLatitude(), getLongitude(), getCountry(), getCity(), getEventType(), getYear());
    }

    @Override
    public String toString() {
        return "\nEvent {" +
                "\nassociatedUsername='" + associatedUsername + '\'' +
                ",\n eventID='" + eventID + '\'' +
                ",\n personID='" + personID + '\'' +
                ",\n latitude=" + latitude +
                ",\n longitude=" + longitude +
                ",\n country='" + country + '\'' +
                ",\n city='" + city + '\'' +
                ",\n eventType='" + eventType + '\'' +
                ",\n year=" + year +
                "\n}";
    }
}
