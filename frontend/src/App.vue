<template>
  <div>
    <NavBar class="mb-5"/>
    <main class="container">
      <div class="notifications" ref="notificationContainer"></div>
      <router-view/>
    </main>
  </div>
</template>

<script setup lang="ts">
import NavBar from './components/NavBar.vue'
import * as bulmaToast from "bulma-toast";
import {onMounted, ref} from "vue";

const notificationContainer = ref<Node>()

onMounted(() => {
  console.info(`Swagger UI: ${window.location.origin}/rest/swagger-ui`)

  bulmaToast.setDefaults({
    duration: 3000,
    pauseOnHover: true,
    appendTo: notificationContainer.value
  })
})
</script>
