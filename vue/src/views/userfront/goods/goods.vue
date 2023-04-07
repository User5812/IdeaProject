<template>
  <div>

    <div style="margin-top: 10px">
      <!--      商品-->
      <el-row :gutter="10">
        <!--    图片-->
        <el-col :span="8">
          <el-card>
            <div>
              <el-image :src="goods.imgs"></el-image>
            </div>
            <div
                style="margin-top: 20px; display: flex;  flex-wrap: wrap; flex-direction: row; justify-content: flex-start">
                <el-image style="width: 60px; height: 60px;" fit="contain" :src="goods.imgs"
                          @mouseover="show(goods.imgs)"></el-image>
            </div>
          </el-card>
        </el-col>

        <!--    商品细节-->
        <el-col :span="16">
          <el-card>
            <div style="text-align: right">
              <div style="display: inline-block; cursor: pointer;"  @click="collect">
                <img :src="collectActiveIcon" alt="收藏" style="width: 15px; ">
                <span style="position: relative; bottom: 3px; font-size: 12px; left: 5px; color: #666">收藏</span>
              </div>
            </div>
            <div style="padding: 10px 0; font-size: 20px; font-weight: bold">{{ goods.name }}</div>
            <div style="padding: 10px 0; color: #666; font-size: 14px">{{ goods.description }}</div>
            <div style="padding: 10px 0; color: #999;">
              <span>上架时间</span>
              <span style="margin-left: 20px; color: #666">{{ goods.createTime }}</span>
            </div>

            <div style="padding: 10px 0">
              <span style="color: #999">抢购价</span>
              <span style="margin-left: 10px; color: orangered; font-size: 20px">￥ {{ (goods.price * goods.discount).toFixed(2) }}</span>
              <span style="color: orangered; margin-left: 10px" v-if="goods.discount < 1"> ({{ (goods.discount * 10).toFixed(1) }}折)</span>
            </div>

            <div v-if="goods.discount < 1" style="padding: 10px 0; text-decoration:line-through; color: #999">
              原价：{{ goods.price }}
            </div>
            <div style="padding: 10px 0; color: #999">
              库存：{{ goods.store }}
            </div>

            <div style="padding: 10px 0; color: #999;">
              <span>备注</span>
              <span style="margin-left: 20px; color: #666">
                本平台专注与校园二手交易，信息仅供参考，实际以卖家描述为准
              </span>
            </div>

            <div style="margin-top: 20px; padding: 10px 0">
              <el-input-number v-model="num" :min="1" :max="10" label="购买数量" style="width: 100px"></el-input-number>
              <el-button style="margin-left: 10px; background-color: orangered; color: white; position: relative"
                         @click="addCart">
                <img :src="cartIcon" style="width: 20px; position: absolute; left: 8px; top: 5px">
                <span style="margin-left: 20px">加入购物车</span>
              </el-button>
            </div>

          </el-card>
        </el-col>

      </el-row>

      <!--      评论-->
      <el-row>
        <el-col :span="24">
          <div style="margin-top: 10px; margin-bottom: 80px">
            <el-card>
              <!--              <div style="padding: 20px; color: #888">-->
              <!--                <div>-->
              <!--                  <el-input type="textarea" :rows="3" v-model="entity.content"></el-input>-->
              <!--                  <div style="text-align: right; padding: 10px"><el-button type="primary" @click="save">评论</el-button></div>-->
              <!--                </div>-->
              <!--              </div>-->

              <div
                  style="padding-bottom: 10px; margin-bottom: 20px; border-bottom: 2px solid orangered; font-size: 20px">
                商品评论
                  <el-button style="float: right" @click="setVisible">发表评论</el-button>
              </div>

              <div style="display: flex; padding: 20px" v-for="item in messages">
                <div style="text-align: center; flex: 1">
                  <el-image :src="diana" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
                </div>
                <div style="padding: 0 10px; flex: 5">
                  <div><b style="font-size: 14px">{{ item.username }}</b></div>
                  <div style="padding: 10px 0; color: #888">
                    {{ item.content }}
                    <el-button type="text" size="mini" @click="del(item)" style="float: right">删除
                    </el-button>
                  </div>
                  <div style="color: #888; font-size: 12px">
                    <span>{{ item.time }}</span>
                    <!--                    <el-button type="text" style="margin-left: 20px" @click="reReply(item.id)">回复</el-button>-->
                  </div>
                </div>
              </div>

              <el-dialog title="回复信息" :visible.sync="dialogFormVisible" width="30%"
                         :close-on-click-modal="false">
                <el-form :model="entity">
                  <el-form-item label="内容" label-width="100px">
                    <el-input v-model="entity.reply" autocomplete="off" type="textarea" :rows="3"></el-input>
                  </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="cancel">取 消</el-button>
                  <el-button type="primary" @click="reply">确 定</el-button>
                </div>
              </el-dialog>
            </el-card>
          </div>
        </el-col>
      </el-row>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from "js-cookie";


export default {
  name: "Goods",
  data() {
    return {
      admin: Cookies.get('admin')? JSON.parse(Cookies.get('admin')): {},
      messages: [],
      dialogFormVisible: false,
      entity: {},
      address: '',
      mainImg: '',
      cartIcon: require('../../../assets/购物车-23.png'),
      collectActiveIcon: require("../../../assets/收藏-激活.png"),
      diana: require("../../../assets/diana.jpg"),
      num: 1,
      id: 1,
      user: {},
      goods: {imgs: []},
      praiseFlag: false
    };
  },
  created() {
    this.id = this.$route.query.id
    this.load()
  },
  methods: {
    collect() {
      request.post("/collect/insertCollect", {
        goodsName: this.goods.name,
        goodsImg: this.goods.imgs,
        goodsId: this.goods.id,
        userId: this.admin.id
      }).then(res => {
        if (res.code === '200') {
          this.$message({
            message: "收藏成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.load();
      })
    },
    loadMessage() {
      request.get("/message/listMessage/" + this.goods.id).then(res => {
        this.messages = res.data;
      })
    },
    save() {  // 新增评论
      if (!this.admin.username) {
        this.$message({
          message: "请登录",
          type: "warning"
        });
        return;
      }
      if (!this.entity.content) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      request.post("/message/insertMessage", {goodsId: this.goods.id, userId: this.admin.id, username: this.admin.username, content: this.entity.reply}).then(res => {
        if (res.code === '200') {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity = {}
        this.loadMessage();
        this.dialogFormVisible = false;
      })
    },
    cancel() {
      this.dialogFormVisible = false;
      this.entity = {};
    },
    reply() {
      this.entity.content = this.entity.reply;
      this.save();
    },
    del(item) {
      console.log("您点击了删除按钮")
      if(item.username !== this.admin.username) {
        this.$notify.error("您无法删除他人的评论");
        return;
      }
      request.delete("/message/deleteMessage/" + item.id).then( res => {
        if (res.code === '200'){
          this.$message({
            type: 'success',
            message: '删除成功！'
          })
        }else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
      this.loadMessage()
    },
    addCart() {
      if(this.num > this.goods.store){
        this.$notify.error("库存不足！")
        return
      }
      request.post("/cart/insertCart", {goodsId: this.goods.id, count: this.num, userId: this.admin.id}).then(res => {
        if (res.code === '200') {
          this.$message({
            type: 'success',
            message: '加入成功！'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    },
    show(img) {
      this.mainImg = img
      console.log(img)
    },
    setVisible() {
      this.dialogFormVisible = true;
    },
    load() {
      request.get("/goods/" + this.id).then(res => {
        this.goods = res.data

        this.loadMessage()
      })
    },
  },
};
</script>

<style scoped>

</style>
