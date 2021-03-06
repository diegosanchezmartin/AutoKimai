package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TimeSheetController {
    private final TimeSheetService timeSheetService;

    @Autowired
    public TimeSheetController(TimeSheetService timeSheetService) {
        this.timeSheetService = timeSheetService;
    }

    @GetMapping("api/v1/projects")
    public List<Projects> getProjects(@RequestParam String username, String token) {
        return timeSheetService.getProjects(username, token);
    }

    @GetMapping("api/v1/activities")
    public List<Activities> getActivities(@RequestParam String username, String token) {
        return timeSheetService.getActivities(username, token);
    }

    @GetMapping("api/v1/timesheets")
    public List<TimeSheetGet> getHorarios(@RequestParam String username, String token){
       return timeSheetService.getSchedules(username, token);
    }

    @PostMapping("api/v1/createSchedule")
    public ResponseEntity registerUserHoursAPI(@RequestBody Request request){
        try {
            timeSheetService.checkDate(request.getNewSchedule(), request.getCredentials());
            return new ResponseEntity(HttpStatus.OK);
        } catch (IllegalArgumentException datesException) {
            datesException.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(request.getNewSchedule());
        } catch (OwnExceptions.RegisteredSchedulesException scheduleException) {
            scheduleException.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(scheduleException);
        } catch (OwnExceptions.RegisteredSchedulesDiscoveredException schedulesDiscoveredException) {
            schedulesDiscoveredException.printStackTrace();
            return ResponseEntity.status(HttpStatus.LOCKED).body(schedulesDiscoveredException);
        } catch (OwnExceptions.RegisteredSchedulesDiscoveredButMustContinueException scheduleDiscoverButContinueException) {
            scheduleDiscoverButContinueException.printStackTrace();
            return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(scheduleDiscoverButContinueException);
        }
    }

    @PostMapping("api/v1/modifySchedule")
    public void modifyUserHoursAPI(@RequestBody Request request){
        timeSheetService.modifyDate(request.getNewSchedule(), request.getCredentials());
    }

    @PostMapping("api/v1/login")
    public ResponseEntity login(@RequestBody UserData userData){
        System.out.println(userData);
        List<Projects> login = timeSheetService.login(userData);
        if(login.isEmpty()){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
