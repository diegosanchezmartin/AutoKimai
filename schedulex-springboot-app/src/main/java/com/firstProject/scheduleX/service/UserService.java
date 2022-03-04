package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.User;
import com.firstProject.scheduleX.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

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
}
