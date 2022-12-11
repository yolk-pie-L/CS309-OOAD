<template>
  <div>
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName " ></el-header>
          <el-header class="tit" >Quiz</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <div class="tableD">
      <el-table :data="quizForm" height="300" class="tableH">
        <el-table-column prop="assignmentName" label="Assignment Title" width="300" />
        <el-table-column prop="status" label="Status" width="250" />
        <el-table-column prop="deadline" label="Due" width="250" />
        <el-table-column prop="score" label="Score" width="200" />
        <el-table-column fixed="right" label="ENTER" width="300">
          <template #default>
            <el-button link type="primary" size="small" @click="handleEnter(scope.$index)">Enter</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "QuizHome",
  data() {
    return {
      bgUrl:'url(https://p0.meituan.net/dpplatform/4ce8553013e2e819c08e6d6ba409bee8473079.jpg)',
      classForm: {
        courseName: "course",
        teacherName: "teacher",
        introduction: "intro",
        coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        privateKeyUrl: "ababa"
      },
      quizForm: [
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted",
          score: ""
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
      ]
    }
  },
  mounted() {
    this.fetchAssignment()
  },
  methods: {
    fetchAssignment() {
      this.$axios.get('api/course/allAssignment?courseId={' + this.classForm.privateKeyUrl + '}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.quizForm.id = result.id
        this.quizForm.assignmentName = result.assignmentName
        this.quizForm.deadline = result.deadline
        this.quizForm.status = result.status
        this.quizForm.score = result.score
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleEnter(index) {
      router.push({path:'/homeworkPage',query: {id:this.homeworkForm.at(index).id}})
    },
  }
}
</script>

<style>
.tableH th,
.tableH tr,
.tableH td {
  background-color: transparent;
}

.tableH {
  background-color: transparent;
  position: absolute;
  top: -100px;
  left: 50px;
  width: 90%;
  border-radius: 5%;
  font-size: 20px;
  font-weight: 600;
}

</style>

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