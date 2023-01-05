<template>
  <!-- 支付宝支付界面 -->
  <div class="ali-pay-success box-shadow mt-4 border-radius p-4">
    <div>
      支付成功，您可以进入
      <el-button
          type="text"
          @click="toSpace"
      >个人中心</el-button
      >查看账户余额
    </div>
  </div>
</template>
<script>
import {useRoute} from "vue-router";
import router from "@/router";

export default {
  components: {},
  props: [],
  data() {
    return {
      payInfo: {},
    };
  },
  mounted() {
    this.payInfo = useRoute().query;
    let formData = { no: this.payInfo.out_trade_no, totalAmount: this.payInfo.total_amount }
    // 更新支付状态
    this.$axios.post("http://localhost:8082/api/alipay/success", formData).then(res => {
      let result = res.data.result;
      if (res.data.code === 200) {
        this.$notify.success(result.string)
      }
    });
  },
  methods: {
    toSpace() {
      router.push('/person/info')
    }
  },
};
</script><style lang="scss">
.ali-pay-success {
}
</style>
