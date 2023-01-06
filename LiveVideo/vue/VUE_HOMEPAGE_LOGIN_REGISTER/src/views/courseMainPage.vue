<template>
  <div :xl="6" :lg="7" class="home">
    <!--标题-->
    <div :style="{'background-image': bgUrl}" class="bgBackground">
      <el-row type="flex" class="row-bg row-two" justify="center" align="top" >
        <el-col :span="10">
          <!--标题-->
          <img src="https://i.328888.xyz/2023/01/06/kll8N.jpeg" class="img1">
        </el-col>
        <el-col :span="10">
          <el-row v-text="this.courseForm.courseName" class="variable1"></el-row>
          <el-row v-text="this.courseForm.teacherName" class="variable2"></el-row>
          <el-row  class="variable3">¥</el-row>
          <el-row v-text="this.courseForm.charge" class="variable3"></el-row>
        </el-col>
      </el-row>
    </div>
    <div :style="{'background-image': bgUrl1}" class="bgBackground1">
      <el-row v-show="coShow" type="flex" class="type2" justify="center" align="bottom" >
        <el-button type="primary" @click="detailed" class="variable4">课程信息</el-button>
      </el-row>
      <el-row v-show="coNotShow" type="flex" class="type1" justify="center" align="bottom">
        <el-button type="primary" @click="detailed" class="variable4">课程信息</el-button>
        <el-button type="primary" @click="joinCourse" class="variable4">报名课程</el-button>
        <el-button type="primary" @click="leave" class="variable4">退课</el-button>
      </el-row>
    </div>
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
        teacherName: "CHEN",
        coursePic: "url",
        charge: 0
      },
      joinForm: {
        studentName: 'black',
        courseId: ''
      },
      bgUrl:'url(https://i.328888.xyz/2023/01/06/kCf0z.jpeg)',
      bgUrl1:'url(https://i.328888.xyz/2023/01/06/klJmJ.jpeg)',

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
        this.courseForm.courseName = "课程名：" + result.courseName
        this.courseForm.teacherName = "老师：" + result.teacherName
        this.courseForm.charge = "费用： " + result.charge
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
          this.$notify.success(`已扣款${this.courseForm.charge}￥`)
          router.push(`/courseDetailPage?courseId=${this.courseId}`)
        } else {
          this.$notify.error(message)
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

.bgBackground{
  background-repeat:no-repeat;
  background-size: 100%;
  width:100%;
  height:30%;
  opacity: 0.75;
}

.bgBackground1{
  background-repeat:no-repeat;
  background-size: 100%;
  position: absolute;
  top:43%;
  width:100%;
  height:60%;
  opacity: 0.8;
}

.img1{
  position: absolute;
  top:10%;
  left: 20%;
  width: 250px;
  height: 250px;
  border-radius: 20px;
  border: 2px solid #000000;
}

.variable1 {
  display: inline-block;
  position: center;
  top: 5px;
  width: 80%;
  font-size: 100px;
  font-weight: 600;
  font-family: mine;
}

.variable2 {
  display: inline-block;
  position: center;
  width: 100%;
  font-size: 50px;
  font-weight: 500;
  font-family: dia;
}

.variable3 {
  display: inline-block;
  position: center;
  font-size: 50px;
  font-weight: 500;
  font-family: dia;
}

.variable4 {
  position: center;
  font-size: 30px;
  font-weight: 400;
  font-family: dia;
}

.type1{
  position: center;
  width: 30%;
  height: 9%;
  top: 55%;
  left: 40%;
  border-radius: 2px;
  border: 2px solid #000000;
}

.type2{
  position: center;
  font-family: dfx;
  width: 12%;
  height: 9%;
  top: 55%;
  left: 45%;
  border-radius: 2px;
  border: 2px solid #000000;
}
</style>
