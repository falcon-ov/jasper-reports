package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.util.List;

/**
 * Generates a report using {@link JRBeanCollectionDataSource}.
 * <p>
 * This class demonstrates how to use a collection of {@link Holiday} beans
 * as the data source for filling a JasperReport template and exporting the result to PDF.
 * </p>
 */
public class ReportWithBean {

    /**
     * Generates a JasperReport using {@link JRBeanCollectionDataSource}.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Retrieve holiday data from {@link HolidayDataProvider}</li>
     *     <li>Wrap the list of {@link Holiday} objects in a {@link JRBeanCollectionDataSource}</li>
     *     <li>Fill the report template defined in {@link PathConstants#HOLIDAYS_JASPER}</li>
     *     <li>Export the report to PDF at {@link PathConstants#REPORT_BEAN_PDF}</li>
     * </ol>
     *
     * @throws JRException if report generation fails
     */
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
