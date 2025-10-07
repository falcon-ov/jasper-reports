package org.example.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Root element for the holidays XML structure.
 * <p>
 * This class is used by Jackson XML to map the root of the XML document
 * into a list of {@link Holiday} objects.
 * </p>
 */
public class HolidaysRoot {

    /**
     * List of holidays parsed from the XML.
     * <p>
     * The {@link JacksonXmlElementWrapper} with {@code useWrapping = false}
     * ensures that each {@code <holiday>} element is mapped directly
     * without an additional wrapping element.
     * </p>
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "holydays")
    private List<Holiday> holidays;

    /**
     * Returns the list of holidays.
     *
     * @return list of {@link Holiday} objects
     */
    public List<Holiday> getHolidays() {
        return holidays;
    }

    /**
     * Sets the list of holidays.
     *
     * @param holidays list of {@link Holiday} objects
     */
    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }
}
