package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;

import java.util.List;

public interface KimaiApi {

    void addHoursAPi(TimeSheetPost horarioNuevo, UserData credentials);

    List<TimeSheetGet> getSchedules();

    List<Projects> getProjects();

    List<Activities> getActivities();

    List<TimeSheetGet> getRecentSchedules(String begin, String end);

    void deleteSchedule(int id);

    List<Projects> login(UserData userData);
}
