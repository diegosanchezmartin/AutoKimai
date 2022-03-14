package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;

public interface KimaiApi {

    void addHoursAPi(TimeSheetPost horarioNuevo);

    List<TimeSheetGet> getSchedules();

    List<Projects> getProjects();

    List<Activities> getActivities();

    List<TimeSheetGet> getRecentSchedules();
}
