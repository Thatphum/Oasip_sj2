const API_URL = `${import.meta.env.VITE_BASE_URL}api`;

class UserDataService {
  retrieveAllUser() {
    //Get Token
    var token = localStorage.getItem('my_tkn');
    if (localStorage.getItem('my_tkn') != null) {
      return fetch(`${API_URL}/users`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });
    } else {
      return fetch(`${API_URL}/users`, {
        method: 'GET',
      });
    }
  }
  retrieveUser(id) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/users/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }
  deleteUser(id) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/users/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }
  createUser(newUser) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/users`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(newUser),
    });
  }
  updateUser(id, update) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/users/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(update),
    });
  }
}
export default new UserDataService();
