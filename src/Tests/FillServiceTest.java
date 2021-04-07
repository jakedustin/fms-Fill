package Tests;

import Results.FillResult;
import Services.ClearService;
import Services.FillService;
import org.junit.jupiter.api.*;
import DAOs.*;
import Models.*;

import java.sql.Connection;


class FillServiceTest {
    Database db = new Database();
    Connection conn;
    FillService fillService;
    UserDAO userDAO;

    @BeforeEach
    void setUp() {
        try {
            fillService = new FillService();
            conn = db.getConnection();
            userDAO = new UserDAO(conn);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            db.closeConnection(false);
            new ClearService().service();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Valid Fill Service")
    void serviceTest() {

    }

    User testUser1 = new User("lalaki", "password", "email@email.com", "first", "last", "m");
    Person testPerson1 = new Person("lil boi","lalaki", "user", "mcuserface", "m", null, null, null);

}