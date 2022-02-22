package com.firstProject.scheduleX.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    @PostMapping
    public void registerNewUser(@RequestBody User usuario){
        userService.addNewUser(usuario);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable ("userId") Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping(path = {"userName"})
    public void updateUser(
            @PathVariable ("userName") String nombreDeUsuario,
            @RequestParam(required=true) LocalDate fechaInicio,
            @RequestParam(required=true) LocalDate fechaFin) {
        userService.updateUser(nombreDeUsuario, fechaInicio, fechaFin);
    }
}
