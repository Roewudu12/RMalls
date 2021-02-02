<template>
	<view class="information">
		<!-- 导航栏 -->
		<u-navbar :is-back="false">
			<view class="unread">消息</view> 
			<view class="search-wrap">
				<u-search :placeholder="placeholder" v-model="keyword" :show-action="false" border-color="#d4237a"></u-search>
			</view>
		</u-navbar>
		<!-- 消息列表 -->
		<view class="info_list" v-for="(item,index) in list" :key="index" @click="enter(index)">
			<view class="info_list_item">
				<image class="item-menu-image" :src="item.icon"></image>
				<view class="item-menu-details">
					<view class="item-menu-title">{{item.title}}</view>
					<view class="item-menu-recent">{{item.recent}}</view>
				</view>
			</view>
			<u-line color="#eaeaea" length="98%"></u-line>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				placeholder:"搜索",
				keyword:"",
				list:[
					{
					  "title": "A字裙",
					  "icon": "https://cdn.uviewui.com/uview/common/classify/1/1.jpg",
					  "recent":"哈哈"
					},
					{
					  "title": "T恤",
					  "icon": "https://cdn.uviewui.com/uview/common/classify/1/2.jpg",
					  "recent":"可以"
					},
					{
					  "title": "半身裙",
					  "icon": "https://cdn.uviewui.com/uview/common/classify/1/3.jpg",
					  "recent":"谢谢"
					},
					{
					  "title": "衬衫",
					  "icon": "https://cdn.uviewui.com/uview/common/classify/1/4.jpg",
					  "recent":"yoyo"
					},
				],
			}
		},
		methods: {
			enter(index){
				console.log(index)
				console.log(this.list[index].title)
			}
		}
	}
</script>

<style scoped lang="scss">
	.unread{
		padding-left: 15rpx;
		color:$u-main-color;
		font-size: 40rpx;
		font-weight: 700;
	}
	.search-wrap {
		margin: 0 20rpx;
		flex: 1;
	}
	.info_list_item{
		padding: 20rpx;
		display: flex;
		flex-direction: inherit;
	}
	.item-menu-image {
		width: 120rpx;
		height: 120rpx;
	}
	.item-menu-details{
		margin-left: 20rpx;
	}
	.item-menu-title{
		margin-top: 10rpx;
		font-size: 35rpx;
		font-weight: 700;
	}
	.item-menu-recent{
		color: $u-tips-color;
		font-size: 25rpx;
		margin-top: 15rpx;
	}
</style>
