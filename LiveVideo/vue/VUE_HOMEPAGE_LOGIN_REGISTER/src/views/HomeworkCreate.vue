<template>
  <div :lg="7" :xl="6" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card class="welcome" shadow=hover>
          <el-header class="tit" v-text="classForm.courseName"></el-header>
          <el-header class="tit">Create Assignment</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <!--form表单-->
    <div>
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form ref="loginForm" :model="homeworkForm" :rules="rules" class="loginForm" label-width="21%">
          <el-form-item class="variable1" label="AssignmentName" prop="assignmentName" style="width: 380px">
            <el-input v-model="homeworkForm.assignmentName"></el-input>
          </el-form-item>
          <el-form-item class="variable1" label="Deadline" prop="deadline" style="width: 380px">
            <el-date-picker v-model="homeworkForm.deadline" type="date" placeholder="选择日期" >
            </el-date-picker>
          </el-form-item>
          <el-form-item class="variable1" label="Description" prop="description" style="width: 380px">
            <el-input v-model="homeworkForm.description"></el-input>
          </el-form-item>
          <el-form-item class="variable1" label="totalGrade" prop="totalGrade" style="width: 380px">
            <el-input v-model="homeworkForm.totalGrade"></el-input>
          </el-form-item>
          <el-form-item class="variable1" label="Additional Source" prop="additionalResources" style="width: 380px">

              <el-form :model="homeworkForm.additionalResources" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
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
              </el-form>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">立即上传</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "HomeworkUpload",
  data() {
    return {
      actionUrl: 'http://localhost:8082/api/section/upload2',//上传的后台地址
      classForm: {
        id: "",
        courseName: "course",
        teacherName: "",
        introduction: "",
        charge: "",
        tag: "",
        coursePicture: "",
        privateKeyUrl: "",
        status: ""
      },
      homeworkForm: {
        courseId: "",
        assignmentName: "",
        deadline: "",
        description: "",
        totalGrade: "",
        additionalResources: [
          {
            resourceName: "",
            resourceUrl: ""
          }
        ]
      },
      rules: {},
    };
  },
  mounted() {
    this.fetchClass()
  },
  methods: {
    fetchClass() {
      this.homeworkForm.courseId = localStorage.getItem("course")
      this.$axios.get(`api/course/{` + this.classForm.id + `}`).then(res => {
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
    },
    handleChange(file, fileList) {
      let formdata = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        formdata.append("f", item.raw)  //将每一个文件图片都加进formdata
      })
      formdata.append("assignmentId", localStorage.getItem("Assignment"))
      console.log(file.size)
      this.$axios.post("http://localhost:8082/api/assignment/upload", formdata).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (result) {
          let aso = {}
          aso.resourceUrl = result.resourceUrl;
          this.homeworkForm.additionalResources.push(aso);
        } else{
          alert(message);
        }
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/course/createAssignment', this.homeworkForm).then(res => {
            // 拿到结果
            let result = res.data.data;
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              localStorage.setItem("course", this.homeworkForm.courseId)
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
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 50%;
  position: absolute;
  left: 40%;
  top: 30%;
}

/deep/ .variable1 {
  font-size: 10px;
  font-weight: 600;
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
</style>

<style lang="less" scoped>
.file-upload {
  .file-upload-el {
    width: 300px;
    margin: auto;
  }

  padding-top: 50px;
}

.v-box-card {
  width: 50%;
  margin: auto;
}
</style>
