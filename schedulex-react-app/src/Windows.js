import React, { useState, createContext } from "react";
import { Route, Routes } from "react-router-dom";
import App from "./pages/App";
import Login from "./pages/Login";

export const AuthContext = createContext("Default authentication");

const Windows = () => {  
  const [auth, setAuth] = useState(false);
  return (
    <AuthContext.Provider value={[auth, setAuth]}>
      <Routes>
        <Route path="*" element={<Login auth={auth} />} />
        <Route path="/app" element={<App />} />
      </Routes>
    </AuthContext.Provider>
  );
};

export default Windows;
