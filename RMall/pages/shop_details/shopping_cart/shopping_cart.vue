<template>
	<view class="shopping_cart">
		<!-- 导航栏 -->
		<u-navbar :is-back="true">
			<view class="nav_title title">购物车</view>
			<view class="nav_right" slot="right">{{nav_right}}</view>
		</u-navbar>
		<!-- 购物车列表 -->
		<view class="shopping_list" :key="index" v-for="(item,index) in shopping_list">
			<u-card>
				<view slot="head" class="card_head">
					<u-checkbox shape="circle" v-model="item.checked" @change="checked_merchant_goods" :name="item.merchant"></u-checkbox>
					<view class="card_title title">
						{{item.merchant}}
					</view>
				</view>
				<view slot="body" class="card_body">
					<u-checkbox-group @change="checkboxGroupChange" class="card_body_check_box">
						<view class="card_body_goods" :key="sindex" v-for="(sitem,sindex) in item.merchandises">
							<view class="goods_check_box">
								<u-checkbox shape="circle" @change="checkboxChange" :name="sitem.name" v-model="sitem.checked"></u-checkbox>
							</view>
							<view class="goods_u_image">
								<u-image width="150rpx" height="150rpx" :src="sitem.icon"></u-image>
							</view>
							<view class="goods_details">
								<view class="goods_details_title">{{sitem.name}}</view>
								<view class="goods_details_details">
									<u-button  @click="click_goods(sitem)" size="mini">{{sitem.details}}</u-button>
									<!-- 商品规格选项弹窗 -->
									<u-popup class="goods_popup" @close="popup_close" length="80%" v-model="show" mode="bottom" :closeable="true">
										<view class="popup_head">
											<!-- 标题 -->
											<view class="title my_row_flex u-padding-15">
												<u-image height="130rpx" width="130rpx" :src="choose_goods_image" :show-loading="true"></u-image>
												<view class="goods_price u-padding-top-35 u-margin-left-30 u-font-xl">￥{{recent_price}}</view>
											</view>
											<!-- 点击去评论区 -->
											<view @click="toContents" class="to_contents u-padding-15 u-margin-20 my_circle_radius my_row_flex">				
												<view class="my_row_flex">
													<u-icon name="chat"></u-icon>
													<view class="u-font-sm u-margin-left-15">有999个人评价"还不错"</view>
												</view>
												<view class="right_content my_row_flex">
													<view class="u-font-sm">全部({{contents_num}})</view>
													<u-icon name="arrow-right-double"></u-icon>
												</view>
												
											</view>
											<u-line color="info"></u-line>
										</view>
										<view class="popup_head_null"></view>
										<view class="popup_body u-padding-10">
											<view class="popup_body_title">
												{{popup_body_title}}
											</view>
											<view class="u-flex u-row-left u-flex-wrap u-margin-top-20">
												<view :key="nindex" v-for="(norm,nindex) in norms" 
												class=" norms my_circle_radius u-padding-10 u-margin-10 u-font-sm">{{norm.name}}</view>
											</view>
											
										</view>
										<view class="popup_foot_null"></view>
										<view class="popup_footer u-padding-15">
											<u-button type="warning" :ripple="true" shape="circle">确定</u-button>
										</view>
										
									</u-popup>
								</view>
								<view class="goods_price_num">
									<view class="goods_price">￥{{sitem.total}}</view>
									<u-number-box class="goods_num" 
									 v-model="sitem.num" :min="1" :input-width="40" :input-height="35" bg-color="#ffffff"
									  @change="valChange(sitem.num,index,sindex)" :disabled-input="true"></u-number-box>
								</view>
							</view>
						</view>
					</u-checkbox-group>
				</view>
			</u-card>
		</view>
		
		<view class='placeholder-view'></view>
		<!-- 底部菜单 -->
		<view class="footer" :style="{ bottom: footerbottom }" >
			<view class="left">
				<u-checkbox class="checkbox" icon-size="30" v-model="all_checked" shape="circle" @change="checked_all_goods">
					全选
				</u-checkbox>
			</view>
			<view class="right">
				 <view class="total">
					合计：
					<view class="total_num">
						 ￥{{total_num}}
					 </view>
				 </view>
				 <u-button type="warning" :ripple="true" :custom-style="buyButton">结算</u-button>
			</view>
			
		</view>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				nav_right: "管理",
				show:false,
				value:1,
				footerbottom:0,
				total_num:0,
				choose_goods_image:"",
				recent_price:0,
				contents_num:0,
				popup_body_title:"颜色分类",
				buyButton:{
					height:"55rpx",
				},
				all_checked:false,
				norms:[
					{
						name:"哈哈哈哈",
					},
					{
						name:"哈哈哈哈",
					},
					{
						name:"哈哈哈sadas哈",
					},
					{
						name:"哈哈dsadsa哈哈",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈",
					},
					{
						name:"哈哈dsadsa哈哈",
					},
					{
						name:"哈哈dsadsa哈哈",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
					{
						name:"哈哈dsadsa哈哈sssssssssssssssssssssssssss",
					},
				],
				shopping_list: [{
						checked: false,
						merchant: "拉面说旗舰店",
						merchandises: [{
								checked: false,
								name: "拉面说浓汤豚骨叉烧面",
								details: "海鲜冬阴功",
								icon: "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
								num: 1,
								price: 24.50,
								total:24.50
							},
							{
								checked: false,
								name: "拉面说浓汤豚骨叉烧面",
								details: "海鲜哈哈面",
								icon: "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
								num: 1,
								price: 11.90,
								total:11.90, 
							}
						]
					},
					{
						checked: false,
						merchant: "拉面说旗舰店2222222",
						merchandises: [{
								checked: false,
								name: "拉面说浓汤豚骨叉烧面2222",
								details: "海鲜冬阴功222222",
								icon: "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
								num: 5,
								price: 21.00,
								total:105.00,
							},
							{
								checked: false,
								name: "拉面说浓汤豚骨叉烧面2",
								details: "海鲜",
								icon: "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
								num: 5,
								price: 21.00,
								total:105.00,
							}
						]
					},
				],
				
			}
		},
		onShow() {
			// 兼容H5下结算条位置
			// #ifdef H5
			this.footerbottom =
				document.getElementsByTagName('uni-tabbar')[0].offsetHeight + 'px';
			// #endif
		},
		methods: {
			
			checked_all_goods(e){
				console.log(e)
			},
			checked_merchant_goods(e) {
				console.log(e)
			},
			checkboxGroupChange(e) {
				console.log(e)
			},
			checkboxChange(e) {
				console.log(e)
			},
			popup_close(){
				
			},
			click_goods_num(e){
				console.log(e)
			},
			toContents(e){
				console.log(e)
			},
			/**
			 * @param {Object} 
			 * 点击商品小标签，显示商品详情信息，更改小标签
			 */
			click_goods(e){
				
				this.choose_goods_image=e.icon;
				this.recent_price = e.price;
				this.show = true;
			},
			/**
			 * @param {Object} value
			 * @param {Object} index
			 * @param {Object} sindex
			 * 根据选择数量计算商品价格，并解决js小数丢精问题
			 */
			valChange(value,index,sindex){
				var merchant = this.$data.shopping_list[index]
				var merchandises=merchant.merchandises[sindex]
				var total = parseFloat(value*merchandises.price).toFixed(2)
				
				merchandises.total =parseFloat(total)
				
			}
		}
	}
</script>

<style scoped lang="scss">
	$footer_height:100rpx;
	$header_height:265rpx;
	.my_circle_radius{
		border-top-right-radius:15rpx;
		border-top-left-radius:15rpx;
		border-bottom-left-radius:15rpx;
		border-bottom-right-radius:15rpx;
	}
	.right_content{
		margin-left: auto;
	
	}
	.title {
		padding-left: 15rpx;
		color: $u-main-color;
		font-weight: 700;
	}
	.popup_head_null{
		height: $header_height;
	}
	.nav_right {
		padding-left: 15rpx;
		color: $u-main-color;
		font-size: 35rpx;
	}

	.nav_title {
		font-size: 40rpx;
	}

	.card_title {
		font-size: 35rpx;
	}

	.card_head {
		display: flex;
		direction: column;
	}
	.card_body{
		width: 100%;
	}
	
	.card_body_check_box {
		display: flex;
		direction: row;
	}

	.card_body_goods {
		display: flex;
		flex-direction: row;
		padding-top: 15rpx;
		padding-bottom: 15rpx;
		width: 610rpx;
	}

	.goods_check_box {
		align-content: center;
		display: flex;
		flex-direction: column;
		justify-content: center;
	}


	.goods_details {
		margin-left: 20rpx;
		display: flex;
		flex-direction: column;
		flex: 1;
	}

	.goods_price_num {
		display: flex;
		flex-direction: row;
		margin-top: 15rpx;
		width: 100%;
	}
	.goods_details_details{
		margin-top: 15rpx;
		width: 100%;
	}
	.goods_num{
		flex: 1;
		text-align: right;
	}
	.goods_price{
		color: #ff0000;
		font-weight: 700;
	}
	.popup_head{
		width: 100%;
		background-color: #fbfbfb;
		position: fixed;
		top: 0rpx;
		height: $header_height;
	}
	.popup_foot_null{
		height: $footer_height;
	}
	.popup_footer{
		width: 100%;
		position: fixed;
		bottom: 0rpx;
		height: $footer_height;
		background-color: #ffffff;
	}
	.footer{
		width: 100%;
		padding: 0 2%;
		background-color: #fbfbfb;
		height: $footer_height;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 28upx;
		position: fixed;
		bottom: 0upx;
		z-index: 5;
		.checkbox{
			margin-left: 15rpx;
		}
		.right{
			float: right;
			display: flex;
			flex-direction: row;
			height: 100%;
			align-items: center;
			.total{
				margin-right: 30rpx;
				display: flex;
				flex-direction: row;
				.total_num{
					color: $uni-color-error;
				}
			}
		}
	}
	.placeholder-view{
		width: 100%;
		height: $footer_height;
	}
	.my_row_flex{
		display: flex;
		flex-direction: row;
	}
	.goods_popup{
		
	}
	.to_contents{
		background-color: $u-type-info-disabled;
	}
	.norms{
		background-color: #eeeeee;
	}
	.my_gray_font{
		color: #e0dce6;
	}
</style>
