import DataService from './DataService.js';
export default class AuthenticationService extends DataService {
  print() {
    console.log(this.API_URL);
  }
  logInUser(user) {
    return fetch(`${API_URL}/auth/match`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(user),
    });
  }
}
