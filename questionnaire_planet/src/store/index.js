import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    login:false,
    userID:"",
    userName:"",
  },
  mutations: {
    setLogin(state) {
      state.login = true
    },
    setLogout(state){
      state.login = false
    }
  },
  actions: {
  },
  modules: {
  }
})
