package DAOs;

import java.sql.*;
import java.util.ArrayList;

import Models.*;

public class EventDAO {
    private Connection conn;

    public EventDAO(Connection conn) { this.conn = conn; }

    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO EVENTS (EventID, AssociatedUsername, PersonID, Latitude, Longitude, Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQLException thrown. Error code " + e.getErrorCode());
        } catch (Exception f) {
            f.printStackTrace();
            throw new DataAccessException("Caught some other exception.");
        }
    }

    public void insertMultiple(ArrayList<Event> events) throws DataAccessException {
        for (Event event : events) {
            insert(event);
        }
    }

    public Event find(String eventID) throws DataAccessException {
        Event event;
        String sql = "SELECT * FROM EVENTS WHERE EventID = ?";
        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                event = new Event(
                        rs.getString("EventID"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"),
                        rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
            throw new DataAccessException("Invalid eventID parameter");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Internal server error");
        }
    }

    public ArrayList<Event> findMultiple(String associatedUsername) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs;

        String sql = "SELECT * FROM EVENTS WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            while (rs.next()) {
                events.add(new Event(
                        rs.getString("EventID"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("PersonID"),
                        rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year")));
            }

            return events;
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }

    public int deleteMultiple(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM EVENTS WHERE AssociatedUsername = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }
}
