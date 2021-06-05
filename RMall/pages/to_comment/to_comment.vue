<template>
	<view>
		<u-navbar back-text="发表评价">
			<view slot="right">
				<u-button size="mini" shape="circle" type="error" @click="publish">发布</u-button>
			</view>
		</u-navbar>
		
		<!-- 商品信息 -->
		<u-card>
			<view class="good_info my_row_flex" slot="head">
				<view class="image">
					<u-image width="100" height="100" :src="order.good.detailImages[0].imageHttp"></u-image>
				</view>
				<view class="intro">
					<view class="name u-line-1 my_font_color_title u-font-md">{{order.good.goodName}}</view>
					<view class="choice u-line-1 my_font_color_info u-font-sm">{{order.goodChoice}}</view>
				</view>
			</view>
			<view class="good_content" slot="body">
				<view class="star my_row_flex">
					<view class="u-margin-right-30">描述相符</view>
					<u-rate v-model="star"></u-rate>
					<view class="u-margin-left-30">{{description[star-1]}}</view>
				</view>
				<view class="content u-margin-top-20" style="width: 100%;">
					<view class="u-margin-top-20">
						<u-input v-model="content" type="textarea" :placeholder="placeholder" :border="true" height="300" :auto-height="true" />
					</view>
				</view>
			</view>
		</u-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				order:{},//商品信息
				star:1,
				description:["非常差","差","一般","好","非常好"],
				content:"",
				placeholder:"从多个角度评价宝贝，↵可以帮助更多想买的人",
				types:['info','info'],
				
			}
		},
		onLoad(e) {
			console.log("/pages/to_comment/to_comment");
			this.order = JSON.parse(e.order);
		},
		methods: {
			/**
			 * 发布
			 */
			publish(){
				let data={
					userId:uni.getStorageSync("userId"),
					goodId:this.order.goodId,
					content:this.content,
					star:this.star
				}
				console.log("publish",data)
				this.$u.post("/comment/publish",data,this.$JsonHeader)
				.then(res=>{
					console.log("publish",res);
					this.updateOrderStatus();
				})
			},
			/**
			 * 评价成功后更新订单状态
			 */
			updateOrderStatus(){
				let data = this.order;
				data.orderStatus=5;
				this.$u.post("/order/update",data,this.$JsonHeader)
				.then(res=>{
					console.log("updateOrderStatus",res);
					this.back();
				})
			},
			/**
			 * 返回订单页面
			 */
			back(){
				this.$u.route({type:"back"});
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
	.good_content{
		.content{
			.tag{
				margin: 10rpx;
			}
		}
	}
	.good_info{
		.intro{
			margin-left: 30rpx;
			.name{
				width: 500rpx;
			}
			.choice{

			}
		}
	}
</style>
