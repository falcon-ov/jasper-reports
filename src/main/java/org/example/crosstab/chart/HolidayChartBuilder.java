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

    /**
     * Подготавливает данные для графика - подсчитывает праздники по месяцам.
     * @param holidays Список праздников
     * @return DRDataSource с данными для чарта
     */
    private static DRDataSource prepareChartData(List<Holiday> holidays) {
        Map<String, int[]> monthCounts = HolidayDataProvider.getMonthCountsByCountry(holidays);

        int[] italiaMonths = monthCounts.getOrDefault("Italia", new int[13]);
        int[] moldaviaMonths = monthCounts.getOrDefault("Moldavia", new int[13]);

        DRDataSource chartDataSource = new DRDataSource("month", "count", "country");
        for (int i = 1; i <= 12; i++) {
            int total = italiaMonths[i] + moldaviaMonths[i];
            if (total == 0) {
                continue;
            }

            String monthLabel = i + ". " + Month.of(i).name().substring(0, 1).toUpperCase() + Month.of(i).name().substring(1).toLowerCase();
            if (italiaMonths[i] > 0) {
                chartDataSource.add(monthLabel, italiaMonths[i], "Italia");
            }
            if (moldaviaMonths[i] > 0) {
                chartDataSource.add(monthLabel, moldaviaMonths[i], "Moldavia");
            }
        }
        return chartDataSource;
    }

}