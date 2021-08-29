import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    login:false,
    userID:"",
    userName:"",
    isPrint:false,
    answer:{
      radioAnswer:{},
      optionAnswer:{},
      text:{},
    }
  },
  mutations: {
    setLogin(state) {
      state.login = true
    },
    setLogout(state){
      state.login = false
    },
    setUserID(state,id){
      state.userID=id
    },
    setUserName(state,name){
      state.userName=name
    },
    setIsPrint(state){
      state.isPrint = true
    },
    setNoPrint(state){
      state.isPrint = false
    },
    setRadioAnswer(state,answer){
      state.answer.radioAnswer=answer
    },
    setOptionAnswer(state,answer){
      state.answer.optionAnswer=answer
    },
    setText(state,answer){
      state.answer.text=answer
    },
  },
  actions: {
  },
  modules: {
  }
})
