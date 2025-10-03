package org.example.util;

import java.io.File;

/**
 * Константы путей для всех файлов проекта
 */
public class PathConstants {

    // Директории
    public static final String RESOURCES_DIR = "src/main/resources/";
    public static final String OUTPUT_DIR = "output/";

    // JRXML шаблоны
    public static final String HOLIDAYS_JRXML = RESOURCES_DIR + "HolidaysReport.jrxml";
    public static final String HOLIDAYS_JASPER = RESOURCES_DIR + "HolidaysReport.jasper";

    // Ресурсы
    public static final String LOGO_PATH = RESOURCES_DIR + "logo.png";

    // Выходные файлы - Jasper Reports
    public static final String REPORT_BEAN_PDF = OUTPUT_DIR + "Report_Bean.pdf";
    public static final String REPORT_MAP_PDF = OUTPUT_DIR + "Report_Map.pdf";
    public static final String REPORT_RESULTSET_PDF = OUTPUT_DIR + "Report_ResultSet.pdf";

    // Выходные файлы - Dynamic Reports
    public static final String DYNAMIC_REPORT_PDF = OUTPUT_DIR + "holidays_report.pdf";
    public static final String DYNAMIC_REPORT_JRXML = OUTPUT_DIR + "holidays_report.jrxml";

    // Выходные файлы - Crosstab Reports
    public static final String CROSSTAB_REPORT_PDF = OUTPUT_DIR + "holidays_crosstab_report.pdf";
    public static final String CROSSTAB_REPORT_JRXML = OUTPUT_DIR + "holidays_crosstab_report.jrxml";

    public static final String REPORT_XML_PDF = OUTPUT_DIR + "Report_Xml.pdf";

    /**
     * Проверяет существование директории output, создает если нужно
     */
    public static void ensureOutputDirExists() {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created output directory: " + OUTPUT_DIR);
        }
    }
}