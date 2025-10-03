package org.example2.dynamicreport;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.example2.model.Holiday;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

public class HolidayDynamicReport {

    public static JasperReportBuilder buildReport(List<Holiday> holidays) {
        // Стили
        StyleBuilder titleStyle = stl.style()
                .bold()
                .setFontSize(24)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder headerStyle = stl.style()
                .setFontSize(16)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder columnHeaderStyle = stl.style()
                .bold()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point());

        StyleBuilder detailStyle = stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point());

        StyleBuilder footerStyle = stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder summaryStyle = stl.style()
                .setFontSize(12)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);

        StyleBuilder imageStyle = stl.style()
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        // Загрузка логотипа
        BufferedImage logoImage = null;
        String logoPath = "src/main/resources/logo.png";
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

        // Кастомные выражения для полей
        AbstractSimpleExpression<String> countryExpression = new AbstractSimpleExpression<String>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("country");
            }
        };

        AbstractSimpleExpression<String> dateExpression = new AbstractSimpleExpression<String>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("date");
            }
        };

        AbstractSimpleExpression<String> nameExpression = new AbstractSimpleExpression<String>() {
            @Override
            public String evaluate(ReportParameters reportParameters) {
                return reportParameters.getFieldValue("name");
            }
        };

        // Компоненты
        ComponentBuilder<?, ?> titleComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410), // Отступ для логотипа (x=410)
                        logoImage != null ? cmp.image(logoImage).setFixedDimension(140, 30).setStyle(imageStyle)
                                .setPrintWhenExpression(exp.<Boolean>jasperSyntax("$V{PAGE_NUMBER} == 1", Boolean.class))
                                : cmp.filler().setFixedDimension(140, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180), // Отступ для текста (x=180)
                        cmp.text("Holidays Report 2021")
                                .setStyle(titleStyle)
                                .setFixedDimension(201, 81)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(123);

        ComponentBuilder<?, ?> pageHeaderComponent = cmp.verticalList(
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(410), // Отступ для логотипа (x=410)
                        logoImage != null ? cmp.image(logoImage).setFixedDimension(140, 30).setStyle(imageStyle)
                                .setPrintWhenExpression(exp.<Boolean>jasperSyntax("$V{PAGE_NUMBER} > 1", Boolean.class))
                                : cmp.filler().setFixedDimension(140, 30)
                ),
                cmp.horizontalList(
                        cmp.filler().setFixedWidth(180), // Отступ для текста (x=180)
                        cmp.text("Page Header")
                                .setStyle(headerStyle)
                                .setFixedDimension(201, 42)
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                )
        ).setFixedHeight(92);

        ComponentBuilder<?, ?> columnHeaderComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130), // Отступ x=130
                cmp.text("Country").setStyle(columnHeaderStyle).setFixedDimension(100, 30),
                cmp.text("Date").setStyle(columnHeaderStyle).setFixedDimension(100, 30),
                cmp.text("Name").setStyle(columnHeaderStyle).setFixedDimension(100, 30)
        ).setFixedHeight(30);

        ComponentBuilder<?, ?> detailComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(130), // Отступ x=130
                cmp.text(countryExpression).setStyle(detailStyle).setFixedDimension(100, 30),
                cmp.text(dateExpression).setStyle(detailStyle).setFixedDimension(100, 30),
                cmp.text(nameExpression).setStyle(detailStyle).setFixedDimension(100, 30)
        ).setFixedHeight(30);

        ComponentBuilder<?, ?> columnFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180), // Отступ x=180
                cmp.text("Column Footer")
                        .setStyle(footerStyle)
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        ComponentBuilder<?, ?> pageFooterComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180), // Отступ x=180
                cmp.text("Page Footer")
                        .setStyle(footerStyle)
                        .setFixedDimension(201, 31)
                        .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
        ).setFixedHeight(31);

        ComponentBuilder<?, ?> summaryComponent = cmp.horizontalList(
                cmp.filler().setFixedWidth(180), // Отступ x=180
                cmp.text("Summary")
                        .setStyle(summaryStyle)
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

            // Генерируем PDF
            report.toPdf(export.pdfExporter(pdfPath));
            System.out.println("PDF report generated: " + pdfPath);

            // Генерируем JRXML
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
}