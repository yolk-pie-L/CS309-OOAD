<template>
  <div id="app" v-title data-title="hello!">
    <nav>
      <router-link to="/login">Login</router-link> |
      <router-link to="/register">Register</router-link>
    </nav>

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
</style>
