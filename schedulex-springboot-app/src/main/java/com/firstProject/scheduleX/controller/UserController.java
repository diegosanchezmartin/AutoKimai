package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.User;
import com.firstProject.scheduleX.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    //Post es para añadir nuevos usuarios a nuestra app
    /*@PostMapping
    public void registerNewUser(@RequestBody User usuario){
        userService.addNewUser(usuario);
    }*/

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable ("userId") Long userId){
        userService.deleteUser(userId);
    }

    /*@PutMapping(path = {"userName"})
    public void updateUser(
            @PathVariable ("userName") String nombreDeUsuario,
            @RequestParam(required=true) LocalDate fechaInicio,
            @RequestParam(required=true) LocalDate fechaFin) {
        userService.updateUser(nombreDeUsuario, fechaInicio, fechaFin);
    }*/

    @PostMapping
    public void registrarHorasUsuarioAPI(@RequestBody TimeSheet horarioNuevo){
        if(horarioNuevo.getBegin().equals(horarioNuevo.getEnd())) {
            try {
                userService.comprobarUnDia(horarioNuevo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(horarioNuevo);
    }
}
