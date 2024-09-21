import Index from "../pages/Index.vue";
import TeamPage from "../pages/TeamPage.vue";
import UserPage from "../pages/UserPage.vue";
import SearchPage from '../pages/SearchPage.vue';
import UserEditPage from "../pages/UserEditPage.vue";
import UserList from "../pages/UserList.vue";

const routes = [
    // page 1
    {path: '/', component: Index},
    {path: '/search', title: '找伙伴', component: SearchPage},
    {path: '/user/list', title: '用户列表', component: UserList},
    // page 2
    {path: '/team',title: '组队', component: TeamPage},
    // page 3
    {path: '/user', title: '个人信息', component: UserPage},
    {path: '/user/edit', title: '编辑个人信息', component: UserEditPage},  // 动态路由 :id  // 键值对 ?id=123
]

export default routes;
