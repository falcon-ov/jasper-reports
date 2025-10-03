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
import org.example.dynamicreport.styles.ReportStyles;
import org.example.model.Holiday;
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
        // Загрузка логотипа
        BufferedImage logoImage = ResourceLoader.loadLogo();

        // Кастомные выражения для полей
        AbstractSimpleExpression<String> countryExpression = new AbstractSimpleExpression<>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("country");
            }
        };

        AbstractSimpleExpression<String> dateExpression = new AbstractSimpleExpression<>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("date");
            }
        };

        AbstractSimpleExpression<String> nameExpression = new AbstractSimpleExpression<>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("name");
            }
        };

        // Компоненты
        ComponentBuilder<?, ?> titleComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410),
                        logoImage != null ? cmp.image(logoImage).setFixedDimension(140, 30).setStyle(ReportStyles.getImageStyle())
                                .setPrintWhenExpression(exp.<Boolean>jasperSyntax("$V{PAGE_NUMBER} == 1", Boolean.class))
                                : cmp.filler().setFixedDimension(140, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180),
                        cmp.text("Holidays Report 2021")
                                .setStyle(ReportStyles.getTitleStyle())
                                .setFixedDimension(201, 81)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(123);

        ComponentBuilder<?, ?> pageHeaderComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410),
                        logoImage != null ? cmp.image(logoImage).setFixedDimension(140, 30).setStyle(ReportStyles.getImageStyle())
                                .setPrintWhenExpression(exp.<Boolean>jasperSyntax("$V{PAGE_NUMBER} > 1", Boolean.class))
                                : cmp.filler().setFixedDimension(140, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180),
                        cmp.text("Page Header")
                                .setStyle(ReportStyles.getHeaderStyle())
                                .setFixedDimension(201, 42)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(92);

        ComponentBuilder<?, ?> columnHeaderComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130),
                cmp.text("Country").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 30),
                cmp.text("Date").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 30),
                cmp.text("Name").setStyle(ReportStyles.getColumnHeaderStyle()).setFixedDimension(100, 30)
        ).setFixedHeight(30);

        ComponentBuilder<?, ?> detailComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130),
                cmp.text(countryExpression).setStyle(ReportStyles.getDetailStyle()).setFixedDimension(100, 30),
                cmp.text(dateExpression).setStyle(ReportStyles.getDetailStyle()).setFixedDimension(100, 30),
                cmp.text(nameExpression).setStyle(ReportStyles.getDetailStyle()).setFixedDimension(100, 30)
        ).setFixedHeight(30);

        ComponentBuilder<?, ?> columnFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Column Footer")
                        .setStyle(ReportStyles.getFooterStyle())
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        ComponentBuilder<?, ?> pageFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Page Footer")
                        .setStyle(ReportStyles.getFooterStyle())
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        ComponentBuilder<?, ?> summaryComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180),
                cmp.text("Summary")
                        .setStyle(ReportStyles.getSummaryStyle())
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        // Настройка отчета
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
}