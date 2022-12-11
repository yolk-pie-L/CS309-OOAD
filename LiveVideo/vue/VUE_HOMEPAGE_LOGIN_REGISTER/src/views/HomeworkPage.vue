<template>
  <div>
    <div>
      <el-row style="height: 95vh">
        <el-col :span="24" style="height: 100%">
          <el-card class="welcome" shadow=hover>
            <el-header class="tit" v-text="homeworkForm.courseName "></el-header>
            <el-header class="tit" v-text="homeworkForm.assignmentName "></el-header>
            <div class="border"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="describe">
      <el-row>
        <el-header class="variable1">AssignmentName:</el-header>
        <el-header class="variable2" v-text="homeworkForm.assignmentName "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Deadline:</el-header>
        <el-header class="variable2" v-text="homeworkForm.deadline "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Status:</el-header>
        <el-header class="variable2" v-text="homeworkForm.status "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Score:</el-header>
        <el-header class="variable2" v-text="homeworkForm.score "></el-header>
        <el-header class="variable2">/</el-header>
        <el-header class="variable2" v-text="homeworkForm.totalGrade "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Create Time:</el-header>
        <el-header class="variable2" v-text="homeworkForm.createTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Update Time:</el-header>
        <el-header class="variable2" v-text="homeworkForm.updateTime "></el-header>
      </el-row>
      <el-row>
        <el-header class="variable1">Description:</el-header>
        <el-header class="variable2" v-text="homeworkForm.description "></el-header>
      </el-row>
    </div>

    <div class="add">
      <el-header class="variable1">AdditionalSource:</el-header>
      <div v-for="item in additionalResources">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="item.resourceUrl"
            class="addi"
            v-text="item.resourceName">
        </el-link>
      </div>
    </div>

    <div class="answer">
      <el-header class="variable1">Answer:</el-header>
      <div v-for="item in answer">
        <el-link
            :body-style="{ padding: '0px', marginBottom: '1px' }"
            :href="item.answerUrl"
            class="addi"
            v-text="item.answerName">
        </el-link>
      </div>
    </div>

<!--    <div class="upload">-->
<!--      <el-upload ref="upload" :before-upload="beforeUpload" :file-list="list" :limit="1" action="doUpload"-->
<!--                 class="upload-demo">-->
<!--        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
<!--        <div slot="tip" class="el-upload__tip">文件大小不能不超过5mb</div>-->
<!--        <div slot="tip" class="el-upload-list__item-name">{{list}}</div>-->
<!--      </el-upload>-->
<!--      <span slot="footer" class="dialog-footer">-->
<!--        <el-button @click="visible = false">取消</el-button>-->
<!--        <el-button type="primary" @click="submitUpload()">确定</el-button>-->
<!--      </span>-->
<!--    </div>-->

    <el-upload
        class="upload-demo"
        action="https://jsonplaceholder.typicode.com/posts/"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        multiple
        :limit="3"
        :on-exceed="handleExceed"
        :file-list="fileList">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>


  </div>
</template>

<script>

export default {
  name: "Homework",
  data() {
    return {
      homeworkForm: {
        assignmentId: "aa",
        courseName: "course",
        teacherName: "teacher",
        assignmentName: "homework1",
        deadline: "date",
        description: "Intro",
        status: "notStarted/return/submitted",
        score: "",
        totalGrade: "",
        createTime: "date",
        updateTime: "date",
        answerStatus: "yes/no",
      },
      additionalResources: [
        {
          resourceName: "aa",
          resourceUrl: "https://element.eleme.io"
        },
        {
          resourceName: "aa",
          resourceUrl: "https://element.eleme.io"
        }
      ],
      answers: [
        {
          answerName: "a",
          answerUrl: "b"
        },
        {
          answerName: "c",
          answerUrl: "d"
        }
      ],
      uploadAnswer:{
        file:"",
      },
      fileList: [
        {
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        },
        {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }
      ]
    }
  },
  mounted() {
    this.fetchAssignment()
  },
  methods: {
    fetchAssignment() {
      this.$axios.get('api/course/assignment?AssignmentId={' + this.$router.query.id + '}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.homeworkForm.assignmentId = result.assignmentId
        this.homeworkForm.courseName = result.courseName
        this.homeworkForm.teacherName = result.teacherName
        this.homeworkForm.assignmentName = result.assignmentName
        this.homeworkForm.deadline = result.deadline
        this.homeworkForm.description = result.description
        this.homeworkForm.status = result.status
        this.homeworkForm.score = result.score
        this.homeworkForm.totalGrade = result.totalGrade
        this.homeworkForm.createTime = result.createTime
        this.homeworkForm.updateTime = result.updateTime
        this.additionalResources = result.additionalResources
        this.homeworkForm.answerStatus = result.answerStatus
        this.answer = result.answer
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    }
  },
}
</script>

<style>
.tableA th,
.tableA tr,
.tableA td {
  background-color: transparent;
}

.tableA {
  background-color: transparent;
  position: absolute;
  top: 35%;
  left: 50%;
  width: 30%;
  border-radius: 10%;
  font-size: 20px;
  font-weight: 600;

}

</style>

<style scoped>

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

.describe {
  position: absolute;
  background-color: #c1d1d7;
  top: 40%;
  left: 10%;
  border-radius: 10%;
}

.add {
  position: absolute;
  top: 30%;
  left: 50%;
  width: 40%;
  height: 20%;
  background-color: #c1d1d7;
}

.addi {
  font-size: 30px;
  font-weight: 600;
}

.answer {
  position: absolute;
  top: 52%;
  left: 50%;
  width: 40%;
  height: 20%;
  background-color: #c1d1d7;
}

.upload {
  position: absolute;
  top: 75%;
  left: 50%;
  width: 40%;
  height: 20%;
  background-color: #c1d1d7;
}
</style>