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
        <el-header class="variable1">AssignmentName:</el-header>
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
      <el-header class="variable1">AdditionalSource:</el-header>
      <div v-for="item in additionalResources">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="item.resourceUrl"
            class="addi"
            v-text="item.resourceName">
        </el-link>
      </div>
    </div>

    <div>
      <el-col :span="7" class="login-card" >
        <!--loginForm-->
        <el-form :model="answers.answerFile" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-upload
              action="#"
              multiple
              :auto-upload="false"
              :show-file-list="true"
              :on-change="handleChange"
              drag>
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
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

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      courseId:"",
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
      additionalResources: [
        {
          resourceName: "aa",
          resourceUrl: "https://element.eleme.io"
        },
        {
          resourceName: "aa",
          resourceUrl: "https://element.eleme.io"
        }
      ],
      answers: {
        assignmentId:"aaa",
        answerFile: [
          {
            answerName: "a",
            answerUrl: "b"
          },
          {
            answerName: "c",
            answerUrl: "d"
          }
        ]
      },
      sectionForm: {
        sectionName: 'blank'
      },
    };
  },
  mounted() {
    this.fetchClass();
    this.fetchAssignment()
  },
  methods: {
    fetchClass(){
      this.courseId=localStorage.getItem("course");
      this.homeworkForm.assignmentId=localStorage.getItem("Assignment");
      // localStorage.removeItem("assignment");
      localStorage.removeItem("course")
    },
    fetchAssignment() {
      this.$axios.get('http://localhost:8082/api/assignment/one', {
        params: {
          assignmentId: this.homeworkForm.assignmentId,
        }}).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.homeworkForm = result
        this.additionalResources = result.additionalResources
        this.answer = result.answer
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
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
      let formdata = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        formdata.append("f", item.raw)  //将每一个文件图片都加进formdata
      })
      formdata.append("assignmentId", localStorage.getItem("Assignment"))
      console.log(file.size)
      this.$axios.post("http://localhost:8082/api/assignment/upload", formdata).then(res => {
        console.log(res)
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/assignment/submit', this.answer).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              localStorage.setItem("course",this.homeworkForm.courseName)
              router.push('/homeworkHome')
            } else {

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
  top: 63%;
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
  top: 100%;
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

.addi {
  font-size: 30px;
  font-weight: 600;
}

.answer {
  position: absolute;
  top: 52%;
  left: 50%;
  width: 40%;
  height: 20%;
  background-color: #c1d1d7;
}

.upload {
  position: absolute;
  top: 75%;
  left: 50%;
  width: 40%;
  height: 20%;
  background-color: #c1d1d7;
}
</style>
