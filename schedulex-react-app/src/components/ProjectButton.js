import React, { useState, useEffect, useContext } from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ProjectButton.module.css";
import { ProjectContext } from "../pages/App";
import { CredentialContext } from "../Windows";

const ProjectButton = () => {
  const [proyectos, setProyectos] = useState([]);
  const [errorBackend, setErrorBackend] = useState(false);
  const [selectedProject, setSelectedProject] = useContext(ProjectContext);
  const [credentials, setCredentials] = useContext(CredentialContext);

  useEffect(() => {
    var url = new URL("http://localhost:8080/api/v1/projects");

    url.searchParams.append("username", credentials.username);
    url.searchParams.append("token", credentials.token);

    console.log(credentials);
    console.log(url);
    fetch(url)
      .then((res) => res.json())
      .then((proyectos) => {
        setProyectos(proyectos);
        setErrorBackend(false);
      })
      .catch((err) => {
        console.log(err.message + "\n El backend está caido");
        setProyectos([]);
        setErrorBackend(true);
      });
  }, []);

  if (errorBackend) {
    return (
      <select className={classes.proyctbton}>
        <option selected value="error">
          Error
        </option>
      </select>
    );
  }
  return (
    <select
      defaultValue={"valorPorDefecto"}
      className={classes.proyctbton}
      onChange={(selected) => setSelectedProject(selected.target.value)}
    >
      <option value="valorPorDefecto">Proyecto</option>
      {proyectos.map((proyecto) => (
        <option key={proyecto.id} value={proyecto.id}>
          {proyecto.name}
        </option>
      ))}
    </select>
  );
};

export default ProjectButton;
