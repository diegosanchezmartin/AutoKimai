package com.firstProject.scheduleX.service;

import com.firstProject.scheduleX.model.*;
import com.firstProject.scheduleX.repository.TimeSheetInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class TimeSheetService implements TimeSheetInterface {

    private WebClient webClient = WebClient.create("http://localhost:8001");

    public void añadirHorasApi(TimeSheetPost horarioNuevo) {
        ResponseEntity<String> block = webClient.post()
                .uri("/api/timesheets")
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(horarioNuevo))
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    public TimeSheetPost crearDiaPorLaMañana(int diaDeLaSemana, TimeSheet horarioNuevo) {
        TimeSheetPost horarioMañana;
        if(diaDeLaSemana != 5){
            horarioMañana = new TimeSheetPost(
                    horarioNuevo.getBegin().toString() + "T08:00:00",
                    horarioNuevo.getEnd().toString() + "T16:00:00",
                    horarioNuevo.getProject(),
                    horarioNuevo.getActivity(),
                    horarioNuevo.getDescription(),
                    horarioNuevo.getFixedRate(),
                    horarioNuevo.getHourlyRate(),
                    horarioNuevo.getUser(),
                    horarioNuevo.isExported(),
                    horarioNuevo.isBillable(),
                    horarioNuevo.getTags());
        } else {
            horarioMañana = new TimeSheetPost(
                    horarioNuevo.getBegin().toString() + "T08:00:00",
                    horarioNuevo.getEnd().toString() + "T15:00:00",
                    horarioNuevo.getProject(),
                    horarioNuevo.getActivity(),
                    horarioNuevo.getDescription(),
                    horarioNuevo.getFixedRate(),
                    horarioNuevo.getHourlyRate(),
                    horarioNuevo.getUser(),
                    horarioNuevo.isExported(),
                    horarioNuevo.isBillable(),
                    horarioNuevo.getTags());
        }

        return horarioMañana;
    }

    public TimeSheetPost crearDiaPorLaTarde(TimeSheet horarioNuevo) {
        TimeSheetPost horarioMañana;
        horarioMañana = new TimeSheetPost(
                horarioNuevo.getBegin().toString() + "T16:00:00",
                horarioNuevo.getEnd().toString() + "T16:15:00",
                horarioNuevo.getProject(),
                horarioNuevo.getActivity(),
                horarioNuevo.getDescription(),
                horarioNuevo.getFixedRate(),
                horarioNuevo.getHourlyRate(),
                horarioNuevo.getUser(),
                horarioNuevo.isExported(),
                horarioNuevo.isBillable(),
                horarioNuevo.getTags());
        return horarioMañana;
    }

    public void comprobarUnDia(TimeSheet horarioNuevo) throws Exception {
        TimeSheetPost diaRegistrado;
        int diaDeLaSemana = horarioNuevo.getBegin().getDayOfWeek().getValue();
        switch(diaDeLaSemana) {
            case 5:
                diaRegistrado = crearDiaPorLaMañana(diaDeLaSemana, horarioNuevo);
                this.añadirHorasApi(diaRegistrado);
                break;
            case 6:
                throw new Exception ("Dia introcido incorrecto: El sábado no se trabaja");
            case 7:
                throw new Exception ("Dia introcido incorrecto: El domingo no se trabaja");
            default:
                diaRegistrado = crearDiaPorLaMañana(diaDeLaSemana, horarioNuevo);
                this.añadirHorasApi(diaRegistrado);
                diaRegistrado = crearDiaPorLaTarde(horarioNuevo);
                this.añadirHorasApi(diaRegistrado);
                break;
        }
    }

    public void comprobarMasDeUnDia(TimeSheet horarioNuevo) throws Exception {
        long diasDeDiferencia;
        int diaDeLaSemana;
        diasDeDiferencia = DAYS.between(horarioNuevo.getBegin(), horarioNuevo.getEnd());
        horarioNuevo.setEnd(horarioNuevo.getBegin());

        for(int i=0; i<=diasDeDiferencia; i++){
            diaDeLaSemana = horarioNuevo.getBegin().getDayOfWeek().getValue();
            if(diaDeLaSemana != 6 && diaDeLaSemana != 7) {
                this.comprobarUnDia(horarioNuevo);
            }
            horarioNuevo.setBegin(horarioNuevo.getBegin().plusDays(1));
            horarioNuevo.setEnd(horarioNuevo.getEnd().plusDays(1));
            switch (diaDeLaSemana) {
                case 1:
                    System.out.println("Lunes: 8 horas y 15 minutos fichadas");
                    break;
                case 2:
                    System.out.println("Martes: 8 horas y 15 minutos fichadas");
                    break;
                case 3:
                    System.out.println("Miércoles: 8 horas y 15 minutos fichadas");
                    break;
                case 4:
                    System.out.println("Jueves: 8 horas y 15 minutos fichadas");
                    break;
                case 5:
                    System.out.println("Viernes: 7 horas fichadas");
                    break;
                case 6:
                    System.out.println("Sábado: día de descanso");
                    break;
                case 7:
                    System.out.println("Domingo: día de descanso");
                    break;
            }
        }
    }


    public List<TimeSheetGet> getHorarios() {
        return webClient.get()
                .uri("/api/timesheets")
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TimeSheetGet.class)
                .collectList()
                .block();
    }

    public List<Projects> getProjects() {
        return webClient.get()
                .uri("/api/projects")
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Projects.class)
                .collectList()
                .block();
    }

    public List<Activities> getActivities() {
        return webClient.get()
                .uri("/api/activities")
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Activities.class)
                .collectList()
                .block();
    }
}