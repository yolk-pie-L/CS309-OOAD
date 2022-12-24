<template>
  <el-row style="padding: 10px; border-bottom: 2px black solid; position: fixed; width: 100%;">
    <el-link href="/">首页</el-link>
    <el-input placeholder="search..." style="width: 200px; margin: auto"></el-input>
    <el-image src="@/assets/logo.png" style="height: 20px"></el-image>
    <el-link href="/" style="margin-right: 10px">VIP</el-link>
    <el-link href="/">通知</el-link>
  </el-row>
  <div id="app">
    <!--路由页面-->
    <router-view/>
  </div>
</template>

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
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}
.nav-list-search {
  width: 100px;
  display: inline-block;
}
</style>
