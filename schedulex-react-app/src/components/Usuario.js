import React from "react";
import UserServices from "../services/UserServices";

class Usuario extends React.Component {
    constructor(props){
        super(props)
        this.state = {
            users:[]
        }
    }

    componentDidMount(){
        UserServices.getUsers().then((response) => {
            this.setState({ users: response.data})
        });
    }

    render (){
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