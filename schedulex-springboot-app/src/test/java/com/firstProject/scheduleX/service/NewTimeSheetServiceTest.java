package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewTimeSheetServiceTest {

    private TimeSheetService getTimeSheetService() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        return timeSheetService;
    }

    @Test
    void when_register_earlier_end_date_than_begin_date_then_throw_IllegalArgumentException() {
        TimeSheetService timeSheetService = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 22),
                LocalDate.of(2022, 3, 21),
                1,
                1
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            List<TimeSheetPost> registeredDay = timeSheetService.checkDate(day);
        });

        String expectedMessage = "Error: Begin is before End: TimeSheet(begin=2022-03-22, end=2022-03-21, project=1, activity=1)";
        String receivedMessage = exception.getMessage();
        assertTrue(receivedMessage.contains(expectedMessage));
    }

    @Test
    void when_register_a_day_between_monday_and_thursday() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_friday() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_saturday_or_sunday_then_expect_empty_list() {
        TimeSheetService timeSheetService = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 19),
                LocalDate.of(2022, 3, 19),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_thursday() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_friday() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_saturdays_and_sundays() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_a_holiday_then_expect_empty_list() {
        TimeSheetService timeSheetService = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_holidays() {
        TimeSheetService timeSheetService = getTimeSheetService();
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
        List<TimeSheetPost> registeredDay = timeSheetService.checkDate(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test()
    void when_register_repeated_schedule_between_monday_and_thursday_then_expect_RegisteredSchedulesException() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getRecentSchedules(Mockito.any(),Mockito.any())).thenReturn(
                List.of(getTimeSheetGetMorningMonToThurs(), getTimeSheetGetAfternoonMonToThurs())
        );
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 29),
                LocalDate.of(2022, 3, 29),
                1,
                1
        );

        Assertions.assertThrows(OwnExceptions.RegisteredSchedulesException.class, () -> {
            List<TimeSheetPost> timeSheetPosts = timeSheetService.checkDate(day);
        });
    }

    @Test()
    void when_register_repeated_schedule_on_friday_then_expect_RegisteredSchedulesException() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getRecentSchedules(Mockito.any(),Mockito.any())).thenReturn(
                List.of(getTimeSheetGetMorningFri())
        );
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 4, 1),
                LocalDate.of(2022, 4, 1),
                1,
                1
        );

        Assertions.assertThrows(OwnExceptions.RegisteredSchedulesException.class, () -> {
            List<TimeSheetPost> timeSheetPosts = timeSheetService.checkDate(day);
        });
    }

    @Test()
    void when_register_repeated_schedule_between_monday_and_thursday_and_discover_registered_schedules_then_expect_RegisteredSchedulesDiscoveredException() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getRecentSchedules(Mockito.any(),Mockito.any())).thenReturn(
                List.of(getIncompleteTimeSheetGetMorningMonToThur(), getTimeIncompleteSheetGetAfternoonMonToThur())
        );
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 29),
                LocalDate.of(2022, 3, 29),
                1,
                1
        );

        Assertions.assertThrows(OwnExceptions.RegisteredSchedulesDiscoveredException.class, () -> {
            List<TimeSheetPost> timeSheetPosts = timeSheetService.checkDate(day);
        });
    }

    @Test()
    void when_register_repeated_schedule_on_friday_and_discover_registered_schedules_then_expect_RegisteredSchedulesDiscoveredException() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getRecentSchedules(Mockito.any(),Mockito.any())).thenReturn(
                List.of(getIncompleteTimeSheetGetMorningFri())
        );
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 4, 1),
                LocalDate.of(2022, 4, 1),
                1,
                1
        );

        Assertions.assertThrows(OwnExceptions.RegisteredSchedulesDiscoveredException.class, () -> {
            List<TimeSheetPost> timeSheetPosts = timeSheetService.checkDate(day);
        });
    }

    @Test
    void when_register_non_repeated_schedule_then_call_api_2_times() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet day = new TimeSheet(
                LocalDate.now(),
                LocalDate.now(),
                1,
                1
        );
        timeSheetService.checkDate(day);
        Mockito.verify(timeSheetService.apiKimai, Mockito.times(2)).addHoursAPi(Mockito.any());
    }

    private TimeSheetGet getTimeSheetGetAfternoonMonToThurs() {
        LocalDate now = LocalDate.of(2022, 3, 29);
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(16, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,15,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getTimeSheetGetMorningMonToThurs() {
        LocalDate now = LocalDate.of(2022, 3, 29);
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(8, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,0,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getTimeSheetGetMorningFri() {
        LocalDate now = LocalDate.of(2022, 4, 1);
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(8, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,0,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getTimeIncompleteSheetGetAfternoonMonToThur() {
        LocalDate now = LocalDate.now();
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(16, 1, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(16,13,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getIncompleteTimeSheetGetMorningMonToThur() {
        LocalDate now = LocalDate.now();
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(9, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(13,0,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    private TimeSheetGet getIncompleteTimeSheetGetMorningFri() {
        LocalDate now = LocalDate.now();
        TimeSheetGet timeSheetGet = new TimeSheetGet();
        timeSheetGet.setBegin(now.atTime(9, 0, 0).toInstant(ZoneOffset.UTC));
        timeSheetGet.setEnd(now.atTime(13,0,0).toInstant(ZoneOffset.UTC));
        return timeSheetGet;
    }

    @Test
    void when_user_click_continue_the_delete_schedule_and_create_new_one() {
        TimeSheetService timeSheetService = getTimeSheetService();
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 29),
                LocalDate.of(2022, 3, 29),
                1,
                1
        );
        List<TimeSheetPost> registeredDayExpected = new ArrayList<>();
        TimeSheetPost registeredDayExpectedMorning = new TimeSheetPost(
                LocalDate.of(2022, 3, 29).toString() + "T08:00:00",
                LocalDate.of(2022, 3, 29).toString() + "T16:00:00",
                1,
                1
        );
        registeredDayExpected.add(registeredDayExpectedMorning);
        TimeSheetPost registeredDayExpectedAfternoon = new TimeSheetPost(
                LocalDate.of(2022, 3, 29).toString() + "T16:00:00",
                LocalDate.of(2022, 3, 29).toString() + "T16:15:00",
                1,
                1
        );

        registeredDayExpected.add(registeredDayExpectedAfternoon);
        List<TimeSheetPost> registeredDay = timeSheetService.modifyDate(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void getSchedules() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        //timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        //Mockito.when(timeSheetService.apiKimai.getSchedules()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), timeSheetService.getSchedules());
    }

    @Test
    void getProjects() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getProjects()).thenReturn(Collections.emptyList());
        timeSheetService.getProjects();
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(1)).getProjects();
    }

    @Test
    void getActivities() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        Mockito.when(timeSheetService.apiKimai.getActivities()).thenReturn(Collections.emptyList());
        timeSheetService.getActivities();
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(1)).getActivities();
    }


}