import React from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ActivityButton.module.css";

class Actividad extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            actividades:[],
            errorBackend: false
        }
    }

    componentDidMount(){
        UserServiceFetch.getActivities().then((res) => {
            this.setState({actividades: res, errorBackend:false});
        }).catch(err => {
            console.log(err.message + "\n El backend está caido");
            this.setState({errorBackend: true, actividades:[]});
        });
    }

    render() {
        if(this.state.errorBackend){
            return (
                <select className={classes.actybton}>
                    <option selected value="error">Error</option>
                </select>
            )
        }
        return (
            <select defaultValue={'ValorPorDefecto'} className={classes.actybton} >
                <option value="ValorPorDefecto" >Actividad</option>
                {this.state.actividades.map(
                    actividad => 
                    <option value={actividad.parentTitle}>{actividad.name}</option>
                )}
            </select>
        )
    }
}

export default Actividad;