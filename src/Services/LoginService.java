package Services;

import DAOs.*;
import Models.AuthenticationToken;
import Models.User;
import Requests.LoginRequest;
import Results.LoginResult;

import java.sql.Connection;

public class LoginService {
    public LoginResult service(LoginRequest loginRequest) {
        Database db = new Database();

        try {
            Connection conn = db.getConnection();
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            UserDAO userDAO = new UserDAO(conn);
            User foundUser = userDAO.find(username);

            if (password.equals(foundUser.getPassword())) {
                AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
                AuthenticationToken auth = new AuthenticationToken(username);

                authenticationTokenDAO.insert(auth);

                db.closeConnection(true);
                return new LoginResult(auth.getAuthtoken(), foundUser.getUsername(), foundUser.getPersonID(), true);
            }
            else {
                db.closeConnection(false);
                return new LoginResult("Error: Request property missing or has invalid value", false);
            }
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
                return new LoginResult(e.getMessage(), false);
            } catch (DataAccessException d) {
                return new LoginResult(e.getMessage(), false);
            }
        }
    }
}
