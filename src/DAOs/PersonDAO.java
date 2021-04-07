package DAOs;

import java.sql.*;
import java.util.ArrayList;

import Models.Person;

public class PersonDAO {

    private final Connection conn;

    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO PERSONS (PersonID, AssociatedUsername, FirstName, LastName, Gender, FatherID, " +
                "MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        } catch (NullPointerException n) {
            throw new DataAccessException("Error: Person cannot be null.");
        }
    }

    public void insertMultiple(ArrayList<Person> persons) throws DataAccessException {
            for (Person person : persons) {
                insert(person);
            }
    }

    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;

        String sql = "SELECT * FROM PERSONS WHERE PersonID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                person = new Person(
                        rs.getString("PersonID"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("FatherID"),
                        rs.getString("MotherID"),
                        rs.getString("SpouseID"));
                return person;
            }

            throw new DataAccessException("Invalid personID parameter");
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }

    public ArrayList<Person> findMultiple(String associatedUsername) throws DataAccessException {
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet rs;

        String sql = "SELECT * FROM PERSONS WHERE AssociatedUsername = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Person newPerson = new Person(
                        rs.getString("PersonID"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("FatherID"),
                        rs.getString("MotherID"),
                        rs.getString("SpouseID"));
                persons.add(newPerson);
            }

            return persons;
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }

    public boolean delete(String personID) throws DataAccessException {
        String sql = "DELETE FROM PERSONS WHERE PersonID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            if (stmt.executeUpdate() < 1) {
                throw new DataAccessException("Error: No matching records found");
            }
            return true;
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }

    public int deleteMultiple(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM PERSONS WHERE AssociatedUsername = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Internal server error");
        }
    }
}
