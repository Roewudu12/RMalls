<template>
	<view>
		<u-navbar back-text="个人信息"></u-navbar>
		<u-card>
			<!-- 头像 -->
			<view slot="head" class="head">
				<view class="image" style="width: 200rpx;">
					<u-upload :action="action" max-count="1" :file-list="fileList" :header="header"
						@on-change="onChange" @on-remove="onRemove"></u-upload>
					<view class="intro">更换头像</view>
				</view>
			</view>
			<view slot="body" class="form">
				<u-form :model="user" ref="uForm">
					<u-form-item label="账号">
						<u-input v-model="user.username" />
					</u-form-item>
					<u-form-item label="电话">
						<u-input v-model="user.userPhone" />
					</u-form-item>
					<u-form-item label="简介">
						<u-input v-model="intro" />
					</u-form-item>
				</u-form>
			</view>
			<view slot="foot">
				<view>				
					<u-button type="success" @click="save">保存</u-button>
				</view>
				<view class="u-margin-top-20">
					<u-button  type="error" @click="exitLogin">退出登录</u-button>
				</view>
			</view>
		</u-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				action: "http://localhost:8888/image/upload",
				fileList: [],
				header: {
					Cookie:uni.getStorageSync('session')
				},
				user: {},
				intro: "", //用户个性签名
				image:"",
			}
		},
		onLoad() {
			console.log("/pages/user_info/user_info");
			this.getUserInfo();
		},
		methods: {
			onChange(res, index, lists, name) {
				console.log("onChange", res, index, lists, name,this.fileList);
				if(this.image!=""){
					//需要删除上传到服务器的图片
					this.removeFromServer();
				}
				this.image = lists[0].response.data.imageHttp;
				
			},
			onRemove(index, lists, name) {
				console.log("onRemove", index, lists, name)
				this.removeFromServer();
				this.image="";
			},
			getUserInfo() {
				this.$u.post("/user/userInfo/byId", {
					userId: uni.getStorageSync("userId")
				}).then(res => {
					console.log("getUserInfo", res);
					this.user = res.data.data;
					if(this.user.userIntroduction){
						//存在
						this.intro = this.user.userIntroduction;
					}
					if(this.user.image){
						this.image = this.user.image;
						this.fileList.push({
							url:this.image
						})
					}
				})
			},
			/**
			 * 从服务器删除图片
			 */
			removeFromServer(){
				let image={
					imageHttp:this.image
				}
				this.$u.post("/image/delete",image,this.$JsonHeader)
				.then(res=>{
					console.log("removeFromServer",res);
				})
			},
			/**
			 * 保存用户信息
			 */
			save(){
				this.user.userIntroduction = this.intro;
				this.user.image = this.image;
				console.log(this.user)
				this.$u.post("/user/userInfo/update",this.user,this.$JsonHeader)
				.then(res=>{
					console.log("save",res);
					this.$u.route({
						type:"back"
					})
				})
			},
			/**
			 * 退出登录
			 */
			exitLogin(){
				this.$u.post("/logout").then(res=>{
					console.log("exitLogin",res);
					uni.removeStorageSync("userId");
					uni.removeStorageSync("session");
					this.$u.route({
						url:"/pages/login/login",
						type:"redirect"
					})
				})
			}
		}
	}
</script>

<style>
	page {
		background-color: #EBEEF5;
	}
</style>
<style scoped lang="scss">
	.head {
		display: flex;
		justify-content: center;

		.image {
			margin-top: 30rpx;

			.intro {
				text-align: center;
			}
		}
	}

	.form {}
</style>
