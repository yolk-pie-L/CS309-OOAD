import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '../views/Login.vue'
import HomeView from "@/views/HomeView";
import regist from "@/views/regist";

const routes = [
  // 配置登陆页面的路由
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/home',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: '/regist',
    name: 'regist',
    component: regist
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
