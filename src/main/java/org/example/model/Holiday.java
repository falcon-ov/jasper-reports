package org.example.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Модель праздника.
 */
public class Holiday {
    private String name;
    private String date;
    private String country;

    public Holiday(String country, String date, String name) {
        this.name = name;
        this.date = date;
        this.country = country;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

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

    @Override
    public String toString() {
        return "Holiday{country='" + country + "', date='" + date + "', name='" + name + "'}";
    }
}