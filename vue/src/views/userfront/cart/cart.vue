<template>
  <div style="margin-top: 10px">
    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column label="商品图片">
        <template slot-scope="scope">
          <a :href="['/front/goods?id=' + scope.row.goodsId]"><el-image :src="scope.row.imgs" style="width: 100px; height: 100px;" fit="contain"></el-image></a>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名称"></el-table-column>
      <el-table-column prop="price" label="商品单价"></el-table-column>
      <el-table-column prop="count" label="购买数量">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.count" :min="1" :max="scope.row.store" label="购买数量" style="width: 100px" @change="changeCount(scope.row)"></el-input-number>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="加入时间"></el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row.id)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin-top: 10px">
      <div style="display: flex; background-color: white; padding: 10px">
        <div style="flex: 1; padding-left: 20px; color: #999; font-size: 12px">
          <div>共 <span style="color: red; font-size: 20px">{{ count }}</span> 件商品</div>
          <div style="color: orange; padding-top: 5px">立即下单！</div>
        </div>
        <div style="flex: 1;color: red; text-align: right">
          <div>
            <span>总价：</span>
            <span>￥ {{ totalPrice }}</span>
            <el-button style="background-color: red; color: white; margin-left: 20px" @click="submitOrder">去结算</el-button>
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
  name: "FrontCart",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      user: {},
      count: 0,
      tableData: [],
      totalPrice: 0,
    }
  },
  created() {
    this.load()
  },
  methods: {
    submitOrder() {
      if (!this.tableData.length) {
        this.$message({
          type: 'warning',
          message: '您还未选择商品'
        })
        return
      }
      this.$router.replace("/front/preOrder")
    },
    load() {
      request.get("/cart/selectUserCart/" + this.admin.id).then( res => {
        if (res.code === '200'){
          this.tableData = res.data
          console.log(res.data)
          this.totalPrice = res.totalPrice
          this.count = res.count
        }
      })
    },
    changeCount(obj) {
      request.put("/cart/updateCart", {id: obj.id, count: obj.count}).then( res =>{
        if(res.code === '200'){
        }else{
          this.$message({
            message: res.msg,
            type: "error"
          })
        }
      })
      this.load()
    },
    del(id) {
      request.delete("/cart/deleteCart/" + id).then(res => {
        if (res.code === '200') {
          this.$message({
            type: 'success',
            message: '操作成功'
          })
          this.load()
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
