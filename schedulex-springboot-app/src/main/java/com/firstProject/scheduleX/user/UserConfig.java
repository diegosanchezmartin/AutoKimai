package com.firstProject.scheduleX.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository){
        return args -> {
            User Diego = new User(
                    "@dsanchezm",
                    LocalDate.of(2022, Month.FEBRUARY, 21),
                    LocalDate.of(2022, Month.FEBRUARY, 23)
            );

            User Alex = new User(
                    "@alejandro",
                    LocalDate.of(2022, Month.FEBRUARY, 20),
                    LocalDate.of(2022, Month.FEBRUARY, 28)
            );

            repository.saveAll(
                    List.of(Diego, Alex)
            );
        };
    }
}
