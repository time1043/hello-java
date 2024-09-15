import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
// import {Button, Icon, NavBar, Tabbar, TabbarItem} from 'vant';
import {createMemoryHistory, createRouter} from 'vue-router';
import routes from "./config/route.ts";


// VueRouter
const router = createRouter({
    history: createMemoryHistory(),
    routes,
})

const app = createApp(App);
// app.use(Button).use(NavBar).use(Icon).use(Tabbar).use(TabbarItem);
app.use(router)
app.mount('#app')
