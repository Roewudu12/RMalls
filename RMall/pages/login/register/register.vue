<template>
	<view class="wrap">
		<view class="top">
			<u-navbar title="注册"></u-navbar>
		</view>
		<view class="content">
			<view class="title">欢迎注册RMall</view>
			<u-form :model="form" ref="uForm" label-width="150">
				<u-form-item label="用户名">
					<u-input v-model="form.username" />
				</u-form-item>
				<u-form-item label="电话">
					<u-input v-model="form.userPhone" />
				</u-form-item>
				<u-form-item label="密码">
					<u-input type="password" v-model="form.password" />
				</u-form-item>
				<u-form-item label="确认密码">
					<u-input type="password" v-model="confirmPassword" />
				</u-form-item>
			</u-form>
			
			<button @tap="submit" :style="[inputStyle]" class="getCaptcha">登录</button>
		</view>
		<view class="buttom">
			<view class="hint">
				<!-- 登录代表同意 -->
				<text class="link">RMall点评用户协议、隐私政策，</text>
				并授权使用您的RMall点评账号信息（如昵称、头像、收获地址）以便您统一管理
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					username: '',
					password: '',
					userPhone: '',
				},
				confirmPassword:'',//确认密码
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.form.username && this.form.password && this.form.userPhone) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			submit() {
				console.log("submit", this.form);
				let data = this.form;
				if(this.form.password == this.confirmPassword){
					data.userAuthority=2;//用户权限为买家
					this.$u.post("/user/insert",data,this.$JsonHeader)
					.then(res=>{
						console.log("register",res);
						this.$u.route({type:"back"})
					})
				}else{
					uni.showToast({
						title:"两次密码输入不正确！",
						duration:3000,
						icon:"none"
					})
				}
			}
		}
	};
</script>

<style lang="scss" scoped>
	.wrap {
		font-size: 28rpx;

		.content {
			width: 600rpx;
			margin: 80rpx auto 0;

			.title {
				text-align: left;
				font-size: 60rpx;
				font-weight: 500;
				margin-bottom: 100rpx;
			}

			input {
				text-align: left;
				margin-bottom: 10rpx;
				padding-bottom: 6rpx;
			}

			.tips {
				color: $u-type-info;
				margin-bottom: 60rpx;
				margin-top: 8rpx;
			}

			.getCaptcha {
				background-color: rgb(253, 243, 208);
				color: $u-tips-color;
				border: none;
				font-size: 30rpx;
				padding: 12rpx 0;

				&::after {
					border: none;
				}
			}

			.alternative {
				color: $u-tips-color;
				display: flex;
				justify-content: space-between;
				margin-top: 30rpx;
			}
		}

		.buttom {
			.loginType {
				display: flex;
				padding: 350rpx 150rpx 150rpx 150rpx;
				justify-content: space-between;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					color: $u-content-color;
					font-size: 28rpx;
				}
			}

			.hint {
				padding: 20rpx 40rpx;
				font-size: 20rpx;
				color: $u-tips-color;

				.link {
					color: $u-type-warning;
				}
			}
		}
	}
</style>
