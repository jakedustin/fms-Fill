package Services;

import DAOs.DataAccessException;
import DAOs.Database;
import Results.ClearResult;

public class ClearService {

    public ClearResult service() {
        Database db = new Database();
        try {
            db.getConnection();
            db.clearTables();

            db.closeConnection(true);

            return new ClearResult("Clear succeeded", true);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException d) {
                d.printStackTrace();
            }
            return new ClearResult("Error: Internal server error", false);
        }
    }
}
