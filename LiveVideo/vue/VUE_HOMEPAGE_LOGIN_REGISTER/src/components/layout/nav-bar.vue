<template>
  <!--导航-->

  <div>
    <div class="animate__animated animate__fadeIn title"  :style="{'background-image': bgUrl}"></div>
    <el-header  class="animate__animated animate__fadeIn">
      <div class="menu-expend">
        <i class="el-icon-menu"></i>
      </div>
<!--      <div v-if="isLog" class="user_show">-->
<!--        <el-image  :src="userForm.photoUrl" class="user_pic"></el-image>-->
<!--        <el-link type="success" class="user_name" v-text="userForm.userName" @click="into()"></el-link>-->
<!--      </div>-->
      <div class="query" style="padding: 10px">
        <el-input v-model="queryInfo.course" clearable placeholder="Course"
                  @clear="fetchCourse"></el-input>
        <el-input v-model="queryInfo.teacher" clearable placeholder="Teacher"
                  @clear="fetchCourse"></el-input>
        <el-button slot="append" icon="el-icon-search" @click="fetchCourse">搜索</el-button>
      </div>
    </el-header>
  </div>

  <div class="business wrap">
    <ul class="box clearfix">
      <div v-for="item in classForm">
        <li>
          <a @click="intoClass(item.id)">
            <h3 v-text="item.courseName"></h3>
            <img :src="item.coursePicture" alt="课程"/>
            <div class="word">
              <h4 v-text="item.teacherName"></h4>
              <i class="border"></i>
              <p v-text="item.introduction"></p>
            </div>
          </a>
        </li>
      </div>
<!--      <li>-->
<!--        <a href="javascript:">-->
<!--          <h3>Course name</h3>-->
<!--          <img src="https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg" alt="课程"/>-->
<!--          <div className="word">-->
<!--            <h4>Teacher</h4>-->
<!--            <i className="border"></i>-->
<!--            <p>Introduction</p>-->
<!--          </div>-->
<!--        </a>-->
<!--      </li>-->
    </ul>
  </div>

  <div class="block">
    <el-pagination
        @size-change="fetchCourse"
        @current-change="handleCurrentChange"
        v-model:current-page="pageForm.page"
        :page-size="8"
        layout="prev, pager, next, jumper"
        :total="1000"
    >
    </el-pagination>
  </div>

</template>

<script>
import router from "@/router";

export default {
  name: "Homepage",
  data() {
    return {
      bgUrl:'url(https://p0.meituan.net/dpplatform/4ce8553013e2e819c08e6d6ba409bee8473079.jpg)',
      user_pic_src:'url(\'this.userForm.photoUrl\')',
      pageForm:{
        page: 1,
        pageNum: 9,
      },
      userForm: {
        userName: "teacher1",
        userType: "Teacher",
        mail: "",
        photoUrl: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        account: "0",
      },
      classForm: [
        {
          courseId:"aa",
          courseName: "course",
          teacherName: "teacher",
          introduction: "intro",
          coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
          privateKeyUrl: "ababa"
        },
      ],
      queryInfo: {
        course: '',
        teacher: ''
      }
    };
  },
  mounted() {
    this.fetchUser()
    this.fetchCourse()
  },
  methods: {
    fetchCourse() {
      this.$axios.get(`http://localhost:8082/api/course/success/all?o=${this.pageForm.pageNum}&page=${this.pageForm.page}&courseName=${this.queryInfo.course}&teacherName=${this.queryInfo.teacher}`).then(res => {
        let result = res.data.result;
        let message = res.data.message;
        this.classForm = result;
        this.classForm.forEach(course => {
          course.coursePicture = this.$picture + course.coursePicture
          console.log(course.coursePicture)
        })
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    fetchUser() {
      console.log(localStorage.getItem('token'))
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        this.userForm.userName = result.userName
        this.userForm.userType = result.userType
        this.userForm.mail = result.mail
        this.userForm.photoUrl = `${this.$pref}/api/picture/${result.photoUrl}`
        this.userForm.account = result.account
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          /*跳转页面*/
          // router.push('/')
        } else if (localStorage.getItem('token')) {
          /*打印错误信息*/
          this.$notify({
            title: '失败',
            message: result,
            type: 'error'
          })
          localStorage.removeItem('token')
          router.push('/')
        }
      })
    },
    into(){
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result;
        if (result.userType === 'Student') {
          router.push('/studentHome');
        } else if (result.userType === 'Teacher') {
          router.push('/teacherHomeView');
        } else {
          console.log(res)
        }
      })
    },
    toLogin(){
      router.push('/login');
    },
    toRegister(){
      router.push('/register');
    },
    intoClass(id){
      localStorage.setItem("courseId",id)
      router.push({path:'/courseMainPage', query: { courseId: id }})
    },
    handleCurrentChange() {
      console.log('changed! pageNum: '+ this.pageForm.pageNum)
      console.log('changed! page: '+ this.pageForm.page)
      this.fetchCourse()
    }
  },
  computed:{
    isLog(){
      let token = localStorage.getItem('token');
      return !(token === null || token === '');
    },
    isNotLog(){
      let token = localStorage.getItem('token');
      return token === null || token === '';
    }
  }
}
</script>

<style>
*{
  margin: 0;
  padding: 0;
}
</style>

<style>
/*搜索框样式*/
.search input.el-input__inner {
  background-color: rgba(0, 0, 0, 0.5);
  border-radius: 20px;
  color: #cccccc;
}
</style>

<style>
.block{
  position: absolute;
  top: 1200px;
  left: 510px;
}
.query{
  position: absolute;
  top: 300px;
  left: 510px;
}
</style>

<style scoped lang="less">

.title {
  position: fixed;
  top: 75px;
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

.el-menu /deep/ .el-menu-item{
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
  top: -20px;
  left: 1000px;
}

.user_pic{
  width: 70px;
  height: 70px;
  border-radius: 50%
}

.user_name{
  position: absolute;
  top:10px;
  left: 50px;
}

/deep/ .el-link{
  font-size: 25px;
}

.el-link .el-icon--right.el-icon {
  vertical-align: text-bottom;
}

//搜索框位置
.search_input {
  position: absolute;
  top: 300px;
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
  box-shadow: 0 2px 4px rgba(58, 118, 142, 0.8);
  padding: 10px 0;
  font-size: 10px;
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

.el-header {
  width: 100%;
  position: sticky;
  top: 0;
  z-index: 9999;
  background-color: rgba(0, 0, 0, 0);
  color: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;
  }

  img {
    height: 40px;
  }

  span {
    margin-left: 15px;
  }

  button {
    opacity: 0.8;
  }

  .el-menu {
    flex-shrink: 0;
  }

  .logo {
    color: #ffd04b;
  }

  .logo:hover {
    cursor: pointer;
  }

}

.loginInfo {
  flex-shrink: 0;
  /*background-color: #545c64;*/
  color: white;
  height: 60px;
  border: none;
  width: 160px;
  position: relative;

  .el-avatar {
    width: 36px;
    height: 36px;
    margin: 0 auto;
    z-index: 200;
  }

  .user-option {
    position: absolute;
    top: 60px;
    width: 150px;
    left: 50%;
    transform: translate(-50%, 0);
    font-size: 14px;
    text-align: center;
    line-height: 30px;
    background-color: #fff;
    visibility: hidden;
    opacity: 0;
    color: #333;
    box-shadow: 0 2px 6px 0 rgb(0 0 0 / 10%);
    border: 1px solid #eee;
    border-radius: 5px;
  }

  .logout {
    margin: 10px auto;
    padding: 0;
    width: 100%;
  }

  .logout:hover {
    background-color: #eee;
  }
}

.loginInfo:hover {
  cursor: pointer;

  .el-avatar {
    transform: translate(0, 50%) scale(1.5);
    transition: .3s;
  }

  .user-option {
    visibility: visible;
    opacity: 1;
    transition: opacity .4s;
  }
}

.zZindex {
  margin-top: 300px;
  padding-top: 100px;
}

.menu-expend {
  display: none !important;
}

.el-menu-hidden {
  /*display: none;*/
  position: absolute;
  top: 60px;
  left: 0;
  border-top: 1px solid #ccc;
  border-right: none;
  width: 100%;

  .el-menu-item {
    border-bottom: 1px solid #ccc;
  }
}

@media screen and (max-width: 1000px) {
  .search_input {
    visibility: hidden;
  }
}

@media screen and (max-width: 768px) {
  .el-menu /deep/ .el-menu-item{
    background-color: rgba(0, 0, 0, 0.3) !important;
  }

  .el-menu-demo {
    display: none;
  }

  .title {
    width: 100%;
    background-position-x: center;
  }

  .menu-expend {
    display: block !important;
    position: absolute;
    top: -20px;
    left: 150px;
  }

  .menu-expend:hover {
    color: #ffd04b;
    cursor: pointer;
  }

  .title {
    background-position-y: 0;
  }

}

.business{
  position: absolute;
  top: 600px;
  left: 125px;
  border-top: 1px solid #ccc;
  border-right: none;
  width: 100%;
}
.business {
  ul {
    li {
      &:hover h3 {
        opacity: 0;
      }

      &:hover img {
        -webkit-transform: scale(1.1);
        -ms-transform: scale(1.1);
        transform: scale(1.1);
      }

      &:hover .word {
        display: block;
        opacity: 1;
        background: rgba(0, 0, 0, 0.6);
      }

      width: 380px;
      height: 220px;
      position: relative;
      float: left;
      overflow: hidden;
      margin-top: 15px;
      margin-left: 15px;

      a {
        h3 {
          position: absolute;
          left: 25px;
          bottom: 20px;
          z-index: 2;
          font-size: 20px;
          color: #fff;
          font-weight: 400;
          opacity: 1;
          filter: alpha(opacity=100);
          -webkit-transition: opacity 0.4s;
          transition: opacity 0.4s;
        }

        img {
          position: relative;
          display: block;
          z-index: 1;
          width: 100%;
          -webkit-transition: -webkit-transform 0.4s;
          transition: -webkit-transform 0.4s;
          transition: transform 0.4s;
          transition: transform 0.4s, -webkit-transform 0.4s;
        }

        .word {
          opacity: 0;
          filter: alpha(opacity=0);
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          z-index: 3;
          padding-top: 20%;
          text-align: center;
          -webkit-transition: opacity 0.4s;
          transition: opacity 0.4s;

          h4 {
            font-size: 20px;
            color: #fff;
            font-weight: 400;
          }

          .border {
            display: block;
            margin: 10px auto;
            width: 22px;
            height: 1px;
            line-height: 0;
            font-size: 0;
            background: #4681e6;
          }

          p {
            font-size: 14px;
            color: #fff;
          }
        }
      }
    }
  }
}
</style>
