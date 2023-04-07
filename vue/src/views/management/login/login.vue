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
          <el-button style="width: 20%; margin-left: 310px; margin-top: 10px" size="small" type="primary" @click="register">我要注册</el-button>
        </el-form-item>
      </el-form>
    </div>


    <!-- 弹窗   -->
    <el-dialog title="欢迎注册" :visible.sync="dialogFormVisible" width="30%"
               :close-on-click-modal="false">
      <el-form :model="entity" :rules="registerRule" ref="reg">
        <el-form-item label="用户名" label-width="150px" prop="username">
          <el-input v-model="entity.username" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="150px" prop="password">
          <el-input type="password" v-model="entity.password" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="昵称" label-width="150px" prop="nickName">
          <el-input v-model="entity.nickName" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" label-width="150px">
          <el-input v-model="entity.email" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" label-width="150px" prop="phone">
          <el-input v-model="entity.phone" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="地址" label-width="150px">
          <el-input v-model="entity.address" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄" label-width="150px" prop="age">
          <el-input v-model="entity.age" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save('reg')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
let checkAge = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('年龄不能为空'));
  }
  if (isNaN(Number(value))) {
    callback(new Error('请输入数字值'));
  } else {
    if (value < rule.max_age) {
      callback(new Error('必须年满18岁'));
    } else {
      callback();
    }
  }
};

import request from "@/utils/request";
import Cookies from "js-cookie"
export default {
  name: 'Login',
  data(){
    return {
      loginAdmin: {},
      admin: {},
      dialogFormVisible: false,
      entity: [],
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ]},
      registerRule: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        age: [
          {max_age:18, validator: checkAge, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '手机号必填', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '手机号码格式不正确', trigger: 'blur' }
        ]
      }
    }


  },
  methods: {
    save(formName) {
      this.$refs[formName].validate(valid => {
        if(valid){
          request.post("/user/save",this.entity).then( res => {
            if(res.code === '200'){
              this.$notify({
                message: '注册成功！',
                type: 'success'
              })
            }else {
              this.$notify({
                message: res.msg,
                type: 'error'
              })
            }
          })
          this.entity = []
          this.dialogFormVisible = false
        }else {}
      })

    },
    register() {
      this.entity = {}
      this.dialogFormVisible = true
    },
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