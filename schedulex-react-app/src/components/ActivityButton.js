import React, {useEffect, useState} from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ActivityButton.module.css";

const ActivityButton = () => {

    const [actividades, setActividades] = useState([]);
    const [errorBackend, setErrorBackend] = useState(false);

    useEffect(() => {
        UserServiceFetch.getActivities().then((actividades) => { 
            setActividades(actividades);
            setErrorBackend(false);
        }).catch(err => {
            console.log(err.message + "\n El backend está caido");
            setActividades([]);
            setErrorBackend(true);
        });
    });
    
    if(errorBackend){
        return (
             <select className={classes.actybton}>
                <option selected value="error">Error</option>
            </select>
        )
    }
        
    return (
        <select defaultValue={'ValorPorDefecto'} className={classes.actybton} >
            <option value="ValorPorDefecto" >Actividad</option>
            {actividades.map(
                actividad => 
                <option value={actividad.parentTitle}>{actividad.name}</option>
            )}
        </select>
    )
}

export default ActivityButton;