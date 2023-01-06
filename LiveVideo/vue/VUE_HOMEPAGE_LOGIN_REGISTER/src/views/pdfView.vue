<template>
<!--  <pdf-view :datasrc="src"></pdf-view>-->
  <iframe :src="src" frameborder="0" style="width: 100%; height: 100%"></iframe>
  <router-link :to="`/homeworkPage?courseId=${courseId}&assignId=${assignId}`">
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
      assignId: useRoute().query.assignId
    }
  },
  name: "pdfView",
  mounted() {
    this.src = getFile(useRoute().query.src)
  }
}
</script>

<style scoped>

</style>
