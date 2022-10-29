const API_URL = `${import.meta.env.VITE_BASE_URL}api`
class AuthenticationService {
  logInUser(user) {
    return fetch(`${API_URL}/auth/match`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify(user)
    })
  }
}
export default new AuthenticationService()