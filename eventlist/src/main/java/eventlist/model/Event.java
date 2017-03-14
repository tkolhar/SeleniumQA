package eventlist.model;

import java.util.Date;

/**
 * An Event represents a single event in our model.
 *
 */
public class Event {

    private int eventId;
    private Date eventDate;
    private String eventType;
    private String eventSummary;
    private int eventMetric;
    private String eventDetails;

    public int getId() {
        return eventId;
    }

    public void setId(int id){
        this.eventId = id;
    }
    public Date getDate() {
        return eventDate;
    }

    public void setDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getType() {
        return eventType;
    }

    public void setType(String eventType) {
        this.eventType = eventType;
    }

    public String getSummary() {
        return eventSummary;
    }

    public void setSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public int getMetric() {
        return eventMetric;
    }

    public void setMetric(int eventMetric) {
        this.eventMetric = eventMetric;
    }

    public String getDetails() {
        return eventDetails;
    }

    public void setDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

}
