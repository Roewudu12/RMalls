<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 基础表格
				</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div class="handle-box">
				<el-input v-model="query.name" placeholder="" class="handle-input mr10"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="orderId" label="订单ID"></el-table-column>
				<el-table-column prop="goodId" label="商品ID"></el-table-column>
				<el-table-column prop="goodNum" label="商品数量"></el-table-column>
				<el-table-column prop="orderPrice" label="订单价格"></el-table-column>
				<el-table-column prop="orderStatus" label="订单状态">
					<template v-slot="scope">
						{{isOrderStatus(scope.row.orderStatus)}}
					</template>
				</el-table-column>
				<el-table-column label="操作" width="180" align="center">
					<template #default="scope">
						<el-button type="text" v-if="scope.row.orderStatus==2 && userAuthority!=3" icon="el-icon-box" @click="toDeliver(scope.$index, scope.row)">发货</el-button>
						<el-button type="text" v-if="scope.row.orderStatus==3" icon="el-icon-truck">查看物流</el-button>
						<el-button type="text" v-if="scope.row.orderStatus==4" icon="el-icon-s-comment">查看评价</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>

	</div>
</template>

<script>
	import qs from "qs";
	import service from '../../utils/request';
	export default {
		name: "用户管理",
		data() {
			return {
				query: {
					address: "",
					name: "",
					pageIndex: 1,
					pageSize: 5
				},
				tableData: [],
				multipleSelection: [],
				delList: [],
				editVisible: false, //编辑弹出框
				insertVisible: false, //新增弹出框  
				pageTotal: 0,
				form: {},
				idx: -1,
				id: -1,
			
				//商品类别级联懒加载配置
				props: {
					value: 'cateId',
					label: 'cateName',
					emitPath: false,
					lazy: true,
					//懒加载
					lazyLoad: this.lazyLoad,
				},
				head_image: {}, //头像图片
				fileList: [], //已上传图片
				fileList2: [], //已上传详情图
				//商品添加可选项
				choices: [],
				//可提交到服务器的商品可选项
				submitChoices: [],
				userAuthority:localStorage.getItem("userAuthority"),
			};
		},
		created() {
			// this.headers.push({
			// 	Cookie: localStorage.getItem("")
			// })
			this.getData();
			this.getTotal();
		},
		methods: {

			// 获取 服务器数据
			getData() {
				let data = {
					"page": this.query.pageIndex,
					"userAuthority": localStorage.getItem("userAuthority"),
					"userId": localStorage.getItem("userId")
				};
				service.post("/order/show/all", qs.stringify(data),
						this.$header)
					.then(res => {
						console.log(res);
						if (res.isSuccess) {
							this.tableData = res.data;
						}
					})
			},
			getTotal() { //获取总数据量
				let data = {
					"userAuthority": localStorage.getItem("userAuthority"),
					"userId": localStorage.getItem("userId")
				};
				service.post("/order/count", qs.stringify(data), this.$header)
					.then(res => {
						console.log(res)
						if (res.isSuccess) {
							this.pageTotal = res.data;
						}
					})
			},
			/**
			 * 订单状态
			 */
			isOrderStatus(val){
				switch(val){
					case 1: return "待付款";
					case 2: return "待发货";
					case 3: return "待收货";
					case 4: return "待评价";
					case 5: return "已完成";
					case 6: return "退货/售后";
				}
				
			},
			// 触发搜索按钮
			handleSearch() {
				this.$set(this.query, "pageIndex", 1);
				this.getData();
			},
			// 分页导航
			handlePageChange(val) {
				console.log(val)
				this.query.pageIndex = val;
				this.getData();
			},
			/**
			 * 去发货
			 */
			toDeliver(index, row){
				console.log("toDeliver",index,row);
				row.orderStatus=3;
				this.$service.post("/order/update",row)
				.then(res=>{
					console.log("toDeliver",res)
				})
			}
		}
	};
</script>

<style scoped lang="scss">
	.choice {
		.title {
			margin-right: 10px;
			font-size: 15px;
		}

		.choice {
			margin-top: 10px;
		}
	}

	.el-tag+.el-tag {
		margin-left: 10px;
	}

	.button-new-tag {
		margin-left: 10px;
		height: 32px;
		line-height: 30px;
		padding-top: 0;
		padding-bottom: 0;
	}

	.input-new-tag {
		width: 90px;
		margin-left: 10px;
		vertical-align: bottom;
	}

	.handle-box {
		margin-bottom: 20px;
	}

	.handle-select {
		width: 120px;
	}

	.handle-input {
		width: 300px;
		display: inline-block;
	}

	.table {
		width: 100%;
		font-size: 14px;
	}

	.red {
		color: #ff0000;
	}

	.mr10 {
		margin-right: 10px;
	}

	.table-td-thumb {
		display: block;
		margin: auto;
		width: 40px;
		height: 40px;
	}
</style>
