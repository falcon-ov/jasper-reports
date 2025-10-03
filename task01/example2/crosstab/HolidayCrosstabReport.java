package org.example2.crosstab;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.BarChartBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example2.model.Holiday;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class HolidayCrosstabReport {

    public static JasperReportBuilder buildReport(List<Holiday> holidays) {
        // Стили
        StyleBuilder titleStyle = stl.style()
                .bold()
                .setFontSize(24)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder crosstabHeaderStyle = stl.style()
                .bold()
                .setFontSize(12)
                .setTabStopWidth(25)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder columnHeaderStyle = stl.style()
                .bold()
                .setTabStopWidth(25)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBackgroundColor(Color.LIGHT_GRAY);

        StyleBuilder crosstabCellStyle = stl.style()
                .setFontSize(12)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point())
                .setTabStopWidth(75);

        StyleBuilder imageStyle = stl.style()
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        // Загрузка логотипа
        BufferedImage logoImage = null;
        String logoPath = "C:/Users/crme287/IdeaProjects/jasper-reports/logo.png";
        try {
            File logoFile = new File(logoPath);
            if (logoFile.exists()) {
                logoImage = ImageIO.read(logoFile);
                System.out.println("Logo loaded successfully: " + logoFile.getAbsolutePath());
            } else {
                System.err.println("Logo file not found at: " + logoFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error loading logo: " + e.getMessage());
        }

        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("country", String.class)
                .setHeaderStyle(crosstabCellStyle)
                .setHeaderWidth(150)
                .setTotalHeaderStyle(crosstabCellStyle);

        CrosstabColumnGroupBuilder<Integer> columnGroup = ctab.columnGroup("month", Integer.class)
                .setHeaderStyle(crosstabCellStyle)
                .setHeaderHeight(16)
                .setOrderType(OrderType.ASCENDING)
                .setTotalHeaderStyle(crosstabCellStyle);

        CrosstabBuilder crosstab = ctab.crosstab()
                .headerCell(cmp.text("Country / Month")
                        .setStyle(crosstabCellStyle)
                        .setFixedHeight(16)
                        .setFixedWidth(150))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(
                        ctab.measure("", "month", Integer.class, Calculation.COUNT)
                                .setStyle(crosstabCellStyle)
                                .setTitleStyle(stl.style().setFontSize(0))
                )
                .setCellHeight(16)
                .setCellWidth(50);

        return report()
                .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                .setPageMargin(margin(20))
                .fields(
                        field("country", String.class),
                        field("date", String.class),
                        field("name", String.class),
                        field("month", Integer.class)
                )
                .title(
                        cmp.verticalList(
                                cmp.horizontalList(
                                        cmp.filler().setFixedWidth(510),
                                        logoImage != null ? cmp.image(logoImage).setFixedDimension(140, 30).setStyle(imageStyle)
                                                .setPrintWhenExpression(exp.<Boolean>jasperSyntax("$V{PAGE_NUMBER} == 1", Boolean.class))
                                                : cmp.filler().setFixedDimension(140, 30)
                                ),
                                cmp.horizontalList(
                                        cmp.filler().setFixedWidth(280),
                                        cmp.text("Holidays Crosstab Report 2021")
                                                .setStyle(titleStyle)
                                                .setFixedDimension(201, 81)
                                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                                )
                        ).setFixedHeight(123)
                )
                .summary(
                        crosstab,
                        cmp.verticalGap(20),
                        cmp.text("Number of Holidays per Month")
                                .setStyle(stl.style().bold().setFontSize(14)
                                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)),
                        cmp.verticalGap(10),
                        createBarChart(holidays)
                )
                .pageFooter(
                        cmp.horizontalList(
                                cmp.filler().setFixedWidth(280),
                                cmp.text("Page Footer")
                                        .setStyle(crosstabHeaderStyle)
                                        .setFixedDimension(201, 31)
                                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                        ).setFixedHeight(31)
                )
                .setDataSource(new JRBeanCollectionDataSource(holidays));
    }

    private static BarChartBuilder createBarChart(List<Holiday> holidays) {
        // Подсчитываем праздники по месяцам для каждой страны
        int[] italiaMonths = new int[13];
        int[] moldovaMonths = new int[13];

        for (Holiday holiday : holidays) {
            Integer month = holiday.getMonth();
            if (month != null) {
                if ("Italia".equals(holiday.getCountry())) {
                    italiaMonths[month]++;
                } else if ("Moldova".equals(holiday.getCountry())) {
                    moldovaMonths[month]++;
                }
            }
        }

        // Создаём datasource для графика
        DRDataSource chartDataSource = new DRDataSource("month", "count", "country");
        for (int i = 1; i <= 12; i++) {
            if (italiaMonths[i] > 0 || moldovaMonths[i] > 0) {
                chartDataSource.add(i, italiaMonths[i], "Italia");
                chartDataSource.add(i, moldovaMonths[i], "Moldova");
            }
        }

        return cht.barChart()
                .setTitle("")
                .setCategory(field("month", Integer.class))
                .series(cht.serie(field("count", Integer.class)).setSeries(field("country", String.class)))
                .setValueAxisFormat(cht.axisFormat()
                        .setLabel("Number of Holidays")
                        .setTickLabelMask("#")) // Только целые числа
                .setCategoryAxisFormat(cht.axisFormat().setLabel("Month"))
                .setShowValues(true)
                .setHeight(300)
                .setDataSource(chartDataSource)
                .addSeriesColor(Color.RED, Color.BLUE);
    }

    public static void generateReport(List<Holiday> holidays, String pdfPath, String jrxmlPath) {
        try {
            System.out.println("Data source size: " + holidays.size());
            for (Holiday holiday : holidays) {
                System.out.println("Holiday: country=" + holiday.getCountry() + ", date=" + holiday.getDate() + ", name=" + holiday.getName());
            }

            JasperReportBuilder report = buildReport(holidays);

            report.toPdf(export.pdfExporter(pdfPath));
            System.out.println("PDF report generated: " + pdfPath);

            try (FileOutputStream fos = new FileOutputStream(jrxmlPath)) {
                report.toXml(fos);
                System.out.println("JRXML report generated: " + jrxmlPath);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error writing JRXML file: " + e.getMessage());
            }

        } catch (DRException e) {
            e.printStackTrace();
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Holiday> holidays = List.of(
                new Holiday("Italia", "01/01/2021", "Capodanno"),
                new Holiday("Italia", "06/01/2021", "Epifania"),
                new Holiday("Italia", "04/04/2021", "Pasqua"),
                new Holiday("Italia", "05/04/2021", "Lunedì dell'Angelo"),
                new Holiday("Italia", "25/04/2021", "Festa della Liberazione"),
                new Holiday("Italia", "01/05/2021", "Festa dei Lavoratori"),
                new Holiday("Italia", "02/06/2021", "Festa della Repubblica"),
                new Holiday("Italia", "15/08/2021", "Ferragosto"),
                new Holiday("Italia", "01/11/2021", "Tutti i Santi"),
                new Holiday("Italia", "08/12/2021", "Immacolata Concezione"),
                new Holiday("Italia", "25/12/2021", "Natale"),
                new Holiday("Italia", "26/12/2021", "Santo Stefano"),
                new Holiday("Moldova", "01/01/2021", "New Year"),
                new Holiday("Moldova", "07/01/2021", "Christmas (Orthodox)"),
                new Holiday("Moldova", "08/03/2021", "Women's Day"),
                new Holiday("Moldova", "01/05/2021", "Labor Day"),
                new Holiday("Moldova", "09/05/2021", "Victory Day"),
                new Holiday("Moldova", "27/08/2021", "Independence Day"),
                new Holiday("Moldova", "31/08/2021", "National Language Day")
        );
        generateReport(holidays, "output/holidays_crosstab_report.pdf", "output/holidays_crosstab_report.jrxml");
    }
}