package eventlist.etl;

public class EventRecord {

    private String event_date;
    private String event_type;
    private String event_summary;
    private String event_size;
    private String event_details;

    public EventRecord(String event_date, 
            String event_type, 
            String event_summary, 
            String event_size, 
            String event_details) {
        this.setEvent_date(event_date);
        this.setEvent_type(event_type);
        this.setEvent_summary(event_summary);
        this.setEvent_size(event_size);
        this.setEvent_details(event_details);
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_summary() {
        return event_summary;
    }

    public void setEvent_summary(String event_summary) {
        this.event_summary = event_summary;
    }

    public String getEvent_size() {
        return event_size;
    }

    public void setEvent_size(String event_size) {
        this.event_size = event_size;
    }

    public String getEvent_details() {
        return event_details;
    }

    public void setEvent_details(String event_details) {
        this.event_details = event_details;
    }

}
