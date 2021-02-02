<template>
	<view class="index">
		<!-- 导航栏 -->
		<u-navbar :is-back="false">
			<view class="search-wrap">
				<u-search :placeholder="placeholder" v-model="keyword" :show-action="false" input-align="center" border-color="#d4237a"></u-search>
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
					<view class="goods-warter" v-for="(item, index) in leftList" :key="index" @click="enter(item.id)">
						<!-- 警告：微信小程序中需要hx2.8.11版本才支持在template中结合其他组件，比如下方的lazy-load组件 -->
						<u-lazy-load threshold="-450" border-radius="10" :image="item.image" :index="index"></u-lazy-load>
						<view class="goods-title">
							{{item.title}}
						</view>
						<view class="goods-details">
							<view class="goods-price">
								{{item.price}}元
							</view>
							<view class="goods-paid-number">
								{{item.paid_number}}人付款
							</view>
						</view>
						<view class="goods-tag">
							<view class="goods-tag-plant">
								2000人种草
							</view>
						</view>
						<view class="goods-shop">
							{{item.shop}}
						</view>
					</view>
				</template>
				<template v-slot:right="{rightList}">
					<view class="goods-warter" v-for="(item, index) in rightList" :key="index" @click="enter(item.id)">
						<u-lazy-load threshold="-450" border-radius="10" :image="item.image" :index="index"></u-lazy-load>
						<view class="goods-title">
							{{item.title}}
						</view>
						<view class="goods-details">
							<view class="goods-price">
								{{item.price}}元
							</view>
							<view class="goods-paid-number">
								{{item.paid_number}}人付款
							</view>
						</view>

						<view class="goods-tag">
							<view class="goods-tag-plant">
								2000人种草
							</view>
						</view>
						<view class="goods-shop">
							{{item.shop}}
						</view>
					</view>
				</template>
			</u-waterfall>
			<u-loadmore bg-color="rgb(240, 240, 240)" :status="loadStatus" @loadmore="addRandomData"></u-loadmore>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
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
				list: [{
						price: 35,
						title: '北国风光，千里冰封，万里雪飘',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic.sc.chinaz.com/Files/pic/pic9/202002/zzpic23327_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 75,
						title: '望长城内外，惟余莽莽',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic.sc.chinaz.com/Files/pic/pic9/202002/zzpic23325_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 385,
						title: '大河上下，顿失滔滔',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2119_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 784,
						title: '欲与天公试比高',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/zzpic23369_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 7891,
						title: '须晴日，看红装素裹，分外妖娆',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2130_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 2341,
						shop: '李白杜甫白居易旗舰店',
						title: '江山如此多娇，引无数英雄竞折腰',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23346_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 661,
						shop: '李白杜甫白居易旗舰店',
						title: '惜秦皇汉武，略输文采',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23344_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 1654,
						title: '唐宗宋祖，稍逊风骚',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23343_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 1678,
						title: '一代天骄，成吉思汗',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23343_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 924,
						title: '只识弯弓射大雕',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23343_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
					{
						price: 8243,
						title: '俱往矣，数风流人物，还看今朝',
						shop: '李白杜甫白居易旗舰店',
						image: 'http://pic1.sc.chinaz.com/Files/pic/pic9/202002/zzpic23343_s.jpg',
						paid_number: 100,
						plant_number: 3214,
					},
				],
			}
		},
		onLoad() {
			this.addRandomData();
		},
		onReachBottom() {
			this.loadStatus = 'loading';
			// 模拟数据加载
			setTimeout(() => {
				this.addRandomData();
				this.loadStatus = 'loadmore';
			}, 1000)
		},

		methods: {
			addRandomData() {
				for (let i = 0; i < 10; i++) {
					let index = this.$u.random(0, this.list.length - 1);
					// 先转成字符串再转成对象，避免数组对象引用导致数据混乱
					let item = JSON.parse(JSON.stringify(this.list[index]))
					item.id = this.$u.guid();
					this.flowList.push(item);
				}
			},
			/**
			 * 单击瀑布流事件
			 */
			enter(id){
				console.log("点击了"+id)
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
