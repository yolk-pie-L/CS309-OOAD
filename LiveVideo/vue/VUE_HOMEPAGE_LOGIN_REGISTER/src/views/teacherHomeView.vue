<template>
  <nav>
    <router-link to="/teacherHomeView">Home</router-link> |
    <router-link to="/courseUpdate">UpdateCourse</router-link> |
    <router-link to="/infoUpdate">UpdateInfo</router-link>
  </nav>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <el-image :src="teacherForm.photoUrl" alt="暂无图片"></el-image>
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="top">
      <el-col :span="1">
        <!--标题-->
        <el-row v-text="this.teacherForm.userName" class="title"></el-row>
        <el-row v-text="this.teacherForm.mail" class="title"></el-row>

      </el-col>
    </el-row>
    <el-row type="flex" class="row-bg card" justify="center" align="middle">
      <el-card class="box-card">
        <el-table :data="tableData" border stripe style="width: 100% ">
          <el-table-column prop="courseName" label="Courses Taught" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="status" label="Courses State" align="center" min-width="500px"></el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                  background
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  layout="total, prev, next, jumper, pager"
                  :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
    <el-row type="flex" class="row-bg card" justify="center" align="middle">
      <el-card class="box-card">
        <el-table :data="friendData" border stripe style="width: 100% ">
          <el-table-column prop="userName" label="Friend Name" align="center" min-width="500px"></el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                  background
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  layout="total, prev, next, jumper, pager"
                  :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-card class="box-card">
        <el-table :data="messageTable" border stripe style="width: 100% ">
          <el-table-column prop="title" label="Message Title" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="date" label="Message Date" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="context" label="Message Contest" align="center" min-width="500px"></el-table-column>
        </el-table>
        <el-row :gutter="20">
          <el-col :span="6" :offset="12">
            <div class="block">
              <el-pagination
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  :total="total"
                  background
                  layout="total, prev, next, jumper, pager"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-row>
    <el-row>
      <el-button type="primary" @click="logOut()" class="btn-ground">注销</el-button>
    </el-row>
    <el-row>
      <el-button type="primary" @click="logOut1()" class="btn-ground">回到主界面</el-button>
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
      teacherForm: {
        userName: "black",
        userType: "Teacher",
        mail: "example@qq.com",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      tableData: [
        {
          courseName: "course",
          privateKeyUrl: "ababa",
          status: "OK"
        }
      ],
      messageTable: [
        {
          title: "Da Jia Hao",
          date: "2022-11-10",
          context: "Hao Ye"
        }
      ],
      friendData: [
        {
          userName: "user",
          photoUrl: "url"
        }
      ]
    };
  },
  mounted() {
    this.fetchData()
    this.fetchCourse()
    this.fetchMessage()
  },
  methods: {
    //获取后端数据
    fetchData() {
      this.$axios.get('api/user?userName={' + this.username + '}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.userName = result.userName
        this.userType = result.userType
        this.mail = result.mail
        this.photoUrl = result.photoUrl
        this.account = result.account
        // 判断结果
        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchCourse() {
      this.$axios.get('api/notice/all?userName={' + this.username + '}').then(res => {
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.tableData = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchMessage() {
      this.$axios.get('api/notice/all?userName={' + this.username + '}&courseName={' + this.tableData.at(0).courseName + '}').then(res => {
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.messageTable = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchFriend() {
      this.$axios.get('api/notice/all?userName={' + this.username + '}&type={teacher}').then(res => {
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.friendData = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    logOut () {
      router.push("/")
    },
    logOut1 () {
      router.push("/1")
    }
    },
}
</script>

<style scoped>
.bg-login {
  height: 100%;
  background-size: 200%;
}

.title {
  text-shadow: -1px 1px 1px #5f565e;
  text-align: center;
  margin-top: 0px;
  color: black;
  font-size: 40px;
}
</style>
