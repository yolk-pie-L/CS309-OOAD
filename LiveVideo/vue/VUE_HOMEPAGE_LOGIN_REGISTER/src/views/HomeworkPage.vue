<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName" ></el-header>
          <el-header class="tit" >Upload Assignment</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <!--form表单-->
    <el-row type="flex" class="row-bg" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="homeworkForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="AssignmentName"  prop="assignmentName" style="width: 380px">
            <el-input v-model="homeworkForm.assignmentName"></el-input>
          </el-form-item>
          <el-form-item label="Deadline" prop="deadline" style="width: 380px">
            <el-input v-model="homeworkForm.deadline"></el-input>
          </el-form-item>
          <el-form-item label="Description" prop="description" style="width: 380px">
            <el-input v-model="homeworkForm.description"></el-input>
          </el-form-item>
          <el-form-item label="totalGrade" prop="totalGrade" style="width: 380px">
            <el-input v-model="homeworkForm.totalGrade"></el-input>
          </el-form-item>
          <el-form-item label="Additional Source" prop="additionalResources" style="width: 380px">
            <el-upload
                :action="uploadURL"
                :style="{backgroundImage:'url(' + dialogImageUrl + ')', backgroundRepeat:'no-repeat', backgroundPosition:'center center', backgroundSize: 'contain'}"
                list-type="picture-card"
                class="uploadImg"
                name="files"
                :before-upload="beforeUpload">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">立即上传</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "HomeworkUpload",
  data() {
    return {
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
        courseId:"",
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
      rules: {

      },
    };
  },
  mounted() {
    this.fetchAssignment()
  },
  methods: {
    fetchClass() {
      this.$axios.get('api/course/{' + this.classForm.id + '}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
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
    beforeUpload (file) {
      this.homeworkForm.additionalResources.append('file', file)
      return false
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/user/checkLogin', this.courseForm).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              Element.Message.success(message);
              /*跳转页面*/
              router.push('/')
            } else {
              /*打印错误信息*/
              Element.Message.error(message);
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
  width: auto;
  position: absolute;
  left: 70%;
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

.row-bg{
  position: absolute;
  top: 20%;
  left: 70%;
}
</style>
