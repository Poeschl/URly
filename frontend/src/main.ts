import {createApp} from 'vue'
import './assets/main.scss'
import App from './App.vue'
import router from "./router/index";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {createPinia} from "pinia";
import {faArrowDown, faArrowRight, faChartLine, faEdit, faPlus, faShieldHalved} from "@fortawesome/free-solid-svg-icons";
import {faCopy, faTrashCan} from "@fortawesome/free-regular-svg-icons";
import * as bulmaToast from 'bulma-toast'

const app = createApp(App)

library.add(faPlus, faEdit, faTrashCan, faArrowRight, faArrowDown, faCopy, faShieldHalved, faChartLine)
app.component('FontAwesomeIcon', FontAwesomeIcon)

app.use(router)

const pinia = createPinia()
app.use(pinia)

bulmaToast.setDefaults({
  duration: 1500,
  pauseOnHover: true
})

app.mount('#app')
