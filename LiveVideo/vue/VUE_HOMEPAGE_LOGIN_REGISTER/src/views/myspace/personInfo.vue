<template>
  <el-avatar size="large" :src="teacherForm.photoUrl"></el-avatar>
  <el-descriptions title="个人信息">
    <el-descriptions-item label="用户名"> {{ this.teacherForm.userName }} </el-descriptions-item>
    <el-descriptions-item label="邮箱"> {{ this.teacherForm.mail }} </el-descriptions-item>
    <el-descriptions-item label="用户类型">
      <el-tag size="small"> {{ this.teacherForm.userType }} </el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="账户 ">
      <el-tag size="small"> {{ this.teacherForm.account }} </el-tag>
    </el-descriptions-item>

  </el-descriptions>
  <el-descriptions title="已加入的课程">
  </el-descriptions>
  <div>
    <div style="margin-bottom: 15px">fill: <el-switch v-model="fill" /></div>
    <el-space :fill="fill" wrap style="width: 100%">
      <el-card v-for="item in courseForm" :key="item" class="box-card">
        <template #header>
          <div class="card-header">
            <span>{{item.courseName}}</span>
            <el-button class="button" text @click="toCourse(item)"> 进入课程 </el-button>
          </div>
        </template>
        <div class="text item">
          {{ item.introduction }}
        </div>
      </el-card>
    </el-space>
  </div>
</template>

<script>
import { ref } from 'vue'
import router from "@/router";
import {getPhoto} from "@/utils";
export default {
  name: "personInfo",
  data() {
    return{
      currentPage: 1,
      pageSize: 5,
      teacherForm: {
        userName: "teacher1",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      courseForm: [
        {
          id:"aa",
          courseName: "course",
          teacherName: "teacher",
          introduction: "intro",
          coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
          status: "OK"
        }
      ],
      messageTable: [
        {
          title: "Da Jia Hao",
          date: "2022-11-10",
          context: "Hao Ye"
        }
      ],
      fill: ref(true)
    }
  },
  mounted() {
    this.fetchData();
    this.fetchCourse();
  },
  methods: {
    fetchData() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.teacherForm = result;
        this.teacherForm.photoUrl = getPhoto(this.teacherForm.photoUrl);
        // 判断结果
        if (result) {
          /*登陆成功*/
          /*跳转页面*/
          console.log(this.teacherForm.userName)
          this.fetchCourse()
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchMessage() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/notice/all?userName={' + this.teacherForm.userName + '}&courseName={' + this.courseForm.at(0).courseName + '}').then(res => {
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.messageTable = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchCourse() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/all', {
        params: {
          o: this.pageSize,
          page: this.currentPage,
          userName: this.teacherForm.userName
        }
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.courseForm = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          this.fetchMessage()
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    toCourse(item) {
      router.push(`/courseDetailPage?courseId=${item.id}`)
    }
  }
}
</script>

<style scoped>

</style>
