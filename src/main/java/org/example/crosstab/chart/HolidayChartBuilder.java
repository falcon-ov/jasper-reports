package org.example.crosstab.chart;

import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import org.example.data.HolidayDataProvider;
import org.example.model.Holiday;

import java.awt.Color;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Построитель графиков для отчетов о праздниках.
 * Использует предобработанные данные для избежания дубликатов подсчета.
 */
public class HolidayChartBuilder {

    /**
     * Создает столбчатую диаграмму с количеством праздников по месяцам для каждой страны.
     * @param holidays Список праздников
     * @return BarChartBuilder
     */
    public static BarChartBuilder createMonthlyHolidaysBarChart(List<Holiday> holidays) {
        DRDataSource chartDataSource = prepareChartData(holidays);

        return cht.barChart()
                .setTitle("")
                .setCategory(field("month", String.class))
                .series(cht.serie(field("count", Integer.class))
                        .setSeries(field("country", String.class)))
                .setValueAxisFormat(cht.axisFormat()
                        .setLabel("Number of Holidays")
                        .setTickLabelMask("#")
                        .setLabelFont(stl.font().setFontSize(8)) // 👈 уменьшение шрифта оси Y
                        .setTickLabelFont(stl.font().setFontSize(8)))
                .setCategoryAxisFormat(cht.axisFormat()
                        .setLabel("Month")
                        .setLabelFont(stl.font().setFontSize(8)) // 👈 уменьшение шрифта оси X
                        .setTickLabelFont(stl.font().setFontSize(8))
                        .setTickLabelRotation(90.0))              // 👈 поворот подписей месяцев
                .setHeight(250)                              // 👈 уменьшение общей высоты графика
                .setShowValues(true)
                .setDataSource(chartDataSource)
                .addSeriesColor(Color.RED, Color.BLUE);

    }

    private static final int MONTHS_IN_YEAR = 12;

    private static DRDataSource prepareChartData(List<Holiday> holidays) {
        Map<String, int[]> monthCounts = HolidayDataProvider.getMonthCountsByCountry(holidays);

        DRDataSource chartDataSource = new DRDataSource("month", "count", "country");

        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
            String monthLabel = formatMonth(month);

            for (Map.Entry<String, int[]> entry : monthCounts.entrySet()) {
                String country = entry.getKey();
                int[] months = entry.getValue();

                if (month < months.length && months[month] > 0) {
                    chartDataSource.add(monthLabel, months[month], country);
                }
            }
        }

        return chartDataSource;
    }

    private static String formatMonth(int month) {
        // Example: "1. January"
        return month + ". " + Month.of(month).name().substring(0, 1).toUpperCase()
                + Month.of(month).name().substring(1).toLowerCase();
    }


}