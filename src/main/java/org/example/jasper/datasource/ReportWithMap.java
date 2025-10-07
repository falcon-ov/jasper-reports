package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates a report using {@link JRMapCollectionDataSource}.
 * <p>
 * This class demonstrates how to transform a list of {@link Holiday} objects
 * into a collection of {@link Map} entries, which are then used as the data source
 * for filling a JasperReport template.
 * </p>
 */
public class ReportWithMap {

    /**
     * Generates a JasperReport using {@link JRMapCollectionDataSource}.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Retrieve holiday data from {@link HolidayDataProvider}</li>
     *     <li>Transform each {@link Holiday} into a {@link Map} with keys
     *         {@code country}, {@code name}, and {@code date}</li>
     *     <li>Wrap the collection of maps in a {@link JRMapCollectionDataSource}</li>
     *     <li>Fill the report template defined in {@link PathConstants#HOLIDAYS_JASPER}</li>
     *     <li>Export the report to PDF at {@link PathConstants#REPORT_MAP_PDF}</li>
     * </ol>
     *
     * @throws JRException if report generation fails
     */
    public static void generate() throws JRException {
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        List<Map<String, ?>> data = new ArrayList<>();

        for (Holiday holiday : holidays) {
            Map<String, Object> row = new HashMap<>();
            row.put("country", holiday.getCountry());
            row.put("name", holiday.getName());
            row.put("date", holiday.getDate());
            data.add(row);
        }

        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(data);

        JasperPrint print = JasperFillManager.fillReport(
                PathConstants.HOLIDAYS_JASPER,
                null,
                dataSource
        );

        JasperExportManager.exportReportToPdfFile(
                print,
                PathConstants.REPORT_MAP_PDF
        );

        System.out.println("Report created with JRMapCollectionDataSource: " + PathConstants.REPORT_MAP_PDF);
    }

    /**
     * Entry point for standalone execution.
     * <p>
     * Allows running the report generation directly from the command line.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try {
            generate();
        } catch (JRException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
