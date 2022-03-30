import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import classes from "./Login.module.css";

async function loginUser(credentials, navigate) {
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
      navigate("/app");
    }
  });
}

const Login = () => {
  const navigate = useNavigate();
  const [username, setUserName] = useState();
  const [token, setUserToken] = useState();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userData = {
      username,
      token,
    };
    console.log(userData);
    await loginUser(userData, navigate);
  };

  return (
    <div className={classes.loginWrapper}>
      <h1>Please Log In</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <p>Username</p>
          <input type="text" onChange={(e) => setUserName(e.target.value)} />
        </label>
        <label>
          <p>Password</p>
          <input
            type="password"
            onChange={(e) => setUserToken(e.target.value)}
          />
        </label>
        <div>
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
