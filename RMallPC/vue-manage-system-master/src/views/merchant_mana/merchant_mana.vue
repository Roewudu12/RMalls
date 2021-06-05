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
				<el-button type="primary" icon="el-icon-delete" class="handle-del mr10" @click="delAllSelection">批量删除
				</el-button>
				<el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button type="primary" @click="insert_click">新增</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header"
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center"></el-table-column>
				<el-table-column prop="goodId" label="商品ID"></el-table-column>
				<el-table-column prop="goodName" label="商品名称"></el-table-column>
				<el-table-column prop="goodPrice" label="商品价格"></el-table-column>
				<el-table-column prop="goodSoldNum" label="商品售出数量"></el-table-column>
				<el-table-column prop="goodStatus" label="商品状态">
					<template v-slot="scope">
						{{scope.row.goodStatus==1?'在售':'下架'}}
					</template>
				</el-table-column>
				<el-table-column prop="goodStock" label="商品库存"></el-table-column>
				<el-table-column label="操作" width="180" align="center">
					<template #default="scope">
						<el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
						</el-button>
						<el-button type="text" icon="el-icon-delete" class="red"
							@click="handleDelete(scope.$index, scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>

		<!-- 编辑、新增弹出框 -->
		<el-dialog :title="(editVisible ? '编辑' : '新增') + '商品'" :model-value="editVisible || insertVisible" width="60%"
			@close="flushform">
			<el-form ref="form" :model="form" label-width="70px">
				<el-form-item label="商品名称">
					<el-input placeholder="请输入商品名称" v-model="form.goodName"></el-input>
				</el-form-item>
				<el-form-item label="商品价格">
					<el-input placeholder="请输入商品价格" v-model="form.goodPrice"></el-input>
				</el-form-item>
				<el-form-item label="商品库存">
					<el-input placeholder="请输入商品库存" v-model="form.goodStock"></el-input>
				</el-form-item>
				<el-form-item label="商品状态">
					<el-select v-model="form.goodStatus" placeholder="请选择商品状态">
						<el-option v-for="item in options_status" :key="item.value" :label="item.label"
							:value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="商品类别">
					<el-cascader v-model="form.cateId" :props="props"></el-cascader>
				</el-form-item>
				<!-- 商品可选项 -->
				<div class="choice" v-if="editVisible">
					<div class="roe_row_flex">
						<div class="title roe_text_col_center">商品可选项 </div>
						<el-button type="success" plain size="mini" @click="addChoice">添加</el-button>
					</div>
					<div class="choice roe_row_flex" v-for="(choice,idx) in choices" :key="idx">
						<div class="title">
							<el-input v-model="choice.title" placeholder="请输入选项名称"></el-input>
						</div>
						<div class="roe_text_col_center">
							<el-tag :key="tag" v-for="tag in choice.dynamicTags" closable :disable-transitions="false"
								@close="handleClose(tag,idx)">
								{{tag}}
							</el-tag>
							<el-input class="input-new-tag" v-if="choice.inputVisible" v-model="choice.inputValue"
								ref="saveTagInput" size="small" @blur="handleInputConfirm(idx)">
							</el-input>
							<el-button v-else class="button-new-tag" size="small" @click="showInput(idx)">+ New Tag
							</el-button>
							<el-button class="" size="mini" type="danger" round @click="delChoice(idx)">删除</el-button>
						</div>
					</div>
				</div>

				<div>
					<div>商品轮播图片</div>
					<el-upload ref="upload" action="http://localhost:8888/image/upload" list-type="picture-card"
						:on-preview="handlePictureCardPreview" :with-credentials="true" :file-list="fileList"
						:on-remove="handleRemove" :on-success="uploadSuccess1">
						<i class="el-icon-plus"></i>
					</el-upload>

					<div>商品详情图片</div>
					<el-upload ref="upload" action="http://localhost:8888/image/upload" list-type="picture-card"
						:on-preview="handlePictureCardPreview" :with-credentials="true" :file-list="fileList2"
						:on-remove="handleRemove" :on-success="uploadSuccess2">
						<i class="el-icon-plus"></i>
					</el-upload>
				</div>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="flushform">取 消</el-button>
					<el-button v-if="editVisible" type="primary" @click="saveEdit">确 定</el-button>
					<el-button v-else type="primary" @click="insert">确 定</el-button>
				</span>
			</template>
		</el-dialog>
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
				options_status: [ //商品状态选项
					{
						value: 1,
						label: "在售",
					},
					{
						value: 2,
						label: "下架",
					},
				],
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
				service.post("/goods/showGood/goods", qs.stringify(data),
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
				service.post("/goods/count", qs.stringify(data), this.$header)
					.then(res => {
						console.log(res)
						if (res.isSuccess) {
							this.pageTotal = res.data;
						}
					})
			},
			// 触发搜索按钮
			handleSearch() {
				this.$set(this.query, "pageIndex", 1);
				this.getData();
			},
			// 删除操作
			handleDelete(index) {
				// 二次确认删除
				this.$confirm("确定要删除吗？", "提示", {
						type: "warning"
					})
					.then(() => {
						this.$service.post("/goods/delete", this.tableData[index])
							.then(res => {
								this.$message.success("删除成功");
								this.getData();
								this.getTotal();
							})

					})
					.catch(() => {});
			},
			// 多选操作
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			delAllSelection() {
				const length = this.multipleSelection.length;
				let str = "";
				this.delList = this.delList.concat(this.multipleSelection);
				for (let i = 0; i < length; i++) {
					str += this.multipleSelection[i].goodName + " ";
				}
				console.log("将要删除：", this.multipleSelection);
				this.$service.post("/goods/delete/batch", this.multipleSelection)
					.then(res => {
						console.log("delAllSelection", res);
						this.$message.error(`删除了${str}`);
						this.multipleSelection = [];
						this.getData();
						this.getTotal();
					})
			},
			// 编辑操作
			handleEdit(index, row) {
				this.idx = index;
				this.form = row;
				this.editVisible = true;
				//将已存在图片放入FileList中
				for (let i = 0; i < this.form.detailImages.length; i++) {
					let item = this.form.detailImages[i];
					if (item.type == 0) {
						this.head_image = {
							imageId: item.imageId,
							url: item.imageHttp,
							goodId: item.goodId,
							type: item.type
						}
					} else if (item.type == 1) {
						this.fileList.push({
							name: item.imageId,
							url: item.imageHttp,
							goodId: item.goodId,
							type: item.type
						})
					} else {
						this.fileList2.push({
							name: item.imageId,
							url: item.imageHttp,
							goodId: item.goodId,
							type: item.type
						})
					}

				}
				
				this.getGoodChoices();
				console.log("正在编辑:", this.form)
			},
			// 保存编辑
			saveEdit() {
				console.log(this.form);
				this.beforeSubmit();
				this.$service.post("/goods/update", this.form)
					.then(res => {
						console.log("saveEdit", res);
						this.flushform();
						this.getData();
					})
			},
			//清除form表单数据
			flushform() {
				this.editVisible = false;
				this.insertVisible = false;
				this.fileList = [];
				this.head_image = {};
				this.fileList2 = [];
				this.choices=[],
				this.submitChoices=[],
				this.$refs.upload.clearFiles(); //清除已上传列表
				this.form = {};
			},
			// 分页导航
			handlePageChange(val) {
				console.log(val)
				this.query.pageIndex = val;
				this.getData();
			},
			//点击新增按钮
			insert_click() {
				this.form = {}; //置空
				this.form.detailImages = [];
					this.insertVisible = true;

			},
			//新增商品
			insert() {
				console.log("新增商品");
				this.form.goodSellerId = localStorage.getItem("userId")
				console.log(this.form);
				this.$service.post("/goods/insert", this.form)
					.then(res => {
						console.log(res);
						if (res.isSuccess) {
							this.$message.success(res.message);
							this.getData();
							this.getTotal();
							this.flushform();
						} else {
							this.$message.error(res.message);
						}
					}, res => {
						this.$message.error("无法连接服务器");
					})
			},
			//删除图片
			handleRemove(file, fileList) {
				console.log("handleRemove", file)
				let data = {
					goodId: this.form.goodId,
					imageHttp: file.response ? file.response.data.imageHttp : file.url,
					imageId: file.response ? "" : file.name
				}
				console.log("handleRemove", data)
				this.$service.post("/image/delete", data)
					.then(res => {
						console.log("handleRemove", res)
						this.form.detailImages.pop()
					})

			},
			handlePictureCardPreview(file) {
				console.log("handlePictureCardPreview")
				console.log(file)
				this.dialogImageUrl = file.url;
				this.dialogVisible = true;
			},
			//文件上传成功时
			uploadSuccess1(res, file, fileList) {
				console.log("uploadSuccess", res)
				this.form.detailImages.push({
					"imageHttp": res.data.imageHttp,
					"type": 1
				});
			},
			//文件上传成功时
			uploadSuccess2(res, file, fileList) {
				console.log("uploadSuccess", res)
				this.form.detailImages.push({
					"imageHttp": res.data.imageHttp,
					"type": 2
				});
			},

			//获取顶级类别列表
			getTopCate() {
				return new Promise((resolve, reject) => {
					this.$service.post("/cate/top")
						.then(res => {
							console.log("getTopCate", res);
							if (res.isSuccess) {
								resolve(res.data)
							}
						})
				})
			},
			//获取子列表
			async getSubCate(cate_id) {
				return new Promise((resolve, reject) => {
					let data = {
						"cateId": cate_id
					};
					this.$service.post("/cate/sub", this.$qs.stringify(data), this.$header)
						.then(res => {
							resolve(res.data)
						})
				});
			},

			//懒加载
			async lazyLoad(node, resolve) {
				console.log("lazyLoad", node, resolve);
				let list = [];
				if (this.$qs.stringify(node.data) == '') {
					list = await this.getTopCate(node.data.cateId);
				} else {
					list = await this.getSubCate(node.data.cateId);
				}

				//判断该节点是否存在
				for (let i = 0; i < list.length; i++) {
					let sublist = await this.getSubCate(list[i].cateId);
					console.log(sublist)
					if (sublist.length == 0) {
						list[i].leaf = true
					}
				}
				console.log(list)
				console.log(node)
				resolve(list)
			},
			/*****tags相关方法******/
			//addChoice点击按钮添加商品可选项
			addChoice() {
				this.choices.push({
					title: "",
					id:'',
					dynamicTags: [],
					inputVisible: false,
					inputValue: '',
				})
				console.log("添加商品可选项：", this.choices)
			},
			delChoice(idx) {
				console.log("delChoice(删除可选项)：", this.choices[idx]);
				if(this.choices[idx].id!=''){
					let data={
						id:this.choices[idx].id
					}
					this.$service.post("/choice/delete",this.$qs.stringify(data),this.$header)
					.then(res=>{
						console.log("delChoice",res);
						this.$message.success(res.message)
					})
				}
				this.choices.splice(idx, 1);
				console.log("delChoice(删除结果)：", this.choices);
			},
			//删除tag
			handleClose(tag, idx) {
				console.log("handleClose", tag)
				this.choices[idx].dynamicTags.splice(this.choices[idx].dynamicTags.indexOf(tag), 1);
			},
			//输入新tag
			showInput(idx) {
				console.log("showInput:", idx)
				this.choices[idx].inputVisible = true;
				this.$nextTick(_ => {
					this.$refs.saveTagInput.$refs.input.focus();
				});
			},
			//提交新tag
			handleInputConfirm(idx) {
				console.log("handleInputConfirm")
				let inputValue = this.choices[idx].inputValue;
				if (inputValue) {
					this.choices[idx].dynamicTags.push(inputValue);
				}
				this.choices[idx].inputVisible = false;
				this.choices[idx].inputValue = '';
			},
			//选项提交到服务器之前的操作
			beforeSubmit() {
				let choices = this.choices;
				for (let i = 0; i < choices.length; i++) {
					let choice = choices[i];
					if (choice.title != '' && choice.dynamicTags.length > 0) {
						let types='';
						for(let j=0;j<choice.dynamicTags.length-1;j++){
							types += choice.dynamicTags[j]+'-'
						}
						types+=choice.dynamicTags[choice.dynamicTags.length-1];
						this.submitChoices.push({
							id: choice.id ? choice.id : '',
							goodId:this.form.goodId?this.form.goodId:'',
							title:choice.title,
							types:types
						})
					}
				}
				this.form.goodChoices = this.submitChoices;
				console.log("beforeSubmit(当前表单)",this.form)
			},
			//获取商品选项操作
			getGoodChoices(){
				let data = {
					goodId:this.form.goodId,
				}
				this.$service.post("/choice/byGoodId",this.$qs.stringify(data),this.$header)
				.then(res=>{
					console.log("getGoodChoices",res);
					for(let i=0;i<res.data.length;i++){
						let choice = res.data[i];
						this.choices.push({
							title: choice.title,
							dynamicTags: choice.types.split('-'),
							id:choice.id,
							inputVisible: false,
							inputValue: '',
						})
					}
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
