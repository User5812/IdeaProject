<template>
  <div>
    <!--  头部区域  -->
    <div style="height: 60px; line-height: 60px; background-color: white;margin-bottom: 3px; display: flex">
      <div style="width: 500px">
        <img src="@/assets/logo.png" alt="物联网商品商城" style="width: 40px; height: 40px; position: relative; top: 10px;
left: 10px">
        <span style="margin-left: 20px; font-size: 24px">物联网商品商城后台管理界面</span>
      </div>
      <div style="flex: 1; text-align: right; padding-right: 30px">
        <el-dropdown>
          <span class="el-dropdown-link" style="cursor: pointer">
            {{admin.username}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
            <el-dropdown-menu slot="dropdown" size="medium">
              <el-dropdown-item><div style="width: 50px; text-align: center" @click="logout">退出</div></el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!--  侧边栏和主体  -->
    <div style="display: flex">
      <!--   侧边栏导航   -->
      <div style="width: 200px; min-height: calc(100vh - 63px); overflow: hidden; margin-left: 2px; background-color: white">
        <el-menu :default-active="$route.path" router class="el-menu-demo" >
          <el-menu-item index="/">
            <i class="el-icon-s-tools"></i>
            <span>首页</span>
          </el-menu-item>
          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-info"></i>
              <span>会员管理</span>
            </template>
            <el-menu-item index="/userList">会员列表</el-menu-item>
          </el-submenu>
<!--          已停用-->
<!--          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员管理</span>
            </template>
            <el-menu-item index="/adminList">管理员列表</el-menu-item>
            <el-menu-item index="/addAdmin">管理员添加</el-menu-item>
          </el-submenu>-->
<!--          商品管理-->
          <el-submenu index="goods">
            <template slot="title">
              <i class="el-icon-info"></i>
              <span>商品管理</span>
            </template>
            <el-menu-item index="/goodsList">商品管理</el-menu-item>
          </el-submenu>
          <!--          类别管理-->
          <el-submenu index="category">
            <template slot="title">
              <i class="el-icon-info"></i>
              <span>品类管理</span>
            </template>
            <el-menu-item index="/categoryList">类别管理</el-menu-item>
            <el-menu-item index="/addCategory">新增类别</el-menu-item>
          </el-submenu>
          <!--          地址管理-->
          <el-submenu index="address">
            <template slot="title">
              <i class="el-icon-info"></i>
              <span>收获地址管理</span>
            </template>
            <el-menu-item index="/addressList">地址收获管理</el-menu-item>
            <el-menu-item index="/addAddress">新增收货地址</el-menu-item>
          </el-submenu>
          <!--          订单管理-->
          <el-submenu index="order">
            <template slot="title">
              <i class="el-icon-info"></i>
              <span>订单管理</span>
            </template>
            <el-menu-item index="/orderList">订单管理</el-menu-item>
          </el-submenu>
        </el-menu>

      </div>
      <!--  主体区域  width: 0可以闲置容器的宽度不被子元素无线撑开-->

      <div style="flex: 1; width: 0;  background-color: white; padding: 10px">
        <router-view/>
      </div>

    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";

export default {
  name: 'Layout',
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {}
    }
  },
  methods: {
    logout(){
      //清除浏览器用户数据
      Cookies.remove('admin')
      this.$router.push('/login');

    },
  }
}
</script>

<style scoped>

</style>