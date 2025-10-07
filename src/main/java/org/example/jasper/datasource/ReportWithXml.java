package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.example.util.PathConstants;

import java.io.InputStream;

/**
 * Generates a report using {@link JRXmlDataSource} (direct XML reading).
 * <p>
 * This class demonstrates how to use an XML file as a data source
 * for filling a JasperReport template and exporting the result to PDF.
 * </p>
 */
public class ReportWithXml {

    /**
     * Generates a JasperReport using {@link JRXmlDataSource}.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Load the XML file from the classpath ({@link PathConstants#HOLIDAYS_XML})</li>
     *     <li>Create a {@link JRXmlDataSource} with an XPath expression</li>
     *     <li>Fill the compiled Jasper template ({@link PathConstants#HOLIDAYS_JASPER})</li>
     *     <li>Export the result to PDF ({@link PathConstants#REPORT_XML_PDF})</li>
     * </ol>
     *
     * @throws JRException if report generation fails
     */
    public static void generate() throws JRException {
        // Load XML file from resources
        InputStream xmlStream = ReportWithXml.class.getResourceAsStream(PathConstants.HOLIDAYS_XML);
        if (xmlStream == null) {
            System.err.println("XML file not found: " + PathConstants.HOLIDAYS_XML);
            return;
        }

        // Create data source using XPath to select nodes
        JRDataSource dataSource = new JRXmlDataSource(xmlStream, "/Year2021/holydays");

        // Fill the Jasper report with data
        JasperPrint print = JasperFillManager.fillReport(
                PathConstants.HOLIDAYS_JASPER,
                null,
                dataSource
        );

        // Export the report to PDF
        String outputPath = PathConstants.REPORT_XML_PDF;
        JasperExportManager.exportReportToPdfFile(print, outputPath);

        System.out.println("Report created with JRXmlDataSource: " + outputPath);
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
