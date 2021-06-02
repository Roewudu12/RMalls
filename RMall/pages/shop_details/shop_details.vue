<template>
	<!-- 商品详情页面 -->
	<view class="shop_details my_backgroundColor_gray">
		<u-navbar :background="background" :is-back="true">
			<view class="search-wrap">
				<u-search :placeholder="placeholder" v-model="keyword" :show-action="false" input-align="center"
					border-color="#d4237a"></u-search>
			</view>
			<view class="u-margin-right-20" slot="right">
				<u-icon size="30" name="share"></u-icon>
			</view>
		</u-navbar>
		<!-- 头部 -->
		<view class="head my_row_flex u-row-around my_text_col_center" v-show="show_head">
			<view>
				<view class="my_font_color_title u-font-lg">宝贝</view>
				<u-line :hair-line="false" color="#d3217b"></u-line>
			</view>
			<view>
				<view class="my_font_color_title u-font-lg">评价</view>
				<u-line :hair-line="false" color="#d3217b"></u-line>
			</view>
			<view>
				<view class="my_font_color_title u-font-lg">详情</view>
				<u-line :hair-line="false" color="#d3217b"></u-line>
			</view>
			<view>
				<view class="my_font_color_title u-font-lg">推荐</view>
				<u-line :hair-line="false" color="#d3217b"></u-line>
			</view>
		</view>

		<!-- 商品展示轮播图 -->
		<view class="shop_images_wrap">
			<u-swiper mode="number" height="750" :autoplay="false" indicator-pos="bottomRight" :circular="false"
				:list="shop_images" name="imageHttp" @click="imgMap(shop_images,index)"></u-swiper>
		</view>
		<!-- 价格显示页面 -->
		<u-card padding="15" :border="false" :head-border-bottom="false" margin="15rpx">
			<!-- 价格 -->
			<view class="price_wrap my_row_flex" slot="head">
				<view class="price my_font_weight my_font_color_price u-font-xl">
					￥{{good.goodPrice}}
				</view>
			</view>
			<!-- 介绍 -->
			<view class="title my_font_weight my_font_color_title u-font-lg" slot="body">
				{{good.goodName}}
			</view>
			<!-- 其他 -->
			<view class="other u-row-around my_row_flex" slot="foot">
				<view>
					<u-icon name="heart"></u-icon>推荐
				</view>
				<view>
					<u-icon name="share"></u-icon>分享
				</view>
			</view>
		</u-card>
		<!-- 商品尺码等信息选择页面 -->
		<u-card padding="15" :border="false" :head-border-bottom="false" margin="15rpx">
			<view slot="body">
				<view class="my_row_flex" @click="show_popup=true">
					<view class="my_font_color_info">
						选择
					</view>
					<view class="u-margin-left-20">
						<view class="u-margin-left-5 my_font_color_title">{{choose_title}}</view>
						<view class="my_row_flex">
							<view class="my_row_flex">
								<u-image class="u-margin-5" :src="item.image" height="60" width="60" v-if="index<3"
									v-for="(item,index) in colors_images" :key="index"></u-image>
							</view>
							<view
								class="u-font-25 my_backgroundColor_gray u-margin-5 my_circle_radius my_text_col_center u-padding-left-20 u-padding-right-20">
								共{{choose_good_info_num}}种{{choose_title}}可选</view>
						</view>
					</view>
					<view class="my_right_elements">
						<u-icon name="arrow-right"></u-icon>
					</view>
				</view>

				<view class="my_row_flex u-margin-top-20">
					<view class="my_font_color_info">
						发货
					</view>
					<view class="u-margin-left-25">
						<view class="my_row_flex">
							<view class="my_font_color_title">{{sender_address}}</view>
							<view class="my_font_color_info u-margin-left-20 u-margin-right-20">|</view>
							<view class="my_font_color_title">快递:{{transporter}}</view>
						</view>
						<view class="my_font_color_info u-font-20">
							配送至:{{receiver_address}}
						</view>
					</view>
					<view class="my_right_elements">
						<u-icon name="arrow-right"></u-icon>
					</view>
				</view>
			</view>
		</u-card>

		<!-- 评价页面信息 -->
		<u-card padding="15" :border="false" :head-border-bottom="false" margin="15rpx">
			<view slot="head" @click="to_comments" class="my_row_flex">
				<view class="my_font_color_title">
					宝贝评价({{content_num}})
				</view>
				<view class="my_right_elements my_row_flex my_font_color_main">
					<view class="u-font-25 u-margin-right-15 my_text_col_center">查看全部</view>
					<u-icon name="arrow-right" size="20"></u-icon>
				</view>
			</view>
			<view class="" slot="body">
				<view class="u-margin-bottom-30" v-for="(item,index) in content_part" :key="index">
					<view class="my_row_flex">
						<u-image class="my_text_col_center" :src="item.user_icon" width="50" height="50" shape="circle">
						</u-image>
						<view class="u-margin-left-25">
							<view class="my_font_color_title u-font-26">{{item.user_name}}</view>
							<view class="my_font_color_info u-font-20">{{item.content_time}}</view>
						</view>
					</view>
					<view class="u-margin-top-10 u-font-26">
						{{item.content}}
					</view>
				</view>
			</view>
		</u-card>

		<!-- 商家展示页面 -->
		<u-card padding="15" :border="false" :head-border-bottom="false" :foot-border-top="false" margin="15rpx">
			<view slot="body">
				<view class="my_row_flex">
					<u-image :src="merchant_image" height="100" width="100"></u-image>
					<view class="u-margin-left-20">
						<view class="my_font_color_title ">{{good.seller.username}}</view>
						<view class="my_row_flex u-margin-top-10">
							<u-icon name="integral-fill" color="#d3217b" v-for="item in grade" :key="item"></u-icon>
						</view>
					</view>
					<view class="my_right_elements my_row_flex">
						<u-button plain type="warning" size="mini" shape="circle">全部宝贝</u-button>
						<u-button type="warning" class="u-margin-left-10" size="mini" shape="circle">进店逛逛</u-button>
					</view>
				</view>
			</view>
		</u-card>

		<u-divider :bg-color="background.backgroundColor">宝贝详情</u-divider>
		<!-- 商品详情页面 -->
		<view>
			<u-image :src="item.imageHttp" v-for="(item,index) in details_image" :key="index" mode="widthFix"
				@click="imgMap(details_image,index)"></u-image>
		</view>
		<u-divider :bg-color="background.backgroundColor" border-color="#d3217b" color="#d3217b">看了又看</u-divider>

		<!-- 商品推荐流 -->
		<!-- 瀑布推荐流 -->
		<view class="waterfull-wrap">
			<u-waterfall v-model="flowList" ref="uWaterfall">
				<template v-slot:left="{leftList}">
					<view class="goods-warter" v-for="(item, index) in leftList" :key="index"
						@click="enter(item.goodId)">
						<!-- 警告：微信小程序中需要hx2.8.11版本才支持在template中结合其他组件，比如下方的lazy-load组件 -->
						<u-lazy-load threshold="-450" border-radius="10" :image="item.detailImages[0].imageHttp"
							:index="index">
						</u-lazy-load>
						<view class="goods-title">
							{{item.goodName}}
						</view>
						<view class="goods-details">
							<view class="goods-price">
								{{item.goodPrice}}元
							</view>
							<view class="goods-paid-number">
								{{item.goodSoldNum?item.goodSoldNum:0}}人付款
							</view>
						</view>
						<view class="goods-tag">
							<view class="goods-tag-plant" v-if="item.goodRecommend">
								{{item.goodRecommend}}人种草
							</view>
						</view>
						<view class="goods-shop">
							{{item.seller.username}}
						</view>
					</view>
				</template>
				<template v-slot:right="{rightList}">
					<view class="goods-warter" v-for="(item, index) in rightList" :key="index"
						@click="enter(item.goodId)">
						<u-lazy-load threshold="-450" border-radius="10" :image="item.detailImages[0].imageHttp"
							:index="index">
						</u-lazy-load>
						<view class="goods-title">
							{{item.goodName}}
						</view>
						<view class="goods-details">
							<view class="goods-price">
								{{item.goodPrice}}元
							</view>
							<view class="goods-paid-number">
								{{item.goodSoldNum?item.goodSoldNum:0}}人付款
							</view>
						</view>

						<view class="goods-tag" v-if="item.goodRecommend">
							<view class="goods-tag-plant">
								{{item.goodRecommend}}人种草
							</view>
						</view>
						<view class="goods-shop">
							{{item.seller.username}}
						</view>
					</view>
				</template>
			</u-waterfall>
			<!-- <u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus"></u-loadmore> -->
		</view>
		<view class='placeholder-bottom-view'></view>
		<!-- 底部购物篮 -->
		<view class="navigation">
			<view class="left">
				<view class="item">
					<u-icon name="server-fill" :size="40" :color="$u.color['contentColor']"></u-icon>
					<view class="text u-line-1">客服</view>
				</view>
				<view class="item">
					<u-icon name="home" :size="40" :color="$u.color['contentColor']"></u-icon>
					<view class="text u-line-1">店铺</view>
				</view>
				<view class="item car" @click="to_shoppingCart">
					<u-badge class="car-num" :count="car_count" type="error" :offset="[-3, -6]"></u-badge>
					<u-icon name="shopping-cart" :size="40" :color="$u.color['contentColor']"></u-icon>
					<view class="text u-line-1">购物车</view>
				</view>
			</view>
			<view class="right">
				<view class="cart btn u-line-1" @click="add_cart=true;show_popup=true">加入购物车</view>
				<view class="buy btn u-line-1" @click="order=true;show_popup=true">立即购买</view>
			</view>
		</view>

		<!-- 分类尺码弹出层 -->
		<u-popup @close="popup_close" length="80%" v-model="show_popup" mode="bottom" :closeable="true">
			<!-- 商品图片等信息 -->
			<view class="popup_head my_row_flex u-padding-15">
				<u-image height="130rpx" width="130rpx" :src="shop_images[0].imageHttp" :lazy-load="true"
					:show-loading="true"></u-image>
				<view class="goods_price u-padding-top-35 u-margin-left-30 u-font-xl my_font_color_price">
					￥{{good.goodPrice}}</view>
			</view>
			<!-- 点击去评论区 -->
			<view @click="toContents" class="to_contents u-padding-15 u-margin-20 my_circle_radius my_row_flex">
				<view class="my_row_flex">
					<u-icon name="chat"></u-icon>
					<view class="u-font-sm u-margin-left-15">有999个人评价"还不错"</view>
				</view>
				<view class="right_content my_row_flex my_right_elements">
					<view class="u-font-sm">全部({{content_num}})</view>
					<u-icon name="arrow-right-double"></u-icon>
				</view>
			</view>
			<u-line color="info"></u-line>
			<!-- 商品选项 -->
			<view class="popup_body u-padding-10">
				<view v-for="(choice,idx) in choices" :key="idx">
					<view class="popup_body_title">
						{{choice.title}}
					</view>
					<view class="u-flex u-row-left u-flex-wrap u-margin-top-20">
						<u-button @click="checked(idx,tidx)" :key="tidx" v-for="(type,tidx) in choice.types"
							:type="type.checked?'warning':''" size="mini" class="u-padding-10 u-margin-10 u-font-sm"
							:plain="true">
							{{type.name}}
						</u-button>

					</view>

				</view>

			</view>
			<view class="popup_foot_null"></view>
			<view class="popup_footer u-padding-15">
				<view v-if="!add_cart && !order" class="my_row_flex" style="width: 100%;">
					<view class="button_left" @click="addToCart">加入购物车</view>
					<view class="button_right" @click="toOrder">立即购买</view>
				</view>
				<view v-else style="width: 100%;">
					<u-button class="button" type="warning" :ripple="true" shape="circle"
						@click="add_cart?addToCart():toOrder()">确定</u-button>
				</view>
			</view>
		</u-popup>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				//商品信息
				good: {},
				//当前推荐瀑布流分页
				page: 1,
				//搜索框
				placeholder: "请输入商品",
				keyword: '',
				choose_title: "颜色分类/尺码",
				choose_good_info_num: 3,
				sender_address: "福建泉州",
				receiver_address: "成都市 双流县 西航港街道",
				transporter: "免运费",
				background: {
					backgroundColor: "#EBEEF5",
				},
				show_popup: false, //单纯选择
				add_cart: false, //加入购物车
				order: false, //下单购买
				choices: [], //商品购买选项
				selected: '请选择', //已选商品选择项拼接字符串
				popup_body_title: '颜色分类',
				car_count: 5,
				show_head: false,
				content_num: 1234,
				buyer_show: 123,
				grade: 5,
				merchant_image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
				// 商品图片
				shop_images: [],
				//商品颜色分类图片
				colors_images: [],
				// 一部分评论
				content_part: [],
				//商品详情图片
				details_image: [],
				/**
				 * 瀑布流
				 */
				flowList: [],

			}
		},
		onLoad(e) {
			console.log("pages/shop_details/shop_details.vue", e);
			this.getGoodDetails(e.id);
		},

		methods: {

			//获取商品详情信息
			getGoodDetails(id) {
				this.$u.post("/goods/showGood/byId", {
					id: id
				}).then(res => {
					console.log("getGoodsDetails", res.data);
					this.good = res.data.data;
					//分配图片
					for (let i = 0; i < this.good.detailImages.length; i++) {
						let item = this.good.detailImages[i];
						if (item.type == 1) {
							this.shop_images.push(item);
						} else {
							this.details_image.push(item);
						}
					}
					this.getGoodsByCate();
					this.getGoodChoices();
				})

			},
			//获取与该页面商品相关的商品
			getGoodsByCate() {
				this.$u.post('/goods/showGood/byCate', {
					"page": this.page,
					"cateId": this.good.cate.cateId
				}).then(res => {
					console.log("getGoodsByCate", res.data);
					this.flowList = res.data.data;

				})
			},
			//获取商品选项
			getGoodChoices() {
				this.$u.post('/choice/byGoodId', {
					"goodId": this.good.goodId
				}).then(res => {
					console.log("getGoodChoices", res);
					let choices = res.data.data;
					for (let i = 0; i < choices.length; i++) {
						let choice = choices[i];
						let typesList = choice.types.split('-');
						let types = [];
						for (let j = 0; j < typesList.length; j++) {
							types.push({
								name: typesList[j],
								checked: false
							})
						}
						this.choices.push({
							title: choice.title,
							types: types
						})
					}
				})
			},
			//用户选择商品选项
			checked(idx, tidx) {
				let name = this.choices[idx].types[tidx].name
				this.choices[idx].types.map(type => {
					if (type.name != name) type.checked = false;
				});
				this.choices[idx].types[tidx].checked = true;

			},
			/**
			 * 单击瀑布流事件
			 */
			enter(id) {
				console.log("点击了" + id);
				uni.navigateTo({
					url: '/pages/shop_details/shop_details?id=' + id,
				})
			},
			/**
			 * @param {Object} images
			 * @param {Object} index
			 * 大图查看图片
			 */
			imgMap(images, index) {
				//将json转为数组
				var my_urls = [];
				for (var i = 0; i < images.length; i++) {
					my_urls.push(images[i].imageHttp)
				}

				uni.previewImage({
					current: index,
					urls: my_urls,
				})
			},
			/**
			 * 进入评论详情
			 * 
			 */
			to_comments() {
				uni.navigateTo({
					url: '/pages/shop_details/comments/comments'
				});
			},
			//加入购物车
			addToCart() {
				this.computeChoices();
				console.log("addToCart", this.selected);
				this.$u.post("/cart/insert", {
					buyerId:uni.getStorageSync("userId"),
					goodId:this.good.goodId,
					goodNum:1,
					goodChoice:this.selected
				},{'content-type': 'application/json'})
				.then(res=>{
					console.log("addToCart",res);
					this.popup_close();
					uni.showToast({
						title:"已加入购物车！",
						duration:2000,
						icon:"none"
					})
				})
				
			},
			//立即购买
			toOrder() {
				this.computeChoices();
				console.log("toOrder",this.good,this.selected)
				let goods = [];
				let good = {
					good:this.good,
					goodNum : 1,
					goodChoice:this.selected
				};
				this.popup_close();
				goods.push(good)
			
				this.$u.route("/pages/confirm_order/confirm_order",{
					goods:JSON.stringify(goods),
					total:good.good.goodPrice
				})
				
			},
			//计算选择项
			computeChoices() {
				let choices = this.choices;
				let selected = '';
				for (let i = 0; i < choices.length; i++) {
					let types = choices[i].types;
					let isAll = false;
					for (let j = 0; j < types.length; j++) {
						let choice = types[j]
						if (choice.checked) {
							isAll = true;
							if (i != choices.length - 1) {
								selected += choice.name + '/';
							} else {
								selected += choice.name;
							}
						}
					}
					if (!isAll) {
						uni.showToast({
							title: "请选择完全",
							duration: 2000,
							icon: "none",
						})
						return false;
					}
				}
				this.selected = selected;
			},
			/**
			 * 关闭弹窗
			 */
			popup_close() {
				this.show_popup = false;
				this.add_cart = false;
				this.order = false;
			}
		}
	}
</script>

<style scoped lang="scss">
	$footer_height:100rpx;

	.head {
		height: 60rpx;
	}

	.placeholder-bottom-view {
		height: $footer_height;
	}

	.search-wrap {
		margin: 0 20rpx;
		flex: 1;
	}

	// 底部导航栏
	.navigation {
		display: flex;
		position: fixed;
		bottom: 0upx;
		margin-top: 100rpx;
		background-color: #ffffff;
		padding: 16rpx 0;
		width: 100%;
		z-index: 5;
		height: $footer_height;

		.left {
			display: flex;
			font-size: 20rpx;

			.item {
				margin: 0 30rpx;

				&.car {
					text-align: center;
					position: relative;

					.car-num {
						position: absolute;
						top: -10rpx;
						right: -10rpx;
					}
				}
			}
		}

		.right {
			display: flex;
			font-size: 28rpx;
			align-items: center;

			.btn {
				line-height: 66rpx;
				padding: 0 30rpx;
				border-radius: 36rpx;
				color: #ffffff;
			}

			.cart {
				background-color: #ed3f14;
				margin-right: 30rpx;
			}

			.buy {
				background-color: #ff7900;
			}
		}
	}

	/**
	 * 瀑布流css
	 */
	.goods-warter {
		border-radius: 8rpx;
		margin: 5rpx;
		background-color: #ffffff;
		padding: 8rpx;
		position: relative;
	}

	.norms {
		background-color: #eeeeee;
	}

	.u-close {
		position: absolute;
		top: 32rpx;
		right: 32rpx;
	}

	.goods-image {
		width: 100%;
		border-radius: 4rpx;
	}

	.goods-title {
		font-size: 30rpx;
		margin-top: 5rpx;
		color: $u-main-color;
	}

	.goods-tag {
		display: flex;
		margin-top: 5rpx;
	}

	.goods-tag-plant {
		background-color: $u-type-error;
		color: #FFFFFF;
		display: flex;
		align-items: center;
		padding: 4rpx 14rpx;
		border-radius: 50rpx;
		font-size: 20rpx;
		line-height: 1;
	}

	.goods-tag-text {
		border: 1px solid $u-type-primary;
		color: $u-type-primary;
		margin-left: 10px;
		border-radius: 50rpx;
		line-height: 1;
		padding: 4rpx 14rpx;
		display: flex;
		align-items: center;
		border-radius: 50rpx;
		font-size: 20rpx;
	}

	.popup_footer {
		display: flex;
		position: fixed;
		bottom: var(--window-bottom);
		width: 100%;

		.button {
			width: 100%;
		}

		.button_left {
			width: 50%;
			height: 60rpx;
			background-color: #ffc903;
			border-top-left-radius: 35rpx;
			border-bottom-left-radius: 35rpx;
			text-align: center;
			font-weight: 600;
			color: #FFFFFF;
			line-height: 60rpx;

		}

		.button_right {
			width: 50%;
			height: 60rpx;
			background-color: #ff9500;
			border-top-right-radius: 35rpx;
			border-bottom-right-radius: 35rpx;
			text-align: center;
			font-weight: 600;
			color: #FFFFFF;
			line-height: 60rpx;
		}
	}

	.goods-details {
		display: flex;
		flex-direction: row;
	}

	.goods-price {
		font-size: 30rpx;
		color: $u-type-error;
		margin-top: 5rpx;
	}

	.goods-paid-number {
		font-size: 22rpx;
		color: $u-tips-color;
		margin-top: 15rpx;
		margin-left: 10rpx;
	}

	.goods-shop {
		font-size: 22rpx;
		color: $u-tips-color;
		margin-top: 5rpx;
	}

	.to_contents {
		background-color: $u-type-info-disabled;
	}
</style>
