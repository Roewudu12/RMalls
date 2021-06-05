<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-calendar"></i> 表单
                </el-breadcrumb-item>
                <el-breadcrumb-item>个人信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item hidden label="用户ID">
                        <el-input v-model="form.userId" type="text"></el-input>
                    </el-form-item>
                    <el-form-item label="用户名称">
                        <el-input v-model="form.username" type="text"></el-input>
                    </el-form-item>
                    <el-form-item label="个人简介">
                        <el-input v-model="form.userIntroduction" type="text"></el-input>
                    </el-form-item>
                    <el-form-item label="用户电话">
                        <el-input v-model="form.userPhone" type="text"></el-input>
                    </el-form-item>
                    <el-form-item label="用户密码">
                        <el-input v-model="form.password" type="password"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">表单提交</el-button>
                        <el-button>取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
import qs from "qs";
import service from '../../utils/request';
export default {
    name: 'baseform',
    data() {
        return {
            form: {
               
            }
            
        };
    },
    created(){//创建时
        let data = {"username":localStorage.getItem("username")};
        service.post("/user/userInfo/byName",qs.stringify(data),
        {headers:{'Content-Type':'application/x-www-form-urlencoded'}})
        .then(res=>{
             console.log(res);
            if(res.isSuccess){
                this.form = res.data;
            }
        })
    }
    ,
    methods: {

        onSubmit() { 
            service.post("/user/userInfo/update",this.form)
            .then(res=>{
                console.log(res);
                if(res.isSuccess){
                    this.$message.success('提交成功！');
                }else{
                    this.$$message.error('提交失败！');
                }
            })
        }
    }
};
</script>