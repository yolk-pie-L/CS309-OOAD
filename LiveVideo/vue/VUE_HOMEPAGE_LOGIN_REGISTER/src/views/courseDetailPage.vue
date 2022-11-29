<template>
  <div :xl="6" :lg="7" class="home">
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="top">
      <el-col :span="10">
        <!--标题-->
        <img src="@/assets/logo.png" alt="logo">
      </el-col>
      <el-col :span="10">
        <el-row v-text="this.courseForm.courseName" class="title"></el-row>
        <el-row v-text="this.courseForm.teacherName" class="title"></el-row>
      </el-col>
    </el-row>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-card class="box-card">
        <el-table :data="infoTable" border stripe style="width: 100% ">
          <el-table-column prop="info" label="Course Info" align="center" min-width="500px"></el-table-column>
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
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-card class="box-card">
        <el-table :data="sectionTable" border stripe style="width: 100% ">
          <el-table-column prop="sectionName" label="Section List" align="center" min-width="500px"></el-table-column>
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
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-button type="primary" @click="">Assignment</el-button>
      <el-button type="primary" @click="">Quiz</el-button>
      <el-button type="primary" @click="">Grade</el-button>
    </el-row>
  </div>
</template>

<script>
// @ is an alias to /src
import router from "@/router";

export default {
  name: 'courseMainPage',
  data() {
    return {
      courseForm: {
        courseName: "checker",
        teacherName: "black",
        coursePic: "url"
      },
      infoTable: [{
        info: "empty hello"
      }],
      sectionTable: [
        {
          sectionName: "example",
          sectionUrl: "url"
        }
      ]
    }
  }
}
</script>
<style scoped>

.title {
  text-shadow: -1px 1px 1px #5f565e;
  text-align: center;
  margin-top: 0px;
  margin-bottom: 20px;
  color: black;
  font-size: 40px;
}
</style>
