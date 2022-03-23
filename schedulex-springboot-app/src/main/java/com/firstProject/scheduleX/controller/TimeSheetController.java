package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TimeSheetController {
    private final TimeSheetService timeSheetService;

    @Autowired
    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @GetMapping("api/v1/projects")
    public List<Projects> getProjects() {
        return timeSheetService.getProjects();
    }

    @GetMapping("api/v1/activities")
    public List<Activities> getActivities() {
        return timeSheetService.getActivities();
    }

    @GetMapping("api/timesheets")
    public List<TimeSheetGet> getHorarios(){
       return timeSheetService.getSchedules();
    }

    @PostMapping("api/v1/user")
    public ResponseEntity registerUserHoursAPI(@RequestBody TimeSheet newSchedule){
        try {
            timeSheetService.checkDate(newSchedule);
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

}
