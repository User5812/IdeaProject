<template>
  <div style="margin-top: 10px">
    <div style="background-color: white; padding: 10px">
      <!--收货地址-->
      <div style="padding: 10px; margin-bottom: 20px; border-bottom: 1px solid #eee">
        <div style="font-size: 20px; border-bottom: 2px solid dodgerblue; padding-bottom: 10px; margin-bottom: 20px">
          收货地址
        </div>
        <div style="padding: 10px 0; color: #555" v-for="item in addressData" :key="item.id">
          <el-radio v-model="chooseAddress" :label="item" border>{{ item.linkUser }}</el-radio>
          <span>{{ item.linkUser }}</span>
          <span style="margin-left: 20px">{{ item.linkPhone }}</span>
          <span style="margin-left: 20px">{{ item.linkAddress }}</span>
        </div>
      </div>


      <!--        商品确认-->
      <el-table :data="carts" stripe style="width: 100%" :show-header="false">
        <el-table-column label="商品图片" width="150">
          <template slot-scope="scope">
            <el-image :src="scope.row.imgs" style="width: 100px; height: 100px;" fit="contain"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column label="价格">
          <template slot-scope="scope">
            <span v-html="scope.row.price + ' X ' + scope.row.count "></span>
          </template>
        </el-table-column>

      </el-table>

      <div style="margin-top: 10px">
        <div style="background-color: white; padding: 10px">
          <div style="color: red; text-align: right">
            <div>
              <span>总价：</span>
              <span>￥ {{ totalPrice }}</span>
            </div>
            <div style="text-align: right; color: #999; font-size: 12px; padding: 5px 0">
              运费： ￥ 0
            </div>
            <div style="padding: 10px 0">
              <el-button style="background-color: red; color: white; width: 100px" @click="submitOrder">提交订单</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "cart",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      chooseAddress: null,
      user: {},
      addressData: [],
      carts: [],
      totalPrice: 0,
      discount: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    submitOrder() {
      console.log(this.chooseAddress)
      if (!this.chooseAddress) {
        this.$message({
          type: 'warning',
          message: '请选择收货地址！'
        })
        return
      }
      // 提交订单
      request.post("/order/save",{
        addressId: this.chooseAddress.id,
        userId: this.admin.id
      }).then( res => {
        if(res.code === '200') {
          this.$message({
            message: '提交订单成功',
            type: 'success'
          })
        }else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
      this.$router.push('/front/home')
    },
    load() {
      // 获取收货地址
      request.get("/address/list",{
        params:{
          userId: this.admin.id
        }
      }).then(res => {
        this.addressData = res.data
      })

      request.get("/cart/selectUserCart/" + this.admin.id).then( res => {
        this.carts = res.data
        this.totalPrice = res.totalPrice
      })

    },
  }
}
</script>

<style scoped>

</style>
