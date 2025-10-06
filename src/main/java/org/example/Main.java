package org.example;

import org.example.crosstab.HolidayCrosstabReport;
import org.example.data.HolidayDataProvider;
import org.example.dynamicreport.HolidayDynamicReport;
import org.example.jasper.ReportCompiler;
import org.example.jasper.datasource.ReportWithBean;
import org.example.jasper.datasource.ReportWithMap;
import org.example.jasper.datasource.ReportWithResultSet;
import org.example.jasper.datasource.ReportWithXml;
import org.example.model.Holiday;
import org.example.util.PathConstants;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PathConstants.ensureOutputDirExists();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Выберите опцию: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        compileJasperReport();
                        break;
                    case "2":
                        generateJasperReports();
                        break;
                    case "3":
                        generateDynamicReport();
                        break;
                    case "4":
                        generateCrosstabReport();
                        break;
                    case "5":
                        generateAllReports();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Выход из программы...");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.\n");
                }
            } catch (Exception e) {
                System.err.println("Ошибка при выполнении операции: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  СИСТЕМА ГЕНЕРАЦИИ ОТЧЕТОВ О ПРАЗДНИКАХ");
        System.out.println("=".repeat(60));
        System.out.println("1. Скомпилировать JRXML шаблон");
        System.out.println("2. Сгенерировать Jasper Reports (Bean, Map, ResultSet, Xml)");
        System.out.println("3. Сгенерировать Dynamic Report");
        System.out.println("4. Сгенерировать Crosstab Report с графиком");
        System.out.println("5. Сгенерировать ВСЕ отчеты");
        System.out.println("0. Выход");
        System.out.println("=".repeat(60));
    }

    private static void compileJasperReport() {
        System.out.println("\n[Компиляция JRXML шаблона...]");
        ReportCompiler.compile();
        System.out.println("Компиляция завершена\n");
    }

    private static void generateJasperReports() throws Exception {
        System.out.println("\n[Генерация Jasper Reports...]");

        System.out.println("- Report with JRBeanCollectionDataSource...");
        ReportWithBean.generate();

        System.out.println("- Report with JRMapCollectionDataSource...");
        ReportWithMap.generate();

        System.out.println("- Report with JRResultSetDataSource...");
        ReportWithResultSet.generate();

        System.out.println("- Report with JRXmlDataSource...");
        ReportWithXml.generate();

        System.out.println("- Все Jasper Reports сгенерированы\n");
    }

    private static void generateDynamicReport() {
        System.out.println("\n[Генерация Dynamic Report...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayDynamicReport.generateReport(
                holidays,
                PathConstants.DYNAMIC_REPORT_PDF,
                PathConstants.DYNAMIC_REPORT_JRXML
        );
        System.out.println("Dynamic Report сгенерирован\n");
    }

    private static void generateCrosstabReport() {
        System.out.println("\n[Генерация Crosstab Report с графиком...]");
        List<Holiday> holidays = HolidayDataProvider.getAllHolidays();
        HolidayCrosstabReport.generateReport(
                holidays,
                PathConstants.CROSSTAB_REPORT_PDF,
                PathConstants.CROSSTAB_REPORT_JRXML
        );
        System.out.println("Crosstab Report с графиком сгенерирован\n");
    }

    private static void generateAllReports() throws Exception {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ГЕНЕРАЦИЯ ВСЕХ ОТЧЕТОВ");
        System.out.println("=".repeat(60));

        compileJasperReport();
        generateJasperReports();
        generateDynamicReport();
        generateCrosstabReport();

        System.out.println("=".repeat(60));
        System.out.println("ВСЕ ОТЧЕТЫ УСПЕШНО СГЕНЕРИРОВАНЫ!");
        System.out.println("  Проверьте папку 'output/' для просмотра результатов");
        System.out.println("=".repeat(60) + "\n");
    }
}