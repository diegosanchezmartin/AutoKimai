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
    void checkOneDay() throws Exception {
        TimeSheetService timeSheetService = new TimeSheetService();
        List<TimeSheetPost> lista = new ArrayList<>();
        timeSheetService.apiKimai = new KimaiApi() {
            @Override
            public void addHoursAPi(TimeSheetPost horarioNuevo) {
                lista.add(horarioNuevo);
            }

            @Override
            public List<TimeSheetGet> getHorarios() {
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
        TimeSheet horarioNuevo = new TimeSheet();
        horarioNuevo.setBegin(LocalDate.now());
        horarioNuevo.setEnd(LocalDate.now());
        timeSheetService.checkDays(horarioNuevo);
        System.out.println(lista);
        assertEquals(Collections.emptyList(), lista);
    }
}