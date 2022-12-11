const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
//   transpileDependencies: true,
//   lintOnSave:false,
//   // 开启代理服务器（方式一）
//   // devServer: {
//   //   proxy:'http://localhost:8082'
//   // }
//
//   //第二种
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8082',
        //正则表达式判断，将路径中的/api转换为""
        pathRewrite: {'^/api': 'http://localhost:8082/api'},
        //用于支持websocket
        ws: true,
        //用于控制请求头中的host值
        changeOrigin: true
      }
    }
  }
})
//
// })

// module.exports = {
//   configureWebpack:{
//     resolve:{
//       // 给路径起别名
//       alias:{
//         'assets': '@/assets',
//         'common': '@/common',
//         'components': '@/components',
//         'network': '@/network',
//         'views': '@/views'
//       }
//     }
//   },
//   devServer:{
//     proxy:{
//       '/sougou':{
//         // 跨域的服务器地址
//         target: 'https://pic.sogou.com/napi/pc/searchList',
//         // 是否允许跨域
//         changeOrigin: true,
//         // 替换掉请求路径的/sougou为“”
//         pathRewrite:{'^/sougou': ""}
//       },
//     }
//   }
// }

module.exports = {
  publicPath: './',
  lintOnSave: true,
  configureWebpack: {
    //关闭 webpack 的性能提示
    performance: {
      hints:false
    }

  },
}


