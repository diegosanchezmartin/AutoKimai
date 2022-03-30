import React, { useState } from "react";
import classes from "./Login.module.css";

async function loginUser(credentials) {
  return fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
  })
  .then(data => data.json())
}

const Login = () => {
  const [username, setUserName] = useState();
  const [token, setUserToken] = useState();

  const handleSubmit = async e => {
      e.preventDefault();
      await loginUser({
          username,
          token
      });
  }

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
