import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import ListAll from '../views/ListAll.vue';
import Detail from '../views/Details.vue';
import CreateEvent from '../views/CreateEvent.vue';
import ListAllCategory from '../views/ListCategory.vue';
import CategoryDetail from '../views/CategoryDetail.vue';
import ListUsers from '../views/UserList.vue';
import UserDetail from '../views/UserDetail.vue';
import User from '../components/User.vue';
import SignIn from '../views/SignIn.vue';
import SignUp from '../views/SignUp.vue';
import AuthenticationService from '../services/AuthenticationService';
import { store } from '../stores/User.js';

const history = createWebHistory('/sj2/');
const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/about', name: 'About', component: About },
  {
    path: '/listevents',
    name: 'ListEvent',
    component: ListAll,
    meta: { requiresAuth: true },
  },
  {
    path: '/detail/:id',
    name: 'Detail',
    component: Detail,
    meta: { requiresAuth: true },
  },
  {
    path: '/CreateEvent',
    name: 'CreateEvent',
    component: CreateEvent,
    meta: { requiresAuth: true },
  },
  {
    path: '/listcategory',
    name: 'ListCategory',
    component: ListAllCategory,
    meta: { requiresAuth: true },
  },
  {
    path: '/categorydetail/:id',
    name: 'CategoryDetail',
    component: CategoryDetail,
    meta: { requiresAuth: true },
  },
  {
    path: '/listusers',
    name: 'ListUsers',
    component: ListUsers,
    meta: { requiresAuth: true },
  },
  {
    path: '/userdetail/:id',
    name: 'UserDetail',
    component: UserDetail,
    meta: { requiresAuth: true },
  },
  {
    path: '/users',
    name: 'Users',
    component: User,
    meta: { requiresAuth: true },
  },
  { path: '/signup', name: 'SignUp', component: SignUp },
  { path: '/signin', name: 'SignIn', component: SignIn },
];
const student = [
  'ListEvent',
  'Detail',
  'CreateEvent',
  'ListCategory',
  'CategoryDetail',
];
const lecturer = [
  'Listvent',
  'Detail',
  'CreateEvent',
  'ListCategory',
  'CategoryDetail',
];
const admin = [
  'ListEvent',
  'Detail',
  'CreateEvent',
  'ListCategory',
  'CategoryDetail',
  'Users',
  'UserDetail',
];

const rolesComponent = {
  student,
  lecturer,
  admin,
};

const router = createRouter({ history, routes });

router.beforeEach(async (to, from, next) => {
  const accessTokenExp = localStorage.getItem('accessTokenExp');
  const refreshToken = localStorage.getItem('refreshToken');
  const user = localStorage.getItem('user');

  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (Date.now() >= accessTokenExp) {
      if (refreshToken != null) {
        const res = await AuthenticationService.retrieveRefreshtoken();
        if (res.status == 200) {
          let data = res.json();
          localStorage.setItem('accessToken', data.accessToken);
          localStorage.setItem('accessTokenExp', data.accessTokenExp);
          localStorage.setItem('refreshToken', data.refreshToken);
          store.setDataUser(data.user);
          // check role
          if (user != null) {
            var role = JSON.parse(user)['roles'];
            if (!rolesComponent[role].includes(to.name)) {
              // goto access denied page
              next({ name: 'SignIn' });
            }
          } else {
            // goto 500 page
            next({ name: 'SignIn' });
          }
        } else {
          //go to time out page or reset localstorage and go to login
          localStorage.clear();
          store.resetDate();
        }
      }
      next({ name: 'SignIn' });
    } else {
      if (user != null) {
        var role = JSON.parse(user)['roles'];
        if (!rolesComponent[role].includes(to.name)) {
          // goto access denied page
          next({ name: 'SignIn' });
        }
      } else {
        // goto 500 page
        next({ name: 'SignIn' });
      }
    }
  }
  next();
});

export default router;
