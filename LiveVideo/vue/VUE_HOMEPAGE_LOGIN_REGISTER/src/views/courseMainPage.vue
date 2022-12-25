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
      <el-button type="primary" @click="detailed">课程信息</el-button>
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
      courseId: '2',
      courseForm: {
        courseName: "DSAA",
        teacherName: "李开",
        coursePic: "url"
      },
      joinForm: {
        studentName: 'black',
        courseId: '2'
      }
    }
  },
  mounted() {

  },
  methods: {
    fetchCourse() {
      // this.courseId = localStorage.getItem('courseId');
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course', {
        params: {
          courseId: this.courseId
        }
      }).then(res => {
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
        let message = res.data;
        // 判断结果
        if (result === 200) {
          /*跳转页面*/
          router.push('/')
        } else {
          alert(message)
        }
      })
      router.push('/payment')
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
      router.push('/courseDetailPage')
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
