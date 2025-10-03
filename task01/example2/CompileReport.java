package org.example2;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import java.io.File;

public class CompileReport {
    public static void main(String[] args) {
        String jrxmlFile = "src/main/resources/HolidaysReport.jrxml";
        String jasperFile = "src/main/resources/HolidaysReport.jasper";

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
}