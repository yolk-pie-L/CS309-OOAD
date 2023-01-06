<template>
  <el-avatar size="large" :src="courseForm.coursePicture"></el-avatar>
  <el-descriptions
      title="课程信息"
      >
    <el-descriptions-item label="课程名"> {{ this.courseForm.courseName }} </el-descriptions-item>
    <el-descriptions-item label="老师名"> {{ this.courseForm.teacherName }} </el-descriptions-item>
    <el-descriptions-item label="课程状态">
      <el-tag size="small"> {{ this.courseForm.status }} </el-tag>
    </el-descriptions-item>

  </el-descriptions>
  <el-descriptions title="课程介绍">
  </el-descriptions>
  <el-row>
    <el-col
        v-for="(o, index) in 1"
        :key="o"
        :span="8"
        :offset="index > 0 ? 2 : 0"
    >
      <el-card :body-style="{ padding: '0px' }">
        <img
            :src="courseForm.coursePicture"
            class="image"
        />
        <div style="padding: 14px">
          <span> {{ this.courseForm.introduction }}</span>
          <div class="bottom">
            <time class="time">{{ currentDate }}</time>
            <el-button text class="form-button" @click="like">like</el-button>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
import { ref } from 'vue'
import router from "@/router";
import {getPhoto} from "@/utils";
import {useRoute} from "vue-router";
export default {
  name: "coinfo",
  data() {
    return{
      courseId: useRoute().query.courseId,
      currentDate: new Date(),
      courseForm: {
        id:"aa",
        courseName: "course",
        teacherName: "teacher",
        introduction: "intro",
        coursePicture: "https://p1.meituan.net/dpplatform/520b1a640610802b41c5d2f7a6779f8a87189.jpg",
        status: "OK"
      },
      messageTable: [
        {
          id: "1",
          title: "Da Jia Hao",
          date: "2022-11-10",
          context: "Hao Ye"
        }
      ],
      fill: ref(false)
    }
  },
  mounted() {
    this.fetchCourseInfo();
  },
  methods: {
    handleChange(val) {
      console.log(val)
    },
    fetchCourseInfo() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/course/${this.courseId}`).then(res => {
        let result = res.data.result
        let code = res.data.code
        if (code === 200) {
          this.courseForm = result
          this.courseForm.coursePicture = getPhoto(this.courseForm.coursePicture)
        } else {
          this.$notify.error(result)
        }
      })
    },
    like() {
      this.$notify.success('你的点赞老师收到啦！')
    }
  }
}
</script>

<style scoped>

.time {
  font-size: 12px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.button {
  padding: 0;
  min-height: auto;
}

.image {
  width: 100%;
  display: block;
}

.form-button {
  margin-top: 3px;
  border-width: 0.5px;
  border-radius: 3px;
  background-color: #5edec8;
  font-size: 17px;
  font-family: "Sitka Heading", cursive, serif, sans-serif;
}

.form-button:hover {
  background-color: lemonchiffon;
}

.modify-button {
  background-color: darksalmon;
  color: whitesmoke;
  border-width: 0.5px;
  margin-top: 5px;
  width: 80px;
}

.modify-button:hover{
  background-color: lightsalmon;
}

</style>
