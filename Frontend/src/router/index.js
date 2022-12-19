import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import ListAll from '../views/EventList.vue';
import Detail from '../views/Details.vue';
import CreateEvent from '../views/CreateEvent.vue';
import ListAllCategory from '../views/ListCategory.vue';
import CategoryDetail from '../views/CategoryDetail.vue';
import ListUsers from '../views/UserList.vue';
import UserDetail from '../views/UserDetail.vue';
import User from '../components/User.vue';
import SignIn from '../views/SignIn.vue';
import SignUp from '../views/SignUp.vue';
import { store } from '../stores/User.js';

const history = createWebHistory('/sj2/');
const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/about', name: 'About', component: About },
  {
    path: '/event-lists',
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
  if (!store.isLogin()) {
    store.resetToken();
  }
  // if (!routes.some((route) => route.name === to.name)) {
  //   //page not found
  //   return next();
  // }
  console.log('to');
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    console.log('check login?');
    if (store.isLogin()) {
      console.log('check role component');
      if (!rolesComponent[store.user.roles].includes(to.name)) {
        //permission denied
        // next({
        //   path: '/signin',
        //   name: 'SignIn',
        // });
        return next();
      }
    } else {
      next({
        path: '/signin',
        name: 'SignIn',
      });
    }
  }
  next();
});

export default router;
