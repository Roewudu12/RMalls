<template>
	<view class="index">
		<!-- 导航栏 -->
		<u-navbar :is-back="false">
			<view class="search-wrap">
				<u-search :placeholder="placeholder" v-model="keyword" :show-action="false" input-align="center"
					border-color="#d4237a"></u-search>
			</view>
		</u-navbar>
		<!-- 轮播图 -->
		<view class="show-pic-wrap">
			<u-swiper :list="show_pic" height="280rpx" :title="true" :effect3d="true"></u-swiper>
		</view>
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
			<u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus"></u-loadmore>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//当前页码
				page: 1,
				//搜索框
				placeholder: "请输入商品",
				keyword: '',
				//轮播图展示   
				show_pic: [{
						image: 'https://cdn.uviewui.com/uview/swiper/1.jpg',
						title: '昨夜星辰昨夜风，画楼西畔桂堂东'
					},
					{
						image: 'https://cdn.uviewui.com/uview/swiper/2.jpg',
						title: '身无彩凤双飞翼，心有灵犀一点通'
					},
					{
						image: 'https://cdn.uviewui.com/uview/swiper/3.jpg',
						title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
					}
				],
				//瀑布流数据
				loadStatus: 'loadmore',
				flowList: [],

			}
		},
		onLoad() {
			console.log("/pages/index/index.vue");
			// this.checkLogin();
			// this.getGoods();
			
		},
		onShow() {
			this.refreshGoods();
		},
		//触底加载更多
		onReachBottom() {
			this.loadStatus = 'loading';
			// 模拟数据加载
			setTimeout(() => {
				this.getGoods();
				this.loadStatus = 'loadmore';
			}, 1000)
		},

		methods: {
			//刷新商品页面
			refreshGoods(){
				console.log("refreshGoods");
				this.$refs.uWaterfall.clear();
				this.page = 1;
				this.getGoods();
			},
			//获取Page页的商品
			getGoods() {
				this.$u.post('/goods/showGood/goods', {
					page: this.page,
					userAuthority:2, 
					userId:uni.getStorageSync("userId")
				}).then(res => {
					console.log("getGoods", res)
					if (res.data.data.length > 0) {
						if(this.page==1){
							this.flowList = res.data.data;
						}else{
							this.flowList = this.flowList.concat(res.data.data);
						}
						
						this.page++;
					} else {
						this.loadStatus = "nomore";
					}
				})
			},
			/**
			 * 单击瀑布流事件
			 */
			enter(id) {
				console.log("点击了" + id)
				this.$u.route('/pages/shop_details/shop_details', {
					id: id,
				});
			}
		}
	}
</script>
<style>
	/* page不能写带scope的style标签中，否则无效 */
	page {
		background-color: rgb(240, 240, 240);
	}
</style>
<style scoped lang="scss">
	.search-wrap {
		margin: 0 20rpx;
		flex: 1;
	}

	.show-pic-wrap {
		padding: 10rpx;
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
</style>
