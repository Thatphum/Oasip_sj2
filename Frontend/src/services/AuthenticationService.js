import { store } from '../stores/User';

const API_URL = `${import.meta.env.VITE_BASE_URL}api`;
class AuthenticationService {
  logInUser(user) {
    return fetch(`${API_URL}/auth/match`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(user),
    });
  }
  retrieveRefreshtoken() {
    return fetch(`${API_URL}/auth/refreshtoken`, {
      method: 'GET',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.refreshToken}`,
      },
    });
  }
}

export default new AuthenticationService();
