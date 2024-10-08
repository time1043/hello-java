import type {ActionType, ProColumns} from '@ant-design/pro-components';
import {ProTable, TableDropdown} from '@ant-design/pro-components';
import {Image} from 'antd';
import {useRef} from 'react';
import {searchUsers} from "@/services/ant-design-pro/api";

export const waitTimePromise = async (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

export const waitTime = async (time: number = 100) => {
  await waitTimePromise(time);
};

const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',  // map
    valueType: "indexBorder",
    width: 48,
  },
  {
    title: '用户名',  // 展示在前端页面
    dataIndex: 'username',  // 对应后端的字段名
    copyable: true, // 是否可复制
    // ellipsis: true, // 是否自动省略
    // tip: '这是一段提示信息', // 鼠标悬停提示信息
  },
  {
    title: "用户账户",
    dataIndex: 'userAccount',
    copyable: true,
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    render: (_, record) => (  // 渲染逻辑 (一格, 一行)
      <div>
        <Image src={record.avatarUrl} width={100} height={100} />
      </div>
    )
  },
  {
    title: "性别",
    dataIndex: 'gender',
    valueEnum: {
      0: {text: '女'},
      1: {text: '男'},
    }
  },
  {
    title: "电话",
    dataIndex: 'phone',
    copyable: true,
  },
  {
    title: "邮箱",
    dataIndex: 'email',
    copyable: true,
  },
  {
    title: "状态",
    dataIndex: 'userStatus',
    valueEnum: {
      0: {text: '正常状态', status: 'Default'},
      1: {text: '非正常状态', status: 'Error'},
    }
  },
  {
    title: "角色",
    dataIndex: "userRole",
    valueType: "select",  // 声明类型 否则默认字符串
    valueEnum: {
      0: {text: '普通用户', status: 'Default'},
      1: {text: '管理员', status: 'Success'},
    }
  },
  {
    title: "星球编号",
    dataIndex: "planetCode"
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    valueType: "dateTime",  // 声明类型 否则默认字符串
  },
  {
    title: '操作',
    valueType: 'option',
    key: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>,
      <a target="_blank" rel="noopener noreferrer" key="view">
        查看
      </a>,
      <TableDropdown
        key="actionGroup"
        onSelect={() => action?.reload()}
        menus={[
          { key: 'copy', name: '复制' },
          { key: 'delete', name: '删除' },
        ]}
      />,
    ],
  },
];

export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      request={async (params, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchUsers();
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        defaultValue: {
          option: {fixed: 'right', disable: true},
        },
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参数与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="高级表格"
    />
  );
};
