package org.example.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.example.util.PathConstants;

import java.io.File;

/**
 * Utility class for compiling JRXML templates into JASPER files.
 * <p>
 * This class uses {@link JasperCompileManager} to transform JRXML report templates
 * into compiled JASPER files that can be filled with data.
 * </p>
 */
public class ReportCompiler {

    /**
     * Compiles the holiday JRXML template into a JASPER file.
     * <p>
     * The input and output paths are defined in {@link PathConstants#HOLIDAYS_JRXML}
     * and {@link PathConstants#HOLIDAYS_JASPER}.
     * </p>
     * <p>
     * If the JRXML file does not exist, the method logs an error and exits.
     * </p>
     */
    public static void compile() {
        String jrxmlFile = PathConstants.HOLIDAYS_JRXML;
        String jasperFile = PathConstants.HOLIDAYS_JASPER;

        try {
            File file = new File(jrxmlFile);
            if (!file.exists()) {
                System.err.println("File not found: " + jrxmlFile);
                return;
            }

            // Compile the report
            System.out.println("Compiling report: " + jrxmlFile);
            JasperCompileManager.compileReportToFile(jrxmlFile, jasperFile);
            System.out.println("Report successfully compiled: " + jasperFile);

        } catch (JRException e) {
            System.err.println("Error while compiling report: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Entry point for standalone execution.
     * <p>
     * Allows running the compiler directly from the command line.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        compile();
    }
}
