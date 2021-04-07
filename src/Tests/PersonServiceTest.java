package Tests;

import DAOs.DataAccessException;
import DAOs.Database;
import DAOs.PersonDAO;
import Models.Person;
import Results.PersonResult;
import Services.ClearService;
import Services.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class PersonServiceTest {
    Database db;
    Connection conn;
    PersonDAO personDAO;
    PersonService personService;

    Person person_1 = new Person("bilbo", "user", "bilbo", "baggins", "m", null, null, null);
    Person person_2 = new Person("frodo", "user", "frodo", "baggins", "m", null, null, null);

    @BeforeEach
    public void setup() {
        new ClearService().service();
        db = new Database();
        try {
            conn = db.getConnection();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        personDAO = new PersonDAO(conn);
        personService = new PersonService();
    }
    @AfterEach
    public void takedown() {
        try {
            db.closeConnection(false);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validSinglePersonRequestTest() {
        try {
            personDAO.insert(person_1);
            db.closeConnection(true);
            PersonResult personResult = personService.service("user", "bilbo");
            db.getConnection();
            personDAO = new PersonDAO(conn);
            Assertions.assertEquals(person_1.getPersonID(), personResult.getPersonID());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

    @Test
    public void invalidSinglePersonRequestTest() {
        try {
            personDAO.insert(person_1);
            db.closeConnection(true);
            PersonResult personResult =  personService.service("user", "bilboing");
            db.getConnection();
            personDAO = new PersonDAO(conn);
            Assertions.assertEquals("Invalid personID parameter", personResult.getMessage());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }
}