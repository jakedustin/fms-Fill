package Services;

import DAOs.*;
import Models.*;
import Requests.RegisterRequest;
import Results.RegisterResult;
import java.sql.*;

public class RegisterService {

    public RegisterResult register(RegisterRequest request) {
        Database db = new Database();
        boolean success = false;

        try {
            Connection conn = db.getConnection();
            User user = new User(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender());

            UserDAO userDAO = new UserDAO(conn);
            AuthenticationToken authenticationToken = userDAO.insert(user);

            db.closeConnection(true);
            return new RegisterResult(user, authenticationToken);
        } catch (DataAccessException e) {
            e.printStackTrace();
            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                return new RegisterResult(f.getMessage());
            }
            return new RegisterResult(e.getMessage());
        }
    }
}
