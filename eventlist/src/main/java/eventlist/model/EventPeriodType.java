package eventlist.model;

/**
 * EventPeriodType represents the possible time periods recognized by the
 * system.
 *
 */
public enum EventPeriodType {
    WEEK,
    MONTH,
    QUARTER;

    /**
     * Returns EventPeriodType from given string.
     *
     * @param text
     * @return
     */
    public static EventPeriodType fromString(String text) {
        if (text != null) {
            for (EventPeriodType b : EventPeriodType.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        return null;
    }
}
