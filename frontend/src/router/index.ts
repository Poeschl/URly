import {createRouter, createWebHashHistory} from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: "/%F0%9F%95%B5", // the encoded 🕵
      name: 'defender',
      component: () => import('../views/DefenderView.vue')
    }
  ]
})

export default router
