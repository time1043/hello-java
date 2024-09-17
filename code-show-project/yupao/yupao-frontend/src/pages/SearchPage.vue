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

  <!-- 标签列表 -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/tag -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/divider -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/collapse -->
  <!-- https://vant-ui.github.io/vant/v3/#/zh-CN/tree-select -->
  <van-divider dashed>已选标签</van-divider>
  <div v-if="activeIds.length === 0">请选择标签...</div>
  <van-row gutter="16" style="padding: 0 16px">
    <van-col v-for="tag in activeIds">
      <van-tag size="small" type="primary" closeable @close="doClose(tag)">
        {{ tag }}
      </van-tag>
    </van-col>
  </van-row>

  <van-divider dashed>选择标签</van-divider>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
</template>

<script setup lang="ts">
import {ref} from 'vue';

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
      {text: 'Swift', id: 'Swift'},
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
    activeIndex.value = 1;  // TODO
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
</script>

<style scoped>

</style>