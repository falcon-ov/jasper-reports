package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.util.List;

/**
 * Генерация отчета с использованием JRBeanCollectionDataSource
 */
public class ReportWithBean {

    public static void generate() throws JRException {
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(holidays);

        JasperPrint print = JasperFillManager.fillReport(
                PathConstants.HOLIDAYS_JASPER,
                null,
                dataSource
        );

        JasperExportManager.exportReportToPdfFile(
                print,
                PathConstants.REPORT_BEAN_PDF
        );

        System.out.println("Report created with JRBeanCollectionDataSource: "
                + PathConstants.REPORT_BEAN_PDF);
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