import React, { useContext, useRef, useState } from 'react';
import Usuario from '../components/Usuario';
import classes from './InterfazInput.module.css';
import { ProjectContext, ActivityContext } from '../App';

function InterfazInput (props) {
    const fechaInicio = useRef();
    const fechaFin = useRef();

    const [selectedProject, setSelectedProject] = useContext(ProjectContext);
    const [selectedActivity, setSelectedActivity] = useContext(ActivityContext);
    const [registeredSchedules, setRegisteredSchedules] = useState([]);
    
    function confirmarFechas(event) {
        event.preventDefault();

        const begin = fechaInicio.current.value; 
        const end = fechaFin.current.value; 
        const project = selectedProject;
        const activity = selectedActivity;
        const description = "string"
        const fixedRate = 0
        const hourlyRate = 0
        const user = 1
        const exported = true
        const billable = true
        const tags = "string"

        const horario={
            begin, 
            end,
            project,
            activity,
            description,
            fixedRate,
            hourlyRate,
            user,
            exported,
            billable,
            tags
        }
        console.log(horario);

        if(horario.activity != null && horario.project != null){
            fetch("http://localhost:8080/api/v1/user",{
                method: "POST",
                headers:{"Content-Type":"application/json"},
                body:JSON.stringify(horario)
            }).then(res => { 
                if(res.status === 200){
                    console.log("Nuevo horario registrado")
                } else if(res.status === 409) {
                    console.log(res.json());
                    alert( 
                        "Warning: Registered Schedules Discovered: \n" +
                        "From: " + 
                            JSON.stringify(begin) + "\n" +
                        "To: " +
                            JSON.stringify(end) + "\n")
                    //JSON.stringify(schedule));
                    /*res.json().then(schedule => {
                        setRegisteredSchedules(schedule);
                        alert(
                            schedule.map(discoveredSchedule => 
                            "Warning: Registered Schedules Discovered: \n" +
                            "From: " + 
                                JSON.stringify(discoveredSchedule.begin) + "\n" +
                            "To: " +
                                JSON.stringify(discoveredSchedule.end) + "\n"))
                            
                            //JSON.stringify(schedule));
                        console.log(schedule);
                    })*/
                } else if(res.status === 422) {
                    console.log(res.json());
                    alert( 
                        "Error: Begin date is later than End date: \n" +
                        "From: " + 
                            JSON.stringify(begin) + "\n" +
                        "To: " +
                            JSON.stringify(end) + "\n")
                }
                
            })
        }else{
            if(horario.activity == null){
                if(horario.project == null){
                    alert("Debes elegir un proyecto y una actividad para el nuevo horario");
                }else{
                    alert("Debes elegir una actividad para el nuevo horario");
                }
            }else{
                if(horario.project == null){
                    alert("Debes elegir un proyecto para el nuevo horario");
                }
            }
        }
    }

    return (
            <form className={classes.form} onSubmit={confirmarFechas}>
                <div className={classes.tablaContenido}>
                    <Usuario />
                </div>
                <div className={classes.control}>
                    <label htmlFor="fechaInicio">Introduce la fecha de inicio: </label>
                    <input type="date" required id="fechaInicio" ref={fechaInicio}></input>
                </div>
                <div className={classes.control}>
                    <label htmlFor="fechaFin">Introduce la fecha de fin: </label>
                    <input type="date" required id="fechaFin" ref={fechaFin}></input>
                </div>
                <div className={classes.actions}>
                    <button>Fichar horas</button>
                </div>
            </form>
    );
}

export default InterfazInput;