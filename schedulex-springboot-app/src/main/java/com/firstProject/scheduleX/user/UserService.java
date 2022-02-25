package com.firstProject.scheduleX.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

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

    public void añadirHorasApi(User usuario) {
        TimeSheet horarioNuevo =  new TimeSheet(
                "2022-02-26T08:00:00",
                "2022-02-26T16:00:00",
                1,
                1,
                "string",
                0,
                0,
                1,
                true,
                true,
                "strings"
        );

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
}
