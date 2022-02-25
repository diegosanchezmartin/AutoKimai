package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    @Query("SELECT s FROM User s WHERE s.nombreDeUsuario = ?1")
    Optional<User> findUserByNombreDeUsuario(String nombreDeUsuario);
}
