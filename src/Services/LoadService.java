package Services;

import DAOs.*;
import Models.*;
import Requests.LoadRequest;
import Results.LoadResult;

import java.sql.Connection;

public class LoadService {
    public LoadResult service(LoadRequest loadRequest) {
        Database db = new Database();
        try {
            new ClearService().service();
            Connection conn = db.getConnection();

            User[] users = loadRequest.getUsers();
            UserDAO userDAO = new UserDAO(conn);
            for (User user : users) {
                userDAO.insert(user);
            }

            Person[] persons = loadRequest.getPersons();
            PersonDAO personDAO = new PersonDAO(conn);
            for (Person person : persons) {
                personDAO.insert(person);
            }

            Event[] events = loadRequest.getEvents();
            EventDAO eventDAO = new EventDAO(conn);
            for (Event event : events) {
                eventDAO.insert(event);
            }

            db.closeConnection(true);
            return new LoadResult(generateMessage(users, persons, events), true);
        }
        catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                return new LoadResult(f.getMessage(), false);
            }
            return new LoadResult("Error: Invalid request data", false);
        }
    }

    private String generateMessage(User[] u, Person[] p, Event[] e) {
        return "Successfully added " +
                u.length + " users, " +
                p.length + " persons, and " +
                e.length + " events to the database.";
    }
}
