<template>
  <div :xl="6" :lg="7" class="home">
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="top">
      <el-col :span="10">
        <!--标题-->
        <img src="@/assets/logo.png" alt="logo">
      </el-col>
      <el-col :span="10">
        <el-row v-text="this.courseForm.courseName" class="title"></el-row>
        <el-row v-text="this.courseForm.teacherName" class="title"></el-row>
      </el-col>
    </el-row>
    <el-row v-show="coShow" type="flex" class="row-bg card" justify="center" align="bottom">
      <el-button type="primary" @click="detailed">课程信息</el-button>
    </el-row>
    <el-row v-show="coNotShow" type="flex" class="row-bg card" justify="center" align="bottom">
      <el-button type="primary" @click="detailed">课程信息</el-button>
      <el-button type="primary" @click="joinCourse">报名课程</el-button>
      <el-button type="primary" @click="leave">退课</el-button>
    </el-row>
  </div>
</template>

<script>
// @ is an alias to /src
import router from "@/router";
import { useRouter, useRoute } from 'vue-router';

export default {
  name: 'courseMainPage',
  data() {
    return {
      coShow: true,
      coNotShow: false,
      courseId: useRoute().query.courseId,
      courseForm: {
        courseName: "DSAA",
        teacherName: "李开",
        coursePic: "url"
      },
      joinForm: {
        studentName: 'black',
        courseId: ''
      }
    }
  },
  mounted() {
    this.fetchUser();
    this.fetchCourse();
    this.fetchUserType();
  },
  methods: {
    fetchUser() {
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result
        this.joinForm.studentName = result.userName;
        this.joinForm.courseId = this.courseId;
      })
    },
    fetchCourse() {
      // this.courseId = localStorage.getItem('courseId');
      console.log('课程id'+this.courseId)
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/course/${this.courseId}`
      ).then(res => {
        // 拿到结果
        let result = res.data.result
        this.courseForm.courseName = result.courseName
        this.courseForm.teacherName = result.teacherName
        // let message = res.data.msg;
        // 判断结果
        if (result) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(this.courseId);
        }
      })
    },
    joinCourse() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.post('http://localhost:8082/api/course/enroll', this.joinForm).then(res => {
        // 拿到结果
        let result = res.data.code
        let message = res.data.result;
        // 判断结果
        if (result === 200) {
          /*跳转页面*/
          router.push('/')
        } else {
          this.$notify({
            title: "加入失败",
            message: message,
            type: "error"
          })
          router.push(`/payment?courseId=${this.courseId}`)
        }
      })
    },
    leave() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.post('http://localhost:8082/api/course/exit', this.joinForm).then(res => {
        // 拿到结果
        let code = res.data.code;
        let message = res.data.message;
        // 判断结果
        if (code === 200) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          alert(message)
        }
      })
    },
    detailed() {
      this.$axios.get('http://localhost:8082/api/course/isRelated', {
        params: {
          courseId: this.courseId,
          userName: this.joinForm.studentName
        }
      }).then(res => {
        if (res.data.code === 200) {
          console.log(res)
          if (res.data.result) {
            router.push(`/courseDetailPage?courseId=${this.courseId}`)
          }
          else {
            alert("您不是这个课程相关的人员")
          }
        }
      })
    },
    async fetchUserType() {
      await this.$axios.get('http://localhost:8082/api/user').then(res => {
        console.log(res)
        if (res.data.code === 200) {
          this.coShow = res.data.result.userType === 'Teacher'
          this.coNotShow = res.data.result.userType !== 'Teacher'
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
<style scoped>

.title {
  text-shadow: -1px 1px 1px #5f565e;
  text-align: center;
  margin-top: 0px;
  margin-bottom: 20px;
  color: black;
  font-size: 40px;
}
</style>
