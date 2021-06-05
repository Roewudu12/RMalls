import {
	createRouter,
	createWebHistory
} from "vue-router";
import Home from "../views/Home.vue";

const routes = [{
	path: '/',
	redirect: '/dashboard'
}, {
	path: "/",
	name: "Home",
	component: Home,
	children: [{
			path: "/dashboard",
			name: "dashboard",
			meta: {
				title: '系统首页'
			},
			component: () => import(
				/* webpackChunkName: "dashboard" */
				"../views/Dashboard.vue")
		},
		{
			path: "/personal_info",
			name: "personal_info",
			meta: {
				title: '个人信息'
			},
			component: () => import(
				"../views/personal_info/personal_info.vue")
		},
		{
			path: "/user_mana",
			name: "user_mana",
			meta: {
				title: '用户管理'
			},
			component: () => import(
				"../views/user_mana/user_mana.vue")
		},
		{
			path: "/merchant_mana",
			name: "merchant_mana",
			meta: {
				title: '商品管理'
			},
			component: () => import(
				"../views/merchant_mana/merchant_mana.vue")
		},
		{
			path: "/order_mana",
			name: "order_mana",
			meta: {
				title: '订单管理'
			},
			component: () => import(
				"../views/order_nama/order_mana.vue")
		},

	]
}, {
	path: "/login",
	name: "Login",
	meta: {
		title: '登录'
	},
	component: () => import(
		/* webpackChunkName: "login" */
		"../views/Login.vue")
}];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
});

router.beforeEach((to, from, next) => {
	console.log("beforeEach()", to)
	document.title = `${to.meta.title} | vue-manage-system`;
	const role = localStorage.getItem('userId');
	console.log(role)
	if (!role && to.path !== '/login') {
		next('/login');
	} else {
		next();
	}
});

export default router;
