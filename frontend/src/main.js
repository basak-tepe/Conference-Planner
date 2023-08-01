import './assets/main.css'
import { createApp } from 'vue';
import PrimeVue from 'primevue/config';
import App from './App.vue'
import 'primevue/resources/themes/lara-light-purple/theme.css';
import { createPinia } from 'pinia'


const pinia = createPinia();
const app = createApp(App);
app.use(PrimeVue);
app.use(pinia);
app.mount('#app');
