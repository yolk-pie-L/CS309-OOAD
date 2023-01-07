<template>
  <div class="leastBox"> <el-row>
    <el-col :span="6">
      <el-card>
<!--        <el-table :data="sectionData" border stripe style="width: 100%">-->
<!--          <el-table-column prop="sectionName" label="Section Name" align="center"-->
<!--                           min-width="180px"></el-table-column>-->
<!--          <el-table-column prop="sectionComplete" label="Section Complete" align="center"-->
<!--                           min-width="180px"></el-table-column>-->
<!--        </el-table>-->
        <template #header>
          <span>Section List</span>
          <router-link :to="`/course/info?courseId=${courseId}`">
            <el-button>返回课程主页</el-button>
          </router-link>
        </template>
        <el-scrollbar>
          <div v-for="(item, i) in sectionData" :key="i" style="padding: 5px;">
            <router-link :to="reFetch(item)">
              <el-card class="box-card">
                <div class="card-header">
                  <span>{{item.sectionName}}</span>
                  <div class="demo-progress">
                    <el-progress :percentage="item.sectionComplete"></el-progress>
<!--                    <el-progress :percentage="50" />-->
<!--                    <el-progress :percentage="100" :format="format" />-->
<!--                    <el-progress :percentage="100" status="success" />-->
<!--                    <el-progress :percentage="100" status="warning" />-->
<!--                    <el-progress :percentage="50" status="exception" />-->
                  </div>
                </div>
              </el-card>
            </router-link>
          </div>
        </el-scrollbar>
      </el-card>
    </el-col>
    <el-col :span="18">
      <div class='video' style="height: 100px;">
        <video-player class="video-player vjs-custom-skin"
                      ref="videoPlayer"
                      @canplay="canPlay($event)"
                      @play="onPlayerPlay($event)"
                      @pause="onPlayerPause($event)"
                      @ended="onPlayerEnded($event)"
                      @playing="onPlayerPlaying($event)"
                      @timeupdate="onPlayerTimeUpdate($event)"
                      :playsinline="true"
                      :options="playerOptions">
        </video-player>
      </div>
    </el-col>
  </el-row></div>

  <el-row v-text="total" style="margin: 0 0 0 50%">

  </el-row>
  <div>
    <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
      <el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>
      <div class="reply-info" >
        <div
            tabindex="0"
            contenteditable="true"
            id="replyInput"
            spellcheck="false"
            placeholder="输入评论..."
            class="reply-input"
            @focus="showReplyBtn"
            @input="onDivInput($event)"
        >
        </div>
      </div>
      <object>
        <embed>
      </object>
      <div class="reply-btn-box" v-show="btnShow">
        <el-button class="reply-btn" size="medium" @click="sendComment" type="primary">发表评论</el-button>
      </div>
    </div>
    <div v-for="(item,i) in comments" :key="i" class="author-title reply-father">
      <el-avatar class="header-img" :size="40" :src="item.headImg"></el-avatar>
      <div class="author-info">
        <span class="author-name">{{item.name}}</span>
        <span class="author-time">{{item.time}}</span>
      </div>
      <div class="icon-btn">
        <span @click="showReplyInput(i,item.name,item.id)"><el-icon><comment></comment></el-icon>{{item.commentNum}}</span>
        <span @click="deleteReply(i,item.name,item.id)"><el-icon><setting></setting></el-icon></span>
      </div>
      <div class="talk-box">
        <p>
          <span class="reply">{{item.comment}}</span>
        </p>
      </div>
      <div class="reply-box">
        <div v-for="(reply,j) in item.reply" :key="j" class="author-title">
          <el-avatar class="header-img" :size="40" :src="reply.fromHeadImg"></el-avatar>
          <div class="author-info">
            <span class="author-name">{{reply.from}}</span>
            <span class="author-time">{{reply.time}}</span>
          </div>
          <div class="icon-btn">
            <span @click="showReplyInput(i,reply.from,reply.id)"><el-icon><comment></comment></el-icon></span>
            <span @click="deleteReply(i,reply.from,reply.fromId)"><el-icon><setting></setting></el-icon></span>
          </div>
          <div class="talk-box">
            <p>
              <span>回复 {{reply.to}}:</span>
              <span class="reply">{{reply.comment}}</span>
            </p>
          </div>
          <div class="reply-box">

          </div>
        </div>
      </div>
      <div  v-show="_inputShow(i)" class="my-reply my-comment-reply">
        <el-avatar class="header-img" :size="40" :src="myHeader"></el-avatar>
        <div class="reply-info" >
          <div tabindex="0" contenteditable="true" spellcheck="false" placeholder="输入评论..."   @input="onDivInput($event)"  class="reply-input reply-comment-input"></div>
        </div>
        <div class=" reply-btn-box">
          <el-button class="reply-btn" size="medium" @click="sendCommentReply(i,j)" type="primary">发表评论</el-button>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import router from "@/router";
import {Comment, Setting, Menu ,Document} from '@element-plus/icons'
import {useRoute} from "vue-router";
import {getPhoto} from "@/utils";
export default {
  components: {
    Comment, Setting, Menu ,Document
  },
  name: "Aside",

  data() {
    return {
      courseId: useRoute().query.courseId,
      sectionId: useRoute().query.sectionId,
      userName: 'SY',
      total: '总评论数： 7',
      courseName: 'black',
      videoURL: "url",
      recodrTime: '0',
      row: 0,
      sectionData: [
        {
          sectionIdIn: "0",
          sectionName: "ababa",
          sectionComplete: 0,
          status: "OK"
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
        sources: [
          {
            type: "video/mp4",  // 类型
            // src: 'http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4'             // url地址
            src: useRoute().query.src           // url地址
          }
        ],
        poster: '',  // 封面地址
        notSupportedMessage: '此视频暂无法播放，请稍后再试',  // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controls: true
        // controlBar: {
        //   timeDivider: true,           // 当前时间和持续时间的分隔符
        //   durationDisplay: true,       // 显示持续时间
        //   remainingTimeDisplay: true, // 是否显示剩余时间功能
        //   fullscreenToggle: true       // 是否显示全屏按钮
        // }
      },
      btnShow: false,
      index:'0',
      replyComment:'',
      myName:'Lana Del Rey',
      myHeader:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
      myId:19870621,
      to:'',
      toId:-1,
      comments:[
        {
          name:'Lana Del Rey',
          id:19870621,
          headImg:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
          comment:'我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
          time:'2019年9月16日 18:43',
          commentNum:2,
          like:15,
          inputShow:false,
          reply:[
            {
              from:'Taylor Swift',
              fromId:19891221,
              fromHeadImg:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
              to:'Lana Del Rey',
              toId:19870621,
              comment:'我很喜欢你的新专辑！！',
              time:'2019年9月16日 18:43',
              commentNum:1,
              like:15,
              inputShow:false
            },
            {
              from:'Ariana Grande',
              fromId:1123,
              fromHeadImg:'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
              to:'Lana Del Rey',
              toId:19870621,
              comment:'别忘记宣传我们的合作单曲啊',
              time:'2019年9月16日 18:43',
              commentNum:0,
              like:5,
              inputShow:false

            }
          ]
        },
        {
          name:'Taylor Swift',
          id:19891221,
          headImg:'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
          comment:'我发行了我的新专辑Lover',
          time:'2019年9月16日 18:43',
          commentNum:1,
          like:5,
          inputShow:false,
          reply:[
            {
              from:'Lana Del Rey',
              fromId:19870621,
              fromHeadImg:'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
              to:'Taylor Swift',
              toId:19891221,
              comment:'新专辑和speak now 一样棒！',
              time:'2019年9月16日 18:43',
              commentNum:25,
              like:5,
              inputShow:false
            }
          ]
        },
        {
          name:'Norman Fucking Rockwell',
          id:20190830,
          headImg:'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
          comment:'Plz buy Norman Fucking Rockwell on everywhere',
          time:'2019年9月16日 18:43',
          commentNum:0,
          like:5,
          inputShow:false,
          reply:[]
        },
      ],
      player: {
        currentTime: 1,
        duration: 114514
      },
      last: 0,
      continueTime: 0
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      console.log(localStorage.getItem('token'))
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get(`http://localhost:8082/api/section/all/${this.courseId}`).then(res => {
        let result = res.data.result;
        this.sectionData = result
        console.log(result)
        if (result) {
          /*登陆成功*/
          console.log('抓取到sectionList数据')
          /*跳转页面*/
          this.fetchUser()
        } else {
          /*打印错误信息*/
          alert(result);
        }
        if (result[0].sectionIdIn === undefined) {
          console.log('section undefined')
        } else {
          console.log('src', this.playerOptions.sources[0].src)
          if (!this.playerOptions.sources[0].src) {
            console.log("无src参数")
            router.push(`/videoPage?courseId=${this.courseId}&sectionId=${result[0].sectionIdIn}&src=http://localhost:8082/api/section/${result[0].sectionIdIn}`)
          } else {
            console.log("有src参数" + this.playerOptions.sources[0].src)
            this.sectionData.forEach(section => {
              // console.log('section: ', section.sectionIdIn, this.sectionId, this.sectionData.indexOf(section))
              if (section.sectionIdIn.toString() === this.sectionId) {
                this.row = this.sectionData.indexOf(section)
                console.log('row at ', this.row)
              }
            })
          }
        }
      })
    },
    fetchUser() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.userName = result.userName
          this.myHeader = getPhoto(result.photoUrl)
          this.fetchComment()
          this.fetchTotalComment()
          this.fetchProgress(document.getElementsByTagName('video')[0])
        } else {
          this.$notify({
            message: result,
            type: "error",
            title: "拉取错误"
          })
        }
      })
    },
    fetchComment() {
      this.$axios.get(`http://localhost:8082/api/comment/${this.sectionId}`).then(res => {
        let result = res.data.result;
        console.log(result)

        if (res.data.code === 200) {
          /*登陆成功*/
          this.comments = result
          this.comments.forEach(comment => {
            comment.headImg = getPhoto(comment.headImg)
            if (comment.reply.length > 0) {
              comment.reply.forEach(reply => {
                reply.fromHeadImg = getPhoto(reply.fromHeadImg)
              })
            }
          })
          /*跳转页面*/
        } else {
          /*打印错误信息*/
          // alert(result);
        }
      })
    },
    fetchTotalComment() {
      this.$axios.get(`http://localhost:8082/api/comment/all/${this.sectionId}`).then(res => {
        let result = res.data.result;
        let message = res.data.msg;
        console.log(result)
        this.total = "总评论数： " + result
        if (res.data.code === 200) {
          /*登陆成功*/

          /*跳转页面*/
        } else {
          /*打印错误信息*/
          // alert(result);
        }
      })
    },
    updateProgress() {
      let json = {
        sectionId: this.sectionId,
        studentName: this.userName,
        totalWatch: this.continueTime,
        currentWatch: this.last,
        videoTime: this.player.duration
      }
      this.$axios.post(`http://localhost:8082/api/section/update`, json).then(res => {
        let result = res.data.result
        if (res.data.code === 200) {

        } else {
          this.$notify.error(result)
        }
      })
    },
    fetchProgress(player) {
      this.$axios.get(`http://localhost:8082/api/course/student/section/grades?studentName=${this.userName}&courseId=${this.courseId}`).then(res => {
        let result = res.data.result
        this.sectionData.forEach(section => {
          result.forEach(obj => {
            if (obj.sectionName === section.sectionName) {
              section.sectionComplete = obj.studentProgress * 100
            }
          })
        })
      })
      this.$axios.get(`http://localhost:8082/api/section/getprogress?studentName=${this.userName}&sectionId=${this.sectionId}`).then(res => {
        let result = res.data.result
        this.continueTime = result.totalWatch
        player.currentTime = result.currentWatch
      })
    },
    reFetch(item) {
      return `/videoPage?courseId=${this.courseId}&sectionId=${item.sectionIdIn}&src=http://localhost:8082/api/section/${item.sectionIdIn}`
    },
    inputFocus(){
      let replyInput = document.getElementById('replyInput');
      replyInput.style.padding= "8px 8px"
      replyInput.style.border ="2px solid blue"
      replyInput.focus()
    },
    showReplyBtn(){
      this.btnShow = true
    },
    hideReplyBtn(){
      let replyInput = document.getElementById('replyInput');
      this.btnShow = false
      replyInput.style.padding= "10px"
      replyInput.style.border ="none"
    },
    showReplyInput(i,name,id){
      this.comments[this.index].inputShow = false
      this.index =i
      this.comments[i].inputShow = true
      this.to = name
      this.toId = id
    },
    _inputShow(i){
      return this.comments[i].inputShow
    },
    sendComment(){
      if(!this.replyComment){
        this.$notify({
          title: "警告",
          type:'warning',
          message:'评论不能为空'
        })
      }else{
        let a ={}
        let input =  document.getElementById('replyInput')
        let timeNow = new Date().getTime();
        let time= this.dateStr(timeNow);
        a.name= this.userName
        a.comment =this.replyComment
        a.headImg = this.myHeader
        a.time = time
        a.commentNum = 0
        a.like = 0
        this.comments.push(a)
        this.replyComment = ''
        input.innerHTML = '';
        let totalNum = parseInt(this.total.split(" ")[1]) + 1
        this.total = "总评论数： " + totalNum.toString()
        this.$axios.post('http://localhost:8082/api/comment/', {
          sectionId: this.sectionId,
          userName: this.userName,
          replyCommentId: -1,
          context: a.comment
        }).then(res => {
          let result = res.data.result;
          if (res.data.code === 200) {
            /*登陆成功*/
            a.id = result
            this.$notify({
              message: "发送成功",
              title: "成功",
              type: "success"
            })
            /*跳转页面*/
            // router.push('/videoPage')
          } else {
            /*打印错误信息*/
            alert(result);
          }
        })
      }
    },
    sendCommentReply(i,j){
      if(!this.replyComment){
        this.$notify({
          title: '警告',
          type:'warning',
          message:'评论不能为空'
        })
      }else{
        let a ={}
        let timeNow = new Date().getTime();
        let time= this.dateStr(timeNow);
        a.from= this.userName
        a.to = this.to
        a.fromHeadImg = this.myHeader
        a.comment =this.replyComment
        a.time = time
        a.commentNum = 0
        a.like = 0
        this.comments[i].reply.push(a)
        this.replyComment = ''
        document.getElementsByClassName("reply-comment-input")[i].innerHTML = ""
        let totalNum = parseInt(this.total.split(" ")[1]) + 1
        this.total = "总评论数： " + totalNum.toString()
        this.$axios.post('http://localhost:8082/api/comment/', {
          sectionId: this.sectionId,
          userName: this.userName,
          replyCommentId: this.toId,
          context: a.comment
        }).then(res => {
          let result = res.data.result;
          if (res.data.code === 200) {
            /*登陆成功*/
            a.id = result
            /*跳转页面*/
            // router.push('/videoPage')
          } else {
            /*打印错误信息*/
            this.$notify({
              message: result,
              title: "评论发送错误",
              type: "error"
            })
          }
        })
      }
    },
    onDivInput: function(e) {
      this.replyComment = e.target.innerHTML;
    },
    dateStr(date){
      //获取js 时间戳
      var time=new Date().getTime();
      //去掉 js 时间戳后三位，与php 时间戳保持一致
      time=parseInt((time-date)/1000);
      //存储转换值
      var s;
      if(time<60*10){//十分钟内
        return '刚刚';
      }else if((time<60*60)&&(time>=60*10)){
        //超过十分钟少于1小时
        s = Math.floor(time/60);
        return  s+"分钟前";
      }else if((time<60*60*24)&&(time>=60*60)){
        //超过1小时少于24小时
        s = Math.floor(time/60/60);
        return  s+"小时前";
      }else if((time<60*60*24*30)&&(time>=60*60*24)){
        //超过1天少于30天内
        s = Math.floor(time/60/60/24);
        return s+"天前";
      }else{
        //超过30天ddd
        var date= new Date(parseInt(date));
        return date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
      }
    },
    deleteReply(i,userName,commentId) {
      this.$axios.post('http://localhost:8082/api/comment/del', {
        userName: userName,
        commentId: commentId
      }).then(res => {
        let result = res.data.result;
        if (res.data.code === 200) {
          /*登陆成功*/
          this.comments.splice(i, 1)
          this.$notify({
            message: "删除成功",
            type: "success",
            title: "成功"
          })
          /*跳转页面*/
          // router.push('/videoPage')
        } else {
          /*打印错误信息*/
          alert(result);
        }
      })
    },
    canPlay(player) {
      this.player = document.getElementsByTagName('video')[0]
      console.log(this.player)
    },
    onPlayerPlay(player) {
      console.log('player play!', player)
      this.timeInterval = setInterval(() => {
        this.continueTime ++
      }, 1000)
    },
    onPlayerPause(player) {
      console.log('player pause!', player)
      clearInterval(this.timeInterval)
    },
    onPlayerEnded(player) {
      console.log('player end!', player)
    },
    onPlayerPlaying(player) {
      console.log('player playing', player)
    },
    onPlayerTimeUpdate(player) {
      console.log('player time update', player)
      this.last = this.player.currentTime
      this.sectionData[this.row].sectionComplete = (this.continueTime /
          document.getElementsByTagName('video')[0].duration * 100).toFixed(1)
      this.updateProgress()
    }
  }
}

</script>

<style scoped>

.demo-progress .el-progress--line {
  /*margin-bottom: 15px;*/
  width: 200px;
}
.leastBox {
  height: 800px;
}
.video{
  display: flex;
  width: 70%;
  height: 70%;
  margin: auto;
}
/deep/ .video-js .vjs-big-play-button{
  top: 45%;
  left: 45%;
}
.my-reply {
  padding: 10px;
  background: #fafbfc;
}
.header-img{
  display: inline-block;
  vertical-align: top;
}
.reply-info{
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}
.reply-input{
  min-height: 20px;
  line-height: 22px;
  padding: 10px 10px;
  color: #ccc;
  background: #fff;
  border-radius: 5px;
}
.reply-input:empty:before{
  content: attr(placeholder);
}
.reply-input:focus:before{
  content: none;
}
.reply-input:focus{
  padding: 8px 8px;
  border: 2px solid blue;
  box-shadow: none;
  outline: none;
}
.reply-btn-box{
  height: 25px;
  margin: 10px 0;
}
.reply-btn{
  position: relative;
  float: right;
  margin-right: 15px;
}
.my-comment-reply{
  margin-left: 50px;
}
.author-title:not(:last-child){
  border-bottom: 1px solid rgba(178,186,194,.3);
}
.author-title{
  padding: 10px;
}
.header-img{
  display: inline-block;
  vertical-align: top;
}
.author-info{
  display: inline-block;
  margin-left: 5px;
  width: 60%;
  height: 40px;
  line-height: 20px;
}
.author-info>span{
  display: block;
  cursor: pointer;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.author-name{
  color: #000;
  font-size: 18px;
  font-weight: bold;
}
.author-time{
  font-size: 14px;
}
.icon-btn{
  width: 30%;
  padding: 0 !important;
  float: right;
}
.icon-btn>span{
  cursor: pointer;
}
.iconfont{
  margin: 0 5px;
}
.talk-box{
  margin: 0 50px;
}
.talk-box>p{
  margin: 0;
}
.reply{
  font-size: 16px;
  color: #000;
}
.reply-box{
  margin: 10px 0 0 50px;
  background-color: #efefef;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {

  /*width: 420px;*/
}

</style>

