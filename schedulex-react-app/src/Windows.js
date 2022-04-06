import React, { useState, createContext } from "react";
import { Route, Routes } from "react-router-dom";
import App from "./pages/App";
import Login from "./pages/Login";

export const AuthContext = createContext("Default authentication");
export const CredentialContext = createContext("Default credentias");

const Windows = () => {
  const [auth, setAuth] = useState(false);
  const [credentials, setCredentials] = useState([]);
  return (
    <AuthContext.Provider value={[auth, setAuth]}>
      <CredentialContext.Provider value={[credentials, setCredentials]}>
        <Routes>
          <Route path="*" element={<Login auth={auth} />} />
          <Route path="/app" element={<App />} />
        </Routes>
      </CredentialContext.Provider>
    </AuthContext.Provider>
  );
};

export default Windows;
