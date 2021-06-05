<template>
	<view>
		<u-navbar :background="background">
			<view class="search-wrap">
				<u-search :placeholder="placeholder" v-model="keyword" :show-action="false" input-align="center" border-color="#d4237a"></u-search>
			</view>
			<view class="u-margin-right-20" slot="right">
				<u-icon size="30" name="share"></u-icon>
			</view>
		</u-navbar>
		<view class="comment" v-for="(comment, index) in comments" :key="index">
			<view class="left">
				<image :src="comment.publisher.image" mode="aspectFill"></image>
			</view>
			<view class="right">
				<view class="top">
					<view class="name">{{ comment.publisher.username }}</view>
					<!-- <view class="like" :class="{ highlight: res.isLike }">
						<view class="num">{{ res.likeNum }}</view>
						<u-icon v-if="!res.isLike" name="thumb-up" :size="30" color="#9a9a9a" @click="getLike(index)"></u-icon>
						<u-icon v-if="res.isLike" name="thumb-up-fill" :size="30" @click="getLike(index)"></u-icon>
					</view> -->
				</view>
				<view class="content">{{ comment.content }}</view>
				<!-- <view class="reply-box">
					<view class="item" v-for="(item, index) in res.replyList" :key="item.index">
						<view class="username">{{ item.name }}</view> 
						<view class="text">{{ item.contentStr }}</view>
					</view>
					<view class="all-reply" @tap="toAllReply" v-if="res.replyList != undefined">
						共{{ res.allReply }}条回复
						<u-icon class="more" name="arrow-right" :size="26"></u-icon>
					</view>
				</view> -->
				<view class="bottom">
					{{ $u.timeFormat(comment.createTime, 'yyyy年mm月dd日 hh时MM分ss秒') }}
					<!-- <view class="reply">回复</view> -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				placeholder: "请输入商品",
				keyword: '',
				background: {
					backgroundColor: "#EBEEF5",
				},
				page:1,
				goodId:"",
				comments:[],//评论列表
			};
		},
		onLoad(e) {
			console.log("/pages/shop_details/comments/comments",e)
			this.goodId = e.goodId;
			this.getComments();
		},
		methods: {
			// 跳转到全部回复
			toAllReply() {
				uni.navigateTo({
					url: '/pages/shop_details/comments/reply'
				});
			},
			// 点赞
			getLike(index) {
				this.commentList[index].isLike = !this.commentList[index].isLike;
				if (this.commentList[index].isLike == true) {
					this.commentList[index].likeNum++;
				} else {
					this.commentList[index].likeNum--;
				}
			},
			/**
			 * 查看该商品评论
			 */
			getComments(){
				let data={
					goodId:this.goodId,
					page:this.page,
					pageNum:10
				}
			
				this.$u.post("/comment/show",data)
				.then(res=>{
					console.log("getComments",res)
					if(this.page==1){
						this.comments=res.data.data;
					}else{
						this.comments=this.comments.concat(res.data.data);
					}
					
				})
			},
		}
	};
</script>

<style lang="scss" scoped>
	.comment {
		display: flex;
		padding: 30rpx;

		.left {
			image {
				width: 64rpx;
				height: 64rpx;
				border-radius: 50%;
				background-color: #f2f2f2;
			}
		}

		.right {
			flex: 1;
			padding-left: 20rpx;
			font-size: 30rpx;

			.top {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 10rpx;

				.name {
					color: #5677fc;
				}

				.like {
					display: flex;
					align-items: center;
					color: #9a9a9a;
					font-size: 26rpx;

					.num {
						margin-right: 4rpx;
						color: #9a9a9a;
					}
				}

				.highlight {
					color: #5677fc;

					.num {
						color: #5677fc;
					}
				}
			}

			.content {
				margin-bottom: 10rpx;
			}

			.reply-box {
				background-color: rgb(242, 242, 242);
				border-radius: 12rpx;

				.item {
					padding: 20rpx;
					border-bottom: solid 2rpx $u-border-color;

					.username {
						font-size: 24rpx;
						color: #999999;
					}
				}

				.all-reply {
					padding: 20rpx;
					display: flex;
					color: #5677fc;
					align-items: center;

					.more {
						margin-left: 6rpx;
					}
				}
			}

			.bottom {
				margin-top: 20rpx;
				display: flex;
				font-size: 24rpx;
				color: #9a9a9a;

				.reply {
					color: #5677fc;
					margin-left: 10rpx;
				}
			}
		}
	}
</style>
