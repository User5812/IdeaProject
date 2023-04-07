<template>
  <div>
    <!--    头部-->
    <div style="width: 100%; height: 60px; line-height: 60px; background-color: white">
      <el-row>
        <el-col :span="4">
          <div style="font-size: 20px; font-weight: bold; text-align: center"><a style="color: #409EFF;" href="/front/home">物联网商品商城</a></div>
        </el-col>
        <el-col :span="8">
          <el-menu class="side-menu" fixed :default-active="$route.path" @select="handleMenuSelect" mode="horizontal" >
            <el-menu-item index="/front/home">
              首页
            </el-menu-item>
            <el-menu-item index="/front/cart">
              购物车
            </el-menu-item>
            <el-menu-item index="/front/order">
              我的订单
            </el-menu-item>
            <el-menu-item index="/front/collect">
              收藏夹
            </el-menu-item>
            <el-menu-item index="/front/person">
              个人中心
            </el-menu-item>
            <el-menu-item index="/front/address">
              收货地址
            </el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="6">
          <div v-if="!$route.path.includes('/search')">
            <el-input style="width: 80%" placeholder="请输入商品名称搜索商品" v-model="searchText"></el-input>
            <el-button style="margin-left: 5px" @click="search">搜索</el-button>
          </div>
          <div v-else>
            <div style="width: 100px;height: 20px"></div>
          </div>
        </el-col>
        <el-col :span="4">
          <div style="text-align: right; padding-right: 10px" v-if="admin.id">
            <el-button type="primary" @click="add()">上传商品<i class="el-icon-upload el-icon--right"></i></el-button>
            <el-dropdown style="float: right; margin-left: 30px" @command="handleCommand">
              <span class="el-dropdown-link" style="cursor: pointer">
                欢迎你！{{ admin.nickName }}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="person">个人信息</el-dropdown-item>
                <el-dropdown-item command="loginOut">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-col>
      </el-row>
    </div>

    <!-- 主体 -->
    <el-row>
      <el-col :span="16" :offset="4" style="margin-bottom: 80px">
        <router-view />
      </el-col>
    </el-row>


    <!-- 弹窗   -->
    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%"
               :close-on-click-modal="false">
      <el-form :model="entity">
        <el-form-item label="商品名称" label-width="150px">
          <el-input v-model="entity.name" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" label-width="150px">
          <el-input type="textarea" v-model="entity.description" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" label-width="150px">
          <el-input v-model="entity.sellerPhone" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="原价" label-width="150px">
          <el-input v-model="entity.price" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="折扣" label-width="150px">
          <el-input v-model="entity.discount" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="库存" label-width="150px">
          <el-input v-model="entity.store" autocomplete="off" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="分类" label-width="150px">
          <el-select v-model="entity.categoryId" placeholder="请选择" style="width: 80%">
            <el-option v-for="item in options" :key="item.id" :label="item.categoryname" :value="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="图片" label-width="150px">
          <el-upload action="http://localhost:9090/api/files/upload" multiple :on-success="handleSuccess" ref="upload">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import Cookies from "js-cookie";
import request from "@/utils/request";

export default {
  name: "Front",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      searchText: '',
      user: {},
      dialogFormVisible: false,
      entity: [],
      options: []
    }
  },
  methods: {
    search() {
      this.$router.replace({path: '/front/search', query: {searchText: this.searchText}})
    },
    handleCommand(command) {
      if (command === 'person') {
        this.$router.push('/front/person');
      }
      if (command === 'loginOut') {
        Cookies.remove('admin')
        this.$router.replace('/login');

      }
    },
    handleMenuSelect(index) {
      console.log(index)
      this.$router.push(index);
    },
    add() {
      this.entity = {}
      this.fileList = []
      if(this.$refs['upload']) {
        this.$refs['upload'].clearFiles()
      }
      this.load()
      this.dialogFormVisible = true
    },
    //上传商品
    handleSuccess(res) {
      console.log(res.data)
      let url = "http://localhost:9090/api/files/" + res.data
      this.entity.imgs = url
    },
    save() {
      this.entity.userId = this.admin.id
      request.post('/goods/save',this.entity).then(res => {
        if(res.code === '200'){
          this.$notify.success("提交商品成功")
        }else{
          this.$notify.error(res.msg)
        }
      })
      this.entity = []
      this.dialogFormVisible = false
    },
    load(){
      request.get('/goods/option').then(res => {
        this.options = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>
