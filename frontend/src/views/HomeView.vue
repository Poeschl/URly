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
          @click="openCreationModal()"
        >
          <span class="icon">
            <FontAwesomeIcon
              class="is-small"
              icon="fa-solid fa-plus"
            />
          </span>
          <span>Add new link</span>
        </button>
        <a
          class="button ml-2"
          v-if="plausibleConfig.enabled"
          :href="plausibleConfig.dashboard"
          target="_blank"
        >
          <span>Plausible Dashboard</span>
          <span class="icon">
            <FontAwesomeIcon
              class="is-small"
              icon="fa-solid fa-up-right-from-square"
            />
          </span>
        </a>
      </div>
    </div>
  </div>
  <LinkList
    :links="links"
    @clicked:delete="deleteLink"
  />

  <LinkModal
    v-if="showCreateModal"
    :link="modalLink"
    @close="closeCreationModal"
    @update:link="saveNewLink"
  />
</template>

<script setup lang="ts">
import LinkList from "@/components/LinkList.vue";
import {useLinkStore} from "@/stores/LinkStore";
import {computed, onMounted, ref} from "vue";
import type Link from "@/models/Link";
import {createEmptyLink} from "@/models/Link";
import LinkModal from "@/components/LinkModal.vue";
import {toast} from "bulma-toast";
import copy from "copy-text-to-clipboard";
import {useConfigStore} from "@/stores/ConfigStore";
import type PlausibleConfig from "@/models/PlausibleConfig";

const linkStore = useLinkStore()
const configStore = useConfigStore()

const links = computed<Link[]>(() => linkStore.links)
const plausibleConfig = computed<PlausibleConfig>(() => configStore.plausibleConfig)

const showCreateModal = ref(false)
const modalLink = ref<Link>(createEmptyLink())

onMounted(() => {
  linkStore.updateLinks()
  configStore.updateConfig()
})

function openCreationModal() {
  modalLink.value = createEmptyLink()
  showCreateModal.value = true
}

function closeCreationModal() {
  showCreateModal.value = false
}

function saveNewLink(link: Link) {
  linkStore.saveLink(link)
    .then((link: Link | void) => {
      const url = window.location.origin + (link as Link).redirectPath
      copy(url)
      toast({message: "Copied created link into clipboard.", type: "is-success"})
    })
  closeCreationModal()
}

function deleteLink(link: Link) {
  linkStore.deleteLink(link)
    .then((link: Link | void) => {
      const url = window.location.origin + (link as Link).redirectPath
      copy(url)
      toast({message: "Link successfully deleted.", type: "is-success"})
    })
}
</script>
