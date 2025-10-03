package org.example2.dynamicreport;

import org.example2.model.Holiday;

import java.util.ArrayList;
import java.util.List;

public class MainDynamicReport {

    public static void main(String[] args) {
        // список праздников
        List<Holiday> holidays = createHolidaysList();

        String pdfPath = "output/holidays_report.pdf";
        String jrxmlPath = "output/holidays_report.jrxml";

        HolidayDynamicReport.generateReport(holidays, pdfPath, jrxmlPath);

        System.out.println("All done! Check 'output' folder for PDF and JRXML.");
    }

    private static List<Holiday> createHolidaysList() {
        List<Holiday> holidays = new ArrayList<>();
        holidays.add(new Holiday("Italia", "01/01/2021", "Capodanno"));
        holidays.add(new Holiday("Italia", "06/01/2021", "Epifania"));
        holidays.add(new Holiday("Italia", "04/04/2021", "Pasqua"));
        holidays.add(new Holiday("Italia", "05/04/2021", "Lunedì dell'Angelo"));
        holidays.add(new Holiday("Italia", "25/04/2021", "Festa della Liberazione"));
        holidays.add(new Holiday("Italia", "01/05/2021", "Festa dei Lavoratori"));
        holidays.add(new Holiday("Italia", "02/06/2021", "Festa della Repubblica"));
        holidays.add(new Holiday("Italia", "15/08/2021", "Ferragosto"));
        holidays.add(new Holiday("Italia", "01/11/2021", "Tutti i Santi"));
        holidays.add(new Holiday("Italia", "08/12/2021", "Immacolata Concezione"));
        holidays.add(new Holiday("Italia", "25/12/2021", "Natale"));
        holidays.add(new Holiday("Italia", "26/12/2021", "Santo Stefano"));
        return holidays;
    }
}
