import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
// import HomeView from "@/views/HomeView";
import regist from "@/views/regist";
Vue.use(VueRouter)

const routes = [
  // 配置登陆页面的路由
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  // {
  //   path: '/',
  //   name: 'HomeView',
  //   component: HomeView
  // },
  {
    path: '/regist',
    name: 'regist',
    component: regist
  }
]

const router = new VueRouter({
  routes
})

export default router
