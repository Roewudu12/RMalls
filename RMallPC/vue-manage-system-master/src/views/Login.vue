<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统{{info}}</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="username">
                        <template #prepend>
                            <el-button icon="el-icon-user"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="param.password"
                        @keyup.enter="submitForm()"
                    >
                        <template #prepend>
                            <el-button icon="el-icon-lock"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
import service from '../utils/request';
import qs from "qs";
export default {
    data() {
        return {
            param: {
                username: "",
                password: "",
            },
            rules: {
                username: [
                    { required: true, message: "请输入用户名", trigger: "blur" }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" }
                ]
            }
        };
    },
    created() {
        this.$store.commit("clearTags");
       
    },
    
    methods: {
        submitForm() {
            var that = this;
            this.$refs.login.validate(valid => {
                if (valid) {
                     service.post("/login",
                     qs.stringify({username:that.param.username,password:that.param.password}),
                     {headers:{'Content-Type':'application/x-www-form-urlencoded'}})
                     .then(res=>{
                         console.log(res)
                         if(res.code == "201"){
							if(res.data.userAuthority==2){
								this.$message.error("当前用户没有权限");
								return false;
							}
                            this.$message.success(res.message);
                            localStorage.setItem("username", res.data.username);
                            localStorage.setItem("userId",res.data.userId)
                            localStorage.setItem("userAuthority",res.data.userAuthority)
                            this.$router.push("/");
                         }else{
                             this.$message.error("账号密码输入错误");
                             return false;
                         }
                     })
                    
                } else {
                    this.$message.error("请输入账号和密码");
                    return false;
                }
            });
        }
    }
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>