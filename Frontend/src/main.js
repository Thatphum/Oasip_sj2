import { createApp } from 'vue';
import App from './App.vue';
import './index.css';
import router from './router';

import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

// import VCalendar from 'v-calendar';
import { SetupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/dist/style.css';

const app = createApp(App);
app.use(router);
app.use(VueSweetalert2);
app.use(SetupCalendar, {});
// Use the components
app.component('Calendar', Calendar);
app.component('DatePicker', DatePicker);

app.mount('#app');
