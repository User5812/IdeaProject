<template>
  <div style="margin-top: 10px">
    <!--    全部-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部订单" name="all">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="联系人：">
                  <span>{{ props.row.linkUser }}</span>
                </el-form-item>
                <el-form-item label="联系地址：">
                  <span>{{ props.row.linkAddress }}</span>
                </el-form-item>
                <el-form-item label="联系电话：">
                  <span>{{ props.row.linkPhone }}</span>
                </el-form-item>
                <el-form-item label="创建时间：">
                  <span>{{ props.row.createTime }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column label="订单编号" prop="orderNo"></el-table-column>
          <el-table-column label="商品" width="400">
            <template slot-scope="scope">
              <div v-for="item in scope.row.goods" :key="item.id" style="display: flex">
                <div style="flex: 2"><a :href="['/front/goods?id=' + item.id]">
                  <el-image :src="item.imgs" style="width: 100px; height: 100px;" fit="contain"></el-image>
                </a></div>
                <div style="flex: 2"><a style="color: #666"
                                        :href="['/front/goods?id=' + item.id]">{{ item.name }}</a></div>
                <div style="flex: 1; line-height: 100px; text-align: center">X {{ item.count }}</div>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="卖家联系电话" width="100">
            <template slot-scope="scope">
              <div v-for="item in scope.row.goods" :key="item.id" style="display: flex; margin-bottom: 80px">
                <span>{{item.sellerPhone}}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="总价格">
            <template slot-scope="scope">
              <span style="color: red">￥ {{ scope.row.totalPrice }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="state"></el-table-column>

        </el-table>
        <div style="margin-top: 10px">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-size="pageSize"
              :page-sizes="[2, 5, 10]"
              layout="prev, pager, next"
              :total="total"
          >
          </el-pagination>
        </div>
      </el-tab-pane>

    </el-tabs>



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
      user: {},
      pageNum: 1,
      pageSize: 10,
      total: 0,
      activeName: 'all',
      tableData: [],
      payData: [],
      commentData: [],
      entity: {},
      state: 'all',
      dialogFormVisible: false,
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab.name);

      this.state = tab.name
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    load() {
      this.commentData = []
      request.get("/order/list", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.admin.id
        }
      }).then(res => {
        this.tableData = res.data.list
      })
    },
    cancel(id) {
      request.put("/api/order", {id: id, state: '已取消'}).then(res => {
        if (res.code === '0') {
          this.$message({
            type: 'success',
            message: '操作成功'
          })
          this.load()
        }
      })
    },
    del(id) {
      request.delete("/api/order/" + id).then(res => {
        if (res.code === '0') {
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
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>
