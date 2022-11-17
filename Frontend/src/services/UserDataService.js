const API_URL = `${import.meta.env.VITE_BASE_URL}api`

export default class UserDataService {
  retrieveAllUser() {
    //Get Token
    var token = localStorage.getItem('my_tkn')
    
    return fetch(`${API_URL}/users` , {
      method : 'GET',
      headers: {
        'Authorization' : `Bearer ${token}`
      }
    })
  }
  retrieveUser(id) {
    return fetch(`${API_URL}/users/${id}`)
  }
  deleteUser(id) {
    return fetch(`${API_URL}/users/${id}`, {
      method: 'DELETE'
    })
  }
  createUser(newUser) {
    return fetch(`${API_URL}/users`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(newUser)
    })
  }
  updateUser(id, update) {
    return fetch(`${API_URL}/users/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(update)
    })
  }

}
