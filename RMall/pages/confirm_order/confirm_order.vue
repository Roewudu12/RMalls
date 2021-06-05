<template>
	<view class="confirm_order my_backgroundColor_gray">
		<u-navbar :is-back="true" back-text="确认订单"></u-navbar>
		<!-- 选择地址 -->
		<u-card :head-border-bottom="false" :show-head="false" @click="to_adress">
			<view slot="body" class="my_row_flex">
				<view class="my_text_col_center">
					<u-image src="/static/confirm_order_image/confirm_order_adress.png" height="80" width="80"></u-image>
				</view>
				<view class="u-margin-left-30">
					<view class="my_row_flex">
						<view class="my_font_color_title">{{address.name}}</view>
						<view class="my_font_color_info u-font-25 u-margin-left-10 ">{{address.phone}}</view>
					</view>

					<view class="u-font-25">{{address.addressProvince+"-"+address.addressCity+"-"+address.addressDistrict+" "+address.addressDetails}}</view>

				</view>
				<view class="my_right_elements my_text_col_center">
					<u-icon name="arrow-right"></u-icon>
				</view>
			</view>
		</u-card>

		<u-card v-for="(item,index) in goods" :key="index" :title="item.good.seller.username" title-size="28">
			<view slot="body">
				<view class="my_row_flex">
					<u-image :src="item.good.detailImages[0].imageHttp" width="180" height="180" border-radius="5"></u-image>
					<view class="u-margin-left-20">
						<view class="my_font_color_title u-font-25">{{item.good.goodName}}</view>
						<view class="u-margin-top-10 my_font_color_info u-font-25 my_backgroudColor_info">{{item.goodChoice}}</view>
					</view>
					<view class="u-margin-20">
						<view class="my_font_color_price">￥{{item.good.goodPrice}}</view>
						<view class="my_font_color_info">x{{item.goodNum}}</view>
					</view>
				</view>
			</view>
			<view slot="foot">
				<!-- 配送方式 -->
				<view class="my_row_flex u-margin-top-20">
					<view class="my_font_color_title">配送方式</view>
					<view class="my_right_elements my_row_flex">
						<view class="my_font_color_title">快递 免邮</view>
						<u-icon name="arrow-right"></u-icon>
					</view>
				</view>
				<!-- 订单备注 -->
				<view class="my_row_flex u-margin-top-20">
					<view class="my_font_color_title">订单备注</view>
					<view class="u-flex-1 u-margin-left-30">
						<u-input v-model="input_value" type="text" :border="false" :placeholder="placeholder" height="30" />
					</view>

				</view>
				<!-- 小计 -->
				<view class="my_row_flex u-margin-top-20">
					<view class="my_row_flex my_right_elements">
						<view class="my_font_color_info u-font-20 my_text_col_center u-margin-right-10">共{{item.goodNum}}件</view>
						<view class="my_font_color_title">小计：</view>
						<view class="my_font_color_price">￥{{parseFloat(item.goodNum*item.good.goodPrice).toFixed(2)}}</view>
					</view>
				</view>
			</view>
		</u-card>

		<view class="empty"></view>
		<view class="footer my_footer my_row_flex u-padding-10">
			<view class="my_right_elements my_row_flex">
				<view class="my_font_color_info u-font-20 my_text_col_center u-margin-right-10">共{{value}}件</view>
				<view class="my_font_color_title my_text_col_center">合计：</view>
				<view class="my_font_color_price my_text_col_center u-margin-right-10">￥{{total}}</view>
				<u-button :custom-style="customStyle" @click="submit" type="warning" size="medium">提交订单</u-button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				address: {},
				customStyle: {
					width: "200rpx",
				},
				value: 1,
				total: 0,
				placeholder: "选填,请先和商家协商一致",
				input_value: "",
				goods:[],//将要购买的商品（直接购买）
			}
		},
		onLoad(e) {
			console.log("/pages/confirm_order/confirm_order")
			//在页面加载时将total设置为price的价格
			if(e.carts){
				this.goods = JSON.parse(e.carts);
			}else{
				this.goods = JSON.parse(e.goods);
			}
			this.total = e.total;
			console.log(this.goods,this.total)
			this.getDefaulAddress();
			
		},
		methods: {
			/**
			 * 计算总金额
			 */
			valChange() {
				var total = parseFloat(this.$data.value * this.$data.good.price).toFixed(2);
				this.$data.total = total;
			},
			/**
			 * 选择地址
			 */
			to_adress(){
				uni.navigateTo({
					url:"/pages/choose_adress/choose_adress"
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
					if(res.data.code == 501){
						this.to_adress();
					}else{
						this.address = res.data.data;
					}
				})
			},
			/**
			 * 提交订单
			 */
			submit(){
				// console.log("submit",this.address,this.goods)
				let data=[];
				let goods = this.goods;
				let addressId = this.address.addressId;
				let submitedCart = [];
				for(let i=0;i<goods.length;i++){
					data.push({
						goodId:goods[i].good.goodId,
						buyerId:uni.getStorageSync("userId"),
						addressId:addressId,
						orderPrice:parseFloat(goods[i].goodNum*goods[i].good.goodPrice).toFixed(2),
						orderStatus:1,
						goodNum:goods[i].goodNum,
						goodChoice:goods[i].goodChoice,
						sellerId:goods[i].good.seller.userId,
						good:goods[i].good
					});
					submitedCart.push(goods[i].cartId);
				}
				console.log("submit",data,submitedCart);
				this.$u.post("/order/submit",data,this.$JsonHeader)
				.then(res=>{
					console.log("submit",res);
					if(res.data.code==201){
						//操作成功删除相关的cart
						this.$u.post("/cart/remove",submitedCart,this.$JsonHeader)
						.then(res=>{
							console.log("submit:removeCarts",res);
							this.$u.route({
								type:"back",
							})
						})
					}
				})
			}
		}
	}
</script>
<style>
	page{
		background-color: #EBEEF5;
	}
</style>
<style scoped lang="scss">
	$footer-height:100rpx;
	.confirm_order {
		// height: calc(100vh);
	}
	.footer{
		height: $footer-height;
		background-color: #FFFFFF
	}
	.empty{
		height: $footer-height;
	}
</style>
