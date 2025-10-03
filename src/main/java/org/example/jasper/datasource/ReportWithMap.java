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
 * Генерация отчета с использованием JRMapCollectionDataSource.
 */
public class ReportWithMap {

    public static void generate() throws JRException {
        List<Holiday> holidays = HolidayDataProvider.getTestHolidays();
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

    public static void main(String[] args) {
        try {
            generate();
        } catch (JRException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}