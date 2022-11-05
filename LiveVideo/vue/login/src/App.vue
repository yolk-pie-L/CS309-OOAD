<template>
  <div id="app" :style="{'background-image': bgUrl,'background-repeat': 'no-repeat', 'background-size': 'cover'}">
    <nav>
      <router-link to="/login">Login</router-link> |
      <router-link to="/regist">Regist</router-link>
    </nav>
<!--/?username=-->
    <!--路由页面-->
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      bgUrl:'url(https://p0.meituan.net/dpplatform/76b2dbcba530ed0b2f16a9b260a99e80481882.jpg)',
    }
  }
}
</script>

<style>
/*全局的父类高度*/
html {
  height: 100%;
}

/*重置body属性*/
body {
  padding: 0;
  margin: 0;
  /*继承html*/
  height: 100%;
}

/*#app的高度也需要继承*/
#app {
  height: 100%;
}

/*全局链接标签去下划线*/
a {
  text-decoration: none;
}

nav {
  padding: 30px;
  border-bottom: black;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}
</style>
