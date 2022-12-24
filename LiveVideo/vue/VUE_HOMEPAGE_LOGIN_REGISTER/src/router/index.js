import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login';
import register from "@/views/regist";
import Home from '@/views/home'
import teacherHomeView from "@/views/teacherHomeView";
import infoUpdate from "@/views/infoUpdate";
import courseUpdate from "@/views/courseUpdate";
import courseMainPage from "@/views/courseMainPage";
import courseDetailPage from "@/views/courseDetailPage";
import studentHome from "@/views/StudentHome";
import studentinfoUpdate from "@/views/StudentinfoUpdate";
import homeworkHome from "@/views/HomeworkHome";
import homeworkPage from "@/views/HomeworkPage";
import administer from "@/views/Administer";
import quizHome from "@/views/QuizHome";
import quizPage from "@/views/QuizPage";
import quizUpload from "@/views/QuizUpload";
import homeworkUpload from "@/views/HomeworkUpload";
import videoPage from "@/views/videoPage";
import addSection from "@/views/addSection";
import addNotice from "@/views/addNotice";
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
  },
  {
    path: '/homeworkHome',
    name: homeworkHome,
    component: homeworkHome
  },
  {
    path: '/homeworkPage',
    name: homeworkPage,
    component: homeworkPage
  },
  {
    path: '/homeworkUpload',
    name: homeworkUpload,
    component: homeworkUpload
  },
  {
    path: "/administer",
    name: administer,
    component: administer
  },
  {
    path: "/quizHome",
    name: quizHome,
    component: quizHome
  },
  {
    path: "/quizPage",
    name: quizPage,
    component: quizPage
  },
  {
    path: "/quizUpload",
    name: quizUpload,
    component: quizUpload
  },
  {
    path: '/videoPage',
    name: videoPage,
    component: videoPage
  },
  {
    path: '/addSection',
    name: addSection,
    component: addSection
  },
  {
    path: '/addNotice',
    name: addNotice,
    component: addNotice
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})


// // 导航守卫
// // 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
// router.beforeEach((to, from, next) => {
// //如果去往登录页则放行
//   if (to.path === '/login'|| to.path ==='/' ||to.path==='/register'||to.path==='/administer'||to.path==='/teacher') {
//     next();
//   } else {
//     // 从本地存储里获取token
//     let token = localStorage.getItem('token');
//     // 判断token是否为空如果为空则跳转到登录页 如果有则放行
//     if (token === null || token === '') {
//       next({path:'/'});
//     } else {
//       next();
//     }
//   }
// });

export default router
