<template>
	<view>
		<view class="wrap">
			<u-navbar :is-back="true" title="我的订单"></u-navbar>
			<view class="u-tabs-box">
				<u-tabs-swiper activeColor="#f29100" ref="tabs" :list="list" :current="current" @change="change"
					:is-scroll="false" swiperWidth="750"></u-tabs-swiper>
			</view>
			<swiper class="swiper-box" :current="swiperCurrent" @transition="transition"
				@animationfinish="animationfinish">
				<!-- 待付款 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(order, index) in orderList[0]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
									<view class="logistics btn" @click="to_cancle(current,index)">取消订单</view>
									<view class="evaluate btn" @click="toPay(index)">去付款</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[0].length>0" :status="loadStatus[0]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<!-- 待发货 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(order, index) in  orderList[1]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
									<view class="logistics btn" @click="to_cancle(current,index)">取消订单</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[1].length>0" :status="loadStatus[1]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<!-- 待收货 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;">
						<view class="page-box">
							<view class="order" v-for="(order, index) in  orderList[2]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
									<view class="logistics btn" @click="to_cancle(current,index)">取消订单</view>
									<view class="logistics btn">查看物流</view>
									<view class="evaluate btn" @click="recieved(index)">已收货</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[2].length>0" :status="loadStatus[2]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<!-- 待评价 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(order, index) in  orderList[3]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
									<view class="logistics btn" @click="to_cancle(current,index)">取消订单</view>
									<view class="evaluate btn" @click="to_comment(index)">评价</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[3].length>0" :status="loadStatus[3]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<!-- 已完成 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(order, index) in  orderList[4]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
									<view class="logistics btn" @click="to_cancle(current,index)">取消订单</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[4].length>0" :status="loadStatus[4]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<!-- 退货及售后 -->
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<view class="page-box">
							<view class="order" v-for="(order, index) in  orderList[5]" :key="index">
								<view class="top">
									<view class="left">
										<u-icon name="home" :size="30" color="rgb(94,94,94)"></u-icon>
										<view class="store">{{ order.good.seller.username }}</view>
										<u-icon name="arrow-right" color="rgb(203,203,203)" :size="26"></u-icon>
									</view>
								</view>
								<view class="item">
									<view class="left">
										<image :src="order.good.detailImages[0].imageHttp" mode="aspectFill"></image>
									</view>
									<view class="content">
										<view class="title u-line-2">{{ order.good.goodName }}</view>
										<view class="type">{{ order.goodChoice }}</view>
									</view>
									<view class="right">
										<view class="price">
											￥{{ order.good.goodPrice }}
										</view>
										<view class="number">x{{ order.goodNum }}</view>
									</view>
								</view>
								<view class="total">
									共{{ order.goodNum  }}件商品 合计:
									<text class="total-price">
										￥{{order.orderPrice}}
									</text>
								</view>
								<view class="bottom">
									<view class="more">
										<u-icon name="more-dot-fill" color="rgb(203,203,203)"></u-icon>
									</view>
								</view>
							</view>
							<u-loadmore v-if="orderList[5].length>0" :status="loadStatus[5]" bgColor="#f2f2f2"></u-loadmore>
							<view v-else>
								<view class="centre">
									<image src="https://cdn.uviewui.com/uview/template/taobao-order.png" mode="">
									</image>
									<view class="explain">
										您还没有相关的订单
										<view class="tips">可以去看看有那些想买的</view>
									</view>
									<view class="btn">随便逛逛</view>
								</view>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderList: [
					[],
					[],
					[],
					[]
				],
				pages: [1, 1, 1, 1,1,1],
				list: [{
						name: '待付款'
					},
					{
						name: '待发货'
					},
					{
						name: '待收货'
					},
					{
						name: '待评价',
						// count: 12
					},
					{
						name: '已完成',
						// count: 12
					},
					{
						name: '退货/售后',
						// count: 12
					}
				],
				current: 0,
				swiperCurrent: 0,
				tabsHeight: 0,
				dx: 0,
				loadStatus: ['loadmore', 'loadmore', 'loadmore', 'loadmore','loadmore','loadmore'],
			};
		},
		onLoad(e) {
			// this.getOrderList(0);
			// this.getOrderList(1);
			// this.getOrderList(2);
			// this.getOrderList(3);
			// if(e.current){
			// 	this.change(e.current)
			// }
		},
		onShow() {
			this.refreshOrderList(0);
			this.refreshOrderList(1);
			this.refreshOrderList(2);
			this.refreshOrderList(3);
			this.refreshOrderList(4);
			this.refreshOrderList(5);
		},
		computed: {

		},
		methods: {
			reachBottom() {
				// 此tab为空数据
				if (this.current != 2) {
					this.loadStatus.splice(this.current, 1, "loading")
					setTimeout(() => {
						this.getOrderList(this.current);
					}, 1200);
				}
			},
			// 页面数据
			getOrderList(idx) {
				let status = parseInt(idx) + 1;
				console.log(this.pages[idx])
				this.$u.post("/order/show/status", {
					userId: uni.getStorageSync("userId"),
					status: status,
					page: this.pages[idx],
				}).then(res => {
					// console.log("getOrderList",status,res)

					let orderLists = res.data.data;
					if (orderLists.length != 0) {
						for (let i = 0; i < orderLists.length; i++) {
							if (orderLists[i].good.$ref) {
								let index = orderLists[i].good.$ref.split("[")[1].split(']')[0];
								orderLists[i].good = orderLists[index].good
							}
						}
						if(this.pages[idx]==1){
							this.orderList[idx] =orderLists;
						}else{
							this.orderList[idx] = this.orderList[idx].concat(orderLists);
						}
						
						this.loadStatus.splice(this.current, 1, "loadmore")
						this.pages[idx] = this.pages[idx] + 1;
					}else if(this.pages[idx]==1){
						console.log("刷新页面")
						this.orderList[idx]=[];
					}else{
						this.loadStatus.splice(this.current, 1, "nomore")
					}
					console.log("getOrderList", status, this.orderList[idx])
				})
			},
			/**
			 * 刷新页面
			 */
			refreshOrderList(idx){
				this.pages[idx] = 1;
				this.getOrderList(idx);
			},
			// tab栏切换
			change(index) {
				this.swiperCurrent = index;
				this.pages[index] = 1;
				this.refreshOrderList(index);
			},
			transition({
				detail: {
					dx
				}
			}) {
				this.$refs.tabs.setDx(dx);
			},
			animationfinish({
				detail: {
					current
				}
			}) {
				this.$refs.tabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},
			/**
			 * 去付款
			 */
			toPay(index){ 
				let data = this.orderList[0][index];
				data.orderStatus = 2;
				this.$u.post("/order/update/pay",data,this.$JsonHeader)
				.then(res=>{
					console.log("toPay",res);
					uni.showToast({
						title:"付款成功"
					})
					this.refreshOrderList(0);
					this.refreshOrderList(1);
				})
			},
			/**
			 * 已收货
			 */
			recieved(index){
				let data = this.orderList[2][index];
				data.orderStatus = 4;
				this.$u.post("/order/update",data,this.$JsonHeader)
				.then(res=>{
					console.log("recieved",res);
					uni.showToast({
						title:"订单完成"
					})
					this.refreshOrderList(2);
					this.refreshOrderList(3);
				})
			},
			/**
			 * 去评价
			 */
			to_comment(index){
				let data = this.orderList[3][index];
				this.$u.route("/pages/to_comment/to_comment",{
					order:JSON.stringify(data)
				})
			},
			/**
			 * 取消订单
			 */
			to_cancle(current,index){
				let data = this.orderList[current][index];
				this.$u.post("/order/cancel",data,this.$JsonHeader)
				.then(res=>{
					console.log("to_cancel",res);
					uni.showToast({
						title:"成功"
					})
					this.refreshOrderList(current);
					this.refreshOrderList(5);
				})
			}
			
		}
	};
</script>

<style>
	/* #ifndef H5 */
	page {
		height: 100%;
		background-color: #f2f2f2;
	}

	/* #endif */
</style>

<style lang="scss" scoped>
	.order {
		width: 710rpx;
		background-color: #ffffff;
		margin: 20rpx auto;
		border-radius: 20rpx;
		box-sizing: border-box;
		padding: 20rpx;
		font-size: 28rpx;

		.top {
			display: flex;
			justify-content: space-between;

			.left {
				display: flex;
				align-items: center;

				.store {
					margin: 0 10rpx;
					font-size: 32rpx;
					font-weight: bold;
				}
			}

			.right {
				color: $u-type-warning-dark;
			}
		}

		.item {
			display: flex;
			margin: 20rpx 0 0;

			.left {
				margin-right: 20rpx;

				image {
					width: 200rpx;
					height: 200rpx;
					border-radius: 10rpx;
				}
			}

			.content {
				.title {
					font-size: 28rpx;
					line-height: 50rpx;
				}

				.type {
					margin: 10rpx 0;
					font-size: 24rpx;
					color: $u-tips-color;
				}

				.delivery-time {
					color: #e5d001;
					font-size: 24rpx;
				}
			}

			.right {
				margin-left: 10rpx;
				padding-top: 20rpx;
				text-align: right;

				.decimal {
					font-size: 24rpx;
					margin-top: 4rpx;
				}

				.number {
					color: $u-tips-color;
					font-size: 24rpx;
				}
			}
		}

		.total {
			margin-top: 20rpx;
			text-align: right;
			font-size: 24rpx;

			.total-price {
				font-size: 32rpx;
			}
		}

		.bottom {
			display: flex;
			margin-top: 40rpx;
			padding: 0 10rpx;
			justify-content: space-between;
			align-items: center;

			.btn {
				line-height: 52rpx;
				width: 160rpx;
				border-radius: 26rpx;
				border: 2rpx solid $u-border-color;
				font-size: 26rpx;
				text-align: center;
				color: $u-type-info-dark;
			}

			.evaluate {
				color: $u-type-warning-dark;
				border-color: $u-type-warning-dark;
			}
		}
	}

	.centre {
		text-align: center;
		margin: 200rpx auto;
		font-size: 32rpx;

		image {
			width: 164rpx;
			height: 164rpx;
			border-radius: 50%;
			margin-bottom: 20rpx;
		}

		.tips {
			font-size: 24rpx;
			color: #999999;
			margin-top: 20rpx;
		}

		.btn {
			margin: 80rpx auto;
			width: 200rpx;
			border-radius: 32rpx;
			line-height: 64rpx;
			color: #ffffff;
			font-size: 26rpx;
			background: linear-gradient(270deg, rgba(249, 116, 90, 1) 0%, rgba(255, 158, 1, 1) 100%);
		}
	}

	.wrap {
		display: flex;
		flex-direction: column;
		height: calc(100vh - var(--window-top));
		width: 100%;
	}

	.swiper-box {
		flex: 1;
	}

	.swiper-item {
		height: 100%;
	}
</style>
