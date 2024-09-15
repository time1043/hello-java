<template>
  <!-- 导航条 -->
  <van-nav-bar
      title="标题"
      left-arrow
      fixed="true"
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

    <!-- WAY2: 路由 -->
    <RouterView/>

  </div>

  <!-- 底部栏 -->
  <van-tabbar v-model=" active
    " @change="onChange">
    <van-tabbar-item icon="home-o" name="index">主页</van-tabbar-item>
    <van-tabbar-item icon="search" name="team">队伍</van-tabbar-item>
    <van-tabbar-item icon="friends-o" name="user">个人</van-tabbar-item>
  </van-tabbar>

</template>

<script setup lang="ts">
import {ref} from 'vue';
import Index from "../pages/Index.vue";
import Team from "../pages/Team.vue";

// 导航条
const onClickLeft = () => console.log('点击左侧按钮');
const onClickRight = () => console.log('点击右侧按钮')

// 底部栏
const active = ref("index");
const onChange = (index) => console.log(index);
</script>

<style scoped>

</style>