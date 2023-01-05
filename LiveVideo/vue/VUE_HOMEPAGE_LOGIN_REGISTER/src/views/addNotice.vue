<template>
  <div class="file-upload">
    <h1 class="title">Add Notice</h1>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="noticeForm" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="课程名称" style="width: 380px">
            <el-select v-model="noticeForm.courseId" class="m-2" placeholder="Select">
              <el-option
                  v-for="item in courseForm"
                  :key="item.id"
                  :label="item.courseName"
                  :value="item.id"
              />
            </el-select>
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
import {ref} from "vue";

export default {
  name: "addNotice",
  data() {
    return {
      value: ref(''),
      noticeForm: {
        courseId: 1,
        title: 'black',
        context: 'example',
        sendMail: true
      },
      currentPage: 1,
      pageSize: 114514,
      courseTitle: "已加入的课程",
      teacherForm: {
        userName: "teacher1",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      courseForm: [{
        id: 1,
        courseName: "course",
      }
      ]
    };
  },
  mounted() {
    this.fetchData()
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
        if (res.data.code === 200) {
          /*登陆成功*/
          this.$notify.success("创建成功")
          /*跳转页面*/
          router.push('/person/info')
        } else {
          /*打印错误信息*/
          this.$notify.error(result)
        }
      })
    },
    fetchData() {
      this.getUserInfo().then(res => {
        this.teacherForm = res
        this.fetchCourse()
      })
    },
    fetchCourse() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/all', {
        params: {
          o: this.pageSize,
          page: this.currentPage,
          userName: this.teacherForm.userName
        }
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.courseForm = result

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
