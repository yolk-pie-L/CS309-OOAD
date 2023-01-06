<template>
  <iframe :src="src" frameborder="0" style="width: 100%; height: 100%"></iframe>
  <router-link :to="this.toLink">
  <el-button style="position: fixed; left: 100px; top: 100px;"> 返回作业列表 </el-button>
  </router-link>
</template>

<script>
import {useRoute} from "vue-router";
import {getFile} from "@/utils";

export default {
  data() {
    return {
      src: getFile(useRoute().query.src),
      courseId: useRoute().query.courseId,
      assignId: useRoute().query.assignId,
      toLink:"",
      userType:""
    }
  },
  name: "pdfView",
  mounted() {
    this.change()
    this.src = getFile(useRoute().query.src)
  },
  methods:{
    change()
    {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('localhost:8082/api/user/'
      ).then(res => {
        // 拿到结果
        let result = res.data.result;
        let message = res.data.msg;
        this.userType = result.userType;
        if (res.data.code===200) {
        } else {
          /*打印错误信息*/
          alert(result);
        }
      })
      if(this.userType==="Teacher"){
        this.toLink = `/homeworkCheck?courseId=${courseId}`
      }
      else this.toLink = `/homeworkPage?courseId=${courseId}&assignId=${assignId}`
    }
  }
}
</script>

<style scoped>

</style>
