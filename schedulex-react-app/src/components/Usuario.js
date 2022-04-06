import React, { useEffect, useState } from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./Usuario.module.css";

const Usuario = ({ horarios, error }) => {
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

  if (error) {
    return (
      <div>
        <h1>Error al ejecutar fetch()</h1>
        <h2>El backend está caído</h2>
      </div>
    );
  }
  return (
    <div>
      <h1> Horas fichadas: </h1>
      <div className={classes.scrollTable}>
        <table>
          <thead>
            <tr>
              <td> Fecha inicio: </td>
              <td> Fecha fin: </td>
            </tr>
          </thead>
          <tbody>
            {horarios.map((timesheet) => (
              <tr key={timesheet.id.toString()}>
                <td>{timesheet.begin}</td>
                <td>{timesheet.end}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Usuario;
