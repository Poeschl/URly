<template>
  <div class="level">
    <div class="level-item">
      <progress v-if="imageLoading" class="progress is-loader is-large is-primary" max="100"></progress>
      <div v-if="!imageLoading" class="card">
        <div class="card-content">
          <div class="media">
            <div class="media-left">
              <figure class="image">
                <Elipsis/>
              </figure>
            </div>
            <div class="media-content">
              <p class="title is-4">The requested page will be "checked"...</p>
              <p class="subtitle is-6">In the meantime enjoy this wonderful cat. 😺</p>
            </div>
          </div>
        </div>
        <div class="card-image">
          <figure class="image">
            <img :src="imageToLoad" alt="Cat of this request">
          </figure>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref, watch} from "vue";
import Elipsis from "@/components/ElipsisSpinner.vue";

const route = useRoute()

const imageLoading = ref<boolean>(true)
const imageToLoad = "https://cataas.com/cat/cute"

onMounted(() => {
  setTimeout(waitForImageToBeLoaded, getRandomWaitTime(200, 1000));
})

watch<boolean>(() => imageLoading.value, (after: boolean) => {
  if (!after) {
    setTimeout(doRedirect, getRandomWaitTime(2000, 7000));
  }
})

const doRedirect = () => {
  const redirectUrl = route.query['🌐'];
  if (redirectUrl != null && redirectUrl.length > 0) {
    console.info("Redirect to " + redirectUrl)
    window.location.href = redirectUrl.toString();
  }
}

const waitForImageToBeLoaded = () => {
  const imagePromise = new Promise((resolve, reject) => {
    const img = new Image();
    img.src = imageToLoad;
    img.onload = resolve;
    img.onerror = reject
  })

  imagePromise.then(() => {
    imageLoading.value = false
  }).catch(() => {
      imageLoading.value = false
      console.error("Couldn't load image!")
    }
  )
}

const getRandomWaitTime = (min: number, max: number): number => {
  return Math.floor(Math.random() * (max - min + 1) + min)
}

</script>

<style scoped lang="scss">
.level-item {
  .title {
    margin-bottom: revert;
  }
}

.card {
  .card-image img {
    max-height: 72vh;
    object-fit: contain;
  }
}

.is-loader {
  width: 30rem;
}
</style>
