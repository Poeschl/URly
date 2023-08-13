<template>
  <div class="field">
    <div class="control">
      <label class="radio" v-for="button in values" :key="button.label">
        <input type="radio"
               :name="name"
               :value="button.value"
               :checked="button.default"
               v-model="value">
        {{ button.label }}
      </label>
    </div>
    <p
      v-if="props.help.length > 0"
      class="help"
    >
      {{ props.help }}
    </p>
  </div>
</template>

<script setup lang="ts">
import {ref, watch} from "vue";

interface radiobutton {
  label: string,
  value: string,
  default: boolean
}

const props = defineProps<{
  name: string,
  values: radiobutton[],
  help: string
}>()

const emit = defineEmits<{
  (e: 'update:value', val: string): void
}>()

const value = ref<string>(props.values.find((button) => button.default)?.value || "")

watch<string>(() => value.value, (after: string) => {
  emit('update:value', after)
})
</script>

<style scoped>

</style>
