
const USER_REST_API_URL = 'http://localhost:8080/api/timesheets';
const ACTIVITIES_REST_API_URL = 'http://localhost:8080/api/v1/activities';
const PROJECTS_REST_API_URL = 'http://localhost:8080/api/v1/projects';

class UserServiceFetch {
    getTimeSheets(){
        return fetch(USER_REST_API_URL).then(res => res.json());
    }   
    getActivities(){
        return fetch(ACTIVITIES_REST_API_URL).then(res => res.json());
    }
    getProjects(){
        return fetch(PROJECTS_REST_API_URL).then(res => res.json());
    }
}

export default new UserServiceFetch();