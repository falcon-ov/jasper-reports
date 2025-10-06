package org.example.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class HolidaysRoot {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "holydays")
    private List<Holiday> holidays;

    public List<Holiday> getHolidays() { return holidays; }
    public void setHolidays(List<Holiday> holidays) { this.holidays = holidays; }
}
