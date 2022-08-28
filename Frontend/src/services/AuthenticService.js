const API_URL = `${import.meta.env.VITE_BASE_URL}api`
class AuthenticService {
  logInUser(user) {
    return fetch(`${API_URL}/match`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(user)
    })
  }
}