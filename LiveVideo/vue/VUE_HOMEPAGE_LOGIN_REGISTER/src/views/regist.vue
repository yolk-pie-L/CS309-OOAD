<template>
  <nav>
    <router-link to="/login">Login</router-link> |
    <router-link to="/register">Register</router-link>
  </nav>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <!--标题-->
        <h1 class="title">SuStech online teaching Register</h1>
      </el-col>
      <el-col :span="6"></el-col>
    </el-row>
    <!--form表单-->
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="角色" prop="person" style="width: 380px">
            <el-select v-model="loginForm.userType">
              <el-option v-for="item in options"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value"
                         :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="邮箱" prop="mail" style="width: 380px">
            <el-input v-model="loginForm.mail"></el-input>
            <el-button @click="sendMail">获取验证码</el-button>
          </el-form-item>
          <el-form-item label="账户" prop="userName" style="width: 380px">
            <el-input v-model="loginForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" style="width: 380px">
            <el-input type="password" v-model="loginForm.password"></el-input>
          </el-form-item>
          <el-form-item label="重复密码" prop="password" style="width: 380px">
            <el-input type="password" v-model="loginForm.repeatPassword"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code" style="width: 380px">
            <el-input v-model="loginForm.code" class="code-input" style="width: 70%;float: left"></el-input>
            <!--验证码图片-->
            <el-image :src="codeImg" class="codeImg"></el-image>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">立即登陆</el-button>
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
  name: "Regist",
  data() {
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
        userType: '',
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
        person: [],
        mail: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        // 设置账户效验规则
        userName: [
          {required: true, message: '请输入账户', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符的账户', trigger: 'blur'}
        ],
        // 设置密码效验规则
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 6, max: 15, message: '长度在 6 到 15 个字符的密码', trigger: 'blur'}
        ],
        // 设置验证码效验规则
        code: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
        ],
        repeatPassword: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          { validator: validatePass2, trigger: 'blur', required: true }
        ]
      },
      // 绑定验证码图片
      codeImg: null
    };
  },
  methods: {
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/register', this.loginForm).then(res => {
            // 拿到结果
            let message = res.data.code;
            // 判断结果
            if (message === 200) {
              /*登陆成功*/
              /*跳转页面*/
              router.push('/login')
            } else {
              /*打印错误信息*/
              this.$notify({
                title: '注册失败',
                message: res.data.result,
                type: 'error'
              })
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    sendMail() {
      this.$axios.post('http://localhost:8082/api/mail?mail='+ this.loginForm.mail ).then(res => {
        // 拿到结果
        let message = res.data.code;
        // 判断结果
        if (message === 200) {
          /*登陆成功*/
          alert("发送成功")
        } else {
          this.$notify({
            title: '发送失败',
            message: res.data.result,
            type: 'error'
          })
        }
      })
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
