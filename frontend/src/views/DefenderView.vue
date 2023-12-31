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
              <p class="subtitle is-6 mt-2" v-if="msDefenderDetected">A Microsoft Defender Referrer is detected.<br/>
                Optional checks will be done.</p>
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
const msDefenderDetected = ref<boolean>(false)

const imageToLoad = "https://cataas.com/cat/cute"

onMounted(() => {
  detectSpecialReferrer()
  setTimeout(waitForImageToBeLoaded, getRandomWaitTime(200, 1000));
})

watch<boolean>(() => imageLoading.value, (after: boolean) => {
  if (!after) {
    setTimeout(attemptRedirect, getRandomWaitTime(2000, 7000));
  }
})

const detectSpecialReferrer = () => {
  const referrer = route.query['🪁'];
  if (referrer != null && referrer.toString().endsWith('.mcas.ms')) {
    console.info("Detected Defender referrer")
    msDefenderDetected.value = true
  }
}

const attemptRedirect = () => {
  const redirectUrl = route.query['🌐'];
  const id = route.query['⛵'];

  if (redirectUrl != null && redirectUrl.length > 0 && id != null && id.length > 0) {
    const decodedUrl = decodeURI(redirectUrl.toString())
    const request: CheckRequest = {id: id.toString(), url: decodedUrl}

    redirecionService.checkLink(request)
      .then((config: DefenderConfig) => {
        if (config.allowed) {
          let additionalTimeOut = 0
          let url = redirectUrl.toString()

          if (config.annoying) {
            url = transformToAnnoyingUrl(url)
          }

          if (msDefenderDetected.value) {
            additionalTimeOut += 3000
          }

          setTimeout(() => redirectTo(url), additionalTimeOut);
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

const transformToAnnoyingUrl = (url: string): string => {
  let newUrl = new URL(url)

  newUrl.protocol = "https:"

  if (newUrl.hash.length > 0) {
    newUrl.hash = "#not-anymore"
  }

  newUrl.searchParams.set(getRandomEmoji(4), getRandomLetters(32))
  newUrl.searchParams.set(getRandomLetters(8), getRandomEmoji(8))

  return newUrl.toString()
}

const getRandomEmoji = (length: number): string => {
  return getRandomOf(length, [
    "😺", "😸", "😼", "🙀", "🖥", "😀", "😃", "😄", "🚗", "😁", "😎", "😉", "😱", "🔧", "🔨", "🏠", "😨",
    "😰", "😥", "😓", "⌚️", "🚢", "💻", "⛳️", "🏹", "🎣", "🤿", "🚴"])
}

const getRandomLetters = (length: number): string => {
  return getRandomOf(length, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789")
}

const getRandomOf = (length: number, characters: string | string[]): string => {
  let result = '';
  const charactersLength = characters.length;
  let counter = 0;
  while (counter < length) {
    if (typeof characters == 'string') {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    } else {
      result += characters[(Math.floor(Math.random() * charactersLength))];
    }
    counter += 1;
  }
  return result;
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
