package org.example.util;

import java.io.File;

/**
 * Path constants for all project files.
 * <p>
 * This class centralizes file and resource paths used across the project,
 * including:
 * <ul>
 *     <li>Resource directories</li>
 *     <li>JRXML templates</li>
 *     <li>Static resources (logo, XML data)</li>
 *     <li>Output files for Jasper, Dynamic, and Crosstab reports</li>
 * </ul>
 * <p>
 * It also provides a utility method to ensure that the output directory exists.
 */
public class PathConstants {

    /** Base directory for resources. */
    public static final String RESOURCES_DIR = "src/main/resources/";

    /** Directory for generated output files. */
    public static final String OUTPUT_DIR = "output/";

    // === JRXML templates ===
    /** JRXML template for holiday reports. */
    public static final String HOLIDAYS_JRXML = RESOURCES_DIR + "HolidaysReport.jrxml";

    /** Compiled Jasper file for holiday reports. */
    public static final String HOLIDAYS_JASPER = RESOURCES_DIR + "HolidaysReport.jasper";

    // === Resources ===
    /** Path to the logo resource inside the classpath. */
    public static final String LOGO_RESOURCE = "/logo.png";

    /** Path to the XML data file with holiday information. */
    public static final String HOLIDAYS_XML = "/MyDataBase.xml";

    // === Output files - Jasper Reports ===
    /** Output PDF generated with JRBeanCollectionDataSource. */
    public static final String REPORT_BEAN_PDF = OUTPUT_DIR + "Report_Bean.pdf";

    /** Output PDF generated with JRMapCollectionDataSource. */
    public static final String REPORT_MAP_PDF = OUTPUT_DIR + "Report_Map.pdf";

    /** Output PDF generated with JRResultSetDataSource. */
    public static final String REPORT_RESULTSET_PDF = OUTPUT_DIR + "Report_ResultSet.pdf";

    /** Output PDF generated with JRXmlDataSource. */
    public static final String REPORT_XML_PDF = OUTPUT_DIR + "Report_Xml.pdf";

    // === Output files - Dynamic Reports ===
    /** Output PDF for the dynamic holiday report. */
    public static final String DYNAMIC_REPORT_PDF = OUTPUT_DIR + "holidays_report.pdf";

    /** JRXML file generated for the dynamic holiday report. */
    public static final String DYNAMIC_REPORT_JRXML = OUTPUT_DIR + "holidays_report.jrxml";

    // === Output files - Crosstab Reports ===
    /** Output PDF for the crosstab holiday report with chart. */
    public static final String CROSSTAB_REPORT_PDF = OUTPUT_DIR + "holidays_crosstab_report.pdf";

    /** JRXML file generated for the crosstab holiday report. */
    public static final String CROSSTAB_REPORT_JRXML = OUTPUT_DIR + "holidays_crosstab_report.jrxml";

    /**
     * Ensures that the output directory exists.
     * <p>
     * If the directory does not exist, it will be created automatically.
     * </p>
     */
    public static void ensureOutputDirExists() {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created output directory: " + OUTPUT_DIR);
        }
    }
}
