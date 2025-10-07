package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.sql.*;
import java.util.List;

/**
 * Generates a report using {@link JRResultSetDataSource}.
 * <p>
 * The data is retrieved from {@link HolidayDataProvider}, inserted into
 * an in-memory H2 database, and then queried back as a {@link ResultSet}.
 * This {@link ResultSet} is wrapped in a {@link JRResultSetDataSource}
 * and used to fill the JasperReport template.
 * </p>
 */
public class ReportWithResultSet {

    /**
     * Generates a JasperReport using {@link JRResultSetDataSource}.
     * <p>
     * Steps performed:
     * <ol>
     *     <li>Create an in-memory H2 database</li>
     *     <li>Create a {@code holidays} table</li>
     *     <li>Insert holiday data from {@link HolidayDataProvider}</li>
     *     <li>Query the data back into a {@link ResultSet}</li>
     *     <li>Wrap the {@link ResultSet} in a {@link JRResultSetDataSource}</li>
     *     <li>Fill the report template defined in {@link PathConstants#HOLIDAYS_JASPER}</li>
     *     <li>Export the report to PDF at {@link PathConstants#REPORT_RESULTSET_PDF}</li>
     * </ol>
     *
     * @throws Exception if any database or JasperReports operation fails
     */
    public static void generate() throws Exception {
        // Create in-memory H2 database
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             Statement st = conn.createStatement()) {

            // Create table
            st.execute("CREATE TABLE IF NOT EXISTS holidays(" +
                    "country VARCHAR(64), " +
                    "name VARCHAR(128), " +
                    "date VARCHAR(32))");

            // Get data from provider
            List<Holiday> holidays = HolidayDataProvider.getAllHolidays();

            // Insert data into table
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO holidays(country, name, date) VALUES (?, ?, ?)")) {
                for (Holiday h : holidays) {
                    ps.setString(1, h.getCountry());
                    ps.setString(2, h.getName());
                    ps.setString(3, h.getDate());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // Query data back
            ResultSet rs = st.executeQuery("SELECT * FROM holidays");

            // Wrap in JRResultSetDataSource
            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

            // Fill report
            JasperPrint print = JasperFillManager.fillReport(
                    PathConstants.HOLIDAYS_JASPER,
                    null,
                    dataSource
            );

            // Export to PDF
            JasperExportManager.exportReportToPdfFile(
                    print,
                    PathConstants.REPORT_RESULTSET_PDF
            );

            System.out.println("Report created with JRResultSetDataSource: "
                    + PathConstants.REPORT_RESULTSET_PDF);
        }
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
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
