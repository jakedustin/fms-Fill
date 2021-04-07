package Services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.EventDAO;
import Models.Event;
import Results.EventResult;

import java.sql.Connection;
import java.util.ArrayList;

public class EventService {
    public EventResult service(String username) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            EventDAO eventDAO = new EventDAO(conn);
            ArrayList<Event> foundEvents = eventDAO.findMultiple(username);
            Event[] returnEvents = new Event[foundEvents.size()];
            for (int i = 0; i < returnEvents.length; ++i) {
                returnEvents[i] = foundEvents.get(i);
            }

            db.closeConnection(true);
            return new EventResult(returnEvents);
        }
        catch (DataAccessException e) {
            try {
                e.printStackTrace();
                db.closeConnection(false);
                return new EventResult(e.getMessage());
            }
            catch (DataAccessException f) {
                f.printStackTrace();
                return new EventResult(e.getMessage());
            }
        }
    }

    public EventResult service(String username, String eventID) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            EventDAO eventDAO = new EventDAO(conn);
            Event foundEvent = eventDAO.find(eventID);
            if (!foundEvent.getAssociatedUsername().equals(username)) {
                db.closeConnection(false);
                return new EventResult("Error: Not authorized to access requested user");
            }
            db.closeConnection(true);
            return new EventResult(foundEvent);
        }
        catch (DataAccessException e){
            try {
                db.closeConnection(false);
                return new EventResult(e.getMessage());
            }
            catch (DataAccessException f) {
                f.printStackTrace();
                return new EventResult(e.getMessage());
            }
        }
    }
}
