package com.firstProject.scheduleX.repository;

import com.firstProject.scheduleX.model.*;
import org.springframework.http.MediaType;

import java.util.List;

public interface TimeSheetInterface {

    void añadirHorasApi(TimeSheetPost horarioNuevo);

    List<TimeSheetGet> getHorarios();

    List<Projects> getProjects();

    List<Activities> getActivities();

    public TimeSheetPost crearDiaPorLaMañana(int diaDeLaSemana, TimeSheet horarioNuevo);

    TimeSheetPost crearDiaPorLaTarde(TimeSheet horarioNuevo);
}
