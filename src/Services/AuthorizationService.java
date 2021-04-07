package Services;

import DAOs.*;
import Models.AuthenticationToken;
import Results.AuthorizationResult;

import java.sql.Connection;

public class AuthorizationService {
    public AuthorizationResult service(String authtoken) {
        Database db = null;
        try {
            db = new Database();
            Connection conn = db.getConnection();
            AuthenticationTokenDAO authenticationTokenDAO = new AuthenticationTokenDAO(conn);
            AuthenticationToken authReturn = authenticationTokenDAO.find(authtoken);

            db.closeConnection(true);
            return new AuthorizationResult(true, authReturn.getUsername());
        }
        catch (DataAccessException e) {
            try {
                db.closeConnection(true);
                return new AuthorizationResult(false, e.getMessage());
            } catch (DataAccessException ignored) {
                return null;
            }
        }
    }
}
