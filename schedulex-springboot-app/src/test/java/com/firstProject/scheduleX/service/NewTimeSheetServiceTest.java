package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import com.firstProject.scheduleX.repository.KimaiRestApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewTimeSheetServiceTest {

    private TimeSheetService2 getTimeSheetService() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        return timeSheetService2;
    }

    @Test
    void when_register_earlier_end_date_than_begin_date() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 22),
                LocalDate.of(2022, 3, 21),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_a_day_between_monday_and_thursday() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 17),
                LocalDate.of(2022, 3, 17),
                1,
                1
        );
        List<TimeSheetPost> registeredDayExpected = new ArrayList<>();
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 3, 17).toString() + "T08:00:00",
                LocalDate.of(2022, 3, 17).toString() + "T16:00:00",
                1,
                1
                );
        registeredDayExpected.add(registeredDayExpectedMorning);
        TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                LocalDate.of(2022, 3, 17).toString() + "T16:00:00",
                LocalDate.of(2022, 3, 17).toString() + "T16:15:00",
                1,
                1
        );
        registeredDayExpected.add(registeredDayExpectedAfternoon);
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_friday() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 18),
                LocalDate.of(2022, 3, 18),
                1,
                1
        );
        List<TimeSheetPost> registeredDayExpected = new ArrayList<>();
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 3, 18).toString() + "T08:00:00",
                LocalDate.of(2022, 3, 18).toString() + "T15:00:00",
                1,
                1
        );
        registeredDayExpected.add(registeredDayExpectedMorning);
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_saturday_or_sunday_then_expect_empty_list() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 19),
                LocalDate.of(2022, 3, 19),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_thursday() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet days = new TimeSheet(
                LocalDate.of(2022, 3, 14),
                LocalDate.of(2022, 3, 17),
                1,
                1
        );
        List<TimeSheetPost> registeredDaysExpected = new ArrayList<>();
        for(int i=14;i<18;i++){
            TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T08:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedMorning);
            TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:15:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedAfternoon);
        }
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_friday() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet days = new TimeSheet(
                LocalDate.of(2022, 3, 14),
                LocalDate.of(2022, 3, 18),
                1,
                1
        );
        List<TimeSheetPost> registeredDaysExpected = new ArrayList<>();
        for(int i=14;i<18;i++){
            TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T08:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedMorning);
            TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:15:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedAfternoon);
        }
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 3, 18).toString() + "T08:00:00",
                LocalDate.of(2022, 3, 18).toString() + "T15:00:00",
                1,
                1
        );
        registeredDaysExpected.add(registeredDayExpectedMorning);
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_saturdays_and_sundays() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet days = new TimeSheet(
                LocalDate.of(2022, 3, 14),
                LocalDate.of(2022, 3, 20),
                1,
                1
        );
        List<TimeSheetPost> registeredDaysExpected = new ArrayList<>();
        for(int i=14;i<18;i++){
            TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T08:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedMorning);
            TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                    LocalDate.of(2022, 3, i).toString() + "T16:00:00",
                    LocalDate.of(2022, 3, i).toString() + "T16:15:00",
                    1,
                    1
            );
            registeredDaysExpected.add(registeredDayExpectedAfternoon);
        }
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 3, 18).toString() + "T08:00:00",
                LocalDate.of(2022, 3, 18).toString() + "T15:00:00",
                1,
                1
        );
        registeredDaysExpected.add(registeredDayExpectedMorning);
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_a_holiday_then_expect_empty_list() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_holidays() {
        TimeSheetService2 timeSheetService2 = getTimeSheetService();
        TimeSheet days = new TimeSheet(
                LocalDate.of(2022, 1, 3),
                LocalDate.of(2022, 1, 7),
                1,
                1
        );
        List<TimeSheetPost> registeredDaysExpected = new ArrayList<>();
        for(int i=3;i<7;i++){
            if(i!=6) {
                TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                        LocalDate.of(2022, 1, i).toString() + "T08:00:00",
                        LocalDate.of(2022, 1, i).toString() + "T16:00:00",
                        1,
                        1
                );
                registeredDaysExpected.add(registeredDayExpectedMorning);
                TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                        LocalDate.of(2022, 1, i).toString() + "T16:00:00",
                        LocalDate.of(2022, 1, i).toString() + "T16:15:00",
                        1,
                        1
                );
                registeredDaysExpected.add(registeredDayExpectedAfternoon);
            }
        }
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 1, 7).toString() + "T08:00:00",
                LocalDate.of(2022, 1, 7).toString() + "T15:00:00",
                1,
                1
        );
        registeredDaysExpected.add(registeredDayExpectedMorning);
        List<TimeSheetPost> registeredDay = timeSheetService2.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_repeated_schedule_then_dont_call_api() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService2.apiKimai.getRecentSchedules(Mockito.any(),Mockito.any())).thenReturn(
                List.of(getTimeSheetGetMorning(), getTimeSheetGetAfternoon())
        );
        TimeSheet day = new TimeSheet(
                LocalDate.now(),
                LocalDate.now(),
                1,
                1
        );
        List<TimeSheetPost> timeSheetPosts = timeSheetService2.checkDate(day);
        assertEquals(Collections.emptyList(), timeSheetPosts);
    }

    @Test
    void when_register_non_repeated_schedule_then_call_api_2_times() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet day = new TimeSheet(
                LocalDate.now(),
                LocalDate.now(),
                1,
                1
        );
        timeSheetService2.checkDate(day);
        Mockito.verify(timeSheetService2.apiKimai, Mockito.times(2)).addHoursAPi(Mockito.any());
    }

    private TimeSheetGet getTimeSheetGetAfternoon() {
        LocalDate now = LocalDate.now();
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(16, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,15,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getTimeSheetGetMorning() {
        LocalDate now = LocalDate.now();
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(8, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,0,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    @Test
    void getSchedules() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        //timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        //Mockito.when(timeSheetService.apiKimai.getSchedules()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(),timeSheetService2.getSchedules());
    }

    @Test
    void getProjects() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService2.apiKimai.getProjects()).thenReturn(Collections.emptyList());
        timeSheetService2.getProjects();
        Mockito.verify(timeSheetService2.apiKimai,Mockito.times(1)).getProjects();
    }

    @Test
    void getActivities() {
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService2.apiKimai.getActivities()).thenReturn(Collections.emptyList());
        timeSheetService2.getActivities();
        Mockito.verify(timeSheetService2.apiKimai,Mockito.times(1)).getActivities();
    }


}