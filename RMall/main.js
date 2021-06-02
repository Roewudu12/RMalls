import Vue from 'vue'
import App from './App'
import uView from 'uview-ui';

Vue.use(uView);
Vue.prototype.$url="http://localhost:8888";
Vue.prototype.$header={'Content-Type':'application/x-www-form-urlencoded'};

Vue.config.productionTip = false

Vue.prototype.checkLogin = function(){
	const userId = uni.getStorageSync('userId')
	if(userId === ''){ // 本地没有token表示未登录
		console.log('未登录返回到登录页')
		uni.reLaunch({url:'/pages/login/login'})
		return false
	}
}

Vue.prototype.$JsonHeader={'content-type': 'application/json'};

App.mpType = 'app'

const app = new Vue({
    ...App
})
// http拦截器，此为需要加入的内容，如果不是写在common目录，请自行修改引入路径
import httpInterceptor from '@/common/http.interceptor.js'
// 这里需要写在最后，是为了等Vue创建对象完成，引入"app"对象(也即页面的"this"实例)
Vue.use(httpInterceptor, app)
app.$mount()
