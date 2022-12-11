<template>
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
        <el-form :model="sectionForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="章节名称" prop="sectionName" style="width: 380px">
            <el-input v-model="sectionForm.sectionName"></el-input>
          </el-form-item>
          <el-upload
              action="#"
              multiple
              :auto-upload="false"
              :show-file-list="true"
              :on-change="handleChange"
              drag
          >
            <!-- 这个图标的书写方式，element-plus和element有区别，注意一下！ -->
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
      exception: '-', //进度条当前状态
      videolist: [], // 视频合集
      progress: 0, // 进度条
      video: '',  // 保存预览地址
      // 表单信息
      sectionForm: {
        sectionName: 'blank'
      },
    };
  },
  methods: {

    uploading() {
      this.$message({
        message: '请等待上传完成',
        type: 'error'
      })
    },
    handleChange(file, fileList) {

      let formdata = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        formdata.append("file", item.raw)  //将每一个文件图片都加进formdata
      })
      console.log(file.size)
      this.$axios.post("http://localhost:8082/api/getPhoto", formdata).then(res => {
        console.log(res)
      })
    },

    // beforeUpload (file) {
    //   // alert(file)
    //   this.infoForm.append('file', file)
    //   alert(this.infoForm.file)
    //   return true
    // },
    // 提交表单

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/upload', this.sectionForm).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              router.push('/')
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
