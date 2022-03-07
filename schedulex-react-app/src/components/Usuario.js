import React from "react";
import UserServiceFetch from "../services/UserServiceFetch";

class Usuario extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            timesheets:[],
            errorBackend: false
        }
    }
    
    componentDidMount(){
        UserServiceFetch.getTimeSheets().then((res) => {
            this.setState({timesheets: res, errorBackend:false});
        }).catch(err => {
            console.log(err.message + "\nEl backend está caido");
            this.setState({errorBackend: true, timesheets:[]});
        });
    }

    render (){
        if(this.state.errorBackend){
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
                            this.state.timesheets.map(
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
}

export default Usuario;