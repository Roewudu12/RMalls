<template>
	<view>
		<u-navbar title="我的收藏"></u-navbar>
		<!-- 商品列表 -->
		<view class="list" :key="index" v-for="(item,index) in favorites">
			<u-card>
				<view slot="head" class="card_head">
					<view class="card_title title">
						{{item.good.seller.username}}
					</view>
				</view>
				<view slot="body" class="card_body">
					<view class="card_body_goods my_row_flex">
						<view class="goods_u_image">
							<u-image width="150rpx" height="150rpx" :src="item.good.detailImages[0].imageHttp">
							</u-image>
						</view>
						<view class="goods_details">
							<view class="goods_details_title">{{item.good.goodName}}</view>
							<view class="goods_price_num">
								<view class="goods_price">￥{{item.good.goodPrice}}</view>
							</view>
							<view class="u-font-sm my_font_color_info">{{ $u.timeFormat(item.createTime, 'yyyy年mm月dd日 hh时MM分ss秒') }}</view>
						</view>
					</view>
				</view>
			</u-card>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				favorites: [],
				page: 1,
				pageNum: 10,
			}
		},
		onLoad() {
			this.getFavorites();
		},
		methods: {
			getFavorites() {
				this.$u.post("/favorite/show", {
					userId: uni.getStorageSync("userId"),
					page: this.page,
					pageNum: this.pageNum
				}).then(res => {
					console.log("getFavorites", res);
					if(res.data.data.length!=0){
						this.favorites = res.data.data;
						this.page++;
					}
				})
			}
		}
	}
</script>

<style scoped lang="scss">
	.goods_price_num {
		display: flex;
		flex-direction: row;
		margin-top: 15rpx;
		width: 100%;
	}

	.goods_price {
		color: #ff0000;
		font-weight: 700;
	}
	.goods_details {
		margin-left: 20rpx;
		display: flex;
		flex-direction: column;
		flex: 1;
	}
</style>
