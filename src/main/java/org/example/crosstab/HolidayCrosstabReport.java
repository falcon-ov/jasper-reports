package org.example.crosstab;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example.crosstab.chart.HolidayChartBuilder;
import org.example.data.HolidayDataProvider;
import org.example.dynamicreport.styles.ReportStyles;
import org.example.model.Holiday;
import org.example.util.PathConstants;
import org.example.util.ResourceLoader;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

/**
 * Generates a crosstab holiday report with an additional bar chart.
 * <p>
 * This report displays the number of holidays per country and month
 * in a crosstab format, and also includes a bar chart visualization.
 * </p>
 */
public class HolidayCrosstabReport {

    /**
     * Builds a {@link JasperReportBuilder} for the holiday crosstab report.
     * <p>
     * The report includes:
     * <ul>
     *     <li>A title with logo</li>
     *     <li>A crosstab grouped by country (rows) and month (columns)</li>
     *     <li>A summary section with a bar chart of holidays per month</li>
     *     <li>A footer</li>
     * </ul>
     *
     * @param holidays list of {@link Holiday} objects
     * @return a configured {@link JasperReportBuilder}
     */
    public static JasperReportBuilder buildReport(List<Holiday> holidays) {
        // Load logo
        BufferedImage logoImage = ResourceLoader.loadLogo();

        // Crosstab row group (by country)
        CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("country", String.class)
                .setHeaderStyle(ReportStyles.getCrosstabCellStyle())
                .setHeaderWidth(100)
                .setTotalHeaderStyle(ReportStyles.getCrosstabCellStyle());

        // Crosstab column group (by month)
        CrosstabColumnGroupBuilder<Integer> columnGroup = ctab.columnGroup("month", Integer.class)
                .setHeaderStyle(ReportStyles.getCrosstabCellStyle())
                .setHeaderHeight(16)
                .setOrderType(OrderType.ASCENDING)
                .setTotalHeaderStyle(ReportStyles.getCrosstabCellStyle())
                .setHeaderValueFormatter(new AbstractValueFormatter<String, Integer>() {
                    @Override
                    public String format(Integer month, ReportParameters reportParameters) {
                        return month == null ? "" : String.valueOf(month);
                    }
                });

        // Crosstab definition
        CrosstabBuilder crosstab = ctab.crosstab()
                .headerCell(cmp.text("Country / Month")
                        .setStyle(ReportStyles.getCrosstabCellStyle())
                        .setFixedHeight(16)
                        .setFixedWidth(100))
                .rowGroups(rowGroup)
                .columnGroups(columnGroup)
                .measures(
                        ctab.measure("", "month", Integer.class, Calculation.COUNT)
                                .setStyle(ReportStyles.getCrosstabCellStyle())
                                .setTitleStyle(stl.style().setFontSize(0))
                )
                .setCellHeight(16)
                .setCellWidth(50);

        // Title
        ComponentBuilder<?, ?> titleComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler(),
                        logoImage != null
                                ? cmp.image(logoImage)
                                .setFixedDimension(140, 30)
                                .setStyle(ReportStyles.getImageStyle())
                                .setPrintWhenExpression(exp.jasperSyntax("$V{PAGE_NUMBER} == 1", Boolean.class))
                                : cmp.filler().setFixedDimension(140, 30)
                ).setFixedHeight(40),

                cmp.horizontalList(
                        cmp.filler(),
                        cmp.text("Holidays Crosstab & Bar Chart Report 2021")
                                .setStyle(ReportStyles.getTitleStyle())
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER),
                        cmp.filler()
                ).setFixedHeight(40)
        ).setFixedHeight(123);

        // Summary with crosstab and chart
        ComponentBuilder<?, ?> summaryComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(50),
                        crosstab,
                        cmp.filler().setFixedWidth(50)
                ),
                cmp.verticalGap(20),
                cmp.text("Number of Holidays per Month")
                        .setStyle(ReportStyles.getSubHeaderStyle()),
                cmp.verticalGap(10),
                HolidayChartBuilder.createMonthlyHolidaysBarChart(holidays)
        );

        // Footer
        ComponentBuilder<?, ?> footerComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(280),
                cmp.text("Page Footer")
                        .setStyle(ReportStyles.getFooterStyle())
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        // Report skeleton
        return report()
                .setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
                .setPageMargin(margin(20))
                .fields(
                        field("country", String.class),
                        field("date", String.class),
                        field("name", String.class),
                        field("month", Integer.class)
                )
                .title(titleComponent)
                .summary(summaryComponent)
                .pageFooter(footerComponent)
                .setDataSource(new JRBeanCollectionDataSource(holidays));
    }

    /**
     * Generates the holiday crosstab report and exports it to PDF and JRXML.
     *
     * @param holidays list of {@link Holiday} objects
     * @param pdfPath  output path for the generated PDF
     * @param jrxmlPath output path for the generated JRXML
     */
    public static void generateReport(List<Holiday> holidays, String pdfPath, String jrxmlPath) {
        try {
            System.out.println("Data source size: " + holidays.size());
            for (Holiday holiday : holidays) {
                System.out.println(holiday);
            }

            JasperReportBuilder report = buildReport(holidays);

            report.toPdf(export.pdfExporter(pdfPath));
            System.out.println("PDF report generated: " + pdfPath);

            try (FileOutputStream fos = new FileOutputStream(jrxmlPath)) {
                report.toXml(fos);
                System.out.println("JRXML report generated: " + jrxmlPath);
            }
        } catch (DRException | IOException e) {
            System.err.println("Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Entry point for standalone execution.
     * <p>
     * Runs the crosstab report generation using {@link HolidayDataProvider}.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("\n[Generating Crosstab Report with chart...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayCrosstabReport.generateReport(
                holidays,
                PathConstants.CROSSTAB_REPORT_PDF,
                PathConstants.CROSSTAB_REPORT_JRXML
        );
        System.out.println("Crosstab Report with chart generated\n");
    }
}
