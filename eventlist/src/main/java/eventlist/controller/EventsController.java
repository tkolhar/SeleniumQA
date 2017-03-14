package eventlist.controller;

import com.google.gson.Gson;
import eventlist.data.EventsDAO;
import eventlist.model.Event;
import eventlist.model.EventPeriodType;
import eventlist.model.GetDataTablesRequestDTO;
import eventlist.model.GetDataTablesResponseDTO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * EventsController handles create/read/update/delete requests.
 */
@WebServlet(name = "EventsController", urlPatterns = {"/api/v1/events"})
public class EventsController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(EventsController.class.getName());

    @Resource(name = "jdbc/EventsDB")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("eventId") != null) {
            processReadSingle(request, response);
        } else {
            processReadList(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("eventId") != null && request.getParameter("eventId").trim() != "") {
            processUpdate(request, response);
        } else {
            processCreate(request, response);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processDelete(request, response);
    }

    private void processCreate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventDate = request.getParameter("eventDate");
        String eventType = request.getParameter("eventType");
        String eventSummary = request.getParameter("eventSummary");
        String eventMetric = request.getParameter("eventMetric");
        String eventDetails = request.getParameter("eventDetails");
       
        try (Connection conn = dataSource.getConnection()) {

            EventsDAO eventsData = new EventsDAO(conn);
            Event event = new Event();
            event.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(eventDate));
            event.setType(eventType);
            event.setSummary(eventSummary);
            event.setMetric(Integer.parseInt(eventMetric));
            event.setDetails(eventDetails);
            eventsData.addEvent(event);

            response.setContentType("text/plain");
            response.getWriter().write("Event created successfully");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Server Exception processing Create ", ex);
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Error creating event");
        }
    }

    private void processReadSingle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventId = request.getParameter("eventId");

        try (Connection conn = dataSource.getConnection()) {

            EventsDAO eventsData = new EventsDAO(conn);

            Event event = eventsData.GetEventDetails(Integer.parseInt(eventId));

            if (event == null) {
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Not found");
            } else {
                Gson gson = new Gson();
                String json = gson.toJson(event);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Server Exception handling GET /eventdetails", ex);
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Error reading event");
        }
    }

    private void processReadList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        String reqDate = request.getParameter("date");
        String reqPeriod = request.getParameter("period");

        String reqStart = request.getParameter("filter[pageIndex]");
        String reqLength = request.getParameter("filter[pageSize]");

        if (reqDate == null
                || reqPeriod == null
                || reqStart == null
                || reqLength == null) {
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Missing parameters detected");
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        LocalDate dt = formatter.parseLocalDate(reqDate);

        GetDataTablesRequestDTO dtRequest = new GetDataTablesRequestDTO();

        try (Connection conn = dataSource.getConnection()) {

            EventsDAO eventsData = new EventsDAO(conn);

            dtRequest.setStart(Integer.parseInt(reqStart) - 1);
            dtRequest.setLength(Integer.parseInt(reqLength));
            dtRequest.setDate(dt);
            EventPeriodType periodType = EventPeriodType.fromString(reqPeriod);

            if (periodType == null) {
                response.setContentType("text/plain");
                response.setStatus(500);
                response.getWriter().write("Unrecognized period type: " + reqPeriod);
                return;
            } else {
                dtRequest.setPeriod(periodType);
            }

            GetDataTablesResponseDTO eventsViewModel = eventsData.GetEventsForDataTable(dtRequest);
            String json = gson.toJson(eventsViewModel);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Server Exception handling Read List", ex);
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Error reading events");
        }
    }

    private void processUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventId = request.getParameter("eventId");
        String eventDate = request.getParameter("eventDate");
        String eventType = request.getParameter("eventType");
        String eventSummary = request.getParameter("eventSummary");
        String eventMetric = request.getParameter("eventMetric");
        String eventDetails = request.getParameter("eventDetails");

        try (Connection conn = dataSource.getConnection()) {

            EventsDAO eventsData = new EventsDAO(conn);
            Event event = new Event();
            event.setId(Integer.parseInt(eventId));
            event.setDate(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(eventDate));
            event.setType(eventType);
            event.setSummary(eventSummary);
            event.setMetric(Integer.parseInt(eventMetric));
            event.setDetails(eventDetails);
            eventsData.updateEvent(event);

            response.setContentType("text/plain");
            response.getWriter().write("Event updated successfully");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Server Exception processing Update ", ex);
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Error updating event");
        }
    }

    private void processDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String eventId = request.getParameter("eventId");
        try (Connection conn = dataSource.getConnection()) {

            EventsDAO eventsData = new EventsDAO(conn);

            eventsData.deleteEvent(Integer.parseInt(eventId));

            response.setContentType("text/plain");
            response.getWriter().write("Event deleted successfully");

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Server Exception processing Delete ", ex);
            response.setContentType("text/plain");
            response.setStatus(500);
            response.getWriter().write("Error deleting event");
        }
    }

}
