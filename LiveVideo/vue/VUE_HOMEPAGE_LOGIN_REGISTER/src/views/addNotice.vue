<template>
  <div class="file-upload">
    <h1 class="title">Add Section</h1>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="noticeForm" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="课程ID" style="width: 380px">
            <el-input v-model="noticeForm.courseId"></el-input>
          </el-form-item>
          <el-form-item label="通知标题" style="width: 380px">
            <el-input v-model="noticeForm.title"></el-input>
          </el-form-item>
          <el-form-item label="通知内容" style="width: 380px">
            <el-input v-model="noticeForm.context"></el-input>
          </el-form-item>
          <el-form-item label="是否发送邮件">
            <el-switch v-model="noticeForm.sendMail"></el-switch>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm">提交</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";
import md5 from "js-md5"

export default {
  name: "addNotice",
  data() {
    return {
      noticeForm: {
        courseId: 1,
        title: 'black',
        context: 'example',
        sendMail: true
      }
    };
  },
  methods: {
    submitForm() {
      this.$axios.post('http://localhost:8082/api/notice/create', {
        courseId: this.noticeForm.courseId,
        title: this.noticeForm.title,
        context: this.noticeForm.context,
        sendMail: this.noticeForm.sendMail
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (result) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    }
  }
}

</script>

<style scoped lang="less">
.title {
  text-shadow: -3px 3px 1px #5f565e;
  text-align: center;
  margin-top: 5%;
  margin-bottom: 5%;
  color: #41b9a6;
  font-size: 40px;
}

</style>
