<template>
  <div :xl="6" :lg="7" class="bg-login">

    <div>
      <el-row style="height: 95vh">
        <el-col :span="24" style="height: 100%">
          <el-card class="welcome" shadow=hover>
            <el-header class="tit" v-text="homeworkForm.courseName "></el-header>
            <el-header class="tit" v-text="homeworkForm.assignmentName "></el-header>
            <div class="border"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="describe">
      <el-row>
        <el-header class="variable1">Assignment Name:</el-header>
        <el-header class="variable2" v-text="homeworkForm.assignmentName "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Deadline:</el-header>
        <el-header class="variable2" v-text="homeworkForm.deadline "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Status:</el-header>
        <el-header class="variable2" v-text="homeworkForm.status "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Score:</el-header>
        <el-header class="variable2" v-text="homeworkForm.score "></el-header>
        <el-header class="variable2">/</el-header>
        <el-header class="variable2" v-text="homeworkForm.totalGrade "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Create Time:</el-header>
        <el-header class="variable2" v-text="homeworkForm.createTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Update Time:</el-header>
        <el-header class="variable2" v-text="homeworkForm.updateTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Description:</el-header>
        <el-header class="variable2" v-text="homeworkForm.description "></el-header>
      </el-row>
    </div>

    <div class="add">
      <el-header class="variable1">Additional Resource:</el-header>
      <div v-for="item in additionalResources">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="toPDF(item)"
            v-text="item"
            class="addi">
        </el-link>
      </div>
    </div>

    <div class="add1">
      <el-header class="variable1">Answer:</el-header>
      <div v-for="item in answers">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="toPDF(item)"
            v-text="item"
            class="addi">
        </el-link>
      </div>
    </div>

    <div>
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="answers" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-upload
              action="#"
              multiple
              :auto-upload="false"
              :show-file-list="true"
              :on-change="handleChange"
              drag>
            <el-icon class="el-icon--upload">
              <upload-filled/>
            </el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">Update</el-button>
            <el-button @click="resetForm('loginForm')">Reset</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </div>
  </div>
</template>


<script>
import router from "@/router";
import {useRoute} from "vue-router";
import {getFile} from "@/utils";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "HomeworkPage",
  data() {
    return {
      courseId: useRoute().query.courseId,
      assignId: useRoute().query.assignId,
      homeworkForm: {
        assignmentId: "aa",
        courseName: "course",
        teacherName: "teacher",
        assignmentName: "homework1",
        deadline: "date",
        description: "Intro",
        status: "notStarted/return/submitted",
        score: "",
        totalGrade: "",
        createTime: "date",
        updateTime: "date",
        answerStatus: "yes/no",
      },
      additionalResources: ["https://element.eleme.io"],
      answers: ["answer file tmp"],
      sectionForm: {
        sectionName: 'blank'
      },
    };
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
          this.homeworkForm.courseName = result.courseName
        }
      })
    },
    toPDF(url) {
      return `http://localhost:8080/#/pdf/preview?assignId=${this.assignId}&courseId=${this.courseId}&src=${url}`
    },
    fetchAssignment() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/assignment/one', {
        params: {
          assignmentId: this.assignId,
        }
      }).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.homeworkForm = result;
        this.additionalResources = result.assignUrls;
        this.answers = result.answer;
        // 判断结果
        this.fetchCourse()
        if (result) {
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
    handleChange(file, fileList) {
      let formData = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        formData.append("f", item.raw)  //将每一个文件图片都加进formdata
      })
      console.log(file.size)
      this.$axios
          .post("http://localhost:8082/api/assignment/upload", formData)
          .then(res => {
            let result = res.data.result;
            let message = res.data.msg;
            if (res.data.code===200) {
              this.$notify.success("上传成功")
              this.answers.push(result.string);
            } else {
              alert(message);
            }
          })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          let formData = new FormData();
          formData.append("answerFile", this.answers);
          formData.append("assignmentId", this.assignId);
          formData.append("status", this.homeworkForm.status)
          this.$axios.post('http://localhost:8082/api/assignment/submit', formData
          ).then(res => {
            // 拿到结果
            let result = res.data.result;
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              router.push(`/homeworkHome?courseId=${this.courseId}`)
            } else {
              alert(result);
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  },
}
</script>

<style scoped>
.bg-login {
  height: 100%;
  background-size: 200%;

}

.btn-ground {
  text-align: center;
}

.login-card {
  background-color: #c1d1d7;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 30%;
  position: absolute;
  top: 90%;
  left: 55%;
}
</style>

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
  top: 30%;
  left: 55%;
  height: 20%;
  width: 30%;
  background-color: #c1d1d7;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
}

.add1 {
  position: absolute;
  top: 60%;
  left: 55%;
  height: 20%;
  width: 30%;
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

</style>
