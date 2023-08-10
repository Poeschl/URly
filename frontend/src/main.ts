import {createApp} from 'vue'
import './assets/main.scss'
import App from './App.vue'
import router from "./router/index";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {createPinia} from "pinia";
import {faEdit, faPlus} from "@fortawesome/free-solid-svg-icons";
import {faTrashCan} from "@fortawesome/free-regular-svg-icons";

const app = createApp(App)

library.add(faPlus, faEdit, faTrashCan)
app.component('FontAwesomeIcon', FontAwesomeIcon)

app.use(router)

const pinia = createPinia()
app.use(pinia)

app.mount('#app')
