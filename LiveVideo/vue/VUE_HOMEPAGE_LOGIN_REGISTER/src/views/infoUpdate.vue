<template>
  <nav>
    <router-link to="/teacherHomeView">Home</router-link> |
    <router-link to="/courseUpdate">UpdateCourse</router-link> |
    <router-link to="/infoUpdate">UpdateInfo</router-link>
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
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="邮箱" prop="code" style="width: 380px">
            <el-input v-model="loginForm.mail" style="width: 70%"></el-input>
            <el-button @click="sendMail" style="width: 30%">发送验证码</el-button>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="width: 380px">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="password" style="width: 380px">
            <el-input type="password" v-model="loginForm.repeatPassword"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code" style="width: 380px">
            <el-input v-model="loginForm.code" class="code-input" style="width: 70%;float: left"></el-input>
            <!--验证码图片-->
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">立即登陆</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
          <el-form-item label="Pic" style="width: 380px">
            <el-upload
                action="/"
                :on-change="handleChange"
                :auto-upload="false"
                list-type="picture-card">
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
    var validatePass1 = (rule, value, callback) => {
      if (value !== this.loginForm.password) {
        callback(new Error('这不是您的邮箱！'));
      } else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.loginForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      mail: '',
      options: [{
        value: 'Student',
        label: '学生',
      }, {
        value: 'Teacher',
        label: '老师',
      }, {
        value: 'Administrator',
        label: '管理员',
      }
      ],
      // 表单信息
      loginForm: {
        person: '',
        // 邮箱数据
        mail: '',
        // 账户数据
        userName: '',
        // 密码数据
        password: '',
        // 重复的密码数据
        repeatPassword: '',
        // 验证码数据
        code: '',
        // 验证码的key，因为前后端分离，这里验证码不能由后台存入session，所以交给vue状态管理
        codeToken: ''
      },
      // 表单验证
      rules: {
        mail: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          { validator: validatePass1, trigger: 'blur', required: true }
        ],
        // 设置密码效验规则
        password: [
          {required: false, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 15, message: '长度在 6 到 15 个字符的密码', trigger: 'blur'}
        ],
        // 设置验证码效验规则
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
        ],
        repeatPassword: [
          {required: false, message: '请再次输入密码', trigger: 'blur'},
          { validator: validatePass2, trigger: 'blur', required: true }
        ]
      },
      // 绑定验证码图片
      codeImg: null
    };
  },
  mounted() {
  },
  methods: {
    beforeUpload (file) {

      this.infoForm.append('file', file)

      return false
    },
    sendMail() {
      this.$axios.post('http://localhost:8082/api/mail?mail=' + this.loginForm.mail).then(res => {
        // 拿到结果
        let message = res.data.code;
        // 判断结果
        if (message === 200) {
          /*登陆成功*/
          this.$notify({
            message: '发送成功',
            type: 'success'
          })
        }
      })
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/user', this.loginForm).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success'
              });
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
