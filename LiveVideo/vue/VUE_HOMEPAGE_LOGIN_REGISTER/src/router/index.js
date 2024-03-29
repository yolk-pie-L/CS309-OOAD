import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/views/Login';
import register from "@/views/regist";
import Home from '@/views/home'
import teacherHomeView from "@/views/myspace/teacherHomeView";
import infoUpdate from "@/views/myspace/infoUpdate";
import courseUpdate from "@/views/myspace/courseUpdate";
import courseMainPage from "@/views/courseMainPage";
import courseDetailPage from "@/views/courseDetailPage";
import studentHome from "@/views/myspace/StudentHome";
import studentinfoUpdate from "@/views/StudentinfoUpdate";
import homeworkHome from "@/views/HomeworkHome";
import homeworkPage from "@/views/HomeworkPage";
import administer from "@/views/Administer";
import quizHome from "@/views/QuizHome";
import quizPage from "@/views/QuizPage";
import quizUpload from "@/views/QuizUpload";
import homeworkCreate from "@/views/HomeworkCreate";
import videoPage from "@/views/videoPage";
import addSection from "@/views/addSection";
import payment from "@/views/payment";
import personInfo from "@/views/myspace/personInfo";
import myspace from "@/views/myspace/myspace";
import addNotice from "@/views/addNotice";
import avatarUpdate from "@/views/myspace/avatarUpdate";
import live from "@/views/live/live";
import studentGrade from "@/views/studentGrade";
import teacherGrade from "@/views/teacherGrade";
import live_all from "@/views/live/live_all";
import paySuccess from "@/views/paySuccess";
import pdfView from "@/views/pdfView";
import homeworkCheck from "@/views/HomeworkCheck";
import cospace from "@/views/cospace/cospace";
import coinfo from "@/views/cospace/coinfo";
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
    path: '/homeworkCreate',
    name: homeworkCreate,
    component: homeworkCreate
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
    path: '/pdf/preview',
    name: pdfView,
    component: pdfView
  },
  {
    path: '/addSection',
    name: addSection,
    component: addSection
  },
  {
    path: '/payment',
    name: payment,
    component: payment
  },
  {
    path: '/pay/success',
    name: paySuccess,
    component: paySuccess
  },
  {
    path: '/live',
    name: live,
    component: live
  },
  {
    path: '/live/all',
    name: live_all,
    component: live_all
  },
  {
    path: '/course',
    name: cospace,
    component: cospace,
    children: [
      {
        path: '/course/info',
        name: coinfo,
        component: coinfo
      },
      {
        path: '/course/homework/home',
        name: homeworkHome,
        component: homeworkHome
      },
      {
        path: '/course/homework/page',
        name: homeworkPage,
        component: homeworkPage
      },
      {
        path: '/course/homework/create',
        name: homeworkCreate,
        component: homeworkCreate
      },
      {
        path: '/course/quiz/home',
        name: quizHome,
        component: quizHome
      },
      {
        path: '/course/quiz/page',
        name: quizPage,
        component: quizPage,
      },
      {
        path: '/course/quiz/upload',
        name: quizUpload,
        component: quizUpload
      },
      {
        path: '/course/video/view',
        name: videoPage,
        component: videoPage
      },
      {
        path: '/course/video/create',
        name: addSection,
        component: addSection
      },
      {
        path: '/course/student-grade',
        name: studentGrade,
        component: studentGrade
      },
      {
        path: '/course/teacher-grade',
        name: teacherGrade,
        component: teacherGrade
      },
      {
        path: '/course/homework/check',
        name: homeworkCheck,
        component: homeworkCheck
      }
    ],
  },
  {
    path: '/person',
    name: myspace,
    component: myspace,
    children: [
      {
        path: '/person/info',
        name: personInfo,
        component: personInfo
      },
      {
        path: '/person/course',
        name: courseUpdate,
        component: courseUpdate
      },
      {
        path: '/person/modify-info',
        name: infoUpdate,
        component: infoUpdate
      },
      {
        path: '/person/modify-avatar',
        name: avatarUpdate,
        component: avatarUpdate
      },
      {
        path: '/person/create-notice',
        name: addNotice,
        component: addNotice
      }
    ],
  },
  {
    path: '/studentGrade',
    name: studentGrade,
    component: studentGrade
  },
  {
    path: '/teacherGrade',
    name: teacherGrade,
    component: teacherGrade
  },
  {
    path: '/homeworkCheck',
    name: homeworkCheck,
    component: homeworkCheck
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
