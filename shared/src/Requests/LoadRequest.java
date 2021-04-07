package Requests;

import Models.*;

public class LoadRequest {
    User[] users;
    Person[] persons;
    Event[] events;

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
