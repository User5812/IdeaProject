<template>
  <div style="height: 100vh; overflow: hidden; position: relative" >
    <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :accuracy="3"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
      <div>{{msg}}</div>
    </el-card>


    <div style="width: 500px; height: 400px; background-color: white; border-radius: 15px; margin: 150px auto;
 padding: 50px">
      <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue">登 录</div>
      <el-form :model="admin" :rules="rules" ref="admin">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号"  prefix-icon="el-icon-user" size="medium" v-model="admin.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" show-password prefix-icon="el-icon-lock" size="medium" v-model="admin.password"></el-input>
        </el-form-item>
        <el-form-item >
          <el-button style="width: 100%; transform: translateX(-10px)" size="medium" type="warning" @click="loginUser">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie"
export default {
  name: 'Login',
  data(){
    return {
      loginAdmin: {},
      admin: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ]}
    }


  },
  methods: {
    loginUser() {
      this.$refs['admin'].validate(valid => {
        if(valid){
          request.post('/user/login',this.admin).then(res => {
            if(res.code === '200'){
              this.loginAdmin = res.data
            }else {
              this.$notify.error(res.msg)
            }
          })
        }else {}
      })

    },
    onSuccess() {
      Cookies.set('admin', JSON.stringify(this.loginAdmin))
      if(this.loginAdmin.identify === '1'){
        this.$router.push('/')
      }else{
        this.$router.push('/front')
      }
      this.$notify.success("登录成功")
    },
    onFail() {
      this.$notify.warning("请重试一次")
    },
    onRefresh() {
      console.log('refresh')
    },
    login(){
      this.$refs['admin'].validate(valid => {
        if(valid){
          request.post('/admin/login',this.admin).then(res => {
            if(res.code === '200'){
              this.loginAdmin = res.data
            }else {
              this.$notify.error(res.msg)
            }
          })
        }else {}
      })

    },
  }
}
</script>

<style>
.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>