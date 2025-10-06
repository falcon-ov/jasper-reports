package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.example.util.PathConstants;

import java.io.InputStream;

/**
 * Генерация отчета с использованием JRXmlDataSource (прямое чтение XML)
 */
public class ReportWithXml {

    public static void generate() throws JRException {
        InputStream xmlStream = ReportWithXml.class.getResourceAsStream(PathConstants.HOLIDAYS_XML);
        if (xmlStream == null) {
            System.err.println("XML file not found: /MyDataBase.xml");
            return;
        }

        // XPath для выборки узлов
        JRDataSource dataSource = new JRXmlDataSource(xmlStream, "/Year2021/holydays");

        JasperPrint print = JasperFillManager.fillReport(
                PathConstants.HOLIDAYS_JASPER,
                null,
                dataSource
        );

        String outputPath = PathConstants.REPORT_XML_PDF;
        JasperExportManager.exportReportToPdfFile(print, outputPath);

        System.out.println("Report created with JRXmlDataSource: " + outputPath);
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