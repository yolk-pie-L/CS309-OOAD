<template>
  <div>
    <el-row style="height: 35vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName " ></el-header>
          <el-header class="tit" >Quiz</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-table :data="quizForm" height="400" class="tableH">
      <el-table-column prop="assignmentName" label="Quiz Title" width="300" />
      <el-table-column prop="status" label="Status" width="250" />
      <el-table-column prop="deadline" label="Due" width="250" />
      <el-table-column prop="score" label="Score" width="200" />
      <el-table-column fixed="right" label="ENTER">
        <template v-slot="scope" #default>
          <el-button link type="primary" size="small" @click="handleEnter(scope.$index)">Enter</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "QuizHome",
  data() {
    return {
      bgUrl:'url(https://p0.meituan.net/dpplatform/4ce8553013e2e819c08e6d6ba409bee8473079.jpg)',
      classForm: {
        id: "0",
        courseName: "course",
        teacherName: "teacher",
        introduction: "intro",
        coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        privateKeyUrl: "ababa"
      },
      quizForm: [
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted",
          score: ""
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
        {
          id:"aa",
          assignmentName: "homework1",
          deadline: "date",
          status: "notStarted/return/submitted"
        },
      ]
    }
  },
  mounted() {
    this.fetchAssignment()
  },
  methods: {
    fetchAssignment() {
      this.classForm.id = this.$route.query.courseId
      console.log(this.classForm.id)
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/' + this.classForm.id ).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.classForm=result
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
      this.$axios.get('http://localhost:8082/api/quiz/all', {
        params: {
          courseId: this.classForm.id,
        }}).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        this.quizForm=result
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleEnter(index) {
      localStorage.setItem("quiz",this.quizForm.at(index).id)
      router.push("/quizPage")
    },
  }
}
</script>

<style>
.tableH th,
.tableH tr,
.tableH td {
  background-color: transparent;
}

.tableH {
  background-color: transparent;
  position: absolute;
  top: -100px;
  left: 50px;
  width: 90%;
  border-radius: 5%;
  font-size: 20px;
  font-weight: 600;
}

</style>

<style scoped>

/deep/.welcome {
  position: absolute;
  top: -20px;
  width: 100%;
  /*height: 25%;*/
  margin-top: 20px;
  background-color: #c1d1d7;
  color: #42b983;
  padding: 20px 0 0 20px;
  font-size: 60px;
  font-weight: 600;
}

.tit{
  position: center;
  top:5px;
}

</style>

<style scoped lang="less">

.business{
  position: absolute;
  top: 800px;
  left: 125px;
  border-top: 1px solid #ccc;
  border-right: none;
  width: 80%;
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

      width: 100%;
      height: 400px;
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
          font-size: 40px;
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
