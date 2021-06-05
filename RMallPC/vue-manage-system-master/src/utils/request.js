import axios from 'axios';
import router from '../router/index.js';
const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
    baseURL:'http://localhost:8888',
	withCredentials : true,
    timeout: 5000
});

service.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.data.code == "1212") {
			router.replace({
				path:"/login"
			})
			
        } else {
             return response.data;
        }
    },
    error => {
        console.log(error);
		
        return Promise.reject();
    }
);


export default service;
