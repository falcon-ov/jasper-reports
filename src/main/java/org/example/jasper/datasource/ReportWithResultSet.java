package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;

import org.example.util.PathConstants;

import java.sql.*;

/**
 * Генерация отчета с использованием JRResultSetDataSource.
 */
public class ReportWithResultSet {

    public static void generate() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

        try (Statement st = conn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS holidays(country VARCHAR(50), name VARCHAR(50), date VARCHAR(20))");
            st.execute("INSERT INTO holidays VALUES ('Moldova','New Year', '01-01-2021')");
            st.execute("INSERT INTO holidays VALUES ('Italy','Christmas', '07-01-2021')");

            ResultSet rs = st.executeQuery("SELECT * FROM holidays");

            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

            JasperPrint print = JasperFillManager.fillReport(
                    PathConstants.HOLIDAYS_JASPER,
                    null,
                    dataSource
            );

            JasperExportManager.exportReportToPdfFile(
                    print,
                    PathConstants.REPORT_RESULTSET_PDF
            );

            System.out.println("Report created with JRResultSetDataSource: " + PathConstants.REPORT_RESULTSET_PDF);
        }
    }

    public static void main(String[] args) {
        try {
            generate();
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}