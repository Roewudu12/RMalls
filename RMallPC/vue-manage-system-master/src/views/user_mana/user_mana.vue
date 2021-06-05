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
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                <el-button type="primary" @click="insert_click">新增</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="userId" label="ID" align="center"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="userPhone" label="电话号码"></el-table-column>
                <el-table-column prop="userAuthority" label="用户权限" align="center">
					<template v-slot="scope">
						{{scope.row.userAuthority==1?'商家':scope.row.userAuthority==2?'买家':'管理员'}}
					</template>
				</el-table-column>
                <el-table-column prop="star" label="评价"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button
                            type="text"
                            icon="el-icon-edit"
                            @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>


        <!-- 编辑弹出框 -->
        <el-dialog :title="editVisible?'编辑':'新增'" :model-value="editVisible||insertVisible" width="30%" @close="dialog_close">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input v-model="form.userPhone"></el-input>
                </el-form-item>
                <el-form-item label="用户权限">
                    <el-select v-model="form.userAuthority" placeholder="请选择">
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                            >
                        </el-option>
                    </el-select>
                </el-form-item>
                 
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="editVisible = false">取 消</el-button>
                    <el-button v-if="editVisible" type="primary" @click="saveEdit">确 定</el-button>
                    <el-button v-else type="primary" @click="insert">确 定</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script>
import service from '../../utils/request';
// import qs from "qs";
// import service from '../../utils/request';
export default {
    name: "用户管理",
    data() {
        return {
            query: {
                pageIndex: 1,
                pageSize: 5
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,//编辑弹出框
            insertVisible:false,//新增弹出框
            pageTotal: 0,
            form: {},
            idx: -1,//当前操作的条目再tableData的下表
            id: -1,
            options:[
                {
                    value: 1,
                    label: "商家",
                },
                {
                    value: 2,
                    label: "买家",
                },
                {
                    value: 3,
                    label: "管理员",
                },
            ]
        };
    },
    created() {
        this.getData();
        this.getTotal();
    },
    
    methods: {
        
        // 获取 服务器数据
        getData() {
            let data = {"page":this.query.pageIndex};
            this.$service.post("/user/showUsers",this.$qs.stringify(data),
            {headers:{'Content-Type':'application/x-www-form-urlencoded'}})
            .then(res=>{
                console.log(res);
                if(res.isSuccess){
                    this.tableData = res.data;
                    this.authorityCN();
                }
            })
        },
        getTotal(){//获取总数据量
            this.$service.post("/user/count")
            .then(res=>{
                console.log(res)
                if(res.isSuccess){
                this.pageTotal = res.data;
                }
            })
        },
        // 触发搜索按钮
        handleSearch() {
            let data = {"input":this.query.name,"page":1}
            this.$service.post("/user/search/like",this.$qs.stringify(data),this.$header)
            .then(res=>{
                console.log(res)
                if(res.isSuccess){
                    this.$message.success("搜索成功!");
                    this.tableData = res.data;
                }else{
                    this.$message.error("搜索失败!");
                }
            })
        },
        // 删除操作
        handleDelete(index) {
            console.log("当前删除第"+index);
            
            // 二次确认删除
            this.$confirm("确定要删除吗？", "提示", {
                type: "warning"
            })
                .then(() => {
                    let data = {"userId":this.tableData[index].userId};
                    this.servicePostParam("/user/delete",data);
                })
                .catch(() => {});
        },
        //post请求封装RequestParam
        servicePostParam(url,data){
             this.$service.post(url,this.$qs.stringify(data),{headers:{'Content-Type':'application/x-www-form-urlencoded'}})
                    .then(res=>{
                        console.log(res);
                        if(res.isSuccess){
                            this.$message.success(res.message);
                            this.query.pageIndex = 1;
                            this.getData();
                            this.getTotal();
                        }else{
                            this.$message.error(res.message);
                        }
                        
                    })
        },
        //post请求封装RequestBody
        servicePost(url,data){
                this.$service.post(url,data)
                .then(res=>{
                    console.log(res);
                    this.query.pageIndex = 1;
                    this.getData();
                    this.getTotal();
                })
        },

        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = "";
            this.delList = this.delList.concat(this.multipleSelection);
            let ids = [];
            for (let i = 0; i < length; i++) {
                ids.push(this.multipleSelection[i].userId);
                str += this.multipleSelection[i].username + " ";
            } 
            console.log(ids);
            let data = ids;
            this.servicePost("/user/delete/users", data);
            
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            //去掉中文
            delete this.form.userAuthorityCN;
            console.log(this.form)
            let data = this.form;
            this.$service.post("/user/userInfo/update",data)
            .then(res=>{
                
                if(res.isSuccess){
                    console.log(res.data)
                    res.data.userAuthorityCN = res.data.userAuthority == 1?'商家':(res.data.userAuthority==2?'买家':'管理员');
                    this.tableData[this.idx] = res.data;
                    this.$message.success("更新成功")
                }
            })
            this.dialog_close();
        },
        // 分页导航
        handlePageChange(val) {
            console.log(val)
            this.query.pageIndex=val;
            this.getData();
        },
        //弹出框被关闭
        dialog_close(){
            console.log("弹出框被关闭")
            this.insertVisible = false;
            this.editVisible=false;
        },
        //点击新增按钮
        insert_click(){
            this.form={};//置空
            this.insertVisible = true;
        },
        //新增User
        insert(){
            console.log("新增用户");
            this.$service.post("/user/insert",this.form)
            .then(res=>{
                console.log(res);
               if(res.isSuccess){
                   this.$message.success(res.message);
                   this.getData();
                   this.getTotal();
                   this.dialog_close();
               }else{
                   this.$message.error(res.message);
               }
            },res=>{
                this.$message.error("无法连接服务器");
            })
        },
        //将权限换成中文显示
        authorityCN(val){
            for(let i=0;i<this.tableData.length;i++){
                
                switch(this.tableData[i].userAuthority){
                case 1: this.tableData[i].userAuthorityCN = "商家";
                    break;
                case 2: this.tableData[i].userAuthorityCN = "买家";
                    break;
                case 3: this.tableData[i].userAuthorityCN = "管理员";
                    break;
                }
            }  
            console.log(this.tableData)
        },
        
    }
};
</script>

<style scoped>
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
