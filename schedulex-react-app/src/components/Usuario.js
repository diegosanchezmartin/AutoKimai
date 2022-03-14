import React, { useEffect, useState } from "react";
import UserServiceFetch from "../services/UserServiceFetch";


const Usuario = () => {

    const [timeSheets, setTimeSheets] = useState([]);
    const [errorBackend, setErrorBackend] = useState(false);

    useEffect(() => {
        UserServiceFetch.getTimeSheets().then((res) => {
            setTimeSheets(res);
            setErrorBackend(false);
        }).catch(err => {
            console.log(err.message + "\nEl backend está caido");
            setTimeSheets([]);
            setErrorBackend(true);
        });
    }, []);
    
    if(errorBackend){
         return (
             <div>
                 <h1>Error al ejecutar fetch()</h1>
                  <h2>El backend está caído</h2>
              </div>
          )
    }
    return (
        <div>
            <h1 className="text"> Horas fichadas: </h1>
            <table className="table table-striped">
                <thead>
                    <tr>
                        <td> Fecha inicio: </td>
                        <td> Fecha fin: </td>
                    </tr>
                </thead>
                <tbody>
                    {
                        timeSheets.map(
                            timesheet => 
                            <tr key = {timesheet.id}>
                                <td>{timesheet.begin}</td>
                                <td>{timesheet.end}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default Usuario;