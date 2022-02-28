import { useRef } from 'react';
import Usuario from '../components/Usuario';
import classes from './InterfazInput.module.css';

function InterfazInput () {
    const fechaInicio = useRef();
    const fechaFin = useRef();

    function confirmarFechas(event) {
        event.preventDefault();

        const begin = fechaInicio.current.value;
        const end = fechaFin.current.value;
        const project = 1;
        const activity = 1;
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

        fetch("http://localhost:8080/api/v1/user",{
            method: "POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(horario)
        }).then(() => {
            console.log("Nuevo horario registrado")
        })
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