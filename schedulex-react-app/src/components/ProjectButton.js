

function DropButton () {

    function getDataProject() {
        fetch("http://localhost:8080/api/v1/projects",{
            method: "GET"})
        .then(response => console.log(response.json()))
    }

    return (
        <select onClick={getDataProject}>
            <option selected value="proyecto">Proyecto</option>
            <option value="grapefruit">Grapefruit</option>
            <option value="lime">Lime</option>
            <option value="mango">Mango</option>
        </select>
    );
}

export default DropButton;