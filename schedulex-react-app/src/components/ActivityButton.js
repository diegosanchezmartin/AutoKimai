import React, { useContext, useEffect, useState } from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ActivityButton.module.css";
import { ActivityContext } from "../pages/App";
import { CredentialContext } from "../Windows";

const ActivityButton = () => {
  const [actividades, setActividades] = useState([]);
  const [errorBackend, setErrorBackend] = useState(false);
  const [selectedActivity, setSelectedActivity] = useContext(ActivityContext);
  const [credentials, setCredentials] = useContext(CredentialContext);

  var url = new URL('http://localhost:8080/api/v1/activities');
  Object.keys(credentials).forEach((key) =>
    url.searchParams.append(key, credentials[key])
  );

  useEffect(() => {
    fetch(url)
      .then((res) => res.json())
      .then((actividades) => {
        setActividades(actividades);
        setErrorBackend(false);
      })
      .catch((err) => {
        console.log(err.message + "\n El backend está caido");
        setActividades([]);
        setErrorBackend(true);
      });
  }, []);

  if (errorBackend) {
    return (
      <select className={classes.actybton}>
        <option selected value="error">
          Error
        </option>
      </select>
    );
  }

  return (
    <select
      defaultValue={"ValorPorDefecto"}
      className={classes.actybton}
      onChange={(selected) => setSelectedActivity(selected.target.value)}
    >
      <option value="ValorPorDefecto">Actividad</option>
      {actividades.map((actividad) => (
        <option key={actividad.id} value={actividad.id}>
          {actividad.name}
        </option>
      ))}
    </select>
  );
};

export default ActivityButton;
