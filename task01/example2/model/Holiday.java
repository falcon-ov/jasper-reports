package org.example2.model;

import java.text.SimpleDateFormat;

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
    public String getDate() { return date; }
    public String getCountry() { return country; }

    public Integer getMonth() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(date).toInstant()
                    .atZone(java.time.ZoneId.systemDefault())
                    .getMonthValue();
        } catch (Exception e) {
            return null;
        }
    }

}
