package org.example.dynamicreport;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
 * Генератор динамического отчета о праздниках.
 */
public class HolidayDynamicReport {

    public static JasperReportBuilder buildReport(List<Holiday> holidays) {
        // Загружаем логотип
        BufferedImage logoImage = ResourceLoader.loadLogo();

        // Заголовок (title)
        ComponentBuilder<?, ?> titleComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410),
                        logoImage != null
                                ? cmp.image(logoImage)
                                .setFixedDimension(120, 30)
                                .setStyle(ReportStyles.getImageStyle())
                                .setPrintWhenExpression(exp.jasperSyntax("$V{PAGE_NUMBER} == 1", Boolean.class))
                                : cmp.filler().setFixedDimension(120, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180),
                        cmp.text("Holidays Report 2021")
                                .setStyle(ReportStyles.getTitleStyle())
                                .setFixedDimension(201, 79)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(99);

        // Заголовок страницы (pageHeader)
        ComponentBuilder<?, ?> pageHeaderComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410),
                        logoImage != null
                                ? cmp.image(logoImage)
                                .setFixedDimension(120, 30)
                                .setStyle(ReportStyles.getImageStyle())
                                .setPrintWhenExpression(exp.jasperSyntax("$V{PAGE_NUMBER} > 1", Boolean.class))
                                : cmp.filler().setFixedDimension(120, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180),
                        cmp.text("Page Header")
                                .setStyle(ReportStyles.getHeaderStyle())
                                .setFixedDimension(201, 50)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(50);

        // Заголовки колонок (columnHeader)
        ComponentBuilder<?, ?> columnHeaderComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130),
                cmp.text("Country").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 40),
                cmp.text("Date").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 40),
                cmp.text("Name").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 40)
        ).setFixedHeight(40);

        // Детали (detail) — вывод полей $F{country}, $F{date}, $F{name}
        ComponentBuilder<?, ?> detailComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130),
                cmp.text(exp.jasperSyntax("$F{country}", String.class))
                        .setStyle(ReportStyles.getDetailStyle())
                        .setFixedDimension(100, 30),
                cmp.text(exp.jasperSyntax("$F{date}", String.class))
                        .setStyle(ReportStyles.getDetailStyle())
                        .setFixedDimension(100, 30),
                cmp.text(exp.jasperSyntax("$F{name}", String.class))
                        .setStyle(ReportStyles.getDetailStyle())
                        .setFixedDimension(100, 30)
        ).setFixedHeight(30);

        // Column Footer
        ComponentBuilder<?, ?> columnFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Column Footer")
                        .setStyle(ReportStyles.getFooterStyle())
                        .setFixedDimension(201, 45)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(45);

        // Page Footer
        ComponentBuilder<?, ?> pageFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Page Footer")
                        .setStyle(ReportStyles.getFooterStyle())
                        .setFixedDimension(201, 54)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(54);

        // Summary
        ComponentBuilder<?, ?> summaryComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Summary")
                        .setStyle(ReportStyles.getSummaryStyle())
                        .setFixedDimension(201, 42)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(42);

        // Настройка отчёта
        return report()
                .setPageFormat(PageType.A4)
                .setPageMargin(margin(20))
                .fields(
                        field("country", String.class),
                        field("date", String.class),
                        field("name", String.class)
                )
                .title(titleComponent)
                .pageHeader(pageHeaderComponent)
                .columnHeader(columnHeaderComponent)
                .detail(detailComponent)
                .columnFooter(columnFooterComponent)
                .pageFooter(pageFooterComponent)
                .summary(summaryComponent)
                .setDataSource(new JRBeanCollectionDataSource(holidays));
    }


    public static void generateReport(List<Holiday> holidays, String pdfPath, String jrxmlPath) {
        try {
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

    public static void main(String[] args) {
        System.out.println("\n[Генерация Dynamic Report...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayDynamicReport.generateReport(
                holidays,
                PathConstants.DYNAMIC_REPORT_PDF,
                PathConstants.DYNAMIC_REPORT_JRXML
        );
        System.out.println("Dynamic Report сгенерирован\n");
    }
}