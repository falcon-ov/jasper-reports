package org.example.dynamicreport.styles;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

import java.awt.Color;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

/**
 * Централизованное управление стилями для отчетов.
 * Обеспечивает консистентный внешний вид всех генерируемых документов.
 */
public class ReportStyles {

    // Стили для заголовков
    public static StyleBuilder getTitleStyle() {
        return stl.style()
                .bold()
                .setFontSize(24)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    public static StyleBuilder getHeaderStyle() {
        return stl.style()
                .setFontSize(16)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    public static StyleBuilder getSubHeaderStyle() {
        return stl.style()
                .bold()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    // Стили для таблиц
    public static StyleBuilder getColumnHeaderStyle() {
        return stl.style()
                .bold()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
    }

    public static StyleBuilder getDetailStyle() {
        return stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point());
    }

    public static StyleBuilder getCrosstabCellStyle() {
        return stl.style()
                .setFontSize(10)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point())
                .setTabStopWidth(75);
    }

    // Стили для footer
    public static StyleBuilder getFooterStyle() {
        return stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    public static StyleBuilder getSummaryStyle() {
        return stl.style()
                .setFontSize(12)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    // Стиль для изображений
    public static StyleBuilder getImageStyle() {
        return stl.style()
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }
}