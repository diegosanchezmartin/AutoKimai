package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TimeSheetService {

    @Autowired
    KimaiApi apiKimai;

    public List<TimeSheetPost> checkDate(TimeSheet newSchedule, UserData credentials) {
        if (newSchedule.getEnd().isBefore(newSchedule.getBegin())) {
            throw new IllegalArgumentException("Error: Begin is before End: " + newSchedule);
        }
        return checkNumberOfDates(newSchedule, credentials);
    }

    public List<TimeSheetPost> registerOneDay(TimeSheet day, UserData credentials, long daysLeft) {
        if (checkDay(day.getBegin(), day.getEnd(), credentials, daysLeft)) {
            TimeSheetPost schedule;
            List<TimeSheetPost> registeredDay = new ArrayList<>();
            if (day.getBegin().getDayOfWeek().getValue() == 5) {
                schedule = createMorningDay(day.getBegin().getDayOfWeek().getValue(), day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule, credentials);
                return registeredDay;
            } else {
                schedule = createMorningDay(day.getBegin().getDayOfWeek().getValue(), day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule, credentials);
                schedule = createAfternoonDay(day);
                registeredDay.add(schedule);
                apiKimai.addHoursAPi(schedule, credentials);
                return registeredDay;
            }
        }
        return Collections.emptyList();
    }

    private boolean checkDay(LocalDate begin, LocalDate end, UserData credentials, long daysLeft) {
        if (!isWeekend(begin)) {
            if (!isHolidays(begin)) {
                if (checkHours(begin, end, credentials, daysLeft).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-dd'T'HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private List<TimeSheetGet> checkHours(LocalDate begin, LocalDate end, UserData credentials, long daysLeft) {
        final LocalDateTime beginDateTime = begin.atTime(LocalTime.of(8, 0, 0));
        final LocalDateTime endDateTime = end.atTime(LocalTime.of(16, 15, 0));
        String beginWithoutZero = DATE_TIME_FORMATTER.format(beginDateTime);
        String endWithoutZero = DATE_TIME_FORMATTER.format(endDateTime);
        final String eightAM = DATE_TIME_FORMATTER_Z.format(begin.atTime(LocalTime.of(8, 0, 0)));
        final String threePM = DATE_TIME_FORMATTER_Z.format(begin.atTime(LocalTime.of(15, 0, 0)));
        final String fourPM = DATE_TIME_FORMATTER_Z.format(begin.atTime(LocalTime.of(16, 0, 0)));
        final String quarterPastFourPM = DATE_TIME_FORMATTER_Z.format(begin.atTime(LocalTime.of(16, 15, 0)));

        List<TimeSheetGet> registeredSchedules = apiKimai.getRecentSchedules(beginWithoutZero, endWithoutZero, credentials);
        if (!registeredSchedules.isEmpty()) {
            for (TimeSheetGet registeredSchedule : registeredSchedules) {
                if (isCompleteSchedule(eightAM, threePM, fourPM, quarterPastFourPM, registeredSchedule)) {
                    //if(daysLeft == 0) {
                        throw new OwnExceptions.RegisteredSchedulesException(registeredSchedule);
                    /*} else {
                        throw new OwnExceptions.RegisteredSchedulesDiscoveredButMustContinueException(registeredSchedule);
                    }*/
                } else {
                    throw new OwnExceptions.RegisteredSchedulesDiscoveredException(registeredSchedule);
                }
            }
        }
        return Collections.emptyList();
    }

    private boolean isCompleteSchedule(String eightAM, String threePM, String fourPM, String quarterPastFourPM, TimeSheetGet registeredSchedule) {
        return (checkScheduleBounds(eightAM, fourPM, registeredSchedule) ||
                checkScheduleBounds(eightAM, threePM, registeredSchedule) ||
                checkScheduleBounds(fourPM, quarterPastFourPM, registeredSchedule));
    }

    private boolean checkScheduleBounds(String start, String end, TimeSheetGet registeredSchedule) {
        return registeredSchedule.getBegin().equals(Instant.parse(start)) &&
                (registeredSchedule.getEnd().equals(Instant.parse(end)));
    }

    private boolean isHolidays(LocalDate begin) {
        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(2022, 1, 1));
        holidays.add(LocalDate.of(2022, 1, 6));
        holidays.add(LocalDate.of(2022, 4, 15));
        holidays.add(LocalDate.of(2022, 8, 15));
        holidays.add(LocalDate.of(2022, 10, 12));
        holidays.add(LocalDate.of(2022, 11, 1));
        holidays.add(LocalDate.of(2022, 12, 6));
        holidays.add(LocalDate.of(2022, 12, 8));

        return holidays.contains(begin);
    }

    private static final EnumSet<DayOfWeek> WEEKEND = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private boolean isWeekend(LocalDate begin) {
        return WEEKEND.contains(begin.getDayOfWeek());
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

    public List<TimeSheetPost> checkNumberOfDates(TimeSheet days, UserData credentials) {
        List<TimeSheetPost> registeredDays = new ArrayList<>();
        long daysOfDifference = DAYS.between(days.getBegin(), days.getEnd());
        LocalDate beginDate = days.getBegin();
        long daysLeft;

        for (int i = 0; i <= daysOfDifference; i++) {
            daysLeft = daysOfDifference - i;
            TimeSheet newDay = new TimeSheet(beginDate.plusDays(i), beginDate.plusDays(i), days.getProject(), days.getActivity());
            registeredDays.addAll(registerOneDay(newDay, credentials, daysLeft));
        }
        return registeredDays;
    }

    public List<TimeSheetPost> modifyDate(TimeSheet newSchedule, UserData credentials) {
        final LocalDateTime beginDateTime = newSchedule.getBegin().atTime(LocalTime.of(8, 0, 0));
        final LocalDateTime endDateTime = newSchedule.getEnd().atTime(LocalTime.of(16, 15, 0));
        String beginWithoutZero = DATE_TIME_FORMATTER.format(beginDateTime);
        String endWithoutZero = DATE_TIME_FORMATTER.format(endDateTime);
        List<TimeSheetGet> registeredSchedules = apiKimai.getRecentSchedules(beginWithoutZero, endWithoutZero, credentials);
        for (TimeSheetGet registeredSchedule : registeredSchedules) {
            apiKimai.deleteSchedule(registeredSchedule.getId(), credentials);
        }
        List<TimeSheetPost> timeSheetPosts = checkDate(newSchedule, credentials);
        return timeSheetPosts;
    }

    public List<Projects> login(UserData userData) {
        return apiKimai.login(userData);
    }

    public List<TimeSheetGet> getSchedules(String username, String token) {
        return apiKimai.getSchedules(username, token);
    }

    public List<Projects> getProjects(String username, String token) {
        return apiKimai.getProjects(username, token);
    }

    public List<Activities> getActivities(String username, String token) {
        return apiKimai.getActivities(username, token);
    }
}
