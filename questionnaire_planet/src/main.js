import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from "./plugins/vuetify";
import ElementUI from "element-ui";
import axios from "axios"

Vue.use(ElementUI);

Vue.config.productionTip = false
axios.defaults.baseURL ='/api/'
Vue.config.devtools = true;
Vue.prototype.$http = axios
Vue.prototype.$qs = qs

new Vue({
  router,
  store,
  vuetify,
  axios,
  qs,
  render: h => h(App)
}).$mount('#app')
