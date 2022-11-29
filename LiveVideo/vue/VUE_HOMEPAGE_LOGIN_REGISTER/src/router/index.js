import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login';
import HomeView from "@/views/HomeView";
import register from "@/views/regist";
import Home from '@/views/home'
import Home1 from '@/views/home1'
import teacherHomeView from "@/views/teacherHomeView";
import infoUpdate from "@/views/infoUpdate";
import courseUpdate from "@/views/courseUpdate";
import courseMainPage from "@/views/courseMainPage";
import courseDetailPage from "@/views/courseDetailPage";
import studentHome from "@/views/StudentHome";
import studentinfoUpdate from "@/views/StudentinfoUpdate";
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
  },
  {
    path: '/teacherHomeView',
    name: teacherHomeView,
    component: teacherHomeView
  },
  {
    path: '/infoUpdate',
    name: infoUpdate,
    component: infoUpdate
  },
  {
    path: '/courseUpdate',
    name: courseUpdate,
    component: courseUpdate
  },
  {
    path: '/courseMainPage',
    name: courseMainPage,
    component: courseMainPage
  },
  {
    path: '/courseDetailPage',
    name: courseDetailPage,
    component: courseDetailPage
  },
  {
    path: '/studentHome',
    name: studentHome,
    component: studentHome
  },
  {
    path: '/studentinfoUpdate',
    name: studentinfoUpdate,
    component: studentinfoUpdate
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
