import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import QuestionnaireManage from '../views/QuestionnaireManage'
import MyQuestionnaire from '../views/MyQuestionnaire'
import Recycle from '../views/Recycle'
import Normal from '../views/Normal'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/login',
    name:'Login',
    component: Login
  },
  {
    path:'/QuestionnaireManage',
    name:'QuestionnaireManage',
    component: QuestionnaireManage,
    children: [
      {
          path: '/',
          component: () => import(/* webpackChunkName: 'root' */ '../views/MyQuestionnaire')
      },
      {
          path: 'Recycle',
          component: () => import(/* webpackChunkName: 'root' */ '../views/Recycle')
      }
  ]
  },
  {
    path:'/normal',
    name:'Normal',
    component: Normal
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
