package eventlist.data;

import eventlist.model.Event;
import eventlist.model.EventPeriodType;
import eventlist.model.GetDataTablesRequestDTO;
import eventlist.model.GetDataTablesResponseDTO;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalDate;

/**
 * EventsDAO retrieves DTOs from the events database.
 *
 *
 */
public class EventsDAO {

    private final Connection conn;

    public EventsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieve the paged event list data.
     *
     * @param request
     * @return
     */
    public GetDataTablesResponseDTO GetEventsForDataTable(GetDataTablesRequestDTO request) throws SQLException {
        if (!validateRequest(request)) {
            return null;
        }

        LocalDate startDate = request.getDate();
        EventPeriodType periodType = request.getPeriod();

        int start = request.getStart();
        int length = request.getLength();

        String sql = "select event_date, event_type, event_summary, event_size, id "
                + "from events where event_date >= ? AND event_date <= ?"
                + "ORDER BY event_date offset ? rows fetch first ? rows only";

        List<Event> list = new LinkedList<Event>();
        LocalDate endDate = getEndDate(periodType, startDate);

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(startDate.toDate().getTime()));

            ps.setDate(2, new java.sql.Date(endDate.toDate().getTime()));
            ps.setInt(3, start);
            ps.setInt(4, length);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Event evt = new Event();

                    evt.setDate(rs.getDate(1));
                    evt.setType(rs.getString(2));
                    evt.setSummary(rs.getString(3));
                    evt.setMetric(rs.getInt(4));
                    evt.setId(rs.getInt(5));

                    list.add(evt);
                }
                rs.close();
            }
        }
        // -- Total query
        int totalRows = 0;
        sql = "select count(*) from events where event_date >= ? AND event_date <= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(startDate.toDate().getTime()));
            ps.setDate(2, new java.sql.Date(endDate.toDate().getTime()));
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    totalRows = rs.getInt(1);
                }
            }
        }
        GetDataTablesResponseDTO eventsViewModel = new GetDataTablesResponseDTO();
        eventsViewModel.setData(list);
        eventsViewModel.setItemsCount(totalRows);
        return eventsViewModel;

    }

    private boolean validateRequest(GetDataTablesRequestDTO request) {
        return !(request == null
                || request.getDate() == null
                || request.getPeriod() == null
                || request.getStart() < 0
                || request.getLength() <= 0);
    }

    private LocalDate getEndDate(EventPeriodType periodType, LocalDate startDate) {
        LocalDate endDate = null;
        if (null != periodType) {
            switch (periodType) {
                case WEEK:
                    endDate = startDate.plusWeeks(1);
                    break;
                case MONTH:
                    endDate = startDate.plusMonths(1);
                    break;
                case QUARTER:
                    endDate = startDate.plusMonths(3);
                    break;
                default:
                    break;
            }
        }
        if (endDate != null) {
            endDate = endDate.plusDays(-1);
        }
        return endDate;
    }

    /**
     * Retrieve the event details for the given event id.
     *
     * @param id
     * @return
     */
    public Event GetEventDetails(int id) throws SQLException {

        String sql = "select event_date, event_type, event_summary, event_size, id, event_detail "
                + "from events where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Event evt = new Event();

                    evt.setDate(rs.getDate(1));
                    evt.setType(rs.getString(2));
                    evt.setSummary(rs.getString(3));
                    evt.setMetric(rs.getInt(4));
                    evt.setId(rs.getInt(5));
                    Clob clob = rs.getClob(6);
                    evt.setDetails(clob.getSubString(1, (int) clob.length()));
                    return evt;
                }
            }
        }

        return null;
    }

    public void addEvent(Event event) throws SQLException {

        String sql = "insert into events(event_date, event_type, event_summary, event_size, event_detail) values (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(event.getDate().getTime()));
            ps.setString(2, event.getType());
            ps.setString(3, event.getSummary());
            ps.setInt(4, event.getMetric());
            Clob clob = conn.createClob();
            clob.setString(1, event.getDetails());
            ps.setClob(5, clob);

            ps.execute();
        }
    }

    public void updateEvent(Event event) throws SQLException {
        String sql = "update events "
                + "set event_date = ?, event_type= ? , event_summary = ?, event_size = ?, event_detail = ? "
                + " where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, new java.sql.Date(event.getDate().getTime()));
            ps.setString(2, event.getType());
            ps.setString(3, event.getSummary());
            ps.setInt(4, event.getMetric());
            Clob clob = conn.createClob();
            clob.setString(1, event.getDetails());
            ps.setClob(5, clob);
            ps.setInt(6, event.getId());
            ps.execute();
        }
    }

    public void deleteEvent(int eventId) throws SQLException {
        String sql = "delete from events where id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

              ps.setInt(1, eventId);

            ps.execute();
        }
    }
}
