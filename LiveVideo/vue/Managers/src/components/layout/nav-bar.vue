<template>
  <!--导航-->
  <div>
    <div class="animate__animated animate__fadeIn title"  :style="{'background-image': bgUrl}"></div>
    <el-header  class="animate__animated animate__fadeIn">
      <div class="menu-expend">
        <i class="el-icon-menu"></i>
      </div>
      <div class="user_show">
        <img :alt="item" src="https://p0.meituan.net/dpplatform/e03c1cef70236dd3beb7a12c17d18c3d8912.jpg" class="user_pic">
      </div>

    </el-header>
    <div class="tableD">
      <el-table :data="tableData" height="300">
        <el-table-column prop="date" label="Date" width="150" />
        <el-table-column prop="TeacherName" label="TeacherName" width="150" />
        <el-table-column prop="CourseName" label="CourseName" width="150" />
        <el-table-column prop="Introduction" label="Introduction" width="400" />
        <el-table-column prop="CoursePic" label="CoursePic" width="400" />
        <el-table-column fixed="right" label="Operation1" width="120">
          <template #default>
            <el-button link type="primary" size="small" @click="handleReject(scope.$index, scope.row)">Reject</el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="Operation1" width="120">
          <template #default>
            <el-button link type="primary" size="small" @click="handleAgree(scope.$index, scope.row)">Agree</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bgUrl:'url(https://i.imgtg.com/2022/11/10/tWWMg.webp)',
      loading: true,
      tableData : [
        {
          date: '2016-05-03',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
        {
          date: '2016-05-02',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
        {
          date: '2016-05-04',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
        {
          date: '2016-05-01',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
        {
          date: '2016-05-01',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
        {
          date: '2016-05-01',
          TeacherName: 'Tom',
          CourseName: 'CS101',
          Introduction: 'aaaaa',
          CoursePic: ''
        },
      ]
    }
  },
  methods:{
    handleReject(index, row) {
      this.tableData.splice(index, 1);
    },
    handleAgree(index, row) {
      this.tableData.splice(index, 1);
    },
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
.tableD{
  background-color: transparent;
  position: absolute;
  top: 350px;
  left: 0;
  width: 100%;
  border-radius: 50%
}

</style>


<style scoped lang="less">

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
  top: 100px;
  left: 1200px;
}

/deep/.user_pic{
  width: 200px;
  height: 200px;
  border-radius: 50%
}

//搜索框位置
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
