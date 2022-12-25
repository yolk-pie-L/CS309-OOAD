<template>
  <nav>
    <router-link to="/teacherHomeView">Home</router-link> |
    <router-link to="/courseUpdate">UpdateCourse</router-link> |
    <router-link to="/infoUpdate">UpdateInfo</router-link> |
    <router-link to="/addNotice">AddNotice</router-link>
  </nav>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <el-image :src="teacherForm.photoUrl" alt="暂无图片"></el-image>
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="top">
      <el-col :span="1">
        <!--标题-->
        <el-row v-text="this.teacherForm.userName" class="title"></el-row>
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
        <el-table :data="messageTable" border stripe style="width: 100% " @row-click="selectItem">
          <el-table-column prop="title" label="Message Title" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="date" label="Message Date" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="context" label="Message Contest" align="center" min-width="500px"></el-table-column>
          <el-table-column prop="button" label="delete Message" align="center" min-width="100px">
            <el-button>删除</el-button>
          </el-table-column>
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
      <el-button type="primary" @click="logOut()">注销</el-button>
      <el-button type="primary" @click="back()">返回主界面</el-button>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  components: {},

  data() {
    return {
      teacherForm: {
        userName: "black",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      tableData: [
        {
          id: "0",
          courseName: "course",
          privateKeyUrl: "ababa",
          status: "OK"
        }
      ],
      messageTable: [
        {
          noticeId: 0,
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
  },
  methods: {
    selectItem(row, column, event) {
      if (event.target.innerText === "删除") {
        this.removeFundBtn(row.id)
      }
    },
    // 删除指定行
    removeFundBtn(params) {
      console.log(params)
      this.$axios.post('http://localhost:8082/api/notice/del', {
        noticeId: params
      }).then(res => {
        let result = res.data.result;
        router.push('/teacherHomeView')
      })
    },
    //获取后端数据
    fetchData() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.teacherForm.userName = result.userName
        this.teacherForm.userType = result.userType
        this.teacherForm.mail = result.mail
        this.teacherForm.photoUrl = result.photoUrl
        this.teacherForm.account = result.account
        // 判断结果
        if (result) {
          /*登陆成功*/
          this.fetchCourse()

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchCourse() {
      this.$axios.get('http://localhost:8082/api/course/all', {
        params: {
          userName: this.teacherForm.userName,
          page: 1,
          o: 5
        }
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.tableData = result

        if (result) {
          /*登陆成功*/
          this.fetchMessage()
          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchMessage() {
      this.$axios.get('http://localhost:8082/api/notice/all', {
        params: {
          courseId: this.tableData[0].id
        }
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.messageTable = result

        if (result) {
          /*登陆成功*/
          console.log(result)
          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchFriend() {
      this.$axios.get('http://localhost:8082/api/notice/all?userName=' + this.username + '&type={teacher}').then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.friendData = result

        if (result) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    logOut () {
      localStorage.removeItem('token')
      router.push("/")
    },
    back () {
      router.push("/")
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
