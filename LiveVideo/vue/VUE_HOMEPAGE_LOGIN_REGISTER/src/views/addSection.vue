<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row type="flex" class="row-bg row-two" justify="center" align="middle">
      <el-col :span="6"></el-col>
      <el-col :span="6">
        <!--标题-->
        <h1 class="title">Add Section</h1>
      </el-col>
      <el-col :span="6"></el-col>
    </el-row>
    <!--form表单-->
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="sectionForm" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="章节名称" prop="sectionName" style="width: 380px">
            <el-input v-model="sectionForm.sectionName"></el-input>
          </el-form-item>
          <el-form-item label="章节分数" prop="sectionName" style="width: 380px">
            <el-input v-model="sectionForm.sectionScore"></el-input>
          </el-form-item>
            <a-upload
                :file-list="fileList"
                :remove="handleRemove"
                :multiple="false"
                :before-upload="beforeUpload">
              <a-button>
                <upload-outlined></upload-outlined>
                选择文件
              </a-button>
            </a-upload>
            <a-button
                type="primary"
                :disabled="fileList.length === 0 || !finishSlice"
                :loading="uploading"
                style="margin-top: 16px"
                @click="handleUpload">
              {{ uploading ? "上传中" : "开始上传" }}
            </a-button>
            <a-progress :percent="Math.round(sliceProgress/sliceCount*100)"
                        :status="sliceProgress===sliceCount ? 'success':'active'" v-if="showSliceProgress"/>
            <a-progress :percent="Math.round(finishCount/sliceCount*100)"
                        :status="finishCount===sliceCount ? 'success':'active'" v-if="showProgress"/>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">Update</el-button>
            <el-button @click="resetForm('loginForm')">Reset</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import router from "@/router";
import SparkMD5 from 'spark-md5'

export default {
  // 文件列表
// eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      fileList: [],
      uploading: false,
      exception: '-', //进度条当前状态
      videolist: [], // 视频合集
      progress: 0, // 进度条
      video: '',  // 保存预览地址
      // 表单信息
      finishSlice: false,
      finishCount: 0,
      showProgress: false,
      sliceCount: 0,
      sliceProgress: 0,
      errorCount: 0,
      showSliceProgress: 0,
      fileChunkList: [],
      sendCount: 0,
      filetype: "",
      filename: "",
      hash: "",
      sectionForm: {
        sectionName: 'blank',
        sectionScore: '0'
      },
    };
  },
  methods: {

    beforeUpload(file) {
// 显示切片进度条
      this.showSliceProgress.value = true;
      // 文件添加到文件列表 这里只展示单文件上传
      this.fileList.value = [file];
      // 一些参数的初始化
      this.fileChunkList = [];
      this.finishSlice.value = false;
      this.finishCount.value = 0;
      this.sliceProgress.value = 0;
      this.showProgress.value = false;
      this.sliceCount.value = 0;
      this.errorCount.value = 0;

      return new Promise((resolve, reject) => {
        // 初始化md5工具对象
        const spark = new SparkMD5.ArrayBuffer();
        // 用于读取文件计算md5
        const fileReader = new FileReader();
        // 这里是依据.来对文件和类型进行分割
        let fileInfo = file.name.split(".")
        this.filename = fileInfo[0];
        // 最后一个.之前的内容都应该认定为文件名称
        if (fileInfo.length > 1) {
          this.filetype = fileInfo[fileInfo.length - 1];
          for (let i = 1; i < fileInfo.length - 1; i++) {
            this.filename = this.filename + "." + fileInfo[i];
          }
        }
        // 这里开始做切片
        // 设置切片大小 可以根据实际情况设置
        const chunkSize = 1024 * 1024;
        // 计算出切片数量
        this.sliceCount.value = Math.ceil(file.size / chunkSize);
        let curChunk = 0;
        // 切片操作的实际方法【定义】
        const sliceNext = () => {
          // 使用slice方法进行文件切片
          const chunkFile = file.slice(curChunk, curChunk + chunkSize);
          // 读取当前切片文件流【这里会触发onload方法】
          fileReader.readAsArrayBuffer(chunkFile);
          // 加入切片列表
          this.fileChunkList.push({
            // 切片文件信息
            chunk: chunkFile,
            // 文件名
            filename: this.filename,
            // 分片索引 这里直接借助sliceProgress来实现
            seq: this.sliceProgress.value + 1,
            // 文件类型
            type: this.filetype,
            // 状态信息 用于标识是否上传成功
            status: false
          });
          // 切片完成变量自增
          this.sliceProgress.value++;
        };

        // 进入方法需要进行首次切片操作
        sliceNext();

        // 读取文件流时会触发onload方法
        fileReader.onload = (e) => {
          // 将文件流加入计算md5
          spark.append(e.target.result);
          // 修改切片位移
          curChunk += chunkSize;
          // 说明还没到达最后一个切片 继续切
          if (this.sliceProgress.value < this.sliceCount.value) {
            sliceNext();
          } else {
            // 说明切片完成了
            this.finishSlice.value = true;
            // 读取文件hash值 false为hex true为raw
            this.hash = spark.end(true);
            message.success("文件分片完成");
            // 将哈希值作为其中一个属性 写入到分片列表中
            this.fileChunkList.forEach((content) => {
              content.hash = this.hash;
            })
          }
        };
      })
    },
    startUpload() {
      return new Promise((resolve, reject) => {
        const next = () => {
          // 递归出口 分片上传完毕
          if (this.finishCount.value + this.errorCount.value >= this.sliceCount.value) {
            return;
          }
          // 记录当前遍历位置
          let cur = this.sendCount.value++;
          // 说明越界了 直接退出
          if (cur >= this.sliceCount.value) {
            return;
          }
          // 获取分片信息
          let content = this.fileChunkList[cur];
          // 已经上传过了 直接跳过【可用于断点续传】
          if (content.status === true) {
            if (this.finishCount.value + this.errorCount.value < this.sliceCount.value) {
              next();
              return;
            }
          }
          // 开始填充上传数据 这里需要使用FormData来存储信息
          const formData = new FormData();
          formData.append("file", content.chunk);
          formData.append("hash", content.hash);
          formData.append("filename", content.filename);
          formData.append("seq", content.seq);
          formData.append("type", content.type);
          // 开始上传
          axios.post("http://localhost:8080/upload", formData).then((res) => {
            // 接收回调信息
            const data = res.data;
            if (data.success) {
              // 成功计数 并设置分片上传状态
              this.finishCount.value += 1;
              content.status = true;
            } else {
              // 失败计数
              this.errorCount.value += 1;
            }
            // 说明完成最后一个分片上传但上传期间出现错误
            if (this.errorCount.value !== 0 && this.errorCount.value + this.finishCount.value === this.sliceCount.value) {
              message.error("上传发生错误，请重传");
              this.showProgress.value = false;
              this.uploading.value = false;
            }
            // 说明还有分片未上传 需要继续递归
            if (this.finishCount.value + this.errorCount.value < this.sliceCount.value) {
              next();
            }
            // 说明所有分片上传成功了 发起合并操作
            if (this.finishCount.value === this.sliceCount.value) {
              this.merge();
            }
          }).catch(error => {
            // 对于图中发生的错误需要捕获并记录
            this.errorCount.value += 1;
            if (this.errorCount.value !== 0 && this.errorCount.value + this.finishCount.value === this.sliceCount.value) {
              message.error("上传发生错误，请重传");
              this.showProgress.value = false;
              this.uploading.value = false;
            }
            // 当前分片上传失败不应影响下面的分片
            if (this.finishCount.value + this.errorCount.value < this.sliceCount.value) {
              next();
            }
            console.log(error)
          })
        };
        // 只允许同时10个任务在等待
        while (this.sendCount.value < 10 && this.sendCount.value < this.sliceCount.value) {
          next();
        }
      });
    },
    merge() {
      message.success('上传成功，等待服务器合并文件');
      // 发起合并请求 传入文件hash值、文件类型、文件名
      axios.post("http://localhost:8080/merge", {
        hash: this.hash,
        type: this.filetype,
        filename: this.filename
      }).then((res) => {
        const data = res.data;
        if (data.success) {
          message.success(data.message);
          // 获取上传成功的文件地址
          console.log(data.content);
          // 其他业务操作...
        } else {
          message.error(data.message)
        }
        this.uploading.value = false;
      }).catch(e => {
        message.error('发生错误了');
        this.uploading.value = false;
      });
    },
    handleRemove(file) {
      const index = this.fileList.value.indexOf(file);
      const newFileList = this.fileList.value.slice();
      this.hash = "";
      newFileList.splice(index, 1);
      this.fileList.value = newFileList;
      // 取消之后需要进行相关变量的重新初始化
      this.fileChunkList = [];
      this.finishSlice.value = false;
      this.finishCount.value = 0;
      this.sliceProgress.value = 0;
      this.showProgress.value = false;
      this.sliceCount.value = 0;
      this.errorCount.value = 0;
    },
    async handleUpload() {
      if (!this.finishSlice.value) {
        alert("文件切片中，请稍等~");
        return;
      }
      // 进度条变更
      this.showSliceProgress.value = false;
      // 先检查是否已经上传过
      axios.get("http://localhost:8080/check?hash=" + this.hash).then((res) => {
        const data = res.data;
        if (data.success) {
          message.success(data.message);
          console.log(data.content);
        } else {
          // 开始上传逻辑 相关变量状态更迭
          this.uploading.value = true;
          // 这里主要是服务于断点续传 避免重复上传已成功分块
          this.sliceCount.value -= this.finishCount.value;
          this.errorCount.value = 0;
          this.finishCount.value = 0;
          this.sendCount.value = 0;
          this.showProgress.value = true;
          console.log("开始上传")
          // 调用上面写好的上传逻辑
          this.startUpload();
        }
      }).catch(error => {
        alert("发生异常了")
        console.log(error)
      })
    },

    // beforeUpload (file) {
    //   // alert(file)
    //   this.infoForm.append('file', file)
    //   alert(this.infoForm.file)
    //   return true
    // },
    // 提交表单

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/api/upload', this.sectionForm).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              router.push('/')
            } else {

            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
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

.logo {
  margin: 30px;
  height: 70px;
  width: 70px;
  position: fixed;
}

.title {
  text-shadow: -3px 3px 1px #5f565e;
  text-align: center;
  margin-top: 10%;
  color: #41b9a6;
  font-size: 40px;
}

.login-card {
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: auto;
}
</style>
