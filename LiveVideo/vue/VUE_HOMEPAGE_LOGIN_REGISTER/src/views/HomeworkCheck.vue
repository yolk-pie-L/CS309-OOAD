<template>
    <div>
      <el-row style="height: 35vh">
        <el-col :span="24" style="height: 100%">
          <el-card class="welcome" shadow=hover>
            <el-header class="tit" v-text="this.teacherName "></el-header>
            <el-header class="tit" v-text="this.courseName "></el-header>
            <div class="border"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <el-input v-model="this.grade" class="add1"></el-input>
      <el-table :data="homeworkForm" height="400" >
        <el-table-column label="StudentName" prop="studentName" width="400"/>
        <el-table-column label="AssignmentName" prop="assignmentName" width="400"/>
        <el-table-column label="TotalGrade" prop="totalGrade" width="200"/>
        <el-table-column fixed="right" label="Check" width="250">
          <template v-slot="scope" #default>
            <el-button link size="small" type="primary" @click="handleCheck(scope.$index)">Check</el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Grade">
          <template v-slot="scope" #default>
            <el-button link size="small" type="primary" @click="handleGrade(scope.$index)">Grade</el-button>
          </template>
        </el-table-column>
      </el-table>
    <div class="add">
      <el-header class="variable1">Answer:</el-header>
      <div v-for="item in this.answerSee">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="toPDF(item)"
            v-text="item"
            class="addi">
        </el-link>
      </div>
    </div>
</template>

<script>
import {useRoute} from "vue-router";

export default {
  name: "HomeworkCheck",
  data(){
    return{
      courseId: useRoute().query.courseId,
      assignId: useRoute().query.assignId,
      teacherName:"teacher",
      courseName:"course",
      grade:100,
      homeworkForm:[{
        assignId: "",
        studentName: "",
        assignmentName: "",
        totalGrade: "总分",
        answers:[],
      },
      ],
      answerSee: ["https://element.eleme.io"],
      submitGrade: {
        id:"",
        studentName:"",
        grade:""
      }
    }
  },
  mounted() {
    this.fetchAssignment()
  },
  methods: {
    fetchCourse() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/course/${this.courseId}`).then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.teacherName = result.teacherName
          this.courseName = result.courseName
        }
      })
    },
    toPDF(url) {
      return `http://localhost:8080/#/pdf/preview?assignId=${this.assignId}&courseId=${this.courseId}&src=${url}`
    },
    fetchAssignment() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/assignment/stuassign?courseId=${this.courseId}`).then(res => {
        let result = res.data.result;
        this.homeworkForm = result
        // 判断结果
        this.fetchCourse()
        if (res.data.code===200) {
        } else {
          /*打印错误信息*/
          alert(result);
        }
      })
    },
    uploading() {
      this.$message({
        message: '请等待上传完成',
        type: 'error'
      })
    },
    handleGrade(index) {
      this.submitGrade.id=this.homeworkForm.at(index).assignId;
      this.submitGrade.studentName=this.homeworkForm.at(index).studentName;
      this.submitGrade.grade=this.grade;
      this.$axios.post('http://localhost:8082/api/assignment/modify', this.submitGrade).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (res.data.code===200) {
          this.$notify.success("成功打分")
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleCheck(index) {
      this.answerSee=this.homeworkForm.at(index).answers
      alert(this.answerSee)
      this.$notify.success("切换作业")
    },
  },
}
</script>

<style scoped>

.variable1 {
  display: inline-block;
  position: center;
  top: 5px;
  font-size: 20px;
  font-weight: 600;
}

.variable2 {
  display: inline-block;
  position: center;
  font-size: 18px;
  font-weight: 500;
}

/deep/ .welcome {
  position: absolute;
  top: -20px;
  width: 100%;
  /*height: 25%;*/
  margin-top: 20px;
  background-color: #c1d1d7;
  color: #42b983;
  padding: 20px 0 0 20px;
  font-size: 60px;
  font-weight: 600;
}

.tit {
  position: center;
  top: 5px;
}

.describe {
  position: absolute;
  background-color: #c1d1d7;
  top: 30%;
  left: 8%;
  height: 70%;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 30%;
}

.add {
  position: absolute;
  top: 75%;
  left: 10%;
  height: 20%;
  width: 80%;
  background-color: #c1d1d7;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
}

.addi {
  font-size: 10px;
  font-weight: 600;
}

.add1{
  position: center;
  width: 60%;
}

</style>

<style>
.tableD th,
.tableD tr,
.tableD td {
  background-color: transparent;
}

.tableD {
  background-color: transparent;
  position: absolute;
  top: 10%;
  left: 50px;
  width: 90%;
  border-radius: 50%
}

</style>
