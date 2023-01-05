<template>
  <div :xl="6" :lg="7" class="bg-login">
    <!--logo-->
    <!--form表单-->
    <el-row type="flex" class="row-bg card" justify="center" align="bottom">
      <el-col :span="7" class="login-card">
        <!--loginForm-->
        <el-form :ref="infoForm" label-width="21%" class="loginForm">
          <el-form-item label="Pic" style="width: 380px">
            <el-upload
                :on-change="handleChange"
                :auto-upload="false"
                :limit="1"
                list-type="picture-card">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="submit">Update</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import router from "@/router";
import {getPhoto} from "@/utils";

export default {
  name: "avatarUpdate",
  data() {
    return {
      infoForm: {
        // 账户数据
        userName: 'checker',
        // 密码数据
        userType: 'Teacher',

        mail: 'example@xx.com',

        photoUrl: 'url',
      },
      formData: ''
    }
  },
  mounted() {
    this.fetchUserInfo()
  },
  methods: {
    fetchUserInfo() {
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.get('http://localhost:8082/api/user').then(res => {
        // 拿到结果
        let result = res.data.result;
        this.infoForm = result;
        // 判断结果
        if (result.data.code === 200) {
          /*登陆成功*/
          /*跳转页面*/
        } else {
          /*打印错误信息*/
          alert(message);
        }
      })
    },
    handleChange(file, fileList){
      this.formData = new FormData()
      fileList.map(item => { //fileList本来就是数组，就不用转为真数组了
        this.formData.append('file', item.raw)  //将每一个文件图片都加进formdata
      })
      console.log(this.formData.file)
      // this.$axios.post("http://localhost:8082/api/getPhoto", formData).then(res => {
      //   console.log(res)
      // })
    },
    submit() {
      this.$axios.post('http://localhost:8082/api/upload', this.formData).then(res => {
        // 拿到结果
        let result = res.data.result;
        // 判断结果
        if (res.data.code === 200) {
          /*登陆成功*/
          this.$notify.success(result.string);
          /*跳转页面*/
          this.infoForm.photoUrl = result.string
          this.modify()
          router.push('/person/info')
        } else {
          /*打印错误信息*/
          this.$notify.error(result);
        }
      })
    },
    modify() {
      this.$axios.post('http://localhost:8082/api/user/avatar', this.infoForm).then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.$notify.success("头像修改成功")
        } else {
          this.$notify.error(result)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
