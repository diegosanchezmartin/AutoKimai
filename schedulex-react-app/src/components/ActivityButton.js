import classes from "./ActivityButton.module.css";

function ActivityButton() {
    return (
        <select className={classes.actybton} onClick={getDataActivity}>
            <option selected value="Actividad">Actividad</option>
            <option value="grapefruit">Grapefruit</option>
            <option value="lime">Lime</option>
            <option value="mango">Mango</option>
        </select>
    );
}

export default ActivityButton;