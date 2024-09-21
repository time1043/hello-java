<template>
  <!-- 搜索框 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/search -->
  <form action="/">
    <van-search
        v-model="searchText"
        show-action
        placeholder="请输入搜索标签"
        @search="onSearch"
        @cancel="onCancel"
    />
  </form>

  <div style="height: calc(100vh - 200px); overflow: auto">
    <!-- 标签列表 -->
    <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/tag -->
    <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/divider -->
    <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/collapse -->
    <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/tree-select -->
    <van-divider dashed>已选标签</van-divider>
    <div v-if="activeIds.length === 0" style="font-size: 12px; text-align: center">请选择标签...</div>
    <div style="padding: 0 20px">
      <van-tag size="small" type="primary"
               closeable @close="doClose(tag)"
               v-for="tag in activeIds"
               style="margin-right: 5px; margin-bottom: 3px; padding: 4px; font-size: 11px;">
        {{ tag }}
      </van-tag>
    </div>

    <van-divider dashed>选择标签</van-divider>
    <van-tree-select
        v-model:active-id="activeIds"
        v-model:main-active-index="activeIndex"
        :items="tagList"
        height="72%"
    />
  </div>

  <!-- 搜索按钮 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/button -->
  <van-button type="primary" size="large" @click="doSearchResult" style="position: absolute; bottom: 50px">
    搜索伙伴
  </van-button>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from "vue-router";

const router = useRouter();

// 选择标签 分类选择
const activeIds = ref([]);
const activeIndex = ref(0);

/**
 * 标签列表 封装响应式
 */
const originTagList = [
  {
    text: '性别',
    children: [
      {text: '男', id: '男'},
      {text: '女', id: '女'},
      {text: 'Javaer', id: 'Javaer'},
      {text: '未知', id: '未知', disabled: true},
    ],
  },
  {
    text: '语言',
    children: [
      {text: 'Java', id: 'Java'},
      {text: 'Python', id: 'Python'},
      {text: 'JavaScript', id: 'JavaScript'},
      {text: 'TypeScript', id: 'TypeScript'},
      {text: 'PHP', id: 'PHP'},
      {text: 'C++', id: 'C++'},
      {text: 'C#', id: 'C#'},
      {text: 'Go', id: 'Go'},
      {text: 'Kotlin', id: 'Kotlin'},
      {text: 'Swift', id: 'Swift'},
      {text: 'Bug', id: 'Bug'},
    ],
  },
];
let tagList = ref(originTagList);

/**
 * 搜索过滤
 * @param val
 */
const onSearch = (val) => {
  // 抽出标签列表 打平  // 过滤
  tagList.value = originTagList.map(parentTag => {
    const tempChildren = [...parentTag.children];
    const tempParentTag = {...parentTag};
    tempParentTag.children = tempChildren.filter(item => item.text.includes(searchText.value));
    console.log(typeof tempParentTag)
    console.log(tempParentTag)

    // TODO
    activeIndex.value = 1;
    return tempParentTag;
  });
}

// 搜索框
const searchText = ref('');
const onCancel = () => {
  searchText.value = '';
  tagList.value = originTagList;
}

// 标签列表 删除标签
const doClose = (tag) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag;
  })
}

/**
 * 搜索按钮 用户列表
 */
const doSearchResult = () => {
  // http://localhost:5173/#/user/list?tags=%E7%94%B7&tags=Python
  router.push({
    path: "/user/list",
    query: {
      tags: activeIds.value
    }
  })
}

</script>

<style scoped>

</style>