<template>
  <el-row style="height: 95vh">
    <el-col :span="24" style="height: 100%">
      <el-card shadow=hover class="welcome">
        <el-header class="tit" >Grade</el-header>
        <div class="border"></div>
      </el-card>
    </el-col>
  </el-row>
  <div class="tableD">
  <el-table :data="gradeForm" height="300" class="tableH" >
    <el-table-column prop="sectionName" label="sectionName" width="300" />
    <el-table-column prop="studentProgress" label="studentProgress" width="250"></el-table-column>
    <el-table-column prop="studentGrade" label="studentGrade" width="250" />
  </el-table>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "studentGrade",
  data() {
    return {
      userName: 'black',
      courseId: '0',
      gradeForm: [
        {
          sectionName: 's1',
          studentProgress: '0.1',
          studentGrade: '10'
        }
      ],
    };
  },
  mounted() {
    this.fetchUser()
  },
  methods: {
    fetchUser() {
      console.log(localStorage.getItem('token'))
      this.courseId = this.$route.query.courseId
      console.log(this.courseId)
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        this.userName = result.userName
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          console.log(this.userName)
          this.fetchGrade()
          /*跳转页面*/
          // router.push('/')
        } else if (localStorage.getItem('token')) {
          /*打印错误信息*/
          this.$notify({
            title: '失败',
            message: result,
            type: 'error'
          })
          localStorage.removeItem('token')
        }
      })
    },
    fetchGrade() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/student/section/grades', {
        params: {
          studentName: this.userName,
          courseId: this.courseId
        }
      }).then(res => {
        // 拿到结果
        let result = res.data.result;
        this.gradeForm = result
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          console.log(this.userName)
          /*跳转页面*/
          // router.push('/')
        } else if (localStorage.getItem('token')) {
          /*打印错误信息*/
          this.$notify({
            title: '失败',
            message: result,
            type: 'error'
          })
          localStorage.removeItem('token')
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/.welcome {
  position: fixed;
  top: -20px;
  width: 100%;
  height: 25%;
  margin-top: 20px;
  background-color: #c1d1d7;
  color: #42b983;
  padding: 20px 0 0 20px;
  font-size: 60px;
  font-weight: 600;
}

.tit{
  position: center;
  top:5px;
}

</style>
