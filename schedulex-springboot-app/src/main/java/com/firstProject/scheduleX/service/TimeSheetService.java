package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.controller.TimeSheetController;
import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TimeSheetService {

    @Autowired
    KimaiApi apiKimai;

    private TimeSheetPost createMorningDay(int dayOfTheWeek, TimeSheet newSchedule) {
        TimeSheetPost morningSchedule;
        if(dayOfTheWeek != 5){
            morningSchedule = new TimeSheetPost(
                    newSchedule.getBegin().toString() + "T08:00:00",
                    newSchedule.getEnd().toString() + "T16:00:00",
                    newSchedule.getProject(),
                    newSchedule.getActivity(),
                    newSchedule.getDescription(),
                    newSchedule.getFixedRate(),
                    newSchedule.getHourlyRate(),
                    newSchedule.getUser(),
                    newSchedule.isExported(),
                    newSchedule.isBillable(),
                    newSchedule.getTags());
        } else {
            morningSchedule = new TimeSheetPost(
                    newSchedule.getBegin().toString() + "T08:00:00",
                    newSchedule.getEnd().toString() + "T15:00:00",
                    newSchedule.getProject(),
                    newSchedule.getActivity(),
                    newSchedule.getDescription(),
                    newSchedule.getFixedRate(),
                    newSchedule.getHourlyRate(),
                    newSchedule.getUser(),
                    newSchedule.isExported(),
                    newSchedule.isBillable(),
                    newSchedule.getTags());
        }

        return morningSchedule;
    }

    private TimeSheetPost createAfternoonDay(TimeSheet newSchedule) {
        TimeSheetPost afternoonSchedule;
        afternoonSchedule = new TimeSheetPost(
                newSchedule.getBegin().toString() + "T16:00:00",
                newSchedule.getEnd().toString() + "T16:15:00",
                newSchedule.getProject(),
                newSchedule.getActivity(),
                newSchedule.getDescription(),
                newSchedule.getFixedRate(),
                newSchedule.getHourlyRate(),
                newSchedule.getUser(),
                newSchedule.isExported(),
                newSchedule.isBillable(),
                newSchedule.getTags());
        return afternoonSchedule;
    }

    private void checkOneDay(TimeSheet newSchedule) throws Exception {
        TimeSheetPost registeredDay;
        int dayOfTheWeek = newSchedule.getBegin().getDayOfWeek().getValue();
        switch(dayOfTheWeek) {
            case 5:
                registeredDay = createMorningDay(dayOfTheWeek, newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                break;
            case 6:
                throw new Exception ("Dia introcido incorrecto: El sábado no se trabaja");
            case 7:
                throw new Exception ("Dia introcido incorrecto: El domingo no se trabaja");
            default:
                registeredDay = createMorningDay(dayOfTheWeek, newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                registeredDay = createAfternoonDay(newSchedule);
                apiKimai.addHoursAPi(registeredDay);
                break;
        }
    }

    private void checkMoreThanOneDay(TimeSheet newSchedule) throws Exception {
        long daysOfDifference;
        int dayOfTheWeek;
        daysOfDifference = DAYS.between(newSchedule.getBegin(), newSchedule.getEnd());
        newSchedule.setEnd(newSchedule.getBegin());

        for(int i=0; i<=daysOfDifference; i++){
            dayOfTheWeek = newSchedule.getBegin().getDayOfWeek().getValue();
            if(dayOfTheWeek != 6 && dayOfTheWeek != 7) {
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

    public void checkdays(TimeSheet newSchedule) {
        if(newSchedule.getBegin().equals(newSchedule.getEnd())) {
            try {
                checkOneDay(newSchedule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                checkMoreThanOneDay(newSchedule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<TimeSheetGet> getSchedules() {
        return apiKimai.getHorarios();
    }

    public List<Projects> getProjects(){
        return apiKimai.getProjects();
    }

    public List<Activities> getActivities(){
        return apiKimai.getActivities();
    }

}