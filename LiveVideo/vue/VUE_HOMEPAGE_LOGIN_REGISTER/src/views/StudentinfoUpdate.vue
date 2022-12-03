<template>
  <nav>
    <router-link to="/studentHome">Home</router-link> |
    <router-link to="/studentinfoUpdate">student info Update</router-link>
  </nav>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <!--标题-->
        <h1 class="title">Personal Information Update</h1>
      </el-col>
      <el-col :span="6"></el-col>
    </el-row>
    <!--form表单-->
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="infoForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="Name" prop="teacherName" style="width: 380px">
            <el-input v-model="infoForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="Mail" prop="courseLabel" style="width: 380px">
            <el-input v-model="infoForm.mail"></el-input>
          </el-form-item>
          <el-form-item label="User" prop="username" style="width: 380px">
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
            <el-button type="primary" @click="submitForm('loginForm')">Update</el-button>
            <el-button @click="resetForm('loginForm')">Reset</el-button>
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
      infoForm: {
        // 账户数据
        userName: 'checker',
        // 密码数据
        userType: 'Teacher',

        mail: 'example@xx.com',

        photoUrl: 'url',
      },
      // 表单验证
      rules: {

      },
    };
  },
  methods: {
    beforeUpload (file) {

      this.infoForm.append('file', file)

      return false
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/upload', this.infoForm).then(res => {
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
.codeImg {
  /*让验证码图片飘在右边*/
  float: right;
  /*设置一些圆角边框*/
  border-radius: 3px;
  /*设置宽度*/
  width: 26%;
}

.bg-login {
  height: 100%;
  background-size: 200%;

}

.btn-ground {
  text-align: center;
}

.logo {
  margin: 30px;
  height: 70px;
  width: 70px;
  position: fixed;
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
