package eventlist.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import eventlist.etl.EventRecord;
import eventlist.etl.JsonStreamLoader;
import java.io.PrintWriter;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * UploadController handles uploaded JSON data files and populates the event
 * database with it.
 *
 */
@WebServlet("/upload")
@MultipartConfig
public class UploadController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UploadController.class.getName());

    @Resource(name = "jdbc/EventsDB")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("file");
        PreparedStatement ps = null;

        try {

            InputStream fileContent = filePart.getInputStream();
            JsonStreamLoader loader = new JsonStreamLoader(fileContent);
            List<EventRecord> list = new LinkedList<>();

            int[] results = null;

            Connection con = dataSource.getConnection();

            PrintWriter out = response.getWriter();

            EventRecord event = loader.ReadNext();
            int batchSize = 50;

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            int numEvents = 0;
            
            // -- Avoid loading all the ebents into memory, insert a batch at a time
            while (event != null) {
                list.add(event);
                event = loader.ReadNext();
                if (list.size() == batchSize) {
                    results = insertEventsBatch(list, con);
                    numEvents += results.length;
                    out.write("Insert batch: " + results.length + "\n");
                    list.clear();
                }
            }
            
            // -- Insert any remaining events left in the list
            if(list.size() > 0){
                results = insertEventsBatch(list, con);
                numEvents += results.length;
                out.write("Insert batch: " + results.length + "\n");
                list.clear();
            }
            
            loader.Close();
            
            out.write("List size: " + numEvents + "\n");
            out.write("File length:" + Integer.toString(request.getContentLength()) + "\n");

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.info("SQLException in closing PreparedStatement");
            }
        }
    }

    private int[] insertEventsBatch(List<EventRecord> list, Connection con) throws IOException, SQLException, NumberFormatException {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        PreparedStatement ps = con.prepareStatement("insert into events(event_date, event_type, event_summary, event_size, event_detail) values (?,?,?,?,?)");

        for (EventRecord event : list) {

            LocalDate dt = formatter.parseLocalDate(event.getEvent_date());
            java.util.Date utilDate = dt.toDate();

            ps.setDate(1, new java.sql.Date(utilDate.getTime()));
            ps.setString(2, event.getEvent_type());
            ps.setString(3, event.getEvent_summary());
            ps.setInt(4, Integer.parseInt(event.getEvent_size()));
            Clob clob = con.createClob();
            clob.setString(1, event.getEvent_details());
            ps.setClob(5, clob);
            ps.addBatch();
        }

        return ps.executeBatch();
    }
}
