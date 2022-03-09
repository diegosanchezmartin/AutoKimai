package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.KimaiApi;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeSheetServiceTest {

    @Test
    void checkDays() {
        TimeSheetService timeSheetService = new TimeSheetService();
        List<TimeSheetPost> list = new ArrayList<>();
        timeSheetService.apiKimai = new KimaiApi() {
            @Override
            public void addHoursAPi(TimeSheetPost horarioNuevo) {
                list.add(horarioNuevo);
            }

            @Override
            public List<TimeSheetGet> getSchedules() {
                return null;
            }

            @Override
            public List<Projects> getProjects() {
                return null;
            }

            @Override
            public List<Activities> getActivities() {
                return null;
            }
        };

        TimeSheet newSchedule = new TimeSheet();
        newSchedule.setBegin(LocalDate.now());
        newSchedule.setEnd(LocalDate.now());
        timeSheetService.checkDays(newSchedule);
        System.out.println(list);
        assertEquals(Collections.emptyList(), list);
    }

    @Test
    void getSchedules() {

    }

    @Test
    void getProjects() {

    }

    @Test
    void getActivities() {

    }
}