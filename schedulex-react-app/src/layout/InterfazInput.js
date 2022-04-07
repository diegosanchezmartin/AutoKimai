import React, { useContext, useRef, useState, useEffect } from "react";
import Usuario from "../components/Usuario";
import classes from "./InterfazInput.module.css";
import {
  ProjectContext,
  ActivityContext,
  ModalOpenContext,
} from "../pages/App";
import ModalResponse from "../components/ModalResponse";
import UserServiceFetch from "../services/UserServiceFetch";
import { CredentialContext } from "../Windows";

const InterfazInput = ({ setOpenModalResponse }) => {
  const fechaInicio = useRef();
  const fechaFin = useRef();

  const [selectedProject, setSelectedProject] = useContext(ProjectContext);
  const [selectedActivity, setSelectedActivity] = useContext(ActivityContext);
  const [openModal, setOpenModal] = useContext(ModalOpenContext);
  const [credentials, setCredentials] = useContext(CredentialContext);
  const [registeredSchedules, setRegisteredSchedules] = useState([]);
  const [beginSchedule, setBeginSchedule] = useState();
  const [endSchedule, setEndSchedule] = useState();
  const [timeSheets, setTimeSheets] = useState([]);
  const [errorBackend, setErrorBackend] = useState(false);

  useEffect(() => {
    UserServiceFetch.getTimeSheets()
      .then((res) => {
        setTimeSheets(res);
        setErrorBackend(false);
      })
      .catch((err) => {
        console.log(err.message + "\nEl backend está caido");
        setTimeSheets([]);
        setErrorBackend(true);
      });
  }, []);

  function confirmarFechas(event) {
    event.preventDefault();

    const begin = fechaInicio.current.value;
    const end = fechaFin.current.value;
    const project = selectedProject;
    const activity = selectedActivity;

    const newSchedule = {
      begin,
      end,
      project,
      activity,
    };
    console.log(newSchedule);

    if (newSchedule.activity != null && newSchedule.project != null) {
      const request = {
        newSchedule,
        credentials,
      };
      console.log(credentials);
      console.log(request);
      fetch("http://localhost:8080/api/v1/createSchedule", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(request),
      }).then((res) => {
        if (res.status === 200) {
          console.log("Nuevo horario registrado");
          UserServiceFetch.getTimeSheets()
            .then((res) => {
              setTimeSheets(res);
              setErrorBackend(false);
            })
            .catch((err) => {
              console.log(err.message + "\nEl backend está caido");
              setTimeSheets([]);
              setErrorBackend(true);
            });

        } else if (res.status === 409) {
          res.json().then((body) => {
            alert(
              "Peligro: Horarios registrados encontrados: \n" +
                "Desde: " +
                JSON.stringify(body.error.begin) +
                "\n" +
                "Hasta: " +
                JSON.stringify(body.error.end) +
                "\n"
            );
          });
        } else if (res.status === 422) {
          console.log(res.json());
          alert(
            "Error: Fecha de inicio es posterior a la fecha fin: \n" +
              "Desde: " +
              JSON.stringify(begin) +
              "\n" +
              "Hasta: " +
              JSON.stringify(end) +
              "\n"
          );
        } else if (res.status === 423) {
          res.json().then((body) => {
            console.log(body.error);
            setBeginSchedule(body.error.begin);
            setEndSchedule(body.error.end);
          });
          setOpenModal(true);
        }
      });
    } else {
      if (newSchedule.activity == null) {
        if (newSchedule.project == null) {
          alert(
            "Debes elegir un proyecto y una actividad para el nuevo horario"
          );
        } else {
          alert("Debes elegir una actividad para el nuevo horario");
        }
      } else {
        if (newSchedule.project == null) {
          alert("Debes elegir un proyecto para el nuevo horario");
        }
      }
    }
  }

  return (
    <div>
      <form className={classes.form} onSubmit={confirmarFechas}>
        <div className={classes.tablaContenido}>
          <Usuario horarios={timeSheets} error={errorBackend}/>
        </div>
        <div className={classes.control}>
          <label htmlFor="fechaInicio">Introduce la fecha de inicio: </label>
          <input
            type="date"
            required
            id="fechaInicio"
            ref={fechaInicio}
          ></input>
        </div>
        <div className={classes.control}>
          <label htmlFor="fechaFin">Introduce la fecha de fin: </label>
          <input type="date" required id="fechaFin" ref={fechaFin}></input>
        </div>
        <div className={classes.actions}>
          <button>Fichar horas</button>
        </div>
      </form>
      {openModal && <ModalResponse begin={beginSchedule} end={endSchedule} />}
    </div>
  );
}

export default InterfazInput;
