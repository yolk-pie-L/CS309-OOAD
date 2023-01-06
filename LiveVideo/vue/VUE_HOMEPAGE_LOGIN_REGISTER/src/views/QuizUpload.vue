<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName" ></el-header>
          <el-header class="tit" >Create Quiz</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <!--form表单-->
    <div>
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form ref="loginForm" :model="quizForm" :rules="rules" class="loginForm" label-width="21%">
          <el-form-item label="Problem"  prop="assignmentName" style="width: 380px" class="variable1">
            <el-input v-model="quizForm.assignmentName"></el-input>
          </el-form-item>
          <el-form-item label="Deadline" prop="deadline" style="width: 380px" class="variable1">
            <el-date-picker v-model="quizForm.deadline" type="date" placeholder="选择日期" >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="Description" prop="description" style="width: 380px" class="variable1">
            <el-input v-model="quizForm.description"></el-input>
          </el-form-item>
          <el-form-item label="totalGrade" prop="totalGrade" style="width: 380px" class="variable1">
            <el-input v-model="quizForm.totalGrade"></el-input>
          </el-form-item>
          <el-form-item label="limitedTime" prop="limitedTime" style="width: 380px" class="variable1">
            <el-input v-model="quizForm.limitedTime"></el-input>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-row>
              <el-button type="primary" @click="changeTo1">添加选择</el-button>
              <el-button type="primary" @click="changeTo2">添加判断</el-button>
            </el-row>
            <el-row>
              <el-button type="primary" @click="addComfirm">确定添加</el-button>
              <el-button type="primary" @click="submitForm">完成</el-button>
            </el-row>
          </el-form-item>
        </el-form>
      </el-col>
    </div>

    <div v-if="check1" class="check11">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form ref="loginForm" :model="quizForm" :rules="rules" class="loginForm" label-width="21%">
          <el-form-item label="Problem"  prop="problem" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.problem"></el-input>
          </el-form-item>
          <el-form-item label="A" prop="A" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.A"></el-input>
          </el-form-item>
          <el-form-item label="B" prop="B" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.B"></el-input>
          </el-form-item>
          <el-form-item label="C" prop="C" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.C"></el-input>
          </el-form-item>
          <el-form-item label="D" prop="D" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.D"></el-input>
          </el-form-item>
          <el-form-item label="Answer" prop="answer" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.answer"></el-input>
          </el-form-item>
        </el-form>
      </el-col>
    </div>

    <div v-if="check2" class="check11">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form ref="loginForm" :model="quizForm" :rules="rules" class="loginForm" label-width="21%">
          <el-form-item label="Problem"  prop="problem" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.problem"></el-input>
          </el-form-item>
          <el-form-item label="Answer" prop="answer" style="width: 380px" class="variable1">
            <el-input v-model="aQuiz.answer"></el-input>
          </el-form-item>
        </el-form>
      </el-col>
    </div>

    <div class="business wrap">
      <ul class="box clearfix">
        <div v-for="item in createQuizJson.problems">
          <li>
            <a >
              <div v-if="item.isSelection">
                <el-header class="variable1" v-text="item.problem"></el-header>
                <div >
                  <el-row>
                    <el-radio v-model="item.answer" label="A" border>A</el-radio>
                    <el-header class="variable2" v-text="item.A"></el-header>
                  </el-row>
                  <el-row>
                    <el-radio v-model="item.answer" label="B" border>B</el-radio>
                    <el-header class="variable2" v-text="item.B"></el-header>
                  </el-row>
                  <el-row>
                    <el-radio v-model="item.answer" label="C" border>C</el-radio>
                    <el-header class="variable2" v-text="item.C"></el-header>
                  </el-row>
                  <el-row>
                    <el-radio v-model="item.answer" label="D" border>D</el-radio>
                    <el-header class="variable2" v-text="item.D"></el-header>
                  </el-row>
                </div>
              </div>
              <div v-else>
                <el-header class="variable1" v-text="item.problem"></el-header>
                <div >
                  <el-radio v-model="item.answer" label="true" border>true</el-radio>
                  <el-radio v-model="item.answer" label="false" border>false</el-radio>
                </div>
              </div>
            </a>
          </li>
        </div>
      </ul>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import {Discount} from "@element-plus/icons";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "quizUpload",
  components: {Discount},
  data() {
    return {
      type:0,
      actionUrl: 'http://localhost:8082/api/section/upload2',//上传的后台地址
      classForm: {
        id: "",
        courseName: "course",
        teacherName: "",
        introduction: "",
        charge: "",
        tag: "",
        coursePicture: "",
        privateKeyUrl: "",
        status: ""
      },
      aQuiz:{
        isSelection:true,
        problem:"xxxx",
        A:"sdfa",
        B:"sss",
        C:"ddd",
        D:"ddd",
        answer:"(A/B/C/D)/(yes/no)"
      },
      quizForm: {
        courseId:"",
        assignmentName: "",
        deadline: "",
        description: "",
        totalGrade: "",
        limitedTime: 30,
        additionalResources: [
          {
            resourceName: "",
            resourceUrl: ""
          }
        ]
      },
      createQuizJson:{
        num:0,
        problems:[
          {
            isSelection:false,
            problem:"xxxx",
            A:"sdfa",
            B:"sss",
            C:"ddd",
            D:"ddd",
            answer:"(A/B/C/D)/(yes/no)"
          }
        ]
      },
      rules: {

      },
    };
  },
  computed:{
    check1(){
      return this.type === 1;
    },
    check2(){
      return this.type === 2;
    }
  },
  mounted() {
    this.fetchClass()
  },
  methods: {
    fetchClass() {
      this.classForm.id=localStorage.getItem("course")
      this.quizForm.courseId=localStorage.getItem("course")
      this.$axios.get(`api/course/{` + this.classForm.id + `}`).then(res => {
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
    },
    beforeUpload (file) {
      this.homeworkForm.additionalResources.append('file', file)
      return false
    },
    // 提交表单

    submitForm() {
      // 表单验证成功
      this.$axios.post('http://localhost:8082/api/course/createQuizJson', this.createQuizJson).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        // 判断结果
        if (result) {
          router.push('/courseDetailPage')
        } else {

        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    changeTo1(){
      this.type=1;
    },
    changeTo2(){
      this.type=2;
    },
    addComfirm(){
      this.createQuizJson.num = 1 + this.createQuizJson.num
      if(this.type===1){
        let newQuiz = {
          isSelection: true,
          problem: this.aQuiz.problem,
          A:this.aQuiz.A,
          B:this.aQuiz.B,
          C:this.aQuiz.C,
          D:this.aQuiz.D,
          answer:this.aQuiz.answer
        }
        this.createQuizJson.problems.push(newQuiz)
      }else {
        let newQuiz = {
          isSelection: false,
          problem: this.aQuiz.problem,
          A:null,
          B:null,
          C:null,
          D:null,
          answer:this.aQuiz.answer
        }
        this.createQuizJson.problems.push(newQuiz)
      }
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

.login-card {
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 100%;
  position: absolute;
  left: 40%;
  top:30%;
}

/deep/.variable1 {
  font-size: 10px;
  font-weight: 600;
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
</style>

<style scoped lang="less">
.file-upload {
  .file-upload-el {
    width: 300px;
    margin: auto;
  }

  padding-top: 50px;
}

.v-box-card {
  width: 50%;
  margin: auto;
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

.button1{
  position: center;
  left: 20%;
}

.check11 {
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 100%;
  position: absolute;
  left: 0;
  top:100%;
}
</style>

<style scoped lang="less">

.business{
  position: absolute;
  top: 180%;
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
