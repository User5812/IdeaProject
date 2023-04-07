import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [
    //====   Login    ====
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/management/login/login.vue')
  },
  //====   UserHome    ====
  {
    path: '/front',
    name: 'userLogin',
    component: () => import('@/views/userfront/Layout.vue'),
    redirect: '/front/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/userfront/home/home.vue')
      },
      {
        path: 'goods',
        name: 'Goods',
        component: () => import('@/views/userfront/goods/goods.vue')
      },
      {
        path: 'cart',
        name: 'Cart',
        component: ()=> import('@/views/userfront/cart/cart.vue')
      },
      {
        path: 'search',
        name: 'Search',
        component: ()=> import('@/views/userfront/search/search.vue')
      },
      {
        path: 'person',
        name: 'Person',
        component: ()=> import ('@/views/management/home/HomeView.vue')
      },
      {
        path: 'collect',
        name: 'Collect',
        component: ()=> import('@/views/userfront/collect/collect.vue')
      },
      {
        path: 'address',
        name: 'Address',
        component: ()=> import('@/views/userfront/address/address.vue')
      },
      {
        path: 'order',
        name: 'Order',
        component: ()=> import('@/views/userfront/order/order.vue')
      },
      {
        path: 'preOrder',
        name: 'PreOrder',
        component: ()=> import('@/views/userfront/preOrder/preOrder.vue')
      }
    ]
  },
    //====   Home    ====
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [
      // ====   User    ====
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/management/home/HomeView.vue')
      },
      {
        path: 'userList',
        name: 'User',
        component: () => import('@/views/management/user/User.vue')
      },
      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('@/views/management/user/EditUser.vue')
      },
      // ====   Admin    ====
      {
        path: 'adminList',
        name: 'Admin',
        component: () => import('@/views/management/admin/List.vue')
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('@/views/management/admin/Add.vue')
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/management/admin/Edit.vue')
      },
      // ====   Goods    ====
      {
        path: "goodsList",
        name: "Goods",
        component: () => import('@/views/management/goods/Goods.vue')
      },
      {
        path: 'editGoods',
        name: 'EditGoods',
        component: () => import('@/views/management/goods/EditGoods.vue')
      },
      // ====   Category    ====
      {
        path: 'CategoryList',
        name: 'Category',
        component: () => import('@/views/management/category/List.vue')
      },
      {
        path: 'addCategory',
        name: 'AddCategory',
        component: () => import('@/views/management/category/Add.vue')
      },
      {
        path: 'editCategory',
        name: 'EditCategory',
        component: () => import('@/views/management/category/Edit.vue')
      },
      // ====   Address    ====
      {
        path: 'addressList',
        name: 'Address',
        component: () => import('@/views/management/address/List.vue')
      },
      {
        path: 'addAddress',
        name: 'AddAddress',
        component: () => import('@/views/management/address/Add.vue')
      },
      {
        path: 'editAddress',
        name: 'EditAddress',
        component: () => import('@/views/management/address/Edit.vue')
      },
      // ====   Order    ====
      {
        path: 'orderList',
        name: 'Order',
        component: () => import('@/views/management/order/List.vue')
      },
    ]
  },

  {
    path: '*',
    name: '404',
    component: () => import('@/views/404.vue')
  }


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



router.beforeEach((to, from, next) => {
  if (to.path === '/login' ) next()
  const admin = Cookies.get("admin")
  const user = Cookies.get("user")
  if (!admin && to.path !== '/login') {
    if(!user && to.path !== '/login') return next("/login")
  }
  next()
})

export default router
