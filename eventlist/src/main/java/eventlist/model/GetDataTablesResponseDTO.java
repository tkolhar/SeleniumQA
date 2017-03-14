package eventlist.model;

import java.util.List;

/**
 * GetDataTablesResponseDTO encapsulates the response sent to the client for a
 * request for paged events data.
 *
 */
public class GetDataTablesResponseDTO {

    private int itemsCount;
    private List<Event> data;

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object other) {

        boolean result = false;
        if (other instanceof GetDataTablesResponseDTO) {
            GetDataTablesResponseDTO that = (GetDataTablesResponseDTO) other;
            result = (this.getItemsCount() == that.getItemsCount());

            List<Event> thisData = this.getData();
            List<Event> thatData = that.getData();
            if (thisData != null && thatData != null) {
                result = thisData.equals(thatData);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (41 * (41 + getItemsCount()));
    }
}
