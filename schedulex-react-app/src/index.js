import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import Windows from "./Windows";
import "./index.css";


ReactDOM.render(
  <BrowserRouter>
    <Windows />
  </BrowserRouter>,
  document.getElementById("root")
);
