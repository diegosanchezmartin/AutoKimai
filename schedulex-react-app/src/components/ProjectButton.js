import React, {useState, useEffect} from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ProjectButton.module.css";

const ProjectButton = () => {

    const [proyectos, setProyectos] = useState([]);
    const [errorBackend, setErrorBackend] = useState(false);

    useEffect(() => {
        UserServiceFetch.getProyects().then((proyectos) => {
            setProyectos(proyectos);
            setErrorBackend(false);
        }).catch(err => {
            console.log(err.message + "\n El backend está caido");
            setProyectos([]);
            setErrorBackend(true);
        });
    });

    if(errorBackend){
        return (
            <select className={classes.proyctbton}>
                <option selected value="error">Error</option>
            </select>
        )
    }
    return (
        <select defaultValue={'valorPorDefecto'} className={classes.proyctbton} >
            <option value="valorPorDefecto">Proyecto</option>
            {proyectos.map(
                proyecto => 
                <option value={proyecto.parentTitle}>{proyecto.name}</option>
            )}
        </select>
    )
}

export default ProjectButton;