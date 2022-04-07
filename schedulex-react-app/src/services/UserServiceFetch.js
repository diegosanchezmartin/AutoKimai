
const USER_REST_API_URL = 'http://localhost:8080/api/v1/timesheets';
const ACTIVITIES_REST_API_URL = 'http://localhost:8080/api/v1/activities';
const PROJECTS_REST_API_URL = 'http://localhost:8080/api/v1/projects';

class UserServiceFetch {
    getTimeSheets(credentials){
        return fetch(USER_REST_API_URL, {
            method: "GET",
            body: JSON.stringify(credentials)
        }).then(res => res.json());
    }   
    getActivities(credentials){
        return fetch(ACTIVITIES_REST_API_URL, {
            method: "GET",
            body: JSON.stringify(credentials)
        }).then(res => res.json());
    }
    getProjects(credentials){
        return fetch(PROJECTS_REST_API_URL, {
            method: "GET",
            body: JSON.stringify(credentials)
        }).then(res => res.json());
    }
}

export default new UserServiceFetch();