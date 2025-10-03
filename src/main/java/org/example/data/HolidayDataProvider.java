package org.example.data;

import org.example.model.Holiday;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
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
        List<Holiday> holidays = new ArrayList<>();

        try {
            InputStream is = HolidayDataProvider.class.getResourceAsStream("/MyDataBase.xml");
            if (is == null) {
                System.err.println("XML file not found: /MyDataBase.xml");
                return holidays;
            }

            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("holydays");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element elem = (Element) nodes.item(i);
                String country = elem.getElementsByTagName("COUNTRY").item(0).getTextContent();
                String date = elem.getElementsByTagName("DATE").item(0).getTextContent();
                String name = elem.getElementsByTagName("NAME").item(0).getTextContent();
                holidays.add(new Holiday(country, date, name));
            }
            System.out.println("Loaded " + holidays.size() + " holidays from XML");

        } catch (Exception e) {
            System.err.println("Error parsing XML: " + e.getMessage());
            e.printStackTrace();
        }

        return holidays;
    }

    /**
     * Возвращает тестовый набор данных для примеров с datasource (первые 2 из полного списка)
     */
    public static List<Holiday> getTestHolidays() {
        List<Holiday> all = getAllHolidays();
        if (all.size() >= 2) {
            return all.subList(0, 2);
        }
        return new ArrayList<>();
    }

    /**
     * Возвращает праздники только для указанной страны
     */
    public static List<Holiday> getHolidaysByCountry(String country) {
        return getAllHolidays().stream()
                .filter(h -> h.getCountry().equalsIgnoreCase(country))
                .toList();
    }

    /**
     * Подсчитывает количество праздников по месяцам для каждой страны.
     * @param holidays Список праздников
     * @return Map<Country, int[13]> где индекс - месяц (1-12)
     */
    public static Map<String, int[]> getMonthCountsByCountry(List<Holiday> holidays) {
        Map<String, int[]> counts = new HashMap<>();
        counts.put("Italia", new int[13]);
        counts.put("Moldavia", new int[13]);

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
}