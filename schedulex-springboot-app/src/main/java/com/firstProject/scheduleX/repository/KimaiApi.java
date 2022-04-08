package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;

import java.util.List;

public interface KimaiApi {

    void addHoursAPi(TimeSheetPost horarioNuevo, UserData credentials);

    List<TimeSheetGet> getSchedules(String username, String token);

    List<Projects> getProjects(String username, String token);

    List<Activities> getActivities(String username, String token);

    List<TimeSheetGet> getRecentSchedules(String begin, String end, UserData credentials);

    void deleteSchedule(int id, UserData credentials);

    List<Projects> login(UserData userData);
}
