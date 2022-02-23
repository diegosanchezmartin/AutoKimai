
const USER_REST_API_URL = 'http://localhost:8080/api/v1/user';

class UserServiceFetch {
    getUsers(){
        return fetch(USER_REST_API_URL).then((res => res.json()));
    }   
}

export default new UserServiceFetch();