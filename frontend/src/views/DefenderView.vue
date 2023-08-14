<template>
    <div class="level">
        <div class="level-item" :class="{ 'glitch': glitch }" ref="pageContainer">
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
                    <figure class="image" :class="{ 'glitch': glitch }">
                        <img class="is-radiusless" :src="imageToLoad" alt="Cat of this request">
                    </figure>
                </div>
                <div class="is-flex is-justify-content-center p-3">
                    <p>Presented by <a href="https://github.com/Poeschl/URly">URly</a> and <a href="https://cataas.com">CATAAS</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref, watch} from "vue";
import Elipsis from "@/components/ElipsisSpinner.vue";
import RedirectionService from "@/services/RedirectionService";
import type DefenderConfig from "@/models/DefenderConfig";
import type CheckRequest from "@/models/CheckRequest";

const route = useRoute()
const redirecionService = new RedirectionService()

const imageLoading = ref<boolean>(true)
const glitch = ref<boolean>(false)

const imageToLoad = "https://cataas.com/cat/cute"

onMounted(() => {
  setTimeout(waitForImageToBeLoaded, getRandomWaitTime(200, 1000));
})

watch<boolean>(() => imageLoading.value, (after: boolean) => {
  if (!after) {
    setTimeout(attemptRedirect, getRandomWaitTime(2000, 7000));
  }
})

const attemptRedirect = () => {
  const redirectUrl = route.query['🌐'];
  const id = route.query['⛵'];

  if (redirectUrl != null && redirectUrl.length > 0 && id != null && id.length > 0) {
    const decodedUrl = decodeURI(redirectUrl.toString())
    const request: CheckRequest = {id: id.toString(), url: decodedUrl}

    redirecionService.checkLink(request)
      .then((config: DefenderConfig) => {
        if (config.allowed) {
          redirectTo(redirectUrl.toString())
        } else {
          glitch.value = true
        }
      })
      .catch(reason => {
        console.error(`Could not check url. (${reason})`)
        glitch.value = true
      })
  }
}

const redirectTo = (url: string) => {
  console.info("Redirect to " + url)
  window.location.href = url;
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
@import "@/assets/glitch";

.level-item {
  .title {
    margin-bottom: revert;
  }
}

.card {
  .card-image {
    img {
      max-height: 72vh;
      object-fit: contain;
    }

    .glitch {
      animation-delay: 2s;
    }
  }
}

.is-loader {
  width: 30rem;
}
</style>
