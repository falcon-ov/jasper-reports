package org.example.dynamicreport.styles;

import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;

import java.awt.Color;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

/**
 * Centralized style management for reports.
 * <p>
 * Provides reusable {@link StyleBuilder} definitions to ensure
 * a consistent look and feel across all generated documents.
 * </p>
 */
public class ReportStyles {

    /**
     * Style for the main report title.
     *
     * @return a bold, centered style with large font size
     */
    public static StyleBuilder getTitleStyle() {
        return stl.style()
                .bold()
                .setFontSize(24)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    /**
     * Style for page headers.
     *
     * @return a centered style with medium font size
     */
    public static StyleBuilder getHeaderStyle() {
        return stl.style()
                .setFontSize(16)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    /**
     * Style for sub-headers.
     *
     * @return a bold, centered style with slightly smaller font size
     */
    public static StyleBuilder getSubHeaderStyle() {
        return stl.style()
                .bold()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    /**
     * Style for column headers in tables.
     *
     * @return a bold style with borders and light gray background
     */
    public static StyleBuilder getColumnHeaderStyle() {
        return stl.style()
                .bold()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
    }

    /**
     * Style for detail rows in tables.
     *
     * @return a centered style with borders
     */
    public static StyleBuilder getDetailStyle() {
        return stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point());
    }

    /**
     * Style for crosstab cells.
     *
     * @return a compact style with borders and tab stop width
     */
    public static StyleBuilder getCrosstabCellStyle() {
        return stl.style()
                .setFontSize(10)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
                .setBorder(stl.pen1Point())
                .setTabStopWidth(75);
    }

    /**
     * Style for footers.
     *
     * @return a centered style with medium font size
     */
    public static StyleBuilder getFooterStyle() {
        return stl.style()
                .setFontSize(14)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    /**
     * Style for summary sections.
     *
     * @return a centered style with smaller font size
     */
    public static StyleBuilder getSummaryStyle() {
        return stl.style()
                .setFontSize(12)
                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
                .setVerticalTextAlignment(VerticalTextAlignment.MIDDLE);
    }

    /**
     * Style for images (e.g., logos).
     *
     * @return a right-aligned style for images
     */
    public static StyleBuilder getImageStyle() {
        return stl.style()
                .setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
    }
}
