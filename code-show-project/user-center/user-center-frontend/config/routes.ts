export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {name: '登录', path: '/user/login', component: './User/Login'},
      {name: '注册', path: '/user/register', component: './User/Register'},
    ],
  },
  {path: '/welcome', name: '欢迎', icon: 'smile', component: './Welcome'},
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin', // 鉴权
    // component: './Admin', // 父子页面
    routes: [
      {path: '/admin', redirect: '/admin/user-manager'},
      {path: '/admin/user-manager', name: '用户管理页', component: './Admin/UserManage'},
      // {path: '/admin/sub-page', name: '二级管理页', component: './Admin'},
    ],
  },
  {name: '查询表格', icon: 'table', path: '/list', component: './TableList'},
  {path: '/', redirect: '/welcome'},
  {path: '*', layout: false, component: './404'},
];
