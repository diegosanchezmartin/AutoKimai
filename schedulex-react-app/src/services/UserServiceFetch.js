
const USER_REST_API_URL = 'http://localhost:8080/api/v1/user';
const ACTIVITIES_REST_API_URL = 'http://localhost:8080/api/v1/activities';
const PROJECTS_REST_API_URL = 'http://localhost:8080/api/v1/projects';

class UserServiceFetch {
    getUsers(){
        return fetch(USER_REST_API_URL).then(res => res.json());
    }   
    getActivities(){
        return fetch(ACTIVITIES_REST_API_URL).then(res => res.json());
    }
    getProyects(){
        return fetch(PROJECTS_REST_API_URL).then(res => res.json());
    }
}

export default new UserServiceFetch();