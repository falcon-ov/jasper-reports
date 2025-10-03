package org.example2.jrdatasource;

import net.sf.jasperreports.engine.*;

import java.sql.*;

public class ReportWithResultSet {
    public static void main(String[] args) throws Exception {
        String jasperFile = "src/main/resources/HolidaysReport.jasper";

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");

        Statement st = conn.createStatement();
        st.execute("CREATE TABLE holidays(country VARCHAR(50), name VARCHAR(50), date VARCHAR(20))");
        st.execute("INSERT INTO holidays VALUES ('Moldova','New Year', '01-01-2021')");
        st.execute("INSERT INTO holidays VALUES ('Italy','Christmas', '07-01-2021')");

        ResultSet rs = st.executeQuery("SELECT * FROM holidays");

        JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

        JasperPrint print = JasperFillManager.fillReport(jasperFile, null, dataSource);
        JasperExportManager.exportReportToPdfFile(print, "output/Report_ResultSet.pdf");

        System.out.println("Report created with JRResultSetDataSource");
    }
}
