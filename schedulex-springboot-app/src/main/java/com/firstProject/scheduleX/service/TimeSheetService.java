package com.firstProject.scheduleX.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firstProject.scheduleX.model.Activities;
import com.firstProject.scheduleX.model.Projects;
import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.TimeSheetGet;
import com.firstProject.scheduleX.model.TimeSheetPost;
import com.firstProject.scheduleX.repository.KimaiApi;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TimeSheetService {

    @Autowired
    KimaiApi apiKimai;

    private TimeSheetPost createMorningDay(int dayOfTheWeek, TimeSheet newSchedule) {
        TimeSheetPost morningSchedule;
        if (dayOfTheWeek != 5) {
            morningSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T08:00:00",
                newSchedule.getEnd().toString() + "T16:00:00", newSchedule.getProject(), newSchedule.getActivity(),
                newSchedule.getDescription(), newSchedule.getFixedRate(), newSchedule.getHourlyRate(),
                newSchedule.getUser(), newSchedule.isExported(), newSchedule.isBillable(), newSchedule.getTags());
        } else {
            morningSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T08:00:00",
                newSchedule.getEnd().toString() + "T15:00:00", newSchedule.getProject(), newSchedule.getActivity(),
                newSchedule.getDescription(), newSchedule.getFixedRate(), newSchedule.getHourlyRate(),
                newSchedule.getUser(), newSchedule.isExported(), newSchedule.isBillable(), newSchedule.getTags());
        }

        return morningSchedule;
    }

    private TimeSheetPost createAfternoonDay(TimeSheet newSchedule) {
        TimeSheetPost afternoonSchedule;
        afternoonSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T16:00:00",
            newSchedule.getEnd().toString() + "T16:15:00", newSchedule.getProject(), newSchedule.getActivity(),
            newSchedule.getDescription(), newSchedule.getFixedRate(), newSchedule.getHourlyRate(),
            newSchedule.getUser(), newSchedule.isExported(), newSchedule.isBillable(), newSchedule.getTags());
        return afternoonSchedule;
    }

    private void checkOneDay(TimeSheet newSchedule) {
        TimeSheetPost registeredDay;
        int dayOfTheWeek = newSchedule.getBegin().getDayOfWeek().getValue();
        switch (dayOfTheWeek) {
            case 5:
                registeredDay = createMorningDay(dayOfTheWeek, newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                break;
            case 6:
                System.out.println("Dia introcido incorrecto: El sábado no se trabaja");
                break;
            case 7:
                System.out.println("Dia introcido incorrecto: El domingo no se trabaja");
                break;
            default:
                registeredDay = createMorningDay(dayOfTheWeek, newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                registeredDay = createAfternoonDay(newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                break;
        }
    }

    private void checkMoreThanOneDay(TimeSheet newSchedule) {
        long daysOfDifference;
        int dayOfTheWeek;
        daysOfDifference = DAYS.between(newSchedule.getBegin(), newSchedule.getEnd());
        newSchedule.setEnd(newSchedule.getBegin());

        for (int i = 0; i <= daysOfDifference; i++) {
            dayOfTheWeek = newSchedule.getBegin().getDayOfWeek().getValue();
            if (dayOfTheWeek != 6 && dayOfTheWeek != 7) {
                this.checkOneDay(newSchedule);
            }
            newSchedule.setBegin(newSchedule.getBegin().plusDays(1));
            newSchedule.setEnd(newSchedule.getEnd().plusDays(1));
            switch (dayOfTheWeek) {
                case 1:
                    System.out.println("Lunes: 8 horas y 15 minutos fichadas");
                    break;
                case 2:
                    System.out.println("Martes: 8 horas y 15 minutos fichadas");
                    break;
                case 3:
                    System.out.println("Miércoles: 8 horas y 15 minutos fichadas");
                    break;
                case 4:
                    System.out.println("Jueves: 8 horas y 15 minutos fichadas");
                    break;
                case 5:
                    System.out.println("Viernes: 7 horas fichadas");
                    break;
                case 6:
                    System.out.println("Sábado: día de descanso");
                    break;
                case 7:
                    System.out.println("Domingo: día de descanso");
                    break;
            }
        }
    }

    public void checkDays(TimeSheet newSchedule) {
        if(checkHolidays(newSchedule)) {
            if(checkRepeatedDays(newSchedule)) {
                if (newSchedule.getBegin().equals(newSchedule.getEnd())) {
                    checkOneDay(newSchedule);
                } else {
                    checkMoreThanOneDay(newSchedule);
                }
            } else {
                if(askConfirmation()){

                }
            }
        }
    }

    private boolean askConfirmation() {
        return true;
    }

    private boolean checkRepeatedDays(TimeSheet newSchedule) {
        return true;
    }

    private boolean checkHolidays(TimeSheet newSchedule) {
        List<LocalDate> holidays = new ArrayList<>();
        LocalDate january1 = LocalDate.of(2022, 1, 1);
        holidays.add(january1);
        LocalDate january6 = LocalDate.of(2022, 1, 6);
        holidays.add(january6);
        LocalDate april15 = LocalDate.of(2022, 4, 15);
        holidays.add(april15);
        LocalDate august15 = LocalDate.of(2022, 8, 15);
        holidays.add(august15);
        LocalDate october12 = LocalDate.of(2022, 10, 12);
        holidays.add(october12);
        LocalDate november1 = LocalDate.of(2022, 11, 1);
        holidays.add(november1);
        LocalDate december6 = LocalDate.of(2022, 12, 6);
        holidays.add(december6);
        LocalDate december8 = LocalDate.of(2022, 12, 8);
        holidays.add(december8);

        if(holidays.contains(newSchedule.getBegin())){
            return false;
        }

        return true;

    }

    public List<TimeSheetGet> getRecentSchedules() {
        return apiKimai.getRecentSchedules();
    }

    public List<TimeSheetGet> getSchedules() {
        return apiKimai.getSchedules();
    }

    public List<Projects> getProjects() {
        return apiKimai.getProjects();
    }

    public List<Activities> getActivities() {
        return apiKimai.getActivities();
    }

}