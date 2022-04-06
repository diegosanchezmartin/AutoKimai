import React, { useCallback, useContext } from "react";
import classes from "./ModalResponse.module.css";
import { ProjectContext, ActivityContext, ModalOpenContext } from "../pages//App";
import { CredentialContext } from "../Windows";

const ModalRespuesta = ({ begin, end }) => {
  const [openModal, setOpenModal] = useContext(ModalOpenContext);
  const [selectedProject, setSelectedProject] = useContext(ProjectContext);
  const [selectedActivity, setSelectedActivity] = useContext(ActivityContext);
  const [credentials, setCredentials] = useContext(CredentialContext);

  const project = selectedProject;
  const activity = selectedActivity;

  const horario = {
    begin,
    end,
    project,
    activity,
  };

  const cancelOnClick = useCallback(async () => {
    setOpenModal(false);
  });

  const continueOnClick = useCallback(async () => {
    setOpenModal(false);
    fetch("http://localhost:8080/api/v1/modifySchedule", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(horario, credentials),
      }).then(() => {
          console.log("Nuevo horario registrado");
          window.location.reload();
      });
  });

  return (
    <div className={classes.modalResponse}>
      <div>
        <h2>Peligro: Horarios registrados encontrados</h2>
        <h2>Desde: </h2>
        <h2>{begin}</h2>
        <h2>Hasta: </h2>
        <h2>{end}</h2>
        <h2>Seguro que quieres continuar?</h2>
      </div>
      <div className={classes.buttonsDistance}>
        <button className={classes.buttonModal} onClick={cancelOnClick}>
          Cancelar
        </button>
        <button className={classes.buttonModal} onClick={continueOnClick}>
          Continuar
        </button>
      </div>
    </div>
  );
};

export default ModalRespuesta;
