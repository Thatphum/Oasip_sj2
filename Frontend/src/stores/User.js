import { reactive } from 'vue';

export const store = reactive({
  user: {
    email: '',
    username: '',
    roles: '',
  },
  setDataUser(user) {
    this.user = user;
  },
  resetDate() {
    this.user.email = '';
    this.user.username = '';
    this.user.roles = '';
  },
});
