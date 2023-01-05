<template>
  <div>
    <div>
      <el-row style="height: 95vh">
        <el-col :span="24" style="height: 100%">
          <el-card class="welcome" shadow=hover>
            <el-header class="tit" v-text="quizForm.courseName "></el-header>
            <el-header class="tit" v-text="quizForm.assignmentName "></el-header>
            <div class="border"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div class="describe">
      <el-row>
        <el-header class="variable1">QuizName:</el-header>
        <el-header class="variable2" v-text="quizForm.assignmentName "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Deadline:</el-header>
        <el-header class="variable2" v-text="quizForm.deadline "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Status:</el-header>
        <el-header class="variable2" v-text="quizForm.status "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Score:</el-header>
        <el-header class="variable2" v-text="quizForm.score "></el-header>
        <el-header class="variable2">/</el-header>
        <el-header class="variable2" v-text="quizForm.totalGrade "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Create Time:</el-header>
        <el-header class="variable2" v-text="quizForm.createTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Update Time:</el-header>
        <el-header class="variable2" v-text="quizForm.updateTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Description:</el-header>
        <el-header class="variable2" v-text="quizForm.description "></el-header>
      </el-row>
    </div>
    <div class="business wrap">
      <ul class="box clearfix">
        <div v-for="item in quizForm.problems">
          <li>
            <a>
              <div v-if="chose">
                <h3 v-text="item.problem"></h3>
                <template>
                  <el-radio v-model="item.answer" label="A" v-text="item.A"></el-radio>
                  <el-radio v-model="item.answer" label="B" v-text="item.B"></el-radio>
                  <el-radio v-model="item.answer" label="C" v-text="item.C"></el-radio>
                  <el-radio v-model="item.answer" label="D" v-text="item.D"></el-radio>
                </template>
              </div>
              <div v-else>
                <h3 v-text="item.problem"></h3>

              </div>
            </a>
          </li>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "QuizPage",
  data(){
    return{
      quizForm: {
        quizId: "aa",
        courseName: "course",
        teacherName: "teacher",
        QuizName: "quiz1",
        deadline: "date",
        description: "Intro",
        status: "notStarted/return/submitted",
        score: "",
        totalGrade: "",
        limitedTime: "(as mins)",
        createTime: "date",
        updateTime: "date",
        num: "10",
        problems: [
          {
            isSelection: "(yes/no)",
            problem: "xxxx",
            A: "sdfa",
            B: "sss",
            C: "ddd",
            D: "ddd",
            answer: "(A/B/C/D)/(yes/no)"
          }
        ]
      }
    }
  },
  computed:{
    chose(){
      return true
    }
  }
}
</script>

<style scoped>

.describe {
  position: absolute;
  background-color: #c1d1d7;
  top: 33%;
  left: 8%;
  height: 70%;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 100%;
}

.variable1 {
  display: inline-block;
  position: center;
  top: 5px;
  font-size: 20px;
  font-weight: 600;
}

.variable2 {
  display: inline-block;
  position: center;
  font-size: 18px;
  font-weight: 500;
}


/deep/ .welcome {
  position: fixed;
  top: -20px;
  width: 100%;
  height: 25%;
  margin-top: 20px;
  background-color: #c1d1d7;
  color: #42b983;
  padding: 20px 0 0 20px;
  font-size: 60px;
  font-weight: 600;
}

.tit {
  position: center;
  top: 5px;
}

</style>

<style scoped lang="less">

.business{
  position: absolute;
  top: 600px;
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
