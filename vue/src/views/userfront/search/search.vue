<template>
  <div>

    <div style="margin-top: 10px">
      <el-row>
        <!--    推荐商品-->
        <el-col :span="24">
          <el-card>

            <div style="padding: 10px 0">
              <div style="margin-bottom: 20px">
                <el-input placeholder="输入商品名称搜索" style="width: 30%" v-model="searchText"></el-input>
                <el-button style="margin-left: 5px" @click="loadTable">搜索</el-button>
              </div>
              <el-row :gutter="10">
                <el-col :span="6" v-for="item in tableData" :key="item.id" style="margin-bottom: 10px">
                  <div style="border: 1px solid #ccc; padding: 10px; cursor: pointer" @click="goodsDetail(item.id)">
                    <el-image :src="item.imgs" style="width: 100%; height: 100px" fit="contain"></el-image>
                    <div style="padding: 5px; height: 20px; text-align: center; font-size: 12px; overflow: hidden">
                      <el-tooltip :content="item.name" placement="bottom" effect="light">
                        <span class="item-title">{{ item.name }}</span>
                      </el-tooltip>
                    </div>
                    <div style="padding: 5px; text-align: center; color: red; font-size: 12px">
                      ￥ {{ item.price }}
                    </div>
                  </div>

                </el-col>
              </el-row>
            </div>

            <!--      分页-->
            <div style="margin-top: 20px">
              <el-pagination
                  small
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="pageNum"
                  :page-size="pageSize"
                  :page-sizes="[4, 8, 12]"
                  layout=" prev, pager, next"
                  :total="total"
              >
              </el-pagination>
            </div>

          </el-card>
        </el-col>
      </el-row>

    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";

const url = "/api/video/"

export default {
  name: "Search",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      searchText: '',
      tableData: [],
      user: {},
      pageNum: 1,
      pageSize: 8,
      total: 0
    };
  },
  created() {
    this.searchText = this.$route.query.searchText || ''
    this.loadTable()
  },
  methods: {
    goodsDetail(id) {
      this.$router.replace({path: '/front/goods', query: {id: id}})
    },
    loadTable() {
      request.get("/goods/page", {
        params: {
          name: this.searchText,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
      }).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total

      })
    },
    handleSizeChange(pageNum) {
      this.pageNum = pageNum;
      this.loadTable()
    },
    handleCurrentChange(pageSize) {
      this.pageSize = pageSize;
      this.loadTable()
    }
  },
};
</script>

<style scoped>

</style>
