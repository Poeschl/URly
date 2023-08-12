<template>
  <div class="field">
    <div class="control">
      <label class="checkbox">
        <input type="checkbox"
               :checked="checked"
               v-model="value">
        {{ label }}
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

const props = defineProps<{
  label: string,
  value: boolean,
  checked: boolean,
  help: string
}>()

const emit = defineEmits<{
  (e: 'update:value', val: string): void
}>()

const value = ref<string>(props.value)

watch<string>(() => value.value, (after: string) => {
  emit('update:value', after)
})
</script>

<style scoped>

</style>
