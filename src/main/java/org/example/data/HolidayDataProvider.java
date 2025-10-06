package org.example.data;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.model.Holiday;
import org.example.model.HolidaysRoot;
import org.example.util.CountryConstants;
import org.example.util.PathConstants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Централизованный провайдер данных о праздниках.
 * Используется во всех типах отчетов для обеспечения консистентности данных.
 */
public class HolidayDataProvider {

    /**
     * Возвращает полный список праздников Италии и Молдовы за 2021 год из XML
     */
    public static List<Holiday> getAllHolidays() {
        try (InputStream is = HolidayDataProvider.class.getResourceAsStream(PathConstants.HOLIDAYS_XML)) {
            if (is == null) {
                throw new IllegalStateException("XML file not found: /MyDataBase.xml");
            }

            XmlMapper xmlMapper = new XmlMapper();
            HolidaysRoot year = xmlMapper.readValue(is, HolidaysRoot.class);

            return year.getHolidays();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML", e);
        }
    }

    /**
     * Подсчитывает количество праздников по месяцам для каждой страны.
     * @param holidays Список праздников
     * @return Map<Country, int[13]> где индекс - месяц (1-12)
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
    public static void main(String[] args) {
        List<Holiday> holidays = getAllHolidays();
        holidays.forEach(System.out::println);
    }
}