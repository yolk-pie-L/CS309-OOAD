<template>
  <el-container>
    <el-header>
      <el-menu
          :default-active="activeIndex"
          class="el-menu-demo"
          mode="horizontal"
          :ellipsis="false"
          @select="handleSelect1"
      >
        <router-link to="/">
          <el-menu-item index="0">首页</el-menu-item>
        </router-link>
        <div class="flex-grow" />
        <el-menu-item index="1">通知中心</el-menu-item>
        <el-avatar :src="pic" size="large"></el-avatar>
        <el-sub-menu index="2">
          <template #title>Workspace</template>
          <el-menu-item index="2-1">个人主页</el-menu-item>
          <el-menu-item index="2-2">充值</el-menu-item>
          <el-menu-item index="2-3">注销</el-menu-item>
          <div v-show="adminShow">
            <el-menu-item index="2-4">管理员界面</el-menu-item>
          </div>
          <!--          <el-sub-menu index="2-4">-->
          <!--            <template #title>item four</template>-->
          <!--            <el-menu-item index="2-4-1">item one</el-menu-item>-->
          <!--            <el-menu-item index="2-4-2">item two</el-menu-item>-->
          <!--            <el-menu-item index="2-4-3">item three</el-menu-item>-->
          <!--          </el-sub-menu>-->
        </el-sub-menu>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside>
        <el-scrollbar>
          <el-menu
              :default-active="activeIndex2"
              class="el-menu-demo"
              mode="vertical"
              :ellipsis="false"
              @select="handleSelect2"
              style="height: 500px"
          >
            <el-menu-item index="0"> </el-menu-item>
            <el-menu-item index="1">课程信息</el-menu-item>
            <el-menu-item index="2">作业列表</el-menu-item>
            <el-menu-item index="3">测验列表</el-menu-item>
            <el-menu-item index="4">视频列表</el-menu-item>
            <el-menu-item index="5">查看成绩</el-menu-item>
            <div v-show="coShow">
              <el-menu-item index="6">上传作业</el-menu-item>
              <el-menu-item index="7">上传测试</el-menu-item>
              <el-menu-item index="8">查看所有学生成绩</el-menu-item>
              <el-menu-item index="9">检查作业</el-menu-item>
              <el-menu-item index="10"></el-menu-item>
            </div>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { ref } from 'vue'
import router from "@/router";
import {useRoute} from "vue-router";
import {getPhoto} from "@/utils";

export default {
  name: 'cospace',
  data() {
    return {
      coShow: true,
      adminShow: false,
      activeIndex: ref('1'),
      activeIndex2: ref('1'),
      courseId: useRoute().query.courseId,
      pic: '',
    }
  },
  mounted() {
    this.fetchUserType()
  },
  methods: {
    handleSelect1: (key, keyPath) => {
      console.log(key, keyPath)
      switch (key) {
        case '2-1':
          router.push(`/person/info`)
          break
        case '2-2':
          router.push(`/payment`)
          break
        case '2-3':
          localStorage.removeItem('token')
          router.push('/')
          break
        case '2-4':
          router.push('/administer')
      }
    },
    handleSelect2(key, keyPath) {
      console.log(key, keyPath)
      console.log(this.courseId)
      switch (key) {
        case '1':
          router.push(`/course/info?courseId=${this.courseId}`)
          break
        case '2':
          router.push(`/course/homework/home?courseId=${this.courseId}`)
          break
        case '3':
          router.push(`/course/quiz/home?courseId=${this.courseId}`)
          break
        case '4':
          router.push(`/course/video/view?courseId=${this.courseId}`)
          break
        case '5':
          router.push(`/course/student-grade?courseId=${this.courseId}`)
          break
        case '6':
          router.push(`/course/homework/create?courseId=${this.courseId}`)
          break
        case '7':
          router.push(`/course/quiz/upload?courseId=${this.courseId}`)
          break
        case '8':
          router.push(`/course/teacher-grade?courseId=${this.courseId}`)
          break
        case '9':
          router.push(`/course/homework/check?courseId=${this.courseId}`)
          break
        case '10':
          // router.push(`homework/home?courseId=${courseId}`)
          break
      }
    },
    async fetchUserType() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      await this.$axios.get('http://localhost:8082/api/user').then(res => {
        console.log(res)
        let result = res.data.result
        if (res.data.code === 200) {
          this.coShow = result.userType === 'Teacher'
          this.pic = getPhoto(result.photoUrl)
          this.adminShow = (result.adminRight === 'Admin' || result.adminRight === 'SuperAdmin')
        } else {
          this.$notify({
            title: '错误',
            message: '获取用户信息错误',
            type: 'error'
          })
          router.push('/login')
        }
      })
    }
  }
}
</script>
