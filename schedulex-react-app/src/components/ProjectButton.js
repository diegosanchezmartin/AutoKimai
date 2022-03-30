import React, {useState, useEffect, useContext} from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ProjectButton.module.css";
import { ProjectContext } from "../App";

const ProjectButton = () => {

    const [proyectos, setProyectos] = useState([]);
    const [errorBackend, setErrorBackend] = useState(false);
    const [selectedProject, setSelectedProject] = useContext(ProjectContext);

    useEffect(() => {
        UserServiceFetch.getProjects().then((proyectos) => {
            setProyectos(proyectos);
            setErrorBackend(false);
        }).catch(err => {
            console.log(err.message + "\n El backend está caido");
            setProyectos([]);
            setErrorBackend(true);
        });
    }, []);

    if(errorBackend){
        return (
            <select className={classes.proyctbton}>
                <option selected value="error">Error</option>
            </select>
        )
    }
    return (
        <select defaultValue={'valorPorDefecto'} className={classes.proyctbton} onChange={selected => setSelectedProject(selected.target.value)}>
            <option value="valorPorDefecto">Proyecto</option>
            {proyectos.map(
                proyecto => 
                <option key={proyecto.id} value={proyecto.id}>{proyecto.name}</option>
            )}
        </select>
    )
}

export default ProjectButton;