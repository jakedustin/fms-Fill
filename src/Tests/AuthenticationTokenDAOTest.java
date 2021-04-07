package Tests;

import org.junit.jupiter.api.*;
import Models.*;
import DAOs.*;

import java.sql.*;

class AuthenticationTokenDAOTest {

    AuthenticationToken auth_1 = new AuthenticationToken("user");
    AuthenticationToken auth_2 = new AuthenticationToken("user");
    AuthenticationToken auth_3 = new AuthenticationToken("user");
    AuthenticationToken fakeAuth = new AuthenticationToken("user", "hoopty-scoopty");
    Database db;
    Connection conn;

    @BeforeEach
    public void setup() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
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
    // Inserts a valid auth token into the database, then pulls it back out
    @Test
    @DisplayName("Valid Insert Test")
    public void insertTest() {
        try {
            AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            authenticationTokenDAO.insert(auth_1);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_1.getAuthtoken()), auth_1);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    //Test 2
    // Insert
    // Find
    // Find (negative)
    @Test
    @DisplayName("Valid Find Test")
    public void findTest() {
        try {
            AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            authenticationTokenDAO.insert(auth_1);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_1.getAuthtoken()), auth_1);

            authenticationTokenDAO.insert(auth_2);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_2.getAuthtoken()), auth_2);

            authenticationTokenDAO.insert(auth_3);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_3.getAuthtoken()), auth_3);

            Assertions.assertThrows(DataAccessException.class, () -> authenticationTokenDAO.find(fakeAuth.getAuthtoken()));
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    //Test 3
    // Insert
    // Find (negative)
    @Test
    @DisplayName("Invalid Find Test")
    public void badFindTest() {
        try {
            AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            authenticationTokenDAO.insert(auth_1);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_1.getAuthtoken()), auth_1);

            authenticationTokenDAO.insert(auth_2);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_2.getAuthtoken()), auth_2);

            Assertions.assertThrows(DataAccessException.class, () -> authenticationTokenDAO.find(auth_3.getAuthtoken()));

            authenticationTokenDAO.insert(auth_3);
            Assertions.assertEquals(authenticationTokenDAO.find(auth_3.getAuthtoken()), auth_3);

            Assertions.assertThrows(DataAccessException.class, () -> authenticationTokenDAO.find(fakeAuth.getAuthtoken()));

        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    //Test 4
    // Insert (negative)
    @Test
    @DisplayName("Null Auth Insertion Test")
    public void nullInsertTest() {
        AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
        AuthenticationToken nullToken = null;
        Assertions.assertThrows(DataAccessException.class, () -> authenticationTokenDAO.insert(nullToken));
    }

}