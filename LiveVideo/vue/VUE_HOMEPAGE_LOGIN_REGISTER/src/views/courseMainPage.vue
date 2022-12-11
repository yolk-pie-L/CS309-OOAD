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
      courseForm: {
        courseName: "checker",
        teacherName: "black",
        coursePic: "url"
      }
    }
  },
  mounted() {
    this.getAA()
  },
  methods:{
    getAA(){
      this.courseForm.courseName=router.query.id;
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
