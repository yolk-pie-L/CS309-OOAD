<template>
  <el-affix>
    <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
    >
      <el-menu-item index="0" @click="toHome">首页</el-menu-item>
      <div class="flex-grow" />
      <el-menu-item>
        <el-avatar size="large" :src="userForm.photoUrl" @click="toOne"></el-avatar>
      </el-menu-item>
      <el-menu-item index="1"> 直播课程 </el-menu-item>
      <el-sub-menu index="2">
        <template #title>{{userForm.userName}}</template>
        <el-menu-item index="2-1" @click="toOne"> {{item.one}}</el-menu-item>
        <el-menu-item index="2-2" @click="toTwo"> {{item.two}}</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </el-affix>
</template>

<script>
import router from '../../router'
import { ref } from 'vue'
import {getPhoto} from "@/utils";
export default {
  data() {
    name: 'menu'
    return {
      userForm: {
        userName: "user",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      item: {
        one: '个人主页',
        two: '注销',
      },
      image: '',
      activeIndex: ref('1'),
      activeIndex2: ref('1')
    };
  },
  mounted() {
    this.fetchUser()
  },
  methods: {
    fetchUser() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        if (res.data.code === 200) {
          let result = res.data.result;
          this.userForm = result;
          this.userForm.photoUrl = getPhoto(this.userForm.photoUrl)
          this.item.one = '个人主页'
          this.item.two = '注销'
        } else {
          this.userForm.userName = '未登录'
          this.userForm.photoUrl = 'https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg'
          this.item.one = '登录'
          this.item.two = '注册'
        }
      })
    },
    handleSelect: (key, keyPath) => {
      console.log(key, keyPath)
      switch (key) {
        case "1":
          router.push("/live/all")
          break
      }
    },
    toHome() {
      router.push('/');
    },
    toOne() {
      if (this.item.one === '个人主页')
        this.toProfile()
      else
        this.toLogin()
    },
    toTwo() {
      if (this.item.two === '注销')
        this.removeToken()
      else
        this.toRegister()
    },
    toProfile() {
      // this.$axios.get('http://localhost:8082/api/user').then(res => {
      //   let result = res.data.result;
      //   if (result.userType === 'Student') {
      //     router.push('/studentHome');
      //   } else if (result.userType === 'Teacher') {
      //     router.push('/teacherHomeView');
      //   } else {
      //     console.log(res)
      //   }
      // })
      router.push('person/info')
    },
    removeToken() {
      localStorage.removeItem('token');
      router.go(0);
    },
    toLogin() {
      router.push('login')
    },
    toRegister() {
      router.push('register')
    }
  }
}
</script>

<style scoped>

</style>
