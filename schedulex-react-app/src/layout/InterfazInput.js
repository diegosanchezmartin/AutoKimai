import { useRef } from 'react';
import Usuario from '../components/Usuario';
import classes from './InterfazInput.module.css';

function InterfazInput () {
    const fechaInicio = useRef();
    const fechaFin = useRef();

    function confirmarFechas(event) {
        event.preventDefault();

        const fechaInicioIntroducida = fechaInicio.current.value;
        const fechaFinIntroducida = fechaFin.current.value;
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