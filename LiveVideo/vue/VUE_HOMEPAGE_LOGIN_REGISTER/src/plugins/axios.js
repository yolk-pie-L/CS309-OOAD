"use strict";
import Vue from 'vue';
import axios from "axios";

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
};

const _axios = axios.create(config);

_axios.interceptors.request.use(
    function(config)  {
      if (localStorage.getItem('token')) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers.Authorization = `token ${localStorage.getItem('token')}`;
      }
      return config;
    },
    function(error) {
      // Do something with request error
      return Promise.reject(error);
    }
);

// Add a response interceptor
_axios.interceptors.response.use(
    function(response) {
      // Do something with response data
      return response;
    },
    function(error) {
      // Do something with response error
      if (error.response) {
        switch (error.response.status) {
          case 401:
            // 返回 401 清除token信息并跳转到登录页面
            localStorage.removeItem('token')
            this.$router.push('/')
        }
      }
      return Promise.reject(error);
    }
);

Plugin.install = function(Vue, options) {
  Vue.axios = _axios;
  window.axios = _axios;
  Object.defineProperties(Vue.prototype, {
    axios: {
      get() {
        return _axios;
      }
    },
    $axios: {
      get() {
        return _axios;
      }
    },
  });
};

// Vue.use(Plugin)

export default Plugin;
