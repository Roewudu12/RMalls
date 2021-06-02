<template>

	<view class="mine">
		<view class="my_status_bar"></view>
		<view class="user my_row_flex u-padding-top-10 u-padding-left-30">
			<u-avatar :src="user.image" size="large" @click="to_login()"></u-avatar>
			<view class="user_info u-margin-left-25 u-padding-top-10">
				<view class="user_name u-font-30">{{user.username}}</view>
				<view class="user_signature u-font-20 u-margin-top-20">{{user.userIntroduction?user.userIntroduction:user_signature}}</view>
			</view>
			<view class="icon_right">
				<u-icon class="icon_arrow-right" name="arrow-right"></u-icon>
			</view>
		</view>

		<!-- 订单操作 -->
		<u-card class="my_circle_radius">
			<view class="my_row_flex u-row-around" slot="head">
				<view class="my_row_flex">
					<u-icon name="star"></u-icon>
					<view class="u-margin-left-5 my_font_weight">收藏</view>
					<view class="u-margin-left-10 u-font-10 my_text_col_center">{{favorite_goods}}</view>
				</view>
				<view class="my_line">|</view>
				<view class="my_row_flex">
					<u-icon name="bookmark"></u-icon>
					<view class="u-margin-left-5 my_font_weight">订阅店铺</view>
					<view class="u-margin-left-10 u-font-10 my_text_col_center">{{subscribed_shop}}</view>
				</view>
				<view class="my_line">|</view>
				<view class="my_row_flex">
					<u-icon name="eye "></u-icon>
					<view class="u-margin-left-5 my_font_weight">足迹</view>
					<view class="u-margin-left-10 u-font-10 my_text_col_center">{{footprint_goods}}</view>
				</view>
			</view>
			<view slot="body">
				<view class="all_orders my_row_flex" @click="to_order">
					<view class="title u-font-25 my_font_weight my_font_color_title">我的订单</view>
					<view class="look_all my_right_elements my_row_flex">
						<view class="u-font-10 my_font_color_info my_text_col_center">查看全部</view>
						<u-icon class="my_font_color_info" size="25" name="arrow-right"></u-icon>
					</view>
				</view>
				<view class="my_row_flex u-row-around u-margin-top-20">
					<view class="pre_payment u-relative">
						<u-badge :count="count_pre_payment" size="mini" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="pre_payment" custom-prefix="rmall-icon" size="60"></u-icon>
						<view class="u-font-10">待付款</view>
					</view>
					<view class="forwarding u-relative">
						<u-badge :count="count_forwarding" size="mini" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="forwarding" custom-prefix="rmall-icon" size="60"></u-icon>
						<view class="u-font-10">代发货</view>
					</view>
					<view class="delivery u-relative">
						<u-badge :count="count_delivery" size="mini" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="delivery" custom-prefix="rmall-icon" size="60"></u-icon>
						<view class="u-font-10">待收货</view>
					</view>
					<view class="comments u-relative">
						<u-badge :count="count_comments" size="mini" type="error" :offset="[-3, -6]"></u-badge>
						<u-icon name="comments" custom-prefix="rmall-icon" size="60"></u-icon>
						<view class="u-font-10">待评价</view>
					</view>
					<view class="after_sale u-text-center u-relative">
						<u-badge :count="count_after_sale" size="mini" type="error" :offset="[-3, 2]"></u-badge>
						<u-icon name="after_sale" custom-prefix="rmall-icon" size="60"></u-icon>
						<view class="u-font-10">退款/售后</view>
					</view>
				</view>
			</view>
		</u-card>

		<!-- 我的订阅 -->
		<u-card class="my_circle_radius">
			<view class="my_row_flex" slot="head">
				<view class="title my_font_weight my_font_color_title">我的订阅</view>
			</view>

			<view slot="body">
				<!-- 商家最新动态 -->
				<view class="new_moving u-margin-bottom-20" :key="index" v-for="(item,index) in new_moving_list">
					<view class="shop_info my_row_flex">
						<u-image width="80rpx" height="80rpx" :src="item.shop_icon"></u-image>
						<view class="shop_info_wrap u-margin-left-20 u-col-center">
							<view class="shop_name my_font_weight my_font_color_title u-flex-1">{{item.shop_name}}</view>
							<view class="shop_intro_wrap my_row_flex u-font-20 u-margin-top-10 u-flex-1">
								<view class="shop_intro">{{item.shop_subtitle}}</view>
								<view class="my_line u-margin-left-5 u-margin-right-5">|</view>
								<view class="publish_time">{{item.publish_time}}</view>
							</view>
						</view>
					</view>
					<view class="shop_publish_info u-margin-top-20 u-margin-bottom-20">
						{{item.publish_introduction}}
					</view>
					<u-grid class="shop_publish_icons" :border="false" :col="2">
						<u-grid-item v-for="(sitem,sindex) in item.publish_icons" v-if="sindex<4" :key="sindex">
							<u-image width="200rpx" height="200rpx" :src="sitem.publish_icon"></u-image>
						</u-grid-item>
					</u-grid>
				</view>
			</view>
		</u-card> 
	</view>
</template>

<script> 
	export default {
		data() {
			return {
				user:{},//用户信息
				avatar_src: "https://cdn.uviewui.com/uview/common/classify/2/1.jpg",
				user_name: "哈哈哈哈",
				user_signature: "暂无个性签名",
				favorite_goods: 142,
				subscribed_shop: 242,
				footprint_goods: 11,
				count_pre_payment: 1,
				count_comments: 0,
				count_delivery: 2,
				count_forwarding: 0,
				count_after_sale: 0,
				new_moving_list: [{
						shop_name: "旗舰店",
						shop_icon: "https://cdn.uviewui.com/uview/common/classify/1/13.jpg",
						shop_subtitle: "品牌旗舰店",
						publish_time: "刚刚",
						publish_introduction: "哈斯卡拉的贺卡收到了撒开绿灯哈萨克交换机咖啡和撒可见度哈萨克三撒娇和贷记卡圣诞节哈斯卡拉的贺卡收到了撒开绿灯哈萨克交换机咖啡和撒可见度哈萨克三撒娇和贷记卡圣诞节哈斯卡拉的贺卡收到了凯撒家里的很快就拉省的看了哈是",
						publish_icons: [{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							}
						],
					},
					{
						shop_name: "旗舰店",
						shop_icon: "https://cdn.uviewui.com/uview/common/classify/1/13.jpg",
						shop_subtitle: "品牌旗舰店",
						publish_time: "刚刚",
						publish_introduction: "撒开绿灯哈萨克交换机咖啡和撒可见度哈萨克三撒娇和贷记卡圣诞节哈斯卡拉的贺卡收到了凯撒家里的很快就拉省的看了哈是",
						publish_icons: [{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							}
						],
					},
					{
						shop_name: "旗舰店",
						shop_icon: "https://cdn.uviewui.com/uview/common/classify/1/13.jpg",
						shop_subtitle: "品牌旗舰店",
						publish_time: "刚刚",
						publish_introduction: "撒开绿灯哈萨克交换机咖啡和撒可见度哈萨克三撒娇和贷记卡圣诞节哈斯卡拉的贺卡收到了凯撒家里的很快就拉省的看了哈是",
						publish_icons: [{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							},
							{
								publish_icon: "https://cdn.uviewui.com/uview/common/classify/2/4.jpg",
							}
						],
					},
				],
			}
		},
		onLoad() {
			this.getUserDetails();
		},
		methods: {
			getUserDetails(){
				this.$u.post("/user/userInfo/byId",{
					userId:uni.getStorageSync("userId")
				}).then(res=>{
					console.log("getUserDetails",res);
					this.user = res.data.data;
				})
			},
			to_order(){
				uni.navigateTo({
					url:"/pages/order/order"
				})
			},
			to_login(){
				// uni.navigateTo({
				// 	url:"/pages/login/login"
				// })
			},
			
		}
	}
</script>

<style scoped lang="scss">
	.mine {
		background-color: #EBEEF5;
		height: 100%;
	}


	.user_name {
		font-weight: 700;
	}

	.icon_right {
		margin-left: auto;
		margin-right: 30rpx;
	}

	.icon_arrow-right {
		margin: auto;
		width: 100%;
		height: 100%;
	}

	.shop_info {
		height: 80rpx;
	}
</style>
