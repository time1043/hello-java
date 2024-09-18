<template>
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/cell -->
  <van-cell title="头像" title-style="text-align:left; ">
    <van-image :src="user.avatarUrl" fit="cover" width="40" height="40"/>
  </van-cell>
  <van-cell title="账号" :value="user.userAccount" title-style="text-align:left;"/>
  <van-cell title="角色" :value="user.userRole" title-style="text-align:left;"/>

  <van-cell title="用户名" is-link :value="user.username" title-style="text-align:left;"
            @click="toEdit('username','用户名', user.username)"/>
  <van-cell title="性别" is-link :value="user.gender" title-style="text-align:left; "
            @click="toEdit('gender','性别', user.gender as number )"/>
  <van-cell title="手机号" is-link :value="user.phone" title-style="text-align:left;"
            @click="toEdit('phone','手机号', user.phone)"/>
  <van-cell title="邮箱" is-link :value="user.email" title-style="text-align:left;"
            @click="toEdit('email','邮箱', user.email)"/>

  <van-cell title="创建时间" :value="user.createTime?.toISOString()" title-style="text-align:left;"/>
  <van-cell title="星球代码" :value="user.planetCode" title-style="text-align:left;"/>
  <van-cell title="用户标签" is-link :value="user.tags" title-style="text-align:left;"/>
</template>

<script setup lang="ts">
import {UserType} from "../models/user";
import {useRouter} from "vue-router";

const router = useRouter();

const user: UserType = {
  id: 1,
  username: "oswin",
  userAccount: "oswin501",
  avatarUrl: "https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png",
  gender: 1,  // 若1则男，若0则女
  phone: "15534340089",
  email: "oswin501@gmail.com",
  createTime: new Date(),
  userRole: 1,
  planetCode: "nn00000001",
  tags: ["java", "cpp"],
};
user.gender = user.gender === 1 ? '男' : '女';
user.userRole = user.userRole === 1 ? '管理员' : '普通用户';

// 编辑用户信息 路由传参
const toEdit = (editKey: string, editName: string, currentValue: number | string | undefined) => {
  router.push({
    path: "/user/edit",
    query: {editKey, editName, currentValue,},
  })
}

</script>

<style scoped>

</style>