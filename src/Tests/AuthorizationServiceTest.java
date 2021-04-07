package Tests;

import Services.AuthorizationService;
import Models.AuthenticationToken;
import DAOs.*;

import Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class AuthorizationServiceTest {
    Database db;
    Connection conn;
    AuthenticationTokenDAO authenticationTokenDAO;
    AuthorizationService service;

    AuthenticationToken auth_1 = new AuthenticationToken("user", "banana");
    AuthenticationToken auth_2 = new AuthenticationToken("user", "orange");
    AuthenticationToken auth_3 = new AuthenticationToken("not_user", "monkeys");

    @BeforeEach
    public void setup() {
        db = new Database();

        try {
            conn = db.getConnection();
            authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            service = new AuthorizationService();
            new ClearService().service();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
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
    public void authenticationDifferentiationTest() {
        try {
            authenticationTokenDAO.insert(auth_1);
            // test to make sure that (1) the auth token has been inserted and (2) the auth tokens are being
            // differentiated by the token, not the username
            Assertions.assertEquals(auth_1, authenticationTokenDAO.find(auth_1.getAuthtoken()));
            DataAccessException e = Assertions.assertThrows(DataAccessException.class, () -> authenticationTokenDAO.find(auth_2.getAuthtoken()));
            Assertions.assertEquals("Error: No matching users found.", e.getMessage());
        }
        catch (DataAccessException e) {
            Assertions.fail(e);
            e.printStackTrace();
        }
    }

    @Test
    public void validAuthtokenTest() {
        try {
            // service should return correct username when provided the linked authtoken
            authenticationTokenDAO.insert(auth_1);
            db.closeConnection(true);
            Assertions.assertEquals(auth_1.getUsername(), service.service(auth_1.getAuthtoken()).getUsername());
            conn = db.getConnection();

            // since both auth_1 and auth_2 have the same username, their authtokens should return the same string
            authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            authenticationTokenDAO.insert(auth_2);
            db.closeConnection(true);
            Assertions.assertEquals(auth_1.getUsername(), service.service(auth_1.getAuthtoken()).getUsername());
            conn = db.getConnection();
        }
        catch (DataAccessException e) {
            Assertions.fail(e.getMessage());
            e.printStackTrace();
        }
    }

}