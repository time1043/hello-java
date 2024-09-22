import axios, {AxiosInstance} from 'axios';

// const isDev = process.env.NODE_ENV === 'development';
const myAxios: AxiosInstance = axios.create({
    // baseURL: isDev? 'http://localhost:8002/api' : 'https://47.47.47.47:8002/api',
    baseURL: 'http://localhost:8002/api'
});
myAxios.defaults.withCredentials = true;


// https://axios-http.com/zh/docs/interceptors
// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    console.log('Axios Request Interceptor: ', config)
    return config;
}, function (error) {
    // 对请求错误做些什么
    console.log('Axios Request Interceptor[Error]: ', error)
    return Promise.reject(error);
});


// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。  // 对响应数据做点什么
    console.log('Axios Response Interceptor: ', response)

    // // 未登录则跳转到登录页面
    // if (response?.data?.code === 40100) {
    //     const redirect = window.location.href;
    //     window.location.href = `/user/login?redirect=${redirect}`;
    // }

    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。  // 对响应错误做点什么
    console.log('Axios Response Interceptor[Error]: ', error)
    return Promise.reject(error);
});

export default myAxios;