<template>
  <el-row>
    <el-col :span="6">
      <el-card class="box-card">
        <el-table :data="sessionData" border stripe style="width: 100% " @row-click="reFetch">
          <el-table-column prop="sessionName" label="Courses enrolled" align="center" min-width="180px"></el-table-column>
        </el-table>
        <el-row>
          <el-col>
            <div class="block">
              <el-pagination
                  background
                  :current-page.sync="currentPage"
                  :page-size="pageSize"
                  layout="total, prev, next, jumper, pager"
                  :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="18">
      <div class='video' style="height: 100px;">
        <video-player class="video-player vjs-custom-skin"
                      ref="videoPlayer"
                      :playsinline="true"
                      :options="playerOptions">
        </video-player>
      </div>
    </el-col>
  </el-row>

</template>


<script>
import router from "@/router";

export default {
  data() {
    return {
      videoURL: "url",
      sessionData: [
        {
          courseName: "course",
          sectionName: "section",
        }
      ],
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0], // 可选的播放速度
        autoplay: false,  // 如果为true,浏览器准备好时开始回放
        muted: false,     // 默认情况下将会消除任何音频。
        loop: false,      // 是否视频一结束就重新开始。
        preload: 'auto',  // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '4:3',  // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: false,  // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [{
          type: "video/mp4",  // 类型
          src: 'http://localhost:8082/api/video/?courseName=aba&sectionName=ababa'             // url地址
        }],
        poster: '',  // 封面地址
        notSupportedMessage: '此视频暂无法播放，请稍后再试',  // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controls: true
        // controlBar: {
        //   timeDivider: true,           // 当前时间和持续时间的分隔符
        //   durationDisplay: true,       // 显示持续时间
        //   remainingTimeDisplay: true, // 是否显示剩余时间功能
        //   fullscreenToggle: true       // 是否显示全屏按钮
        // }
      }
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.$axios.get('http://localhost:8082/api/video/?courseName=aba&sectionName=ababa').then(res => {
        let result = JSON.parse(res.data);
        let message = res.data.msg;
        this.sessionData = result
        console.log(this.sessionData)
        this.videoURL = result.videoURL
        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/videoPage')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    reFetch(row) {
      // router.push("/")
      console.log(row.sessionName)
      this.$axios.get('api/video/' + row.sessionName).then(res => {
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.tableData = result
        this.videoURL = result.videoURL
        if (result) {
          /*登陆成功*/

          /*跳转页面*/
          router.push('/videoPage')
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    }
  }
}

</script>

<style scoped>
.video{
  width: 70%;
  height: 70%;
  margin: auto;
}
</style>

