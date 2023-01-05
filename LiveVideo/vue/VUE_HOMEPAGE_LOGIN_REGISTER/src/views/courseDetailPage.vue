<template>
  <menubar></menubar>
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
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-card class="box-card">
        <el-table :data="infoTable" border stripe style="width: 100% ">
          <el-table-column prop="info" label="Course Info" align="center" min-width="500px"></el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  :total="total"
                  background
                  layout="total, prev, next, jumper, pager"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-card class="box-card">
        <el-table :data="sectionTable" border stripe style="width: 100% ">
          <el-table-column prop="sectionName" label="Section List" align="center" min-width="500px"></el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  :total="total"
                  background
                  layout="total, prev, next, jumper, pager"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
    <el-row v-show="coShow" type="flex" class="row-bg card" justify="center" align="bottom">
      <el-button type="primary" @click="toAssignUpload">Assignment</el-button>
      <el-button type="primary" @click="toQuizUpload">Quiz</el-button>
      <el-button type="primary" @click="toAllGrade">Grade</el-button>
      <el-button type="primary" @click="videoPage">lessons</el-button>
      <el-button type="primary" @click="addSection">Add Section</el-button>
    </el-row>
    <el-row v-show="coNotShow" type="flex" class="row-bg card" justify="center" align="bottom">
      <el-button type="primary" @click="toAssign">Assignment</el-button>
      <el-button type="primary" @click="toQuiz">Quiz</el-button>
      <el-button type="primary" @click="toGrade">Grade</el-button>
      <el-button type="primary" @click="videoPage">lessons</el-button>
    </el-row>
  </div>
</template>

<script>
// @ is an alias to /src
import router from "@/router";
import {useRoute} from "vue-router";
import menubar from "@/components/layout/menu";

export default {
  name: 'courseDetailPage',
  components: {
    menubar
  },
  data() {
    return {
      coShow: true,
      coNotShow: false,
      courseId: useRoute().query.courseId,
      courseForm: {
        courseName: "checker",
        tag: "CS202",
        teacherName: "black",
        coursePic: "url",
        charge: "0"
      },

      infoTable: [{
        info: "empty hello"
      }],
      sectionTable: [
        {
          sectionName: "example",
          sectionUrl: "url"
        }
      ]
    }
  },
  mounted() {
    this.fetchCourse()
    this.fetchUserType()
  },
  methods: {
    fetchCourse() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/' + this.courseId).then(res => {
        // 拿到结果
        let result = res.data.result
        console.log(result)
        this.courseForm.courseName = result.courseName
        this.courseForm.tag = result.tag
        this.courseForm.coursePic = result.coursePicture
        this.courseForm.teacherName = result.teacherName
        this.infoTable[0].info = result.introduction
        this.courseForm.charge = result.charge
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
    toAssign() {
      router.push(`/homeworkHome`)
    },
    toAssignUpload() {
      router.push(`/homeworkUpload`)
    },
    toQuiz() {
      router.push(`/quizHome`)
    },
    toQuizUpload() {
      router.push(`/quizUpload`)
    },
    videoPage() {
      router.push(`/videoPage?courseId=${this.courseId}`)
    },
    addSection() {
      router.push(`/addSection?courseId=${this.courseId}`)
    },
    toGrade() {
      router.push(`/studentGrade?courseId=${this.courseId}`)
    },
    toAllGrade() {
      router.push(`/teacherGrade?courseId=${this.courseId}`)
    },
    async fetchUserType() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
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
          // router.push('/login')
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
