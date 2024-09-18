import Index from "../pages/Index.vue";
import TeamPage from "../pages/TeamPage.vue";
import UserPage from "../pages/UserPage.vue";
import SearchPage from '../pages/SearchPage.vue';
import UserEditPage from "../pages/UserEditPage.vue";

const routes = [
    // page 1
    {path: '/', component: Index},
    {path: '/search', component: SearchPage},
    // page 2
    {path: '/team', component: TeamPage},
    // page 3
    {path: '/user', component: UserPage},
    {path: '/user/edit', component: UserEditPage},  // 动态路由 :id  // 键值对 ?id=123
]

export default routes;
