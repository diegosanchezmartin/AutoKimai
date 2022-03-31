import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Login.module.css";
import { AuthContext } from "../Windows"

const LoginUser = (credentials, navigate, setAuth) => {
  return fetch("http://localhost:8080/api/v1/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  }).then((res) => {
    if (res.status === 200) {
      console.log("Inicio de sesión correcto");
      console.log("Bienvenido " + credentials.username);
      setAuth(true);
      navigate("/app");
    }
  });
}

const Login = () => {
  const navigate = useNavigate();
  const [username, setUserName] = useState();
  const [token, setUserToken] = useState();
  const [auth, setAuth] = useContext(AuthContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userData = {
      username,
      token,
    };
    console.log(userData); 
    console.log(auth);
    LoginUser(userData, navigate, setAuth);
    console.log(auth);
  };

  return (
    <div className={classes.loginWrapper}>
      <h1>Iniciar sesión</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <p>Nombre de usuario: </p>
          <input type="text" onChange={(e) => setUserName(e.target.value)} />
        </label>
        <label>
          <p>Contraseña</p>
          <input
            type="password"
            onChange={(e) => setUserToken(e.target.value)}
          />
        </label>
        <div>
          <button type="submit">Continuar</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
