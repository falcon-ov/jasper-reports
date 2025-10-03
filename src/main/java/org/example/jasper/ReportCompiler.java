package org.example.jasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.example.util.PathConstants;

import java.io.File;

/**
 * Компилятор JRXML шаблонов в JASPER файлы.
 */
public class ReportCompiler {

    public static void compile() {
        String jrxmlFile = PathConstants.HOLIDAYS_JRXML;
        String jasperFile = PathConstants.HOLIDAYS_JASPER;

        try {
            // Проверка существования файла
            File file = new File(jrxmlFile);
            if (!file.exists()) {
                System.err.println("Файл не найден: " + jrxmlFile);
                return;
            }

            // Компилируем отчёт
            System.out.println("Компилируем отчёт: " + jrxmlFile);
            JasperCompileManager.compileReportToFile(jrxmlFile, jasperFile);
            System.out.println("Отчёт успешно скомпилирован: " + jasperFile);

        } catch (JRException e) {
            System.err.println("Ошибка при компиляции отчёта: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        compile();
    }
}