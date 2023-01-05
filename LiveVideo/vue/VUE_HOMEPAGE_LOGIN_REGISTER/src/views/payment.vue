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
      coDis: true,
      out_trade_no:'',
      subject:'',
      total_amount:'',
      body:'付费课程',
      userName: 'user'
    }
  },
  mounted() {
    this.fetchUserInfo();
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 16; i++) {
      s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
    s[8] = s[13];
    this.out_trade_no = s.join("");
  },
  methods: {
    pay:function(){
      this.$axios.defaults.headers.common["token"] = localStorage.getItem('token');
      this.$axios.post(`http://localhost:8082/api/alipay?userName=${this.userName}`,{out_trade_no:this.out_trade_no,subject:this.subject,total_amount:this.total_amount,body:this.body}).then(successResponse=>{
        console.log(successResponse.data)
        document.querySelector('body').innerHTML = successResponse.data.result.string;//查找到当前页面的body，将后台返回的form替换掉他的内容
        document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
      }).
      catch();
    },
    async fetchUserInfo() {
      await this.$axios.get('http://localhost:8082/api/user').then(res => {
        let result = res.data.result
        if (res.data.code === 200) {
          this.userName = result.userName
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

