package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.Activities;
import com.firstProject.scheduleX.model.Projects;
import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.TimeSheetGet;
import com.firstProject.scheduleX.service.TimeSheetService;
import com.firstProject.scheduleX.service.TimeSheetService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TimeSheetController {
    private final TimeSheetService2 timeSheetService2;

    @Autowired
    public TimeSheetController(TimeSheetService2 timeSheetService2) {
        this.timeSheetService2 = timeSheetService2;
    }

    @GetMapping("api/v1/projects")
    public List<Projects> getProjects() {
        return timeSheetService2.getProjects();
    }

    @GetMapping("api/v1/activities")
    public List<Activities> getActivities() {
        return timeSheetService2.getActivities();
    }

    @GetMapping("api/timesheets")
    public List<TimeSheetGet> getHorarios(){
       return timeSheetService2.getSchedules();
    }
    @PostMapping("api/v1/user")
    public ResponseEntity registerUserHoursAPI(@RequestBody TimeSheet newSchedule){
        timeSheetService2.checkDate(newSchedule);
        return null;
    }

}
