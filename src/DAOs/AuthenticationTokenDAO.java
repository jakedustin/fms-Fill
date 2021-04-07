package DAOs;

import Models.*;
import java.sql.*;

public class AuthenticationTokenDAO {

    private final Connection conn;

    public AuthenticationTokenDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(AuthenticationToken authToken) throws DataAccessException {
        String sql = "INSERT INTO AUTH_TOKEN (Username, AuthToken) VALUES(?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getUsername());
            stmt.setString(2, authToken.getUniqueID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("SQLException thrown. Error code " + e.getErrorCode());
        } catch (NullPointerException f) {
            throw new DataAccessException("Error: authtoken cannot be null");
        }
    }

    public AuthenticationToken find(String authToken) throws DataAccessException {
        AuthenticationToken token;
        String sql = "SELECT * FROM AUTH_TOKEN WHERE AuthToken = ?";
        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();

            if (rs.next()) {
                token = new AuthenticationToken(
                        rs.getString("Username"),
                        rs.getString("AuthToken"));
                return token;
            }
            throw new DataAccessException("Error: No matching users found.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQLException thrown. Error code " + e.getErrorCode());
        }
    }
}
