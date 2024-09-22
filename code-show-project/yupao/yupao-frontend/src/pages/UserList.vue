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
        <!-- user.tags为空时，显示todo标签 -->
        <template v-if="!user.tags || (Array.isArray(user.tags) && user.tags.length === 0)">
          <van-tag plain type="danger"
                   style="margin-right: 5px; margin-bottom: 3px; padding: 4px; font-size: 11px;">
            TODO
          </van-tag>
        </template>

        <!-- 另外渲染：user.tags取出一个标签，若该标签和tags中任何一个一样，则渲染该标签 -->
        <template v-else>
          <van-tag size="small" plain
                   v-for="tag in user.tags"
                   :type="isSpecialTag(tag) ? 'danger' : 'primary'"
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

  <!-- 搜索条件为空时，提示用户无数据 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/empty -->
  <van-empty v-if="!userList || userList.length<1" description="暂无用户数据"/>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {UserType} from "../models/user";
import myAxios from "../plugins/myAxois.ts";
import qs from "qs";  // paramsSerializer

const route = useRoute();

/*const mockUsers = [
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
    profile: "",
  },
];*/

// const tags = route.query.tags;  // Array<string> // TODO  // SearchPage
const tags = Array.isArray(route.query.tags) ? route.query.tags : [route.query.tags];
console.log("tags: ", tags)  // Array [ "Java", "Python" ]

const userList = ref<UserType[]>();  // Array<UserType>  ref  // Backend
// const userList = ref<UserType[]>(mockUsers);  // Array<UserType>  ref

// console.log("=================================================================[userList1]")
// console.log(userList.value);
// console.log("=================================================================[tags]")
// console.log("tags: ", tags)

/**
 * 请求后端数据
 */
onMounted(async () => {
  // console.log("=================================================================[backend]")
  // console.log("Requesting to backend...")
  const userListData = await myAxios.get("/user/search/tags", {
    params: {
      tagNameList: tags,
    },
    // ?tagNameList[]=Java&tagNameList[]=Python -> ?tagNameList=Java&tagNameList=Python
    paramsSerializer: (params) => {
      return qs.stringify(params, {arrayFormat: "repeat"})
    }
  }).then(function (response) {
    console.log("Got data from backend/user/search/tags[success]: ", response.data)
    return response.data?.data;  // Array<UserType>
  }).catch(function (error) {
    console.log("Got data from backend/user/search/tags[error]: ", error);
  })

  if (userListData) {
    // '["python", "typescript"]' -> ['python', 'typescript']
    userListData.forEach(user => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = userListData;

    // console.log("=================================================================[userList2]")
    // console.log(userList.value);
  }
})

// 判断标签：若tags中有该标签，则渲染该标签
const isSpecialTag = (tag: string) => {
  // console.log("Checking tag: ", tag, "...", tags.includes(tag.toLowerCase())); // 大小写不敏感
  tags.forEach((tag, index) => {
    tags[index] = tag.toLowerCase();
  });
  return tags.includes(tag);
};
</script>

<style scoped>

</style>