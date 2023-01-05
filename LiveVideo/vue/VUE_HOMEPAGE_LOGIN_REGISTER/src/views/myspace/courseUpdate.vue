<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <!--标题-->
        <h1 class="title"> {{ 'Course ' + method }} </h1>
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
          <el-form-item label="课程标签" prop="courseLabel" style="width: 380px">
            <el-input v-model="courseForm.tag"></el-input>
          </el-form-item>
          <el-form-item label="课程收费" prop="courseFee" style="width: 380px">
            <el-input v-model="courseForm.charge"></el-input>
          </el-form-item>
          <el-form-item label="课程描述" prop="courseDescription" style="width: 380px">
            <el-input v-model="courseForm.description" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="Pic" style="width: 380px">
            <el-upload
                action="/"
                :on-change="handleChange"
                :auto-upload="false"
                :limit="1"
                list-type="picture-card">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submit('loginForm')">立即上传</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";
import {useRoute} from "vue-router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      method: useRoute().query.method,
      // 表单信息
      courseForm: {
        // 账户数据
        courseName: 'checker',
        // 密码数据
        teacherName: '',

        tag: 'CS444',

        charge: '100',

        pictureUrl: '',

        description: 'test',
      },
      formData: {
        file: ''
      }
    };
  },
  mounted() {
    this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
    this.$axios.get('http://localhost:8082/api/user').then(res=>{
      let result = res.data.result;
      this.courseForm.teacherName = result.userName;
    })
  },
  methods: {
    handleChange (file, fileList) {
      this.formData = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        this.formData.append('file', item.raw)  //将每一个文件图片都加进formdata
      })
    },
    submit(formName) {
      this.$axios.post('http://localhost:8082/api/upload', this.formData).then(res => {
        // 拿到结果
        let result = res.data.result;
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          this.$notify.success(result.string);
          /*跳转页面*/
          this.courseForm.pictureUrl = result.string
          this.submitForm(formName)
        } else {
          /*打印错误信息*/
          this.$notify.error(result);
        }
      })
    },
      // 提交表单
    submitForm(formName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          let url = 'http://localhost:8082/api/course/' + (this.method === 'create' ? 'insert' : 'modify')
          this.$axios.post(url, this.courseForm).then(res => {
            // 拿到结果
            let result = res.data.code;
            let message = res.data.msg;
            // 判断结果
            if (result === 200) {
              /*登陆成功*/
              this.$notify.success(this.method === 'create' ? "创建成功，正在审核" : "修改成功，正在审核")
              /*跳转页面*/
              router.push('/person/info')
            } else {
              /*打印错误信息*/
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
