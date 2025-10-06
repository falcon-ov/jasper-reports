package org.example.jasper.datasource;

import net.sf.jasperreports.engine.*;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.sql.*;
import java.util.List;

/**
 * Генерация отчета с использованием JRResultSetDataSource,
 * при этом данные берутся из HolidayDataProvider и вставляются в БД.
 */
public class ReportWithResultSet {

    public static void generate() throws Exception {
        // создаём in-memory H2 базу
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
             Statement st = conn.createStatement()) {

            // создаём таблицу
            st.execute("CREATE TABLE IF NOT EXISTS holidays(" +
                    "country VARCHAR(64), " +
                    "name VARCHAR(128), " +
                    "date VARCHAR(32))");

            // берём данные из провайдера
            List<Holiday> holidays = HolidayDataProvider.getAllHolidays();

            // вставляем данные в таблицу
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

            // читаем данные обратно через ResultSet
            ResultSet rs = st.executeQuery("SELECT * FROM holidays");

            // оборачиваем в JRResultSetDataSource
            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

            // заполняем отчёт
            JasperPrint print = JasperFillManager.fillReport(
                    PathConstants.HOLIDAYS_JASPER,
                    null,
                    dataSource
            );

            // экспортируем в PDF
            JasperExportManager.exportReportToPdfFile(
                    print,
                    PathConstants.REPORT_RESULTSET_PDF
            );

            System.out.println("Report created with JRResultSetDataSource: "
                    + PathConstants.REPORT_RESULTSET_PDF);
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
