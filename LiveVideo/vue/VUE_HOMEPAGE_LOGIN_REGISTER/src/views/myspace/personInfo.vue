<template>
  <el-avatar size="large" :src="teacherForm.photoUrl"></el-avatar>
  <el-descriptions title="个人信息">
    <el-descriptions-item label="用户名"> {{ this.teacherForm.userName }} </el-descriptions-item>
    <el-descriptions-item label="邮箱"> {{ this.teacherForm.mail }} </el-descriptions-item>
    <el-descriptions-item label="用户类型">
      <el-tag size="small"> {{ this.teacherForm.userType }} </el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="账户 ">
      <el-tag size="small"> {{ this.teacherForm.account }} </el-tag>
    </el-descriptions-item>

  </el-descriptions>
  <el-descriptions :title="courseTitle">
  </el-descriptions>
  <div>
    <div style="margin-bottom: 15px">fill: <el-switch v-model="fill" /></div>
    <el-space :fill="fill" wrap style="width: 100%">
      <el-card v-for="item in courseForm" :key="item" class="box-card">
        <template #header>
          <div class="card-header">
            <span>{{item.courseName}}</span>
            <el-button class="button" text @click="toCourse(item)"> 进入课程 </el-button>
            <el-popconfirm title="Are you sure to delete this?" @confirm="leave(item.id)">
              <template #reference>
                <el-button class="button" text> {{ exitC }} </el-button>
              </template>
            </el-popconfirm>
          </div>
        </template>
        <div class="text item">
          {{ item.introduction }}
        </div>
      </el-card>
    </el-space>
  </div>

  <el-descriptions title="通知列表"/>
  <div class="demo-collapse">
    <el-collapse v-for="item in this.messageTable" :key="item" @change="handleChange(item)">
      <el-collapse-item :title="item.title + ' ' + item.date" :name="messageTable.indexOf(item)">
        <div>
          {{item.context}}
        </div>
        <div>
          <el-button @click="del(item.id)">删除通知</el-button>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { ref } from 'vue'
import router from "@/router";
import {getPhoto} from "@/utils";
export default {
  name: "personInfo",
  data() {
    return{
      currentPage: 1,
      pageSize: 114514,
      courseTitle: "已加入的课程",
      exitC: '',
      teacherForm: {
        userName: "teacher1",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      courseForm: [
        {
          id:"aa",
          courseName: "course",
          teacherName: "teacher",
          introduction: "intro",
          coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
          status: "OK"
        }
      ],
      messageTable: [
        {
          id: "1",
          title: "Da Jia Hao",
          date: "2022-11-10",
          context: "Hao Ye"
        }
      ],
      fill: ref(false)
    }
  },
  mounted() {
    this.fetchUserInfo();
  },
  methods: {
    handleChange(val) {
      console.log(val)
    },
    fetchUserInfo() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.teacherForm = result;
        if (getPhoto(this.teacherForm.photoUrl) !== null)
          this.teacherForm.photoUrl = getPhoto(this.teacherForm.photoUrl);

        if (this.teacherForm.userType === 'Teacher') {
          this.courseTitle = "已创建的课程"
          this.exitC = "删除课程"
        } else {
          this.courseTitle = "已加入的课程"
          this.exitC = "退出课程"
        }
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          /*跳转页面*/
          console.log(this.teacherForm.userName)
          this.fetchCourse()
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchMessage() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/notice/user/all?userName=${this.teacherForm.userName}`).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.messageTable = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
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

        if (res.data.code === 200) {
          /*登陆成功*/

          /*跳转页面*/
          this.fetchMessage()
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    leave(courseId) {
      if (this.teacherForm.userType === 'Student') {
        let joinForm = {
          studentName: this.teacherForm.userName,
          courseId: courseId
        }
        this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
        this.$axios.post('http://localhost:8082/api/course/exit', joinForm).then(res => {
          // 拿到结果
          let code = res.data.code;
          let message = res.data.message;
          // 判断结果
          if (code === 200) {
            /*登陆成功*/

            /*跳转页面*/
            router.push('/')
          } else {
            /*打印错误信息*/
            alert(message)
          }
        })
      }
      if (this.teacherForm.userType === 'Teacher') {
        this.$axios.post(`http://localhost:8082/api/course/del/${courseId}`).then(res => {
          let result = res.data.result;
          if (res.data.code === 200) {
            this.$notify.success('删除成功')
          } else {
            this.$notify.error(result)
          }
        })
      }
    },
    toCourse(item) {
      router.push(`/course?courseId=${item.id}`)
    },
    del(id) {
      this.$axios.post(`http://localhost:8082/api/notice/del?noticeId=${id}`).then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.$notify.success('删除成功')
          this.messageTable.forEach(mes => {
            if (mes.id === id)
              this.messageTable.splice(mes, 1)
          })
        } else {
          this.$notify.error(result)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
