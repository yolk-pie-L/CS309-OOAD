<template>
  <div>
    <el-row style="height: 35vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName " ></el-header>
          <el-header class="tit" >Assignment</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
      <el-table :data="homeworkForm" height="400" class="tableH" style="width: 90%">
        <el-table-column prop="assignmentName" label="Assignment Title" width="300" />
        <el-table-column prop="status" label="Status" width="300" />
        <el-table-column prop="deadline" label="Due" width="300" />
        <el-table-column fixed="right" label="ENTER">
          <template v-slot="scope" #default>
            <el-button link type="primary" size="small" @click="handleEnter(scope.$index)">Enter</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
</template>

<script>
import router from "@/router";
import {useRoute} from "vue-router";

export default {
  name: "HomeworkHome",
  data() {
    return {
      courseId: useRoute().query.courseId,
      bgUrl:'url(https://p0.meituan.net/dpplatform/4ce8553013e2e819c08e6d6ba409bee8473079.jpg)',
      classForm: {
        courseName: "course",
        teacherName: "teacher",
        introduction: "intro",
        coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        privateKeyUrl: "1"
      },
      homeworkForm: [
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
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/course/${this.courseId}`).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.classForm=result
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
      this.$axios.get('http://localhost:8082/api/assignment/all', {
            params: {
              courseId: this.courseId,
            }}).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.homeworkForm = result;
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleEnter(index) {
      router.push(`/homeworkPage?assignId=${this.homeworkForm.at(index).id}&courseId=${this.courseId}`)
    },
    toPDF(url) {
      return `http://localhost:8080/#/pdf/preview?assignId=${this.assignId}&courseId=${this.courseId}&src=${url}`
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
  top: 100px;
  left: 10px;
  border-radius: 5%;
  font-size: 20px;
  font-weight: 600;
}

</style>

<style scoped>

/deep/.welcome {
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

.tit{
  position: center;
  top:5px;
}

</style>
