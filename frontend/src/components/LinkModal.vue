<template>
  <div
    class="modal is-active"
  >
    <div
      class="modal-background"
    />
    <div class="modal-content">
      <div class="card">
        <div class="card-header">
          <h4
            class="card-header-title is-size-4"
          >
            Create new link
          </h4>
        </div>

        <div class="card-content form">
          <TextInput
            v-model:value="link.originalUrl"
            help="The target url"
            label="Original url"
            placeholder="Insert the full url to which should be redirected"
          />
          <RadioInput
            name="type"
            help="What type of url should be created."
            :values="[{label: 'Short url', value: 'SHORT', default: true}, {label: 'Really long url', value: 'LONG', default: false}]"
            @update:value="updateType"/>

          <CheckboxInput
            help="Should this redirection should be tracked?"
            :checked="false"
            label="Track with Plausible"
            v-model:value="link.tracking"
            :disabled="!configStore.plausibleConfig.enabled"
          />
          <CheckboxInput
            help="Should this redirection should use the annoying Defender?"
            :checked="false"
            label="Use the Defender page"
            v-model:value="link.defending"
            :disabled="false"/>

        </div>
        <div class="card-footer">
          <a
            class="card-footer-item"
            @click="save"
          >
            Save
          </a>
          <a
            class="card-footer-item"
            @click="$emit('close')"
          >
            Cancel
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import TextInput from "@/components/form/TextInput.vue";
import type Link from "@/models/Link";
import {LinkType} from "@/models/Link";
import {computed, ref} from "vue";
import RadioInput from "@/components/form/RadioInput.vue";
import CheckboxInput from "@/components/form/CheckboxInput.vue";
import {useConfigStore} from "@/stores/ConfigStore";

const configStore = useConfigStore()

const props = defineProps<{
  link: Link
}>()

const emit = defineEmits<{
  (e: 'close'): void
  (e: 'update:link', val: Link): void
}>()

const link = ref<Link>(props.link)
const isTrackingEnabled = computed<boolean>(() => configStore!!.plausibleConfig.enabled)

const updateType = (linkType: string) => {
  link.value.type = LinkType[linkType as keyof typeof LinkType]
}
const save = () => {
  emit("update:link", link.value)
}


</script>

<style scoped lang="scss">

</style>
