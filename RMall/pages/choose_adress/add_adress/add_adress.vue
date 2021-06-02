<template>
	<view class="wrap">
		<u-navbar :is-back="true" :title="title+'收货地址'"></u-navbar>
		<view class="top">
			<view class="item">
				<view class="left">收货人</view>
				<u-input type="text" style="width: 100%;" v-model="address.name" placeholder="请填写收货人姓名" />
			</view>
			<view class="item">
				<view class="left">手机号码</view>
				<u-input type="text" style="width: 100%;" v-model="address.phone" placeholder="请填写收货人手机号" />
			</view>
			<view class="item" @click="showRegionPicker">
				<view class="left">所在地区</view>
				<u-input @click="showRegionPicker" v-model="region" style="width: 100%;" disabled type="text" placeholder="省市区县、乡镇等" />
			</view>
			<view class="item address">
				<view class="left">详细地址</view>
				<u-input v-model="address.addressDetails" style="width: 100%;" type="textarea" placeholder="街道、楼牌等" :border="true" :height="100" :auto-height="true" />
			</view>
			<!-- <view class="site-clipboard">
				<textarea placeholder-class="line" value="" placeholder="粘贴文本,可自动识别姓名和地址等" />
				<view class="clipboard">
					地址粘贴板
					<u-icon name="arrow-down" class="icon" :size="20"></u-icon>
				</view>
			</view> -->
		</view>
		<view class="bottom">
			<view class="default">
				<view class="left">
					<view class="set">设置默认地址</view>
					<view class="tips">提醒：每次下单会默认推荐该地址</view>
				</view>
				<view class="right"><u-switch v-model="address.isDefault"></u-switch></view>
			</view>
		</view>
		<view class="u-padding-left-20 u-padding-right-20">
			<u-button style="width: 100%;" type="warning" shape="circle" :ripple="true" @click="setAddress">保存</u-button>
		</view>
		
		<u-picker mode="region" ref="uPicker" v-model="show" @cancel="cancel" @confirm="confirm" />
	</view>
</template>

<script>
export default {
	data() {
		return {
			show: false,
			title:"新增",
			address:{
				userId:uni.getStorageSync("userId"),
				name:"",
				phone:"",
				addressDetails:"",
				addressProvince:"",
				addressDistrict:"",
				addressCity:"",
				isDefault:false,
			},
		};
	},
	onLoad(e) {
		console.log("/pages/choose_adress/add_adress/add_adress")
		if(e.address){
			console.log("存在address")
			this.title = "编辑";
			this.address = JSON.parse(e.address);
		}
	},
	computed:{
		region:function(){
			if(this.address.addressProvince=='') return "";
			return this.address.addressProvince+"-"+this.address.addressCity+"-"+this.address.addressDistrict;
		}
	},
	methods: {
		showRegionPicker() {
			this.show = true;
		},
		/**
		 * 提交表单添加地址
		 */
		setAddress(){
			let url = "/address/add";
			if(this.title=="编辑"){
				url="/address/edit"
			}
			console.log("setAddress",url)
			this.$u.post(url,this.address,this.$JsonHeader)
			.then(res=>{
				console.log("setAddress",res);
				this.backTo();
			})
		},
		/**
		 * 取消按钮地区选择框
		 */
		cancel(e){
			console.log("cancel",e)
		},
		/**
		 * 确认按钮地区选择框
		 */
		confirm(e){
			console.log("confirm",e)
			this.address.addressProvince = e.province.label;
			this.address.addressCity = e.city.label;
			this.address.addressDistrict = e.area.label;
			
		},
		/**
		 * 返回上一页
		 */
		backTo(){
			this.$u.route({
				type:"back"
			})
		}
	}
};
</script>

<style lang="scss" scoped>
/deep/ .line {
	color: $u-light-color;
	font-size: 28rpx;
}
.wrap {
	background-color: #f2f2f2;
	.top {
		background-color: #ffffff;
		border-top: solid 2rpx $u-border-color;
		padding: 22rpx;
		.item {
			display: flex;
			font-size: 32rpx;
			line-height: 100rpx;
			align-items: center;
			border-bottom: solid 2rpx $u-border-color;
			.left {
				width: 180rpx;
			}
			input {
				text-align: left;
			}
		}
		
		.address {
			padding: 20rpx 0;
			textarea {
				// width: 100%;
				height: 150rpx;
				background-color: #f7f7f7;
				line-height: 60rpx;
				margin: 40rpx auto;
				padding: 20rpx;
			}
		}
		.site-clipboard {
			padding-right: 40rpx;
			textarea {
				// width: 100%;
				height: 150rpx;
				background-color: #f7f7f7;
				line-height: 60rpx;
				margin: 40rpx auto;
				padding: 20rpx;
			}
			.clipboard {
				display: flex;
				justify-content: center;
				align-items: center;
				font-size: 26rpx;
				color: $u-tips-color;
				height: 80rpx;
				.icon {
					margin-top: 6rpx;
					margin-left: 10rpx;
				}
			}
		}
	}
	.bottom {
		margin-top: 20rpx;
		padding: 40rpx;
		padding-right: 0;
		background-color: #ffffff;
		font-size: 28rpx;
		.tag {
			display: flex;
			.left {
				width: 160rpx;
			}
			.right {
				display: flex;
				flex-wrap: wrap;
				.tags {
					width: 140rpx;
					padding: 16rpx 8rpx;
					border: solid 2rpx $u-border-color;
					text-align: center;
					border-radius: 50rpx;
					margin: 0 10rpx 20rpx;
					display: flex;
					font-size: 28rpx;
					align-items: center;
					justify-content: center;
					color: $u-content-color;
					line-height: 1;
				}
				.plus {
					//padding: 10rpx 0;
				}
			}
		}
		.default {
			margin-top: 50rpx;
			display: flex;
			justify-content: space-between;
			border-bottom: solid 2rpx $u-border-color;
			line-height: 64rpx;
			.tips {
				font-size: 24rpx;
			}
			.right {
			}
		}
	}
}
</style>
