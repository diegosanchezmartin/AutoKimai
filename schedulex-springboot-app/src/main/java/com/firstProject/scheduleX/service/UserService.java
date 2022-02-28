package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.User;
import com.firstProject.scheduleX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private WebClient webClient = WebClient.create("http://localhost:8001");

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User usuario) {
        Optional<User> usuarioPorNombre =
                userRepository.findUserByNombreDeUsuario((usuario.getnombreDeUsuario()));
        if(usuarioPorNombre.isPresent()) {
            throw new IllegalStateException("El nombre de usuario ya está elegido");
        }
        userRepository.save(usuario);
    }

    public void deleteUser(Long userId) {
        boolean existe = userRepository.existsById(userId);
        if(!existe){
            throw new IllegalStateException(
                    "Usuario con el id: " + userId + " no existe"
            );
        }
        userRepository.deleteById(userId);
    }


    public void updateUser(String nombreDeUsuario, LocalDate fechaInicio, LocalDate fechaFin) {
        User usuario = userRepository.findUserByNombreDeUsuario(nombreDeUsuario)
                .orElseThrow(() -> new IllegalStateException(
                        "El usuario con nombre " + nombreDeUsuario + " no existe"
                ));

        if(fechaInicio != null && fechaInicio.isSupported(ChronoField.YEAR_OF_ERA) &&
        fechaInicio.isSupported(ChronoField.MONTH_OF_YEAR) && fechaInicio.isSupported(ChronoField.DAY_OF_MONTH)
        && fechaInicio.isSupported(ChronoField.ERA)) {
            usuario.setFechaInicio(fechaInicio);
        }

        if(fechaFin != null && fechaFin.isSupported(ChronoField.YEAR_OF_ERA) &&
                fechaFin.isSupported(ChronoField.MONTH_OF_YEAR) && fechaFin.isSupported(ChronoField.DAY_OF_MONTH)
                && fechaFin.isSupported(ChronoField.ERA)) {
            usuario.setFechaInicio(fechaFin);
        }
    }

    public void añadirHorasApi(TimeSheet horarioNuevo) {

        ResponseEntity<String> block = webClient.post()
                .uri("/api/timesheets")
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(horarioNuevo))
                .retrieve()
                .toEntity(String.class)
                .block();

        System.out.println(block.getStatusCode());
        System.out.println(block.getBody());

    }

    public void comprobarUnDia(TimeSheet horarioNuevo) throws Exception {
        SimpleDateFormat formatearFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        try {
            fechaInicio = formatearFecha.parse(horarioNuevo.getBegin());
        } catch(ParseException e) {
            System.out.println("No se ha podido formatear la fecha de inicio de la actividad");
        }
        GregorianCalendar calendario = new GregorianCalendar();
        calendario.setTime(fechaInicio);
        int diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        switch(diaDeLaSemana) {
            case 1:
                throw new Exception ("Dia introcido incorrecto: El domingo no se trabaja");
            case 2:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,10) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,10) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 3:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,11) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,11) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 4:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,11) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,11) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 5:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,11) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,11) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 6:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T15:00:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 7:
                throw new Exception ("Dia introcido incorrecto: El sábado no se trabaja");
        }
    }
}
