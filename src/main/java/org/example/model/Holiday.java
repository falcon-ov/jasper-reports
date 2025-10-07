package org.example.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a holiday entity mapped from XML.
 * <p>
 * This class is used by Jackson XML to deserialize holiday data
 * from the XML structure into Java objects.
 * </p>
 */
public class Holiday {

    @JacksonXmlProperty(localName = "NAME")
    private String name;

    @JacksonXmlProperty(localName = "DATE")
    private String date;

    @JacksonXmlProperty(localName = "COUNTRY")
    private String country;

    /**
     * Default no-args constructor required by Jackson.
     */
    public Holiday() {}

    /**
     * Constructs a holiday with the given parameters.
     *
     * @param country the country where the holiday is celebrated
     * @param date    the date of the holiday in {@code dd/MM/yyyy} format
     * @param name    the name of the holiday
     */
    public Holiday(String country, String date, String name) {
        this.name = name;
        this.date = date;
        this.country = country;
    }

    /** @return the holiday name */
    public String getName() { return name; }

    /** @param name the holiday name */
    public void setName(String name) { this.name = name; }

    /** @return the holiday date as a string */
    public String getDate() { return date; }

    /** @param date the holiday date in {@code dd/MM/yyyy} format */
    public void setDate(String date) { this.date = date; }

    /** @return the country where the holiday is celebrated */
    public String getCountry() { return country; }

    /** @param country the country where the holiday is celebrated */
    public void setCountry(String country) { this.country = country; }

    /**
     * Extracts the month number from the holiday date.
     *
     * @return the month value (1–12), or {@code null} if parsing fails
     */
    public Integer getMonth() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(date).toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .getMonthValue();
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + date);
            return null;
        }
    }

    /**
     * Returns a string representation of the holiday.
     *
     * @return a string containing country, date, and name
     */
    @Override
    public String toString() {
        return "Holiday{country='" + country + "', date='" + date + "', name='" + name + "'}";
    }
}
