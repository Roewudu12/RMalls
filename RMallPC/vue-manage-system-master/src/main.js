import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import qs from "qs"
import service from './utils/request'
import './assets/css/icon.css'

const app = createApp(App)
installElementPlus(app)
app
    .use(store)
    .use(router)
    .mount('#app')
	
app.config.globalProperties.$header={headers:{'Content-Type':'application/x-www-form-urlencoded'}};
app.config.globalProperties.$qs=qs;
app.config.globalProperties.$service=service;