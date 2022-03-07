package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
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
        return timeSheetService.getHorarios();
    }

    @PostMapping("api/v1/user")
    public void registrarHorasUsuarioAPI(@RequestBody TimeSheet horarioNuevo){
        if(horarioNuevo.getBegin().equals(horarioNuevo.getEnd())) {
            try {
                timeSheetService.comprobarUnDia(horarioNuevo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                timeSheetService.comprobarMasDeUnDia(horarioNuevo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(horarioNuevo);
        System.out.println(getHorarios());

    }
}
