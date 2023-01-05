<template>
  <div class="_base-count-down">
    <!--    使用插槽，把倒计时组件的值传递给父组件-->
    <!--    字符拼接处理时间展示，slice(-2)提取字符串倒数第二个字到最后一个字  -->
    <slot v-bind="{
        d: days, h: hours, m: mins, s: seconds,
        hh: `00${hours}`.slice(-2),
        mm: `00${mins}`.slice(-2),
        ss: `00${seconds}`.slice(-2),
      }"></slot>
  </div>
</template>

<script>
export default {
  name: "countdown",
  data() {
    return {
      days: '0', // 天数
      hours: '00', // 小时
      mins: '00', // 分钟
      seconds: '00', // 秒
      timer: null,
      curTime: 0,
    }
  },
  props: {
    time: {
      type: [Number, String],
      default: 0
    },
    isMilliSecond: { // time是毫秒还是秒为单位 true为是毫秒级
      type: Boolean,
      default: true
    },
    end: { // 传入到期时间（时间戳）
      type: [Number, String],
      default: 0
    }
  },
  computed: {
    /**
     * 计算需要倒计时的时间段（剩余时间），判断并转换为秒
     */
    duration() {
      if (this.end) {
        // 判断是否毫秒级，还原成毫秒级
        let end = String(Math.round(this.end)).length >= 13 ? Math.round(+this.end) : Math.round(+this.end * 1000);
        // 获取毫秒级的剩余时间
        end = end - Date.now();
        return Math.round(+end / 1000);
      } else {
        // 如果是毫秒级，则除以1000并去除小数点
        const time = this.isMilliSecond ? Math.round(+this.time / 1000) : Math.round(+this.time);
        return time ;
      }
    }
  },
  mounted() {
    this.countDown();
  },
  /**
   * 监听时间间隔，如果发现duration变化，说明新的时间time传入组件，这时就要重新调用this.countDown()重新计时
   */
  watch: {
    duration() {
      this.countDown();
    }
  },
  methods:{
    /**
     * 开始倒计时
     */
    countDown(){
      this.curTime = Math.round(Date.now() / 1000) // 当前的时刻,s为单位
      this.getTime(this.duration)
    },
    /**
     * getTime的目的就是获得 天、时、分、秒并且显示，通过定时器实时来刷新这几个值，从而实现倒计时
     */
    getTime(time){
      // 清除上一个setTimeout
      this.timer && clearTimeout(this.timer);
      if (time < 0) {
        return;
      }
      const { dd, hh, mm, ss } = this.durationFormatter(time);
      console.log(dd, hh, mm, ss)
      this.days = dd || 0;
      this.hours = hh || 0;
      this.mins = mm || 0;
      this.seconds = ss || 0;
      this.timer = setTimeout(() => {
        const now =  Math.round(new Date().getTime() / 1000)
        const diffTime = now - this.curTime ; // 解决因浏览器切到后台，导致延迟，剩余时间不变的问题
        const step = diffTime > 1 ? diffTime : 1 // 页面退到后台的时候不会计时，对比时间差，大于1s的重置倒计时
        this.curTime = now;
        this.getTime(time - step);
      }, 1000);
    },
    /**
     * 将剩余时间转换为剩余的天、时、分、秒
     */
    durationFormatter(time){
      if (!time) return { ss: 0 };
      let t = time;
      const ss = t % 60;
      t = (t - ss) / 60;
      if (t < 1) return { ss };
      const mm = t % 60;
      t = (t - mm) / 60;
      if (t < 1) return { mm, ss };
      const hh = t % 24;
      t = (t - hh) / 24;
      if (t < 1) return { hh, mm, ss };
      const dd = t;
      return { dd, hh, mm, ss };
    }
  }
}
</script>

<style lang="stylus" scoped>

</style>


