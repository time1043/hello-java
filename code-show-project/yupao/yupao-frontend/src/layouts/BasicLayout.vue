<template>
  <!-- 导航条 fixed="true" -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/nav-bar -->
  <van-nav-bar
      :title="title"
      left-arrow
      fixed
      @click-left="onClickLeft"
      @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18"/>
    </template>
  </van-nav-bar>

  <!-- 中间内容：插槽/路由、或根据点击切换页面 -->
  <!-- <slot name="content"></slot>-->
  <div id="content">

    <!-- WAY1: v-if active -->
    <!--
    <template v-if="active === 'index'">
      <Index/>
    </template>
    <template v-if="active === 'team'">
      <Team/>
    </template>
    -->

    <!-- WAY2: Vue Router -->
    <RouterView/>
  </div>

  <!-- 底部栏 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/tabbar -->
  <!-- <van-tabbar route v-model="active" @change="onChange">-->
  <van-tabbar route @change="onChange">
    <van-tabbar-item icon="home-o" name="index" to="/">主页</van-tabbar-item>
    <van-tabbar-item icon="search" name="team" to="/team">队伍</van-tabbar-item>
    <van-tabbar-item icon="friends-o" name="user" to="/user">个人</van-tabbar-item>
  </van-tabbar>

</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {ref} from "vue";
import routes from "../config/route.ts";

// Vue Router
const router = useRouter();
// 动态标题
const DEFAULT_TITLE = "伙伴匹配";
const title = ref(DEFAULT_TITLE);

/**
 * 根据路由切换标题
 */
router.beforeEach((to, from) => {
  const toPath = to.path;
  const route = routes.find((route) => {
    return toPath == route.path;
  })
  title.value = route?.title ?? DEFAULT_TITLE;
});

// 导航条
const onClickLeft = () => {
  // https://router.vuejs.org/zh/guide/essentials/navigation.html
  router.back();
}
const onClickRight = () => {
  // https://router.vuejs.org/zh/guide/essentials/navigation.html
  router.push('/search');
}

// 底部栏
// const active = ref("index");
const onChange = (index) => console.log(index);
</script>

<style scoped>
/* 设置导航条和底部栏的高度 */
.van-nav-bar {
  height: 46px;
}

.van-tabbar {
  height: 50px;
}

/* 设置 #content 的高度，使其占满剩余空间 */
#content {
  height: calc(100vh - 46px - 50px);
  width: 100%;
}
</style>
