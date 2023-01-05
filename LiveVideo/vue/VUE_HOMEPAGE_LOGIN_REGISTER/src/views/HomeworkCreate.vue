<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--标题-->
    <el-row style="height: 95vh">
      <el-col :span="24" style="height: 100%">
        <el-card shadow=hover class="welcome">
          <el-header class="tit" v-text="classForm.courseName" ></el-header>
          <el-header class="tit" >Upload Assignment</el-header>
          <div class="border"></div>
        </el-card>
      </el-col>
    </el-row>
    <!--form表单-->
    <div>
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :model="homeworkForm" :rules="rules" ref="loginForm" label-width="21%" class="loginForm">
          <el-form-item label="AssignmentName"  prop="assignmentName" style="width: 380px" class="variable1">
            <el-input v-model="homeworkForm.assignmentName"></el-input>
          </el-form-item>
          <el-form-item label="Deadline" prop="deadline" style="width: 380px" class="variable1">
            <el-input v-model="homeworkForm.deadline"></el-input>
          </el-form-item>
          <el-form-item label="Description" prop="description" style="width: 380px" class="variable1">
            <el-input v-model="homeworkForm.description"></el-input>
          </el-form-item>
          <el-form-item label="totalGrade" prop="totalGrade" style="width: 380px" class="variable1">
            <el-input v-model="homeworkForm.totalGrade"></el-input>
          </el-form-item>
          <el-form-item label="Additional Source" prop="additionalResources" style="width: 380px" class="variable1">
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
              </div>
            </el-col>
          </el-form-item>
          <el-form-item class="btn-ground">
            <el-button type="primary" @click="submitForm('loginForm')">立即上传</el-button>
            <el-button @click="resetForm('loginForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </div>
  </div>
</template>

<script>
import router from "@/router";
import md5 from "js-md5";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "HomeworkUpload",
  data() {
    return {
      actionUrl: 'http://localhost:8082/api/section/upload2',//上传的后台地址
      classForm: {
        id: "",
        courseName: "course",
        teacherName: "",
        introduction: "",
        charge: "",
        tag: "",
        coursePicture: "",
        privateKeyUrl: "",
        status: ""
        },
      homeworkForm: {
        courseId:"",
        assignmentName: "",
        deadline: "",
        description: "",
        totalGrade: "",
        additionalResources: [
          {
            resourceName: "",
            resourceUrl: ""
          }
        ]
      },
      rules: {

      },
    };
  },
  mounted() {
    this.fetchClass()
  },
  methods: {
    fetchClass() {
      this.$axios.get('api/course/{' + this.classForm.id + '}').then(res => {
        // 拿到结果
        let result = JSON.parse(res.data.data);
        let message = res.data.msg;
        this.classForm=result
        // 判断结果
        if (result) {
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    beforeUpload (file) {
      this.homeworkForm.additionalResources.append('file', file)
      return false
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 表单验证成功
          this.$axios.post('http://localhost:8082/user/checkLogin', this.courseForm).then(res => {
            // 拿到结果
            let result = JSON.parse(res.data.data);
            let message = res.data.msg;
            // 判断结果
            if (result) {
              /*登陆成功*/
              Element.Message.success(message);
              /*跳转页面*/
              router.push('/')
            } else {
              /*打印错误信息*/
              Element.Message.error(message);
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
    },
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

      //文件名称和格式，方便后台合并的时候知道要合成什么格式
      let fileName = file.name;
      let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
      //这里判断文件格式，有其他格式的自行判断
      if (suffix != 'mp4') {
        this.$message.error('文件格式错了哦。。');
        return;
      }

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

.login-card {
  background-color: #ffffff;
  opacity: 0.9;
  box-shadow: 0 0 20px #ffffff;
  border-radius: 10px;
  padding: 40px 40px 30px 15px;
  width: 50%;
  position: absolute;
  left: 40%;
  top:30%;
}

/deep/.variable1 {
  font-size: 10px;
  font-weight: 600;
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
</style>

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
