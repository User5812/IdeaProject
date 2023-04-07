<template>
    <div>
        <!--    搜索表单-->
    <div style="margin-bottom: 20px;">
      <el-input style="width: 240px;" placeholder="请输入商品名称" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px;" placeholder="请输入类别名称" v-model="params.categoryId"></el-input>
      <el-button style="margin-left: 5px;" type="primary" @click="load">
        <i class="el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button type="warning" @click="reset">
        <i class="el-icon-refresh"></i>
        <span>重置</span>
      </el-button>
      <el-button @click="add" type="primary" size="small" style="margin: 10px">新增</el-button>
    </div>

    <!-- 表单 -->
    <el-table :data="tableData" stripe>
        <el-table-column prop="id" label="编号" width="60px"></el-table-column>
        <el-table-column fixed prop="name" label="商品名称" width="200px"></el-table-column>
        <el-table-column fixed label="上架状态" width="100px">
          <template v-slot="scope">
            <el-switch
                v-model="scope.row.status"
                @change="changeStatus(scope.row)"
                active-color="#13ce66"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="userId" label="关联上传用户" width="100px"></el-table-column>
        <el-table-column prop="description" label="描述" width="300px"></el-table-column>
        <el-table-column prop="no" label="编号" width="150px"></el-table-column>
        <el-table-column prop="price" label="价格" width="100px"></el-table-column>
        <el-table-column prop="discount" label="折扣" width="60px"></el-table-column>
        <el-table-column prop="store" label="库存" width="60px"></el-table-column>
        <el-table-column prop="praise" label="点赞" width="100px"></el-table-column>
        <el-table-column prop="sales" label="销量" width="100px"></el-table-column>
        <el-table-column prop="categoryId" label="类别" width="100px"></el-table-column>
        <el-table-column label="预览" width="100px" >
          <template slot-scope="scope">
            <el-image style="width: 100px; height: 100px" :src="scope.row.imgs" :preview-src-list="[scope.row.imgs]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="140px"></el-table-column>
        <el-table-column prop="recommend" label="推荐" width="100px"></el-table-column>


      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="primary" @click="$router.push('/editGoods?id=' + scope.row.id)">编辑</el-button>
          <el-popconfirm
              title="这是一段内容确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button style="margin-left: 5px" type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block" style="margin-top: 20px;">
  <el-pagination
    background=""
    layout="prev, pager, next"
    @current-change="handleCurrentChange"
    :current-page="params.pageNum"
    :page-size="params.pageSize"
    :total="total">
  </el-pagination>
</div>

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
          <el-form-item label="商品编号" label-width="150px">
            <el-input v-model="entity.no" autocomplete="off" style="width: 80%"></el-input>
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
          <el-form-item label="是否推荐" label-width="150px">
            <el-switch
                v-model="entity.recommend"
                active-color="#13ce66"
                inactive-color="#ccc">
            </el-switch>
          </el-form-item>
          <el-form-item label="是否启用" label-width="150px">
            <el-switch
                v-model="entity.status"
                active-color="#13ce66"
                inactive-color="#ccc">
            </el-switch>
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
import request from '@/utils/request'



export default {
  name: 'Goods',
  data() {
    return {
      tableData: [],
      entity: [],
      options: [],
      total:0,
      params: {
        pageNum: 1,
        pageSize: 5,
        name: '',
        phone: '',
      },
      dialogFormVisible: false,

    }
  },
  created() {
    this.load()
  },
  methods: {
    //新增商品
    handleSuccess(res) {
      console.log(res.data)
      let url = "http://localhost:9090/api/files/" + res.data
      this.entity.imgs = url
    },
    save() {
      request.post('/goods/save',this.entity).then(res => {
        if(res.code === '200'){
          this.$notify.success("新增商品成功")
        }else{
          this.$notify.error(res.msg)
        }
      })
      this.entity = []
      this.$router.push('/goodsList')
      this.dialogFormVisible = false
    },
    //改变状态
    changeStatus(row) {
      request.put('/goods/update', row).then(res => {
        if(res.code === '200'){
          this.$notify.success('操作成功')
          this.load()
        }else{
          this.$notify.error(res.msg)
        }
      })
    },
    add() {
      this.entity = {}
      this.fileList = []
      if(this.$refs['upload']) {
        this.$refs['upload'].clearFiles()
      }
      this.dialogFormVisible = true
    },
    del(id){
      request.delete("/goods/delete/" + id).then(res => {
        if(res.code === '200'){
          this.$notify.success("删除成功")
          this.load();
        }else{
          this.$notify.error(res.msg)
        }
      })
    },
    load(){
      // fetch('http://localhost:9090/user/list').then(res => res.json()).then(res => {
      //   console.log(res);
      //   this.tableData = res.data;
      // })
      request.get('/goods/page',{params: this.params}).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total = res.data.total
        }
        
      })
      request.get('/goods/option').then(res => {
        this.options = res.data
      })
      
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 5,
        name: '',
        phone: '',
      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发
      //console.log(pageNum);
      this.params.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style lang="scss" scoped>

</style>