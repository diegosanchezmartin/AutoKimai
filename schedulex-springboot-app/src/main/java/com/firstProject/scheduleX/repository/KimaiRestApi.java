package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.Activities;
import com.firstProject.scheduleX.model.Projects;
import com.firstProject.scheduleX.model.TimeSheetGet;
import com.firstProject.scheduleX.model.TimeSheetPost;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class KimaiRestApi implements KimaiApi {

/*    @Value("${api.kimai.url}")
    private String urlApi;

    private WebClient webClient = WebClient.create(urlApi);*/
    private WebClient webClient = WebClient.create("http://localhost:8001");


    public void addHoursAPi(TimeSheetPost horarioNuevo) {
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

    public List<TimeSheetGet> getRecentSchedules(String begin, String end) {
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
        webClient.delete()
                .uri("/api/timesheets/" + String.valueOf(id))
                .header("X-AUTH-USER","admin@kimai.local")
                .header("X-AUTH-TOKEN", "password")
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
