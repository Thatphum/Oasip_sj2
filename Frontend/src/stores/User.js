import { reactive } from 'vue';
import AuthenticationService from '../services/AuthenticationService';

export const store = reactive({
  user: JSON.parse(localStorage.getItem('user')),
  accessToken: localStorage.getItem('accessToken'),
  accessTokenExp: new Date(localStorage.getItem('accessTokenExp')),
  refreshToken: localStorage.getItem('refreshToken'),
   isLogin() {
    return ( this.accessToken) && (this.accessTokenExp) > new Date();
  },
  async resetToken() {
    if (this.refreshToken != null) {
      this.accessToken = null;
      this.accessTokenExp = null;
      localStorage.removeItem('accessToken');
      localStorage.removeItem('accessTokenExp');
      const res = await AuthenticationService.retrieveRefreshtoken();
      if (res.status == 200) {
        var data = await res.json();
        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('accessTokenExp', data.accessTokenExp);
        this.accessToken = data.accessToken;
        this.accessTokenExp = new Date(data.accessTokenExp);
      } else {
        this.logout();
        return false;
      }
      return true;
    }
    return false;
  },
  logout() {
    this.user = null;
    this.accessToken = null;
    this.accessTokenExp = null;
    this.refreshToken = null;
    localStorage.clear();
  },
  getUsername() {
    return this.user.username;
  },
});
