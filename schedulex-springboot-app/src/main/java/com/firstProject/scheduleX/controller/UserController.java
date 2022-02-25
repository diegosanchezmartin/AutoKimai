package com.firstProject.scheduleX.controller;

import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.User;
import com.firstProject.scheduleX.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void registrarHorasUsuarioAPI(@RequestBody TimeSheet horarioNuevo1){
        TimeSheet horarioNuevo =  new TimeSheet(
                "2022-02-23T08:00:00",
                "2022-02-23T16:00:00",
                1,
                1,
                "string",
                0,
                0,
                1,
                true,
                true,
                "string"
        );

        System.out.println(horarioNuevo1);
        userService.añadirHorasApi(horarioNuevo1);
    }
}
