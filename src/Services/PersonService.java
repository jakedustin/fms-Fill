package Services;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import Models.Person;
import Results.PersonResult;

import java.sql.Connection;
import java.util.ArrayList;

public class PersonService {
    public PersonResult service(String username) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            PersonDAO personDAO = new PersonDAO(conn);
            ArrayList<Person> foundPersons = personDAO.findMultiple(username);
            Person[] returnPersons = new Person[foundPersons.size()];
            for (int i = 0; i < returnPersons.length; ++i) {
                returnPersons[i] = foundPersons.get(i);
            }

            db.closeConnection(true);
            return new PersonResult(returnPersons);
        }
        catch (DataAccessException e) {
            try {
                e.printStackTrace();
                db.closeConnection(false);
                return new PersonResult(e.getMessage());
            }
            catch (DataAccessException f) {
                f.printStackTrace();
                return new PersonResult(e.getMessage());
            }
        }
    }

    public PersonResult service(String username, String personID) {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            PersonDAO personDAO = new PersonDAO(conn);
            Person foundPerson = personDAO.find(personID);
            if (!foundPerson.getAssociatedUsername().equals(username)) {
                db.closeConnection(false);
                return new PersonResult("Error: Not authorized to access requested user");
            }
            db.closeConnection(true);
            return new PersonResult(foundPerson);
        }
        catch (DataAccessException e){
            try {
                db.closeConnection(false);
            }
            catch (DataAccessException f) {
                return new PersonResult(f.getMessage());
            }
            return new PersonResult(e.getMessage());
        }
    }
}
