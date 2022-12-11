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
    <el-row  type="flex" justify="space-around" align="middle">
      <el-button type="primary" @click="this.$router.push('/courseDetailPage')">课程信息</el-button>
      <el-button type="primary" @click="joinCourse">报名课程</el-button>
      <el-button type="primary" @click="leave">退课</el-button>
    </el-row>
  </div>
</template>

<script>
// @ is an alias to /src
import router from "@/router";

export default {
  name: 'courseMainPage',
  data() {
    return {
      studentName: 'black',
      courseId: '123',
      courseForm: {
        courseName: "checker",
        teacherName: "black",
        coursePic: "url"
      }
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        // let message = res.data.msg;
        console.log(res)
        // 判断结果
        if (res.data.userName) {
          /*登陆成功*/
          this.studentName = res.data.result.userName
          /*跳转页面*/
          router.push('/1')
        } else {
          /*打印错误信息*/
          alert(this.studentName);
        }
      })
    },
    joinCourse() {
      this.$axios.post('http://localhost:8082/api/course/enroll', {
        studentName: this.studentName,
        courseId: this.courseId
      }).then(res => {
        // 拿到结果
        let result = res.data.code
        let message = res.data;
        // 判断结果
        if (result === 200) {
          /*跳转页面*/
          router.push('/1')
        } else {
          alert(message)
        }
      })
    },
    leave() {
      this.$axios.post('http://localhost:8082/api/course/exit?studentName=${studentName}&courseId=${courseId}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        // 判断结果
        if (result) {
          /*登陆成功*/
          Element.Message.success(message);
          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          Element.Message.error(message);
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
