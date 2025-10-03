package org.example2.jrdatasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example2.model.Holiday;

import java.util.*;

public class ReportWithBean {
    public static void main(String[] args) throws JRException {
        String jasperFile = "src/main/resources/HolidaysReport.jasper";

        List<Holiday> holidays = Arrays.asList(
                new Holiday("Moldova", "01-01-2021", "New Year"),
                new Holiday("Italy","07-01-2021","Christmas")
        );

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(holidays);

        JasperPrint print = JasperFillManager.fillReport(jasperFile, null, dataSource);
        JasperExportManager.exportReportToPdfFile(print, "output/Report_Bean.pdf");

        System.out.println("Report created with JRBeanCollectionDataSource");
    }
}
