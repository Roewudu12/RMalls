<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items" >
                <template v-if="item.subs &&  isAuthority(item)">
                    <el-submenu :index="item.index" :key="item.index">
                        <template #title>
                            <i :class="item.icon"></i>
                            <span>{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template #title>{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem, i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item  v-if="isAuthority(item)" :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <template #title>{{ item.title }}</template>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
// import bus from "../common/bus";
export default {
    data() {
        return {
			userAuthority:'',//当前用户权限
            items: [
                {
                    icon: "el-icon-lx-home",
                    index: "dashboard",
                    title: "系统首页"
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "personal_info",
                    title: "个人信息管理"
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "user_mana",
                    title: "用户管理",
					userAuthority:3
                },
                {
                    icon: "el-icon-lx-cascades",
                    index: "merchant_mana",
                    title: "商品管理"
                },
                
                {
                    icon: "el-icon-lx-cascades",
                    index: "order_mana",
                    title: "订单管理"
                },
              
                
            ]
        };
    },
	created() {
		this.userAuthority = localStorage.getItem("userAuthority")
	},
    computed: {
        onRoutes() {
            return this.$route.path.replace("/", "");
        },
        collapse(){
            return this.$store.state.collapse
        }
    },
	methods:{
		//判断用户是否有进入此item的权限
		isAuthority(item){
			if(!item.userAuthority){
				return true
			}else{
				if(item.userAuthority == this.userAuthority){
					return true
				}else{
					return false
				}
			}
		}
	}
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
