<template>
  <div class="list-item box">

    <div class="list-item-content">
      <div class="level">
        <div class="level-item is-flex-grow-0">
          <div class="level nobm">
            <a
              class="level-item"
              title="Copy link to clipboard"
              @click="copyClicked">
              <div class="is-truncate">{{ link.redirectPath }}</div>
              <FontAwesomeIcon
                class="is-small ml-1 has-text-grey"
                icon="fa-regular fa-copy"
              />
            </a>
            <div class="level-item">
              <FontAwesomeIcon
                class="is-small mx-3 has-text-primary is-hidden-mobile"
                icon="fa-solid fa-arrow-right"
              />
              <FontAwesomeIcon
                class="is-small has-text-primary is-hidden-tablet"
                icon="fa-solid fa-arrow-down"
              />
            </div>
            <div class="level-item is-truncate">{{ link.originalUrl }}</div>
          </div>
        </div>
        <div class="level-item is-flex-grow-0">
          <div class="level is-mobile">
            <div class="level-item" :title="'Link Tracking: ' + link.tracking">
              <FontAwesomeIcon
                class="is-small mx-3"
                :class="{ 'has-text-grey' : !link.tracking, 'has-text-success' : link.tracking }"
                icon="fa-solid fa-chart-line"
              />
            </div>
            <div class="level-item" :title="'Link Defender: ' + link.tracking">
              <FontAwesomeIcon
                class="is-small mx-3"
                :class="{ 'has-text-grey' : !link.defending, 'has-text-success' : link.defending }"
                icon="fa-solid fa-shield-halved"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="list-item-title"></div>
    </div>

    <div class="list-item-controls">
      <div class="buttons is-right">
        <button
          class="button"
          @click="deleteLink"
        >
          <FontAwesomeIcon
            class="is-small has-text-danger"
            icon="fa-regular fa-trash-can"
          />
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type Link from "@/models/Link";
import copy from 'copy-text-to-clipboard';
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {toast} from "bulma-toast";

const props = defineProps<{
  link: Link
}>()

const emits = defineEmits<{
  (e: 'clicked:delete', val: Link): void
}>()

function copyClicked() {
  const url = window.location.origin + props.link.redirectPath
  copy(url)
  toast({message: 'Link copied into clipboard', type: 'is-success'})
}

function deleteLink() {
  emits('clicked:delete', props.link)
}
</script>

<style scoped lang="scss">
@import "@/assets/custom-variables";


.list-item-content .level {
  margin-bottom: 0;
}

.is-truncate {

  @media screen and (max-width: $tablet - 1) {
    max-width: 12rem;
  }

  @media screen and (min-width: $tablet) and (max-width: $desktop - 1px) {
    max-width: 16rem;
  }

  max-width: 24rem;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: inline-block;
}

.level.nobm {
  .level-item:not(:last-child) {
    margin-bottom: 0;
  }
}
</style>
