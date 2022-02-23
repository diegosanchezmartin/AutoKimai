import React from "react";
import UserServiceFetch from "../services/UserServiceFetch";

class Usuario extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            users:[],
            errorBackend: false
        }
    }

    /* Usando axios descomentar esta líneas:
    componentDidMount(){
        UserServices.getUsers().then((response) => {
            this.setState({ users: response.data})
        });
    }*/

    
    componentDidMount(){
        UserServiceFetch.getUsers().then((res) => {
            this.setState({users: res, errorBackend:false});
        }).catch(err => {
            console.log(err.message + "\nEl backend está caido");
            this.setState({errorBackend: true, users:[]});
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
                <h1 className="text"> Lista de usuarios: </h1>
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <td> Id de usuario: </td>
                            <td> Nombre de usuario: </td>
                            <td> Fecha inicio: </td>
                            <td> Fecha fin: </td>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            this.state.users.map(
                                user => 
                                <tr key = {user.id}>
                                    <td>{user.id}</td>
                                    <td>{user.nombreDeUsuario}</td>
                                    <td>{user.fechaInicio}</td>
                                    <td>{user.fechaFin}</td>
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