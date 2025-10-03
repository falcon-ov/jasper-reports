package org.example2.jrdatasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import java.util.*;

public class ReportWithMap {
    public static void main(String[] args) throws JRException {
        String jasperFile = "src/main/resources/HolidaysReport.jasper";

        // создаём список Map
        List<Map<String, ?>> data = new ArrayList<>();


        Map<String, Object> row1 = new HashMap<>();
        row1.put("country", "Moldova");
        row1.put("name", "New Year");
        row1.put("date", "01-01-2021");
        data.add(row1);

        Map<String, Object> row2 = new HashMap<>();
        row2.put("country", "Italy");
        row2.put("name", "Christmas");
        row2.put("date", "07-01-2021");
        data.add(row2);


        JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(data);

        JasperPrint print = JasperFillManager.fillReport(jasperFile, null, dataSource);
        JasperExportManager.exportReportToPdfFile(print, "output/Report_Map.pdf");

        System.out.println("Report created with JRMapCollectionDataSource");
    }
}
