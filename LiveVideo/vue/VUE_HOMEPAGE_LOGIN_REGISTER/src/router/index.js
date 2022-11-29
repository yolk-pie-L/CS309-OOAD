import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login';
import HomeView from "@/views/HomeView";
import register from "@/views/regist";
import Home from '@/views/home'

const routes = [
  {
    path:'/',
    name:'home',
    component:Home
  },
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
    path: '/register',
    name: 'register',
    component: register
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
