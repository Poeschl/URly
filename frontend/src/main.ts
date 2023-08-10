import {createApp} from 'vue'
import './assets/main.scss'
import App from './App.vue'
import router from "./router/index";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {createPinia} from "pinia";

const app = createApp(App)

library.add()
app.component('FontAwesomeIcon', FontAwesomeIcon)

app.use(router)

const pinia = createPinia()
app.use(pinia)

app.mount('#app')
