<template>
  <!--https://vant-ui.github.io/vant/v3/#/zh-CN/form-->
  <van-form @submit="onSubmit">
    <van-cell-group inset>
      <van-field
          v-model="editUser.currentValue"
          :name="editUser.editKey"
          :label="editUser.editName"
          :placeholder="`请输入${editUser.editName}`"
          :rules="[{ required: true, message: '请填写用户性别' }]"
      />
    </van-cell-group>
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        确认修改
      </van-button>
    </div>
  </van-form>
</template>

<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";

const router = useRouter();
const route = useRoute();

// 取到路由参数
console.log(route.query)

// 表单数据 对象定义
const editUser = ref({
  editKey: route.query.editKey,
  editName: route.query.editName,
  currentValue: route.query.currentValue,
})
// editUser.value.currentValue = editUser.value.currentValue === "1" ? "男" : "女"

const onSubmit = (values) => {
  console.log('submit', values);  // { editKey: "gender", editName: "性别", currentValue: "1" }
  // TODO：将editKey editName currentValue提交到后端
};
</script>

<style scoped>

</style>