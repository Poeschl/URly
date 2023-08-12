<template>
  <div class="list has-visible-pointer-controls">
    <LinkListElement
      v-for="currentLink in links"
      :key="currentLink.id"
      :link="currentLink"
      @clicked:delete="$emit('clicked:delete', $event)"/>
  </div>
</template>

<script setup lang="ts">
import LinkListElement from "@/components/LinkListElement.vue";
import type Link from "@/models/Link";

const props = defineProps<{
  links: Link[]
}>()

defineEmits<{
  (e: 'clicked:delete', val: Link): void
}>()

</script>

<style scoped>

</style>
