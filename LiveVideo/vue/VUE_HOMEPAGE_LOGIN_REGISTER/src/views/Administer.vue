<template>
  <div>
    <div :style="{'background-image': bgUrl}" class="animate__animated animate__fadeIn title"></div>
    <el-header class="animate__animated animate__fadeIn">
      <div class="menu-expend">
        <i class="el-icon-menu"></i>
      </div>
      <div class="user_show">
        <el-image :src="userForm.photoUrl" alt="暂无图片" class="user_pic"></el-image>
      </div>
    </el-header>

    <div class="tableD">
      <el-table :data="tableData" height="300">
        <el-table-column label="TeacherName" prop="teacherName" width="150"/>
        <el-table-column label="CourseName" prop="courseName" width="150"/>
        <el-table-column label="Tag" prop="tag" width="150"/>
        <el-table-column label="Charge" prop="charge" width="150"/>
        <el-table-column label="Introduction" prop="introduction" width="400"/>
        <el-table-column label="CoursePic" prop="coursePicture" width="400"/>
        <el-table-column fixed="right" label="Operation1" width="120">
          <template v-slot="scope" #default>
            <el-button link size="small" type="primary" @click="handleReject(scope.$index)">Reject</el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Operation1" width="120">
          <template v-slot="scope" #default>
            <el-button link size="small" type="primary" @click="handleAgree(scope.$index)">Agree</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="query">
      <el-input v-model="queryInfo.name" clearable placeholder="Search for user"
                @clear="getUserList"></el-input>
      <el-select v-model="queryInfo.type" placeholder="请选择">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button slot="append" icon="el-icon-search" @click="getUserList">搜索</el-button>
    </div>

    <div class="tableU">
      <el-table :data="tableUser" height="300">
        <el-table-column label="UserName" prop="userName" width="300"/>
        <el-table-column label="UserType" prop="userType" width="390"/>
        <el-table-column label="Privilege" prop="adminRight" width="300"/>
        <el-table-column fixed="right" label="Operation1" width="300">
          <template v-slot="scope" #default>
            <el-button link size="small" type="primary" @click="handleChangePrivilege(scope.$index)">
              Change
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>

<script>

import {getPhoto} from "@/utils";
import router from "@/router";

export default {
  name: "Administer",
  data() {
    return {
      bgUrl: 'url(https://i.imgtg.com/2022/11/10/tWWMg.webp)',
      loading: true,
      userForm: {
        userName: "black",
        userType: "Teacher",
        adminRight: 'Admin',
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      tableData: [
        {
          id: '',
          teacherName: 'Tom',
          courseName: 'CS101',
          introduction: 'aaaaa',
          coursePicture: '',
          tag: '',
          charge: '',
          privateKeyUrl: '',
          status: ''
        },
      ],
      tableUser: [
        {
          userName: "black",
          userType: "Teacher",
          photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
          adminRight: "Admin",
        },
        {
          userName: "black",
          userType: "Teacher",
          photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
          adminRight: "Admin",
        },
      ],
      queryInfo: {
        name: '',
        type: ''
      },
      options: [{
        value: 'student',
        label: 'student'
      }, {
        value: 'teacher',
        label: 'teacher'
      }, {
        value: 'all',
        label: 'all'
      }],
    }
  },
  mounted() {
    this.fetchUserInfo()
    this.fetchClass()
  },
  methods: {
    fetchUserInfo() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.userForm = result
          this.userForm.photoUrl = getPhoto(this.userForm.photoUrl)
        } else {
          this.$notify.error("您未登录")
          router.push("/login")
        }
        if (this.userForm.adminRight === 'NonAdmin') {
          this.$notify.error("您不是管理员")
          router.push("/")
        }
      })
    },
    fetchClass() {
      this.$axios.get('http://localhost:8082/api/admin/waiting').then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.tableData = result;
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleReject(index) {
      let form2 = new URLSearchParams();
      form2.append("courseId", this.tableData.at(index).id);
      form2.append("approved", "false");
      this.$axios.post('http://localhost:8082/api/admin/status', form2).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (result) {
          this.fetchClass();
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleAgree(index) {
      let form2 = new URLSearchParams();
      form2.append("courseId", this.tableData.at(index).id);
      form2.append("approved", "true");
      this.$axios.post('http://localhost:8082/api/admin/status', form2).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (result) {
          this.fetchClass();
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleChangePrivilege(index) {
      let form1 = new URLSearchParams();
      form1.append("userName", this.tableUser.at(index).userName);
      this.$axios.post('http://localhost:8082/api/admin/privilege', form1).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        if (res.data.code === 200) {
          this.$notify.success("修改成功")
          this.fetchClass();
          this.getUserList();
        } else {
          /*打印错误信息*/
          this.$notify.error(result)
        }
      })
    },
    async getUserList() {
      this.$axios.get('http://localhost:8082/api/admin/all', {
        params: {
          userName: this.queryInfo.name,
          type: this.queryInfo.type,
        }
      }).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.tableUser = result
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
  }
}
</script>


<style>
* {
  margin: 0;
  padding: 0;
}
</style>

<style>
/*搜索框样式*/
.search input.el-input__inner {
  background-color: transparent;
  border-radius: 20px;
  color: #cccccc;
}
</style>

<style>
.tableD th,
.tableD tr,
.tableD td {
  background-color: transparent;
}

.tableD {
  background-color: transparent;
  position: absolute;
  top: 350px;
  left: 50px;
  width: 90%;
  border-radius: 50%
}

</style>

<style>
.tableU th,
.tableU tr,
.tableU td {
  background-color: transparent;
}

.tableU {
  background-color: transparent;
  position: absolute;
  top: 800px;
  left: 50px;
  width: 90%;
  border-radius: 50%
}

</style>

<style lang="less" scoped>

.query {
  background-color: transparent;
  position: absolute;
  top: 700px;
  left: 30%;
  width: 20%;
  border-radius: 50%
}

.title {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100%;
  background-repeat: no-repeat;
  background-size: cover;
  border-bottom: 100px solid #fff;
}

.el-header {
  transition: .2s;
}

.el-header:hover {
  opacity: 1 !important;
}

.el-menu {
  background-color: rgba(0, 0, 0, 0) !important;
}

.el-menu /deep/ .el-menu-item {
  background-color: rgba(0, 0, 0, 0) !important;
}


.el-menu /deep/ .el-menu-item i {
  color: rgba(255, 255, 255);
}

.el-menu /deep/ .el-menu-item:hover i {
  color: white;
}

.el-menu /deep/ .el-menu-item:hover {
  background-color: rgba(0, 0, 0, 0.5) !important;
}

.user_show {
  position: absolute;
  top: 100px;
  left: 1200px;
}

/deep/ .user_pic {
  width: 200px;
  height: 200px;
  border-radius: 50%
}

.search_input {
  position: absolute;
  top: 270px;
  left: 550px;
  box-sizing: border-box;
}

.search_input ul {
  position: absolute;
  top: 30px;
  width: 100%;
  border: 1px solid #e5e9ef;
  margin-top: 1px;
  background: #fff;
  z-index: 10000;
  border-radius: 2px;
  box-shadow: 0 2px 4px rgba(58, 118, 142, 0.16);
  padding: 10px 0;
  font-size: 14px;
  box-sizing: border-box;
}

.search_input ul li {
  padding: 0 16px;
  height: 32px;
  line-height: 32px;
  cursor: pointer;
  word-wrap: break-word;
  word-break: break-all;
  display: block;
  color: #222;
  position: relative;
  /*transition: .2 ease;*/
}

.search_input ul li:hover {
  background-color: #e8f3ff;
}

.el-search {
  position: absolute;
  top: 0;
  left: 270px;
}

.search-blog {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  padding-left: 20px;
  padding-right: 20px;
}

</style>
