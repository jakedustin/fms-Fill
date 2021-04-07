package DAOs;

import java.sql.*;

public class Database {
    private Connection conn;

    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            conn = DriverManager.getConnection(CONNECTION_URL);

            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        System.out.println("Opening connection");
        if (conn == null) {
            return openConnection();
        }
        else {
            return conn;
        }
    }

    public void closeConnection(boolean commit) throws DataAccessException {
        System.out.println("Attempting to close connection\n");
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to close connection.");
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void clearTables() throws DataAccessException {
        String[] tableName = new String[]{"USERS", "EVENTS", "PERSONS","AUTH_TOKEN"};
        try (Statement stmt = conn.createStatement()) {
            for (String s : tableName) {
                String sql = "DELETE FROM " + s;
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing tables. Error number: " +
                    e.getErrorCode());
        }
    }
}
