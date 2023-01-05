import './plugins/axios'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-cli-plugin-axios'
import '../node_modules/element-plus/theme-chalk/index.css'
import './index.css'
import VideoPlayer from 'vue-video-player'
import 'video.js/dist/video-js.css'
import 'videojs-contrib-hls.js/src/videojs.hlsjs'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import methods from './methods'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'videojs-flash'


require('video.js/dist/video-js.css')
// require('vue-video-player/src/custom-theme.css')
const hls = require('videojs-contrib-hls')
const app = createApp(App);
app.use(router).use(store).use(VueAxios, axios).use(ElementPlus).use(hls).use(VideoPlayer).use(Antd).mount('#app')
app.config.globalProperties.$axios=axios
app.config.globalProperties.$pref='http://localhost:8082'
app.config.globalProperties.$picture='http://localhost:8082/api/picture/'

Object.keys(methods).forEach(k => {
    app.config.globalProperties[k] = methods[k]
})

app.directive('title', {
    inserted: function (el, binding) {
        document.title = el.dataset.title
    }
})


