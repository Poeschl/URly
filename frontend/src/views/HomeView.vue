<template>
  <div class="level">
    <div class="level-left">
      <div class="level-item">
        <h1 class="is-size-2 mb-4">
          Your Links
        </h1>
      </div>
    </div>
    <div class="level-right">
      <div class="level-item">
        <button
          class="button is-primary"
          @click="openEditModal(null)"
        >
          <span class="icon">
            <FontAwesomeIcon
              class="is-small"
              icon="fa-solid fa-plus"
            />
          </span>
          <span>Add new link</span>
        </button>
      </div>
    </div>
  </div>
  <LinkList :links="links"/>
</template>

<script setup lang="ts">
import LinkList from "@/components/LinkList.vue";
import {useLinkStore} from "@/stores/LinkStore";
import {computed, onMounted} from "vue";
import type Link from "@/models/Link";

const linkStore = useLinkStore()

const links = computed<Link[]>(() => linkStore.links)

onMounted(() => {
  linkStore.updateLinks()
})

</script>
