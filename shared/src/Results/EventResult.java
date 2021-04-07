package Results;

import Models.Event;

public class EventResult {
    public EventResult(Event[] data) {
        this.data = data;
        success = true;
    }

    public EventResult(Event event) {
        this.associatedUsername = event.getAssociatedUsername();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
        this.success = true;
    }

    public EventResult(String message) {
        this.message = message;
        success = false;
    }

    String associatedUsername;
    String eventID;
    String personID;
    float latitude;
    float longitude;
    String country;
    String city;
    String eventType;
    int year;
    Event[] data;
    String message;
    boolean success;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    public Event[] getData() {
        return data;
    }
}
