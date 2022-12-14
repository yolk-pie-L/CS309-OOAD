<template>
  <div class="main">
    商户订单<input v-model="out_trade_no"><br>
    订单名称<input v-model="subject"><br>
    付款金额<input v-model="total_amount"><br>
    商品描述<input v-model="body"><br>
    <el-button v-on:click="pay" type="success" round>支付宝支付</el-button>
  </div>
</template>

<script>

export default {
  data() {
    return {
      out_trade_no:'',
      subject:'',
      total_amount:'',
      body:''
    }
  },
  methods: {
    pay:function(){
      this.$axios.post('alipay',{out_trade_no:this.out_trade_no,subject:this.subject,total_amount:this.total_amount,body:this.body}).then(successResponse=>{
        document.querySelector('body').innerHTML = successResponse.data;//查找到当前页面的body，将后台返回的form替换掉他的内容
        document.forms[0].submit();  //执行submit表单提交，让页面重定向，跳转到支付宝页面
      }).
      catch();
    },
  }
}
</script>



}

