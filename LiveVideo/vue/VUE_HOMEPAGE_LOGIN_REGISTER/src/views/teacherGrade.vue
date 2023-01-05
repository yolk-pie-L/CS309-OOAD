<template>
  <el-row style="height: 95vh">
    <el-col :span="24" style="height: 100%">
      <el-card shadow=hover class="welcome">
        <el-header class="tit" >Grade</el-header>
        <div class="border"></div>
      </el-card>
    </el-col>
  </el-row>
  <el-button @click="exportGrade" style="position: fixed; top:100px; left: 50px">导出成绩</el-button>
  <div class="tableD">
    <el-table :data="gradeForm" height="300" class="tableH" @row-click="selectItem">
      <el-table-column prop="studentName" label="studentName" width="300"></el-table-column>
      <el-table-column label="studentSectionGradeList" width="250">
        <el-table-column v-for="(title, index) in gradeForm[0].studentSectionGradeList"
                         :key="index" :label="title.sectionName">
          <template v-slot="scope">
          <span>{{scope.row.studentSectionGradeList[index].studentGrade}}/{{scope.row.studentSectionGradeList[index]
              .fullGrade}}</span>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="studentAssignGradeList" width="250">
        <el-table-column v-for="(title, index) in gradeForm[0].studentAssignGradeList"
                         :key="index" :label="title.assignName">
          <template v-slot="scope">
          <span>{{scope.row.studentAssignGradeList[index].studentGrade}}/{{scope.row.studentAssignGradeList[index]
              .totalGrade}}</span>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column>
        <el-button @click="handleDelete(scope.$index)">删除学生</el-button>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import ajax from "@/utils/ajax";

export default {
  name: "teacherGrade",
  data() {
    return {
      userName: 'black',
      courseId: '0',
      gradeForm: [
        {
          studentName: 's1',
          studentSectionGradeList: [
            {
              sectionName: 's1',
              fullGrade: 99,
              studentGrade: 10
            }
          ],
          studentAssignGradeList: [
            {
              assignName: 'assi1',
              totalGrade: 100,
              studentGrade: 10
            }
          ]
        }
      ],
    };
  },
  mounted() {
    this.fetchUser()
  },
  methods: {
    fetchUser() {
      console.log(localStorage.getItem('token'))
      this.courseId = this.$route.query.courseId
      console.log(this.courseId)
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        this.userName = result.userName
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          console.log(this.userName)
          this.fetchGrade()
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
        }
      })
    },
    fetchGrade() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/course/' + this.courseId + '/grades').then(res => {
        // 拿到结果
        let result = res.data.result;
        this.gradeForm = result
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          console.log(this.userName)
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
        }
      })
    },
    exportGrade() {
      // let a = document.createElement('a')
      // a.href = `http://localhost:8082/api/course/export?courseId=${this.courseId}`
      // a.click()
      let option = {
        fileName: 'StudentGrade.xls',
        fileType: 'xls'
      }
      ajax.getFile(`http://localhost:8082/api/course/export?courseId=${this.courseId}`, option)
      // this.$axios.get(`http://localhost:8082/api/course/export?courseId=${this.courseId}`).then(res => {
      // })
    }
    },
    selectItem(row, column, event) {
      this.selectedFundRow = row
      if (event.target.innerText === "删除学生") {
        this.removeFundBtn(this.selectedFundRow)
      }
    },
    removeFundBtn(params) {
      this.gradeForm = this.gradeForm.filter((ele) => {
        var flag = false
        // 如果不一致，则保留该行
        for (const key in params) {
          if (ele[key] !== params[key]) {
            flag = true
            break
          }
        }
        return flag
      })
    },
  }
}
</script>

<style scoped>
/deep/.welcome {
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

.tit{
  position: center;
  top:5px;
}
</style>
