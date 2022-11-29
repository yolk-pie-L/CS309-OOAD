<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <!--标题-->
        <h1 class="title">Course Update</h1>
      </el-col>
      <el-col :span="6"></el-col>
    </el-row>
    <!--form表单-->
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="courseForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="课程名字" prop="username" style="width: 380px">
            <el-input v-model="courseForm.courseName"></el-input>
          </el-form-item>
          <el-form-item label="老师名字" prop="teacherName" style="width: 380px">
            <el-input v-model="courseForm.teacherName"></el-input>
          </el-form-item>
          <el-form-item label="课程标签" prop="courseLabel" style="width: 380px">
            <el-input v-model="courseForm.courseLabel"></el-input>
          </el-form-item>
          <el-form-item label="课程收费" prop="courseFee" style="width: 380px">
            <el-input v-model="courseForm.courseFee"></el-input>
          </el-form-item>
          <el-form-item label="课程描述" prop="courseDescription" style="width: 380px">
            <el-input v-model="courseForm.courseDescription"></el-input>
          </el-form-item>
          <el-form-item label="课程图片" prop="username" style="width: 380px">
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
  name: "Login",
  data() {
    return {
      // 表单信息
      courseForm: {
        // 账户数据
        courseName: 'checker',
        // 密码数据
        teacherName: 'checkerName',

        courseLabel: 'CS444',

        courseFee: '100',

        courseDescription: 'test',
      },
      // 表单验证
      rules: {

      },
    };
  },
  methods: {
    beforeUpload (file) {

      this.courseForm.append('file', file)

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

.title {
  text-shadow: -3px 3px 1px #5f565e;
  text-align: center;
  margin-top: 10%;
  color: #41b9a6;
  font-size: 40px;
}

.login-card {
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: auto;
}
</style>
