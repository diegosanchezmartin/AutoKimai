import { Link } from 'react-router-dom';
import classes from './BarraSuperior.module.css';
import logoScheduleX from '../assets/images/ScheduleXlogoHorizontal.png';
import iconoPerfil from '../assets/images/iconoPerfil.png';

function BarraSuperior () {
    return (
        <header className={classes.header}>
            <div className={classes.image}>
                <Link to='/'>
                    <img src={logoScheduleX} alt="logoScheduleX"/>
                </Link>
            </div>
            <div className={classes.actions}>
                <button>
                    <img src={iconoPerfil} alt="iconoDePerfil"/>
                    <p>@user</p>
                </button>
            </div>
        </header>
    );
}

export default BarraSuperior;