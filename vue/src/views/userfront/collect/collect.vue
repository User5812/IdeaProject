<template>
  <div>
    <!--    <div style="padding: 5px 0">-->
    <!--      <el-input v-model="text" @keyup.enter.native="load" style="width: 200px"> <i slot="prefix" class="el-input__icon el-icon-search"></i></el-input>-->
    <!--      <el-button @click="add" type="primary" size="mini" style="margin: 10px">新增</el-button>-->
    <!--    </div>-->
    <el-table :data="tableData" border stripe style="width: 100%; margin-top: 10px">
      <el-table-column label="商品名称">
        <template slot-scope="scope">
          <a :href="['/front/goods?id=' + scope.row.goodsId]">{{ scope.row.goodsName }}</a>
        </template>
      </el-table-column>
      <el-table-column prop="goodsImg" label="商品图片">
        <template slot-scope="scope">
          <el-image :src="scope.row.goodsImg" :preview-src-list="[scope.row.goodsImg]" style="width: 100%; height: 100px" fit="contain"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="收藏时间"></el-table-column>

      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <!--          <el-button type="primary" icon="el-icon-edit" circle  @click="edit(scope.row)"></el-button>-->
          <el-popconfirm
              @confirm="del(scope.row.id)"
              title="确定取消收藏吗？"
          >
            <el-button type="danger" slot="reference" style="margin-left: 10px">取消收藏</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin-top: 10px; background-color: white; padding: 10px 0;">
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


  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: "Collect",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      fileList: [],
      options: [],
      text: '',
      user: {},
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      entity: {},
      total: 0,
      dialogFormVisible: false
    };
  },
  created() {
    this.load()
  },
  methods: {
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    load() {
      request.get("/collect/pageCollect", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          userId: this.admin.id
        }
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        console.log(res)
      })
    },
    del(id) {
      console.log(id)
      request.delete("/collect/deleteCollect/" + id).then( res => {
        if(res.code === '200'){
          this.$message({
            message: '删除成功',
            type: 'success'
          })
          this.load()
        }else {
          this.$message({
            message: res.msg,
            type: 'error'
          })
        }
      })
    }
  },
};
</script>

<style scoped>
</style>
