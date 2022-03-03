import React from "react";
import UserServiceFetch from "../services/UserServiceFetch";
import classes from "./ProjectButton.module.css";

class Proyecto extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            proyectos:[],
            errorBackend: false
        }
    }

    componentDidMount(){
        UserServiceFetch.getActivities().then((res) => {
            this.setState({proyectos: res, errorBackend:false});
        }).catch(err => {
            console.log(err.message + "\n El backend está caido");
            this.setState({errorBackend: true, proyectos:[]});
        });
    }

    render() {
        if(this.state.errorBackend){
            return (
                <select className={classes.proyctbton}>
                    <option selected value="error">Error</option>
                </select>
            )
        }
        return (
            <select defaultValue={'valorPorDefecto'} className={classes.proyctbton} >
                <option value="valorPorDefecto">Proyecto</option>
                {this.state.proyectos.map(
                    proyecto => 
                    <option value={proyecto.parentTitle}>{proyecto.name}</option>
                )}
            </select>
        )
    }
}

export default Proyecto;