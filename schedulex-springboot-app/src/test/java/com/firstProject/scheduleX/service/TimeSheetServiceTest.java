package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSheetServiceTest {

    static class KimaiApiTest implements  KimaiApi {
        List<TimeSheetPost> list = new ArrayList<>();
        @Override
        public void addHoursAPi(TimeSheetPost horarioNuevo) {
            list.add(horarioNuevo);
        }

        @Override
        public List<TimeSheetGet> getSchedules() {
            return Collections.emptyList();
        }

        @Override
        public List<Projects> getProjects() {
            return Collections.emptyList();
        }

        @Override
        public List<Activities> getActivities() {
            return Collections.emptyList();
        }

        @Override
        public List<TimeSheetGet> getRecentSchedules(String begin, String end) {
            return Collections.emptyList();
        }
    }

/*
    @Test
    void when_monday_to_tuesday_then_track_two_times() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = apiKimai;
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2020, 1, 1));
        newSchedule.setEnd(LocalDate.of(2020, 1, 1));
        timeSheetService.checkDays(newSchedule);
        TimeSheetPost morning = new TimeSheetPost();
        morning.setBegin("2020-01-01T08:00:00");
        morning.setEnd("2020-01-01T16:00:00");
        TimeSheetPost afternoon= new TimeSheetPost();
        afternoon.setBegin("2020-01-01T16:00:00");
        afternoon.setEnd("2020-01-01T16:15:00");
        assertEquals(List.of(morning,afternoon), apiKimai.list);
    }

    @Test
    void when_friday_then_track_seven_hours() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = apiKimai;
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 3, 4));
        newSchedule.setEnd(LocalDate.of(2022, 3, 4));
        timeSheetService.checkDays(newSchedule);
        TimeSheetPost morning = new TimeSheetPost();
        morning.setBegin("2022-03-04T08:00:00");
        morning.setEnd("2022-03-04T15:00:00");
        assertEquals(List.of(morning), apiKimai.list);
    }


    @Test
    void when_register_one_week_track_nine_events() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = apiKimai;
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 3, 7));
        newSchedule.setEnd(LocalDate.of(2022, 3, 13));
        timeSheetService.checkDays(newSchedule);
        assertEquals(9, apiKimai.list.size());
    }

    @Test
    void when_register_on_saturdays_then_no_events() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 3, 12));
        newSchedule.setEnd(LocalDate.of(2022, 3, 12));
        timeSheetService.checkDays(newSchedule);
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(0)).addHoursAPi(Mockito.any());
    }

    @Test
    void when_register_on_sundays_then_no_events() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 3, 13));
        newSchedule.setEnd(LocalDate.of(2022, 3, 13));
        timeSheetService.checkDays(newSchedule);
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(0)).addHoursAPi(Mockito.any());
    }

    @Test
    void when_register_on_holidays_then_no_events() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 1, 1));
        newSchedule.setEnd(LocalDate.of(2022, 1, 1));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 1, 6));
        newSchedule.setEnd(LocalDate.of(2022, 1, 6));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 4, 15));
        newSchedule.setEnd(LocalDate.of(2022, 4, 15));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 8, 15));
        newSchedule.setEnd(LocalDate.of(2022, 8, 15));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 10, 12));
        newSchedule.setEnd(LocalDate.of(2022, 10, 12));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 11, 1));
        newSchedule.setEnd(LocalDate.of(2022, 11, 1));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 12, 6));
        newSchedule.setEnd(LocalDate.of(2022, 12, 6));
        timeSheetService.checkDays(newSchedule);
        newSchedule.setBegin(LocalDate.of(2022, 12, 8));
        newSchedule.setEnd(LocalDate.of(2022, 12, 8));
        timeSheetService.checkDays(newSchedule);
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(0)).addHoursAPi(Mockito.any());
    }

    @Test
    void when_register_a_repeated_day_then_no_events() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.of(2022, 3, 16));
        newSchedule.setEnd(LocalDate.of(2022, 3, 16));
        //Mock
        timeSheetService.checkDays(newSchedule);
        TimeSheet repeatedSchedule = new TimeSheet();
        repeatedSchedule.setBegin(LocalDate.of(2022, 3, 16));
        repeatedSchedule.setEnd(LocalDate.of(2022, 3, 16));
        timeSheetService.checkDays(repeatedSchedule);
        Mockito.verify(timeSheetService.apiKimai,Mockito.times(2)).addHoursAPi(Mockito.any());
    }
*/
    @Test
    void when_register_a_day_between_monday_and_thursday() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerOneDay(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_friday() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerOneDay(day);
        assertEquals(registeredDayExpected, registeredDay);
    }

    @Test
    void when_register_a_saturday_or_sunday_then_expect_empty_list() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 19),
                LocalDate.of(2022, 3, 19),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.registerOneDay(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_thursday() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerMoreThanOneDay(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_between_monday_and_friday() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerMoreThanOneDay(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_saturdays_and_sundays() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerMoreThanOneDay(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_a_holiday_then_expect_empty_list() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 1, 1),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.registerOneDay(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }

    @Test
    void when_register_more_than_one_day_including_holidays() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
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
        List<TimeSheetPost> registeredDay = timeSheetService2.registerMoreThanOneDay(days);
        assertEquals(registeredDaysExpected, registeredDay);
    }

    @Test
    void when_register_repeated_schedule_then_call_api_2_times_only() {
        final KimaiApiTest apiKimai = new KimaiApiTest();
        TimeSheetService2 timeSheetService2 = new TimeSheetService2();
        timeSheetService2.apiKimai = apiKimai;
        TimeSheet day = new TimeSheet(
                LocalDate.of(2022, 3, 19),
                LocalDate.of(2022, 3, 19),
                1,
                1
        );
        List<TimeSheetPost> registeredDay = timeSheetService2.registerOneDay(day);
        assertEquals(Collections.emptyList(), registeredDay);
    }
/*
    @Test
    void getSchedules() {
        TimeSheetService timeSheetService = new TimeSheetService();
        timeSheetService.apiKimai = new KimaiApiTest();
        //timeSheetService.apiKimai = Mockito.mock(KimaiApi.class);
        //Mockito.when(timeSheetService.apiKimai.getSchedules()).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(),timeSheetService.getSchedules());
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

 */
}