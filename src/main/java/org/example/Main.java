package org.example;

import org.example.crosstab.HolidayCrosstabReport;
import org.example.data.HolidayDataProvider;
import org.example.dynamicreport.HolidayDynamicReport;
import org.example.jasper.ReportCompiler;
import org.example.jasper.datasource.ReportWithBean;
import org.example.jasper.datasource.ReportWithMap;
import org.example.jasper.datasource.ReportWithResultSet;
import org.example.jasper.datasource.ReportWithXml;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        PathConstants.ensureOutputDirExists();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        compileJasperReport();
                        break;
                    case "2":
                        generateJasperReports();
                        break;
                    case "3":
                        generateDynamicReport();
                        break;
                    case "4":
                        generateCrosstabReport();
                        break;
                    case "5":
                        generateAllReports();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
                }
            } catch (Exception e) {
                System.err.println("Error while executing operation: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    /**
     * Prints the main menu with available options.
     */
    private static void printMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  HOLIDAY REPORT GENERATION SYSTEM");
        System.out.println("=".repeat(60));
        System.out.println("1. Compile JRXML template");
        System.out.println("2. Generate Jasper Reports (Bean, Map, ResultSet, Xml)");
        System.out.println("3. Generate Dynamic Report");
        System.out.println("4. Generate Crosstab Report with chart");
        System.out.println("5. Generate ALL reports");
        System.out.println("0. Exit");
        System.out.println("=".repeat(60));
    }

    /**
     * Compiles the JRXML template into a JasperReport file
     * using {@link ReportCompiler}.
     */
    private static void compileJasperReport() {
        System.out.println("\n[Compiling JRXML template...]");
        ReportCompiler.compile();
        System.out.println("Compilation completed\n");
    }

    /**
     * Generates multiple Jasper Reports using different data sources:
     * <ul>
     *     <li>{@link ReportWithBean} - JRBeanCollectionDataSource</li>
     *     <li>{@link ReportWithMap} - JRMapCollectionDataSource</li>
     *     <li>{@link ReportWithResultSet} - JRResultSetDataSource</li>
     *     <li>{@link ReportWithXml} - JRXmlDataSource</li>
     * </ul>
     *
     * @throws Exception if report generation fails
     */
    private static void generateJasperReports() throws Exception {
        System.out.println("\n[Generating Jasper Reports...]");

        System.out.println("- Report with JRBeanCollectionDataSource...");
        ReportWithBean.generate();

        System.out.println("- Report with JRMapCollectionDataSource...");
        ReportWithMap.generate();

        System.out.println("- Report with JRResultSetDataSource...");
        ReportWithResultSet.generate();

        System.out.println("- Report with JRXmlDataSource...");
        ReportWithXml.generate();

        System.out.println("- All Jasper Reports generated\n");
    }

    /**
     * Generates a Dynamic Report using {@link HolidayDynamicReport}.
     */
    private static void generateDynamicReport() {
        System.out.println("\n[Generating Dynamic Report...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayDynamicReport.generateReport(
                holidays,
                PathConstants.DYNAMIC_REPORT_PDF,
                PathConstants.DYNAMIC_REPORT_JRXML
        );
        System.out.println("Dynamic Report generated\n");
    }


    /**
     * Generates a Crosstab Report with a chart using {@link HolidayCrosstabReport}.
     */
    private static void generateCrosstabReport() {
        System.out.println("\n[Generating Crosstab Report with chart...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayCrosstabReport.generateReport(
                holidays,
                PathConstants.CROSSTAB_REPORT_PDF,
                PathConstants.CROSSTAB_REPORT_JRXML
        );
        System.out.println("Crosstab Report with chart generated\n");
    }

    /**
     * Generates all available reports:
     * <ul>
     *     <li>Compiles JRXML template</li>
     *     <li>Generates Jasper Reports</li>
     *     <li>Generates Dynamic Report</li>
     *     <li>Generates Crosstab Report</li>
     * </ul>
     *
     * @throws Exception if any report generation fails
     */
    private static void generateAllReports() throws Exception {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  GENERATING ALL REPORTS");
        System.out.println("=".repeat(60));

        compileJasperReport();
        generateJasperReports();
        generateDynamicReport();
        generateCrosstabReport();

        System.out.println("=".repeat(60));
        System.out.println("ALL REPORTS SUCCESSFULLY GENERATED!");
        System.out.println("  Check the 'output/' folder to view the results");
        System.out.println("=".repeat(60) + "\n");
    }
}
