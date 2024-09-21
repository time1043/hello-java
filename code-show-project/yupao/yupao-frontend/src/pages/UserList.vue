<template>
  <!-- 展示用户列表 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/card -->
  <div style="margin-bottom: 50px;">

    <van-card
        v-for="(user, index) in userList"
        :key="index"
        :title="user.username"
        :desc="user.profile || '交个朋友吧 ~'"
        :thumb="user.avatarUrl"
        :tag="user.gender === 1? '♂' : '♀'"
    >
      <template #tags>
        <template v-if="!user.tags || (Array.isArray(user.tags) && user.tags.length === 0)">
          <van-tag plain type="danger">todo</van-tag>
        </template>

        <template v-else>
          <van-tag size="small" type="primary"
                   v-for="tag in user.tags"
                   style="margin-right: 5px; margin-bottom: 3px; padding: 4px; font-size: 11px;">
            {{ tag }}
          </van-tag>
        </template>
      </template>
      <template #footer>
        <van-button size="mini">联系我</van-button>
      </template>
    </van-card>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {ref} from "vue";
import {UserType} from "../models/user";

const route = useRoute();

const mockUsers = [
  {
    id: 1,
    username: "oswin",
    userAccount: "oswin501",
    avatarUrl: "https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png",
    gender: 1,
    phone: "15534340089",
    email: "oswin501@gmail.com",
    userRole: 1,
    planetCode: "nn00000001",
    tags: [
      "java", "python", "cpp", "rust",
      "独立开发前后端", "正在学spring", "正在学react", "正在学langchain",
      "竞赛", "苏州", "家里蹲", "没心没肺", "小学鸡"
    ],
    profile: "我是一名程序员，热爱编程，喜欢分享。",
  },
  {
    id: 2,
    username: "liuzhen",
    userAccount: "liuzhen501",
    avatarUrl: "https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png",
    gender: 0,
    phone: "15534340089",
    email: "liuzhen501@gmail.com",
    userRole: 1,
    planetCode: "nn00000001",
    // tags: ["python", "java", "happy"]
  },
];

const {tags} = route.query;  // Array<string> // TODO
const userList = ref<UserType[]>(mockUsers);  // Array<UserType>  ref

</script>

<style scoped>

</style>