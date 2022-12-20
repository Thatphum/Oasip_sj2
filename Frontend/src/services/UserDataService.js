import { store } from '../stores/User';

const API_URL = `${import.meta.env.VITE_BASE_URL}api`;

class UserDataService {
  retrieveAllUser() {
    return fetch(`${API_URL}/users`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  retrieveUser(id) {
    return fetch(`${API_URL}/users/${id}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  deleteUser(id) {
    return fetch(`${API_URL}/users/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  createUser(newUser) {
    return fetch(`${API_URL}/users`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(newUser),
    });
  }
  updateUser(id, update) {
    return fetch(`${API_URL}/users/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: JSON.stringify(update),
    });
  }
}

export default new UserDataService();
