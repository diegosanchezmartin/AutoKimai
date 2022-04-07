import { Link } from 'react-router-dom';
import classes from './BarraSuperior.module.css';
import logoScheduleX from '../assets/images/ScheduleXlogoHorizontal.png';
import iconoPerfil from '../assets/images/iconoPerfil.png';
import ProjectButton from '../components/ProjectButton';
import ActivityButton from '../components/ActivityButton';
import { CredentialContext } from "../Windows";
import { useContext } from "react";

const BarraSuperior = () => {

    const [credentials, setCredentials] = useContext(CredentialContext);

    return (
        <header className={classes.header}>
            <section className={classes.selectbtons}>
                <ProjectButton />
                <ActivityButton />
            </section>
            <section className={classes.logo}>
                <div className={classes.image}>
                    <Link to='/'>
                        <img src={logoScheduleX} alt="logoScheduleX"/>
                    </Link>
                </div>
            </section>
            <section className={classes.profile}>
                <div className={classes.actions}>
                    <button>
                        <img src={iconoPerfil} alt="iconoDePerfil"/>
                        <p>@{credentials.username}</p>
                    </button>
                </div>
            </section>
        </header>
    );
}

export default BarraSuperior;