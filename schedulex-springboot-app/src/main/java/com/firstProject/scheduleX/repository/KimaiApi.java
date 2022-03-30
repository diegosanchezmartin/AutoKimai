package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface KimaiApi {

    void addHoursAPi(TimeSheetPost horarioNuevo);

    List<TimeSheetGet> getSchedules();

    List<Projects> getProjects();

    List<Activities> getActivities();

    List<TimeSheetGet> getRecentSchedules(String begin, String end);

    void deleteSchedule(int id);

    List<Projects> login(UserData userData);
}
