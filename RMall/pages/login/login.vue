<template>
	<view class="wrap">
		<view class="top"></view>
		<view class="content">
			<view class="title">欢迎登录RMall</view>
			<input class="u-border-bottom" type="name" v-model="form.username" placeholder="请输入用户名" />
			<input class="u-border-bottom u-margin-top-10" type="text" password="true" v-model="form.password"
				placeholder="请输入密码" />
			<!-- <view class="tips">未注册的手机号验证后自动创建RMall账号</view> -->
			<button @tap="submit" :style="[inputStyle]" class="getCaptcha">登录</button>
			<view class="alternative">
				<view class="password">手机验证码登录</view>
				<view class="issue">遇到问题</view>
			</view>
		</view>
		<view class="buttom">
			<view class="loginType">
				<view class="wechat item">
					<view class="icon">
						<u-icon size="70" name="weixin-fill" color="rgb(83,194,64)"></u-icon>
					</view>
					微信
				</view>
				<view class="zhifubao item">
					<view class="icon">
						<u-icon size="70" name="zhifubao" color="rgb(17,183,233)"></u-icon>
					</view>
					支付宝
				</view>
			</view>
			<view class="hint">
				登录代表同意
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
				}
			}
		},
		computed: {
			inputStyle() {
				let style = {};
				if (this.form.username && this.form.password) {
					style.color = "#fff";
					style.backgroundColor = this.$u.color['warning'];
				}
				return style;
			}
		},
		methods: {
			submit() {
				this.$u.post('/login', this.form)
					.then(res => {
						console.log("login", res)
						uni.setStorageSync("session", res.cookies[0]);
						uni.setStorageSync("userId", res.data.data.userId);
						uni.switchTab({
							url: "../index/index"
						})
					})

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
