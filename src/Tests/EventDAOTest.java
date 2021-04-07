package Tests;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;

import Models.*;
import DAOs.*;

class EventDAOTest {
    Event event_1 = new Event("11111", "user", "user", 1, 2, "USA", "SLC", "kasal", 2010);
    Event event_2 = new Event("wowowow", "user", "userwife", 1, 2, "USA", "Provo", "birth", 1999);
    Event event_3 = new Event("oh_you", "patrick", "userwife", 1, 2, "USA", "Provo", "death", 2060);

    Database db;
    Connection conn;

    @BeforeEach
    public void setup() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch(DataAccessException e) {
            Assertions.fail();
            e.printStackTrace();
        }
    }

    @AfterEach
    public void takedown() {
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //Test 1
    // Insert
    // Find
    //Test should insert a valid event object into the database, then return the same event
    @Test
    public void insertOneEventTest() {
        try {
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.insert(event_1);
            Assertions.assertEquals(eventDAO.find(event_1.getEventID()), event_1);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    //Test 2
    // Insert
    // Find (negative)
    //Test should insert an event with bad parameters and assert that the proper exception is thrown
    @Test
    public void badEventIDTest() {
        try {
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.insert(event_1);
            Assertions.assertThrows(DataAccessException.class, () -> eventDAO.find("user"));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    //Test 3
    // Insert
    // Find (negative)
    //Test should attempt to access events tied to a username that does not exist
    @Test
    @DisplayName("Invalid Username Test")
    public void badUsernameTest() {
        try {
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.insert(event_1);
            Assertions.assertThrows(DataAccessException.class, () -> eventDAO.find("bad_user"));
        } catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
        }
    }

    //Test 4
    // Insert
    // Find Multiple
    //Test should pull multiple events tied to a single user
    @Test
    @DisplayName("Insert Multiple Valid Test")
    public void multipleValidTest() {
        try {
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.insert(event_1);
            eventDAO.insert(event_2);
            eventDAO.insert(event_3);
            //Testing to ensure there are two events returned
            ArrayList<Event> findResult = eventDAO.findMultiple(event_1.getAssociatedUsername());
            Assertions.assertEquals(2, findResult.size());
            //Testing to ensure that there is one event returned
            findResult = eventDAO.findMultiple(event_3.getAssociatedUsername());
            Assertions.assertEquals(1, findResult.size());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    //Test 5
    // Insert
    // Find Multiple
    // Delete Multiple
    // Find Multiple (negative)
    @Test
    @DisplayName("Delete Multiple Valid Test")
    public void deleteMultipleValidTest() {
        try {
            EventDAO eventDAO = new EventDAO(conn);
            eventDAO.insert(event_1);
            eventDAO.insert(event_2);
            eventDAO.insert(event_3);
            //Testing to ensure there are two events returned
            ArrayList<Event> findResult = eventDAO.findMultiple(event_1.getAssociatedUsername());
            Assertions.assertEquals(2, findResult.size());
            //Testing to ensure that there is one event returned
            findResult = eventDAO.findMultiple(event_3.getAssociatedUsername());
            Assertions.assertEquals(1, findResult.size());
            //Testing the delete function - deleting events for the "user" user ID should return a value of 2
            Assertions.assertEquals(2, eventDAO.deleteMultiple(event_1.getAssociatedUsername()));
            Assertions.assertEquals(0, eventDAO.findMultiple(event_1.getAssociatedUsername()).size());
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e);
        }
    }
}