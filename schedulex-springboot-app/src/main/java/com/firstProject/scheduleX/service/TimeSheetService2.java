package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TimeSheetService2 {

    @Autowired
    KimaiApi apiKimai;

    public List<TimeSheetPost> checkDays(TimeSheet newSchedule) {
        if (newSchedule.getEnd().isAfter(newSchedule.getBegin())) {
            return registerMoreThanOneDay(newSchedule);
        } else if (newSchedule.getBegin().equals(newSchedule.getEnd())) {
            return registerOneDay(newSchedule);
        }
        return Collections.emptyList();
    }

    public List<TimeSheetPost> registerOneDay(TimeSheet day) {
        if (checkDay(day)) {
            TimeSheetPost schedule;
            List<TimeSheetPost> registeredDay = new ArrayList<>();
            if (day.getBegin().getDayOfWeek().getValue() == 5) {
                schedule = createMorningDay(day.getBegin().getDayOfWeek().getValue(), day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule);
                return registeredDay;
            } else {
                schedule = createMorningDay(day.getBegin().getDayOfWeek().getValue(), day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule);
                schedule = createAfternoonDay(day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule);
                return registeredDay;
            }
        }
        return Collections.emptyList();
    }

    private boolean checkDay(TimeSheet day) {
        if (checkWeekends(day)) {
            if (checkHolidays(day)) {
                if (checkHours(day).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<TimeSheetGet> checkHours(TimeSheet day) {
        String beginWithoutZero;
        String endWithoutZero;
        if (day.getBegin().toString().substring(5, 6).equals("0")) {
            beginWithoutZero = day.getBegin().toString().substring(0, 5) + day.getBegin().toString().substring(6, 10) + "T08:00:00";
        } else {
            beginWithoutZero = day.getBegin() + "T08:00:00";
        }
        if (day.getEnd().toString().substring(5, 6).equals("0")) {
            endWithoutZero = day.getEnd().toString().substring(0, 5) + day.getEnd().toString().substring(6, 10) + "T16:15:00";
        } else {
            endWithoutZero = day.getEnd().toString() + "T16:15:00";
        }

        List<TimeSheetGet> registeredSchedules = apiKimai.getRecentSchedules(beginWithoutZero, endWithoutZero);
        if (!registeredSchedules.isEmpty()) {
            System.out.println("Warning: Registered Schedules Discovered: ");
            for (TimeSheetGet registeredSchedule : registeredSchedules) {
                System.out.println("From: " + registeredSchedule.getBegin() + " To " + registeredSchedule.getEnd());
            }
            //this.askConfirmation();
            //return ResponseEntity.status(HttpStatus.CONFLICT).body(registeredSchedules);
            return registeredSchedules;
        }
        return Collections.emptyList();
    }

    private boolean checkHolidays(TimeSheet day) {
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

        if (holidays.contains(day.getBegin())) {
            return false;
        }
        return true;
    }

    private boolean checkWeekends(TimeSheet day) {
        if (day.getBegin().getDayOfWeek().getValue() == 6 ||
                day.getBegin().getDayOfWeek().getValue() == 7) {
            return false;
        }
        return true;
    }

    private TimeSheetPost createMorningDay(int dayOfTheWeek, TimeSheet newSchedule) {
        TimeSheetPost morningSchedule;
        if (dayOfTheWeek != 5) {
            morningSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T08:00:00",
                    newSchedule.getEnd().toString() + "T16:00:00", newSchedule.getProject(), newSchedule.getActivity());
        } else {
            morningSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T08:00:00",
                    newSchedule.getEnd().toString() + "T15:00:00", newSchedule.getProject(), newSchedule.getActivity());
        }

        return morningSchedule;
    }

    private TimeSheetPost createAfternoonDay(TimeSheet newSchedule) {
        TimeSheetPost afternoonSchedule;
        afternoonSchedule = new TimeSheetPost(newSchedule.getBegin().toString() + "T16:00:00",
                newSchedule.getEnd().toString() + "T16:15:00", newSchedule.getProject(), newSchedule.getActivity());
        return afternoonSchedule;
    }

    public List<TimeSheetPost> registerMoreThanOneDay(TimeSheet days) {
        long daysOfDifference;
        List<TimeSheetPost> registeredDays = new ArrayList<>();
        List<TimeSheetPost> oneDaySchedules;
        daysOfDifference = DAYS.between(days.getBegin(), days.getEnd());
        days.setEnd(days.getBegin());

        for (int i = 0; i <= daysOfDifference; i++) {
            oneDaySchedules = registerOneDay(days);
            if (!oneDaySchedules.isEmpty()) {
                for (TimeSheetPost schedule : oneDaySchedules) {
                    registeredDays.add((schedule));
                }
            }
            days.setBegin(days.getBegin().plusDays(1));
            days.setEnd(days.getEnd().plusDays(1));
        }

        return registeredDays;
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
