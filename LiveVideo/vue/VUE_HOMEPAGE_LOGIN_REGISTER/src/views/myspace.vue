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
        <el-menu-item index="1">Processing Center</el-menu-item>
        <el-sub-menu index="2">
          <template #title>Workspace</template>
          <el-menu-item index="2-1">item one</el-menu-item>
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
            <router-link to="/person/info">
              <el-menu-item index="1">个人信息</el-menu-item>
            </router-link>
            <router-link to="/person/modify-info">
              <el-menu-item index="2">信息修改</el-menu-item>
            </router-link>
            <router-link to="/person/modify-avatar">
              <el-menu-item index="3">头像修改</el-menu-item>
            </router-link>
            <div v-show="coShow">
            <router-link :to="{path: '/person/course', query: {method: 'create'}}">
              <el-menu-item index="4">创建课程</el-menu-item>
            </router-link>
            <router-link :to="{path: '/person/course', query: {method: 'modify'}}">
              <el-menu-item index="5">修改课程</el-menu-item>
            </router-link>
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

export default {
  name: 'MySpace',
  data() {
    return {
      coShow: true,
      adminShow: false,
      activeIndex: ref('1'),
      activeIndex2: ref('1')
    }
  },
  mounted() {
    this.fetchUserType()
  },
  methods: {
    handleSelect1: (key, keyPath) => {
      console.log(key, keyPath)
      switch (key) {
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
    async fetchUserType() {
      await this.$axios.get('http://localhost:8082/api/user').then(res => {
        console.log(res)
        let result = res.data.result
        if (res.data.code === 200) {
          this.coShow = result.userType === 'Teacher'
          this.adminShow = (result.adminRight === 'Admin' || res.adminRight === 'SuperAdmin')
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
