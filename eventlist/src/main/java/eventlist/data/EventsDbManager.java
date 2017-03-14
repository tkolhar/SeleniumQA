package eventlist.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EventsDbManager works with the CreateDatabaseListener to intialize the Events
 * database.
 */
public class EventsDbManager {

    private static final Logger logger = Logger.getLogger(EventsDbManager.class.getName());

    public static void CreateIfNotExists(Connection con, String sqlPath) {
        try {
            if (!schemaHasBeenInitialized(con)) {
                initializeSchema(con, sqlPath);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Determines if there is an "events" table in the current database.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    private static boolean schemaHasBeenInitialized(Connection con) throws SQLException {
        final DatabaseMetaData metaData = con.getMetaData();
        final ResultSet tablesResultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

        try {
            while (tablesResultSet.next()) {
                final String tableName = tablesResultSet.getString("TABLE_NAME");
                if (tableName != null && "events".equalsIgnoreCase(tableName)) {
                    return true;
                }
            }
        } finally {
            if (tablesResultSet != null) {
                tablesResultSet.close();
            }
        }
        return false;
    }

    private static void initializeSchema(Connection con, String sqlPath) {

        try {
            logger.info("initalizing Schema");
            String databaseSqlPath = sqlPath;
            String sql = new String(Files.readAllBytes(Paths.get(databaseSqlPath)));

            Statement statement = con.createStatement();
            statement.execute(sql);
            statement.execute("CREATE INDEX DateIndex ON Events(event_date)");
            logger.info("in-memory database created");
        } catch (SQLException asd) {
            logger.log(Level.SEVERE, "", asd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
