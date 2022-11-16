const API_URL = `${import.meta.env.VITE_BASE_URL}api`

class UserDataService {
  retrieveAllUser() {
    //Get Token
    var token = localStorage.getItem('my_tkn')
    console.log(token);
    if (localStorage.getItem('my_tkn')!= null) {
      return fetch(`${API_URL}/users` , {
      method : 'GET',
      headers: {
        'Authorization' : `Bearer ${token}`
      }
    })
    }else{
      return fetch(`${API_URL}/users` , {
        method : 'GET'
      })
    }
    
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
export default new UserDataService()