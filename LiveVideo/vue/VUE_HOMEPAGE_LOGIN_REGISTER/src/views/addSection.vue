<template>
  <div class="file-upload">
    <h1>Add Section</h1>
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="6" class="login-card">
        <!--loginForm-->
        <el-form :model="sectionForm" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="课程序号" prop="sectionName" style="width: 380px">
            <el-input v-model="sectionForm.courseId" disabled></el-input>
          </el-form-item>
          <el-form-item label="章节名称" prop="sectionName" style="width: 380px">
            <el-input v-model="sectionForm.sectionName"></el-input>
          </el-form-item>
          <el-form-item label="章节分数" prop="sectionScore" style="width: 380px">
            <el-input v-model="sectionForm.sectionScore"></el-input>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col>
        <div class="file-upload-el">
          <el-upload
              class="upload-demo"
              drag
              ref="upload"
              :limit=1
              :action="actionUrl"
              :on-exceed="handleExceed"
              :http-request="handUpLoad"
              :auto-upload="false">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
          <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">提交并上传到服务器</el-button>
        </div>
        <div>
          <!-- autoplay-->
          <el-card class="v-box-card">
            <video :src="videoUrl"
                   controls
                   autoplay
                   class="video"
                   width="100%">
            </video>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";
import md5 from "js-md5"
import {useRoute} from "vue-router";

export default {
  name: "FileUpload",
  data() {

    return {
      actionUrl: 'http://localhost:8082/api/section/upload2',//上传的后台地址
      shardSize: 10 * 1024 * 1024,
      videoUrl: '',
      sectionForm: {
        courseId: '15',
        sectionName: 'black',
        sectionScore: '10'
      }
    };
  },
  mounted() {
    this.sectionForm.courseId = useRoute().query.courseId
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    async check(key) {
      var res = await this.$axios.get('http://localhost:8082/api/section/check', {
        params: {'key': key}
      })
      let resData = res.data;
      return resData.result;
    },
    async recursionUpload(param, file) {
      //FormData私有类对象，访问不到，可以通过get判断值是否传进去
      let _this = this;
      let key = param.key;
      let shardIndex = param.shardIndex;
      let shardTotal = param.shardTotal;
      let shardSize = param.shardSize;
      let size = param.size;
      let fileName = param.fileName;
      let suffix = param.suffix;

      let fileShard = _this.getFileShard(shardIndex, shardSize, file);

      //param.append("file", fileShard);//文件切分后的分片
      //param.file = fileShard;
      let totalParam = new FormData();
      totalParam.append('file', fileShard);
      totalParam.append("key", key);
      totalParam.append("shardIndex", shardIndex);
      totalParam.append("shardSize", shardSize);
      totalParam.append("shardTotal", shardTotal);
      totalParam.append("size", size);
      totalParam.append("fileName", fileName);
      totalParam.append("suffix", suffix);
      totalParam.append("sectionName", this.sectionForm.sectionName);
      totalParam.append("courseId", this.sectionForm.courseId);
      totalParam.append("sectionScore", this.sectionForm.sectionScore);
      let config = {
        //添加请求头
        headers: {"Content-Type": "multipart/form-data"}
      };
      console.log(param);
      var res = await this.$axios.post('http://localhost:8082/api/section/upload2', totalParam, config)

      var resData = res.data;
      console.log(resData)
      if (resData.code === 200) {
        if (shardIndex < shardTotal) {
          this.$notify({
            title: '成功',
            message: '分片' + shardIndex + '上传完成。。。。。。',
            type: 'success'
          });
        } else {
          this.videoUrl = resData.data;//把地址赋值给视频标签
          this.$notify({
            title: '全部成功',
            message: '文件上传完成。。。。。。',
            type: 'success'
          });
          await router.push(`/courseDetailPage?courseId=${this.sectionForm.courseId}`)
        }

        if (shardIndex < shardTotal) {
          console.log('下一份片开始。。。。。。');
          // 上传下一个分片
          param.shardIndex = param.shardIndex + 1;
          await _this.recursionUpload(param, file);
        }
      } else {
        this.$notify({
          title: '失败',
          message: resData.result,
          type: 'success'
        });
      }


    },

    async handUpLoad(req) {
      let _this = this;
      var file = req.file;
      /*  console.log('handUpLoad', req)
        console.log(file);*/
      //let param = new FormData();
      //通过append向form对象添加数据

      //文件名称和格式，方便后台合并的时候知道要合成什么格式
      let fileName = file.name;
      let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
      //这里判断文件格式，有其他格式的自行判断
      if (suffix != 'mp4') {
        this.$message.error('文件格式错了哦。。');
        return;
      }

      // 文件分片
      // let shardSize = 10 * 1024 * 1024;    //以10MB为一个分片
      // let shardSize = 50 * 1024;    //以50KB为一个分片
      let shardSize = _this.shardSize;
      let shardIndex = 1;		//分片索引，1表示第1个分片
      let size = file.size;
      let shardTotal = Math.ceil(size / shardSize); //总片数
      // 生成文件标识，标识多次上传的是不是同一个文件
      let key = md5(file.name + file.size + file.type);
      let param = {
        key: key,
        shardIndex: shardIndex,
        shardSize: shardSize,
        shardTotal: shardTotal,
        size: size,
        fileName: fileName,
        suffix: suffix
      }
      /*param.append("uid", key);
      param.append("shardIndex", shardIndex);
      param.append("shardSize", shardSize);
      param.append("shardTotal", shardTotal);
      param.append("size", size);
      param.append("fileName", fileName);
      param.append("suffix", suffix);

*/

      let checkIndexData = await _this.check(key);//得到文件分片索引
      console.log(checkIndexData)
      let checkIndex = checkIndexData.findex;

      //console.log(checkIndexData)
      if (checkIndex == -1) {
        this.recursionUpload(param, file);
      } else if (checkIndex < shardTotal) {
        param.shardIndex = param.shardIndex + 1;
        this.recursionUpload(param, file);
      } else {
        this.videoUrl = checkIndexData.fname;//把地址赋值给视频标签
        this.$message({
          message: '极速秒传成功。。。。。',
          type: 'success'
        });
      }


      //console.log('结果：', res)
    },

    getFileShard(shardIndex, shardSize, file) {
      let _this = this;
      let start = (shardIndex - 1) * shardSize;	//当前分片起始位置
      let end = Math.min(file.size, start + shardSize); //当前分片结束位置
      let fileShard = file.slice(start, end); //从文件中截取当前的分片数据
      return fileShard;
    },


  }
}

</script>

<style scoped lang="less">
.file-upload {
  .file-upload-el {
    width: 300px;
    margin: auto;
  }

  padding-top: 50px;
}

.v-box-card {
  width: 50%;
  margin: auto;
}
</style>
