import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from "element-ui";
import axios from "axios"
import vuetify from './plugins/vuetify'
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

Vue.config.productionTip = false
axios.defaults.baseURL ='/api/'
Vue.config.devtools = true;
Vue.prototype.$http = axios

new Vue({
  router,
  store,
  axios,
  vuetify,
  render: h => h(App)
}).$mount('#app')
