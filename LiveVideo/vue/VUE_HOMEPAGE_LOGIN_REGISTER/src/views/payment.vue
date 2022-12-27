<template>
  <div class="main">
    商户订单<input v-model="out_trade_no" disabled><br>
    订单名称<input v-model="subject" disabled><br>
    付款金额<input v-model="total_amount" :disabled="coDis"><br>
    商品描述<input v-model="body" disabled><br>
    <el-button v-on:click="pay" type="success" round>支付宝支付</el-button>
  </div>
</template>

<script>

import {useRoute} from "vue-router";
import router from "@/router";

export default {
  data() {
    return {
      courseId: useRoute().query.courseId,
      coDis: true,
      out_trade_no:'',
      subject:'',
      total_amount:'',
      body:'付费课程'
    }
  },
  mounted() {
    if (this.courseId)
      this.fetchCourseInfo();
    else
      this.fetchUserInfo();
  },
  methods: {
    pay:function(){
      this.$axios.post('http://localhost:8082/api/alipay',{out_trade_no:this.out_trade_no,subject:this.subject,total_amount:this.total_amount,body:this.body}).then(successResponse=>{
        console.log(successResponse.data)
        document.querySelector('body').innerHTML = successResponse.data.result.string;//查找到当前页面的body，将后台返回的form替换掉他的内容
        document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
      }).
      catch();
    },
    async fetchCourseInfo() {
      await this.$axios.get(`http://localhost:8082/api/course/${this.courseId}`).then(res => {
        let result = res.data.result;
        console.log(result)
        if (res.data.code === 200) {
          this.subject = result.courseName + '付费课程订单'
          this.total_amount = result.charge;
          this.coDis = true
        } else {
          this.$notify({
            type: "error",
            message: result,
            title: "加载失败"
          })
          router.push('/')
        }
      })
    },
    async fetchUserInfo() {
      await this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.subject = result.userName + '充值'
          this.body = '个人充值消费'
          this.coDis = false
        } else {
          this.$notify({
            message: result,
            title: "检查失败",
            type: "error"
          })
          router.push('/')
        }
      })
    }
  }
}
</script>



}

