<template>
	<view>
		<u-navbar :is-back="true" title="我的收货地址"></u-navbar>
		<view class="item" v-for="(address, index) in addresses" :key="index">
			<view class="top" @click="backToOrder(index)">
				<view class="name">{{ address.name }}</view>
				<view class="phone">{{ address.phone }}</view>
				<view class="tag"  v-if="index==0">
					<text class="red">默认</text>
				</view>
			</view>
			<view class="bottom">
				<view @click="backToOrder(index)">{{address.addressProvince+"-"+address.addressCity+"-"+address.addressDistrict+" "+address.addressDetails}}</view>
				<u-icon name="edit-pen" @click="toEditSite(index)" :size="40" color="#999999"></u-icon>
			</view>
		</view>
		<view class="addSite" @tap="toAddSite">
			<view class="add">
				<u-icon name="plus" color="#ffffff" class="icon" :size="30"></u-icon>新建收货地址
			</view>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			siteList: [],
			addresses:[],
		};
	},
	onLoad() {
	},
	onShow(){
		this.getDefaulAddress();
	},
	methods: {
		/**
		 * 获取非默认地址
		 */
		getAddresses() {
			this.$u.post("/address/show",{
				userId:uni.getStorageSync("userId"),
			}).then(res=>{
				console.log("getAddresses",res)
				this.addresses = this.addresses.concat(res.data.data);
			})
		},
		/**
		 * 获取默认地址
		 */
		getDefaulAddress(){
			this.$u.post("/defaultAdd/get",{
				userId:uni.getStorageSync("userId")
			}).then(res=>{
				console.log("getDefaulAddress",res)
				this.addresses = [];
				res.data.data.isDefault = true;
				this.addresses.push(res.data.data);
				this.getAddresses()
			})
		},
		/**
		 * 去新建地址
		 */
		toAddSite(){
			console.log("toAddSite")
			this.$u.route("/pages/choose_adress/add_adress/add_adress")
			
		},
		/**
		 * 去编辑地址
		 */
		toEditSite(index){
			console.log("toEditSite",index)
			if(!this.addresses[index].isDefault){
				this.addresses[index].isDefault = false;
			}
			let address = JSON.stringify(this.addresses[index]);
			this.$u.route("/pages/choose_adress/add_adress/add_adress",{
				address:address,
			})
		},
		/**
		 * 返回去下单
		 */
		backToOrder(index){
			console.log("backToOrder",index)
			let pages = getCurrentPages();  //获取所有页面栈实例列表
			let prevPage = pages[ pages.length - 2 ];  //上一页页面实例
			prevPage.$vm.address = this.addresses[index];   
			this.$u.route({
				type:"back",
			})
		}
	}
};
</script>

<style lang="scss" scoped>
.item {
	padding: 40rpx 20rpx;
	.top {
		display: flex;
		font-weight: bold;
		font-size: 34rpx;
		.phone {
			margin-left: 60rpx;
		}
		.tag {
			display: flex;
			font-weight: normal;
			align-items: center;
			text {
				display: block;
				width: 60rpx;
				height: 34rpx;
				line-height: 34rpx;
				color: #ffffff;
				font-size: 20rpx;
				border-radius: 6rpx;
				text-align: center;
				margin-left: 30rpx;
				background-color:rgb(49, 145, 253);
			}
			.red{
				background-color:red
			}
		}
	}
	.bottom {
		display: flex;
		margin-top: 20rpx;
		font-size: 28rpx;
		justify-content: space-between;
		color: #999999;
	}
}
.addSite {
	display: flex;
	justify-content: space-around;
	width: 600rpx;
	line-height: 100rpx;
	position: absolute;
	bottom: 30rpx;
	left: 80rpx;
	background-color: red;
	border-radius: 60rpx;
	font-size: 30rpx;
	.add{
		display: flex;
		align-items: center;
		color: #ffffff;
		.icon{
			margin-right: 10rpx;
		}
	}
}
</style>
