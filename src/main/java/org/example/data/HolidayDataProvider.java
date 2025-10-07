package org.example.data;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.model.Holiday;
import org.example.model.HolidaysRoot;
import org.example.util.CountryConstants;
import org.example.util.PathConstants;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Centralized data provider for holidays.
 * <p>
 * This class is used across all report types to ensure consistent
 * access to holiday data from the XML source.
 * </p>
 */
public class HolidayDataProvider {

    /**
     * Loads the full list of holidays for Italy and Moldova in 2021 from the XML file.
     * <p>
     * The XML file is defined in {@link PathConstants#HOLIDAYS_XML} and mapped
     * into {@link HolidaysRoot} using {@link XmlMapper}.
     * </p>
     *
     * @return a list of {@link Holiday} objects
     * @throws RuntimeException if the XML file is missing or cannot be parsed
     */
    public static List<Holiday> getAllHolidays() {
        try (InputStream is = HolidayDataProvider.class.getResourceAsStream(PathConstants.HOLIDAYS_XML)) {
            if (is == null) {
                throw new IllegalStateException("XML file not found: " + PathConstants.HOLIDAYS_XML);
            }

            XmlMapper xmlMapper = new XmlMapper();
            HolidaysRoot year = xmlMapper.readValue(is, HolidaysRoot.class);

            return year.getHolidays();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML", e);
        }
    }

    /**
     * Counts the number of holidays per month for each country.
     * <p>
     * The result is a map where the key is the country name and the value
     * is an {@code int[13]} array. Indexes 1–12 correspond to months,
     * index 0 is unused.
     * </p>
     *
     * @param holidays list of {@link Holiday} objects
     * @return a map of country → array of monthly holiday counts
     */
    public static Map<String, int[]> getMonthCountsByCountry(List<Holiday> holidays) {
        Map<String, int[]> counts = new HashMap<>();
        counts.put(CountryConstants.ITALY, new int[13]);
        counts.put(CountryConstants.MOLDOVA, new int[13]);

        for (Holiday holiday : holidays) {
            Integer month = holiday.getMonth();
            if (month != null) {
                int[] countryCounts = counts.get(holiday.getCountry());
                if (countryCounts != null) {
                    countryCounts[month]++;
                }
            }
        }
        return counts;
    }

    /**
     * Entry point for standalone execution.
     * <p>
     * Loads all holidays and prints them to the console.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        List<Holiday> holidays = getAllHolidays();
        holidays.forEach(System.out::println);
    }
}
