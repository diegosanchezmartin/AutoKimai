

function ActivityButton() {

    function getDataActivity(){
        fetch('http://localhost:8080/api/v1/activities',{
            method: "GET"})
        .then(response => console.log(response.json()))
    }

    return (
        <select onClick={getDataActivity}>
            <option selected value="Actividad">Actividad</option>
            <option value="grapefruit">Grapefruit</option>
            <option value="lime">Lime</option>
            <option value="mango">Mango</option>
        </select>
    );
}

export default ActivityButton;