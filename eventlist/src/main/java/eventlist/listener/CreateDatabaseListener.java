package eventlist.listener;

import eventlist.data.EventsDbManager;
import java.sql.*;
import java.util.logging.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * CreateDatabaseListener is responsible for creating the events database if it
 * doesn't already exist.
 *
 */
public class CreateDatabaseListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(CreateDatabaseListener.class.getName());
    private static final String JDBC_DB_NAME = "eventsDatabase";
    private static final String JDBC_CONN_STR = "jdbc:derby:" + JDBC_DB_NAME;

    private ServletContext servletContext = null;

    /**
     *
     * @param servletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();

        Connection con = null;
        try {
            logger.info("Loading Derby DB Driver...");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection(JDBC_CONN_STR + ";create=true");
            String sqlPath = servletContext.getRealPath("/WEB-INF/database.sql");
            EventsDbManager.CreateIfNotExists(con, sqlPath);

        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Could not load Derby Embedded Driver!", e);
        } catch (SQLException sqle) {
            logger.log(Level.SEVERE, "Fatal Database Error!", sqle);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("Servlet Context Destroyed");
        try {
            logger.info("Shutting down Derby DB...");
            DriverManager.getConnection(JDBC_CONN_STR + ";shutdown=true");
        } catch (SQLException sqle) {
            if (sqle.getMessage().equals("Database '" + JDBC_DB_NAME + "' shutdown.")) {
                logger.info("Derby DB Shutdown successfully!");
            } else {
                throw new RuntimeException("An error occurred shutting down the Derby instance!", sqle);
            }
        }

    }
}
