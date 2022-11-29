import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login';
import HomeView from "@/views/HomeView";
import register from "@/views/regist";
import Home from '@/views/home'
import Home1 from '@/views/home1'

const routes = [
  {
    path:'/1',
    name:'home',
    component:Home
  },
  {
    path:'/',
    name:'home1',
    component:Home1
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
