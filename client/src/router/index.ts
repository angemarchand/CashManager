import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/register.vue'
import Cart from '@/views/Cart.vue'
import OrderSummary from "@/views/OrderSummary.vue";
import OrderConfirmation from "@/views/OrderConfirmation.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/register',
      name: 'register',
      component: Register,
    },
    {
      path: '/cart',
      name: 'cart',
      component: Cart,
    },
    {
      path: '/order-summary',
      name: 'order-summary',
      component: OrderSummary
    },
    {
      path: '/order-confirmation',
      name: 'order-confirmation',
      component: OrderConfirmation,
    }
    //{
    //path: '/about',
    //name: 'about',
    // route level code-splitting
    // this generates a separate chunk (About.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    //component: () => import('../views/AboutView.vue'),
    //},
  ],
})

export default router
