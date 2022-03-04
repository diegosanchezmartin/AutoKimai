package com.firstProject.scheduleX.service;

import ch.qos.logback.core.status.InfoStatus;
import com.firstProject.scheduleX.model.Activities;
import com.firstProject.scheduleX.model.Projects;
import com.firstProject.scheduleX.model.TimeSheet;
import com.firstProject.scheduleX.model.TimeSheetGet;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class TimeSheetService {

    private WebClient webClient = WebClient.create("http://localhost:8001");

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
        System.out.println(horarioNuevo.getBegin());
        int diaDeLaSemana = horarioNuevo.getBegin().getDayOfWeek().getValue();
        System.out.println(diaDeLaSemana);
        String diaIntroducido = horarioNuevo.getBegin().toString();
        switch(diaDeLaSemana) {
            case 1:
                throw new Exception ("Dia introcido incorrecto: El domingo no se trabaja");
            case 2:
                horarioNuevo.setBegin(Instant.parse(diaIntroducido + "T08:00:00"));
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
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,10) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,10) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 4:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,10) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,10) + "T16:15:00");
                this.añadirHorasApi(horarioNuevo);
                break;
            case 5:
                horarioNuevo.setBegin(horarioNuevo.getBegin() + "T08:00:00");
                horarioNuevo.setEnd(horarioNuevo.getEnd() + "T16:00:00");
                this.añadirHorasApi(horarioNuevo);
                horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0,10) + "T16:00:00");
                horarioNuevo.setEnd(horarioNuevo.getBegin().substring(0,10) + "T16:15:00");
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

    public void comprobarMasDeUnDia(TimeSheet horarioNuevo) throws Exception {
        SimpleDateFormat formatearFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;
        Date fechaFin = null;
        int diasDeDiferencia, diaDeLaSemana;
        String diaInicioString;
        GregorianCalendar calendario = new GregorianCalendar();

        /*try {
            fechaInicio = formatearFecha.parse(horarioNuevo.getBegin());
        } catch(ParseException e) {
            System.out.println("No se ha podido formatear la fecha de inicio de la actividad");
        }
        try {
            fechaFin = formatearFecha.parse(horarioNuevo.getEnd());
        } catch(ParseException e) {
            System.out.println("No se ha podido formatear la fecha de fin de la actividad");
        }*/

        diasDeDiferencia = (int)((fechaFin.getTime() - fechaInicio.getTime()) / 86400000);

        calendario.setTime(fechaInicio);
        horarioNuevo.setEnd(horarioNuevo.getBegin());

        for(int i=0; i<=diasDeDiferencia; i++){
            diaDeLaSemana = calendario.get(Calendar.DAY_OF_WEEK);
            calendario.add(Calendar.DAY_OF_YEAR, 1);
            if(diaDeLaSemana != 1 && diaDeLaSemana != 7) {
                this.comprobarUnDia(horarioNuevo);
            }
            fechaInicio = calendario.getTime();
            diaInicioString = formatearFecha.format(fechaInicio).substring(8, 10);
    //        horarioNuevo.setBegin(horarioNuevo.getBegin().substring(0, 8) + diaInicioString);
            horarioNuevo.setEnd(horarioNuevo.getBegin());
            switch (diaDeLaSemana) {
                case 1:
                    System.out.println("Domingo: día de descanso");
                    break;
                case 2:
                    System.out.println("Lunes: 8 horas y 15 minutos fichadas");
                    break;
                case 3:
                    System.out.println("Martes: 8 horas y 15 minutos fichadas");
                    break;
                case 4:
                    System.out.println("Miércoles: 8 horas y 15 minutos fichadas");
                    break;
                case 5:
                    System.out.println("Jueves: 8 horas y 15 minutos fichadas");
                    break;
                case 6:
                    System.out.println("Viernes: 7 horas fichadas");
                    break;
                case 7:
                    System.out.println("Sábado: día de descanso");
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