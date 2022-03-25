import React, { useCallback, useContext } from "react";
import classes from "./ModalResponse.module.css";
import { ModalOpenContext } from "../App";

const ModalRespuesta = ({ begin, end }) => {

  const [openModal, setOpenModal] = useContext(ModalOpenContext);

  const cancelOnClick = useCallback(async () => {
    setOpenModal(false);
  })

  const continueOnClick = useCallback(async () => {
    setOpenModal(false);
  })

  return (
    <div className={classes.modalResponse}>
      <div>
        <h2>Warning: Registered Schedules Discovered</h2>
        <h2>{begin}</h2>
        <h2>{end}</h2>
        <h2>Seguro que quieres continuar?</h2>
      </div>
      <div className={classes.buttonsDistance}>
        <button className={classes.buttonModal} onClick={cancelOnClick}>Cancelar</button>
        <button className={classes.buttonModal} onClick={continueOnClick}>Continuar</button>
      </div>
    </div>
  );
};

export default ModalRespuesta;
