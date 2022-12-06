import './plugins/axios'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import VueAxios from 'vue-cli-plugin-axios'
import '../node_modules/element-plus/theme-chalk/index.css'
import './index.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App);
app.use(router).use(store).use(VueAxios, axios).use(ElementPlus).mount('#app')
app.config.globalProperties.$axios=axios

app.directive('title', {
    inserted: function (el, binding) {
        document.title = el.dataset.title
    }
})

