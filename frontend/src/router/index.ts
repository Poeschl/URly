import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeView.vue'),
    },
    {
      path: "/d/%F0%9F%95%B5", // the encoded 🕵
      name: 'defender',
      component: () => import('../views/DefenderView.vue')
    }
  ]
})

export default router
