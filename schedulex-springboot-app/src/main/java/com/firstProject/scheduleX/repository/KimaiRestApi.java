package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.*;

@Service
public class KimaiRestApi implements KimaiApi {

    private final WebClient webClient;

    @Autowired
    public KimaiRestApi(@Value( "${api.kimai.url}") final String api) {
        webClient = WebClient.create(api);
    }

    public void addHoursAPi(TimeSheetPost horarioNuevo, UserData credentials) {
        //webClient = WebClient.create(api);
        System.out.println(credentials);
        ResponseEntity<String> block = webClient.post()
                .uri("/api/timesheets")
                .header("X-AUTH-USER",credentials.getUsername())
                .header("X-AUTH-TOKEN", credentials.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(horarioNuevo))
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    public List<TimeSheetGet> getSchedules(String username, String token) {
        List<TimeSheetQindel> userSchedules = webClient.get()
                .uri("/api/timesheets")
                .header("X-AUTH-USER", username)
                .header("X-AUTH-TOKEN", token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TimeSheetQindel.class)
                .collectList()
                .block();

        if(!userSchedules.isEmpty()){
            List<TimeSheetGet> castSchedules = new ArrayList<>();
            for(TimeSheetQindel schedule : userSchedules) {
                castSchedules.add(new TimeSheetGet(
                        schedule.getActivity(),
                        schedule.getProject(),
                        Instant.parse(schedule.getBegin().substring(0, 19) + "Z"),
                        Instant.parse(schedule.getEnd().substring(0, 19) + "Z"),
                        schedule.getId()
                ));
            }
            return castSchedules;
        }
        return Collections.emptyList();
    }

    public List<Projects> getProjects(String username, String token) {
        //webClient = WebClient.create(api);
        return webClient.get()
                .uri("/api/projects")
                .header("X-AUTH-USER", username)
                .header("X-AUTH-TOKEN", token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Projects.class)
                .collectList()
                .block();
    }

    public List<Projects> login(UserData userData) {
        //webClient = WebClient.create(api);
        return webClient.get()
                .uri("/api/projects")
                .header("X-AUTH-USER", userData.getUsername())
                .header("X-AUTH-TOKEN", userData.getToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Projects.class)
                .collectList()
                .block();
    }

    public List<Activities> getActivities(String username, String token) {
        //webClient = WebClient.create(api);
        return webClient.get()
                .uri("/api/activities")
                .header("X-AUTH-USER", username)
                .header("X-AUTH-TOKEN", token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Activities.class)
                .collectList()
                .block();
    }

    public List<TimeSheetGet> getRecentSchedules(String begin, String end, UserData credentials) {
        //webClient = WebClient.create(api);
        List<TimeSheetQindel> userSchedules = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/timesheets")
                        .queryParam("begin", begin)
                        .queryParam("end", end)
                        .build())
                .header("X-AUTH-USER", credentials.getUsername())
                .header("X-AUTH-TOKEN", credentials.getToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TimeSheetQindel.class)
                .collectList()
                .block();

        if(!userSchedules.isEmpty()){
            List<TimeSheetGet> castSchedules = new ArrayList<>();
            for(TimeSheetQindel schedule : userSchedules) {
                castSchedules.add(new TimeSheetGet(
                        schedule.getActivity(),
                        schedule.getProject(),
                        Instant.parse(schedule.getBegin().substring(0, 19) + "Z"),
                        Instant.parse(schedule.getEnd().substring(0, 19) + "Z"),
                        schedule.getId()
                ));
            }
            return castSchedules;
        }
        return Collections.emptyList();
    }

    public void deleteSchedule(int id, UserData credentials){
        //webClient = WebClient.create(api);
        webClient.delete()
                .uri("/api/timesheets/" + String.valueOf(id))
                .header("X-AUTH-USER",credentials.getUsername())
                .header("X-AUTH-TOKEN", credentials.getToken())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
