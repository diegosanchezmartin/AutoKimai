package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class KimaiRestApi implements KimaiApi {

    @Value( "${api.kimai.url}" )
    private String api;

    private WebClient webClient;

    @PostConstruct
    public void assignWebClient(){
        webClient = WebClient.create("http://localhost:8001");
    }

    public void addHoursAPi(TimeSheetPost horarioNuevo) {
        //webClient = WebClient.create(api);
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

    public List<TimeSheetGet> getSchedules() {
        //webClient = WebClient.create(api);
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
        //webClient = WebClient.create(api);
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

    public List<Activities> getActivities() {
        //webClient = WebClient.create(api);
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

    public List<TimeSheetGet> getRecentSchedules(String begin, String end) {
        //webClient = WebClient.create(api);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/timesheets")
                        .queryParam("begin", begin)
                        .queryParam("end", end)
                        .build())
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TimeSheetGet.class)
                .collectList()
                .block();
    }

    public void deleteSchedule(int id){
        //webClient = WebClient.create(api);
        webClient.delete()
                .uri("/api/timesheets/" + String.valueOf(id))
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
