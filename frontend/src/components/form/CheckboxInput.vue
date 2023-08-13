<template>
  <div class="field">
    <div class="control">
      <label class="checkbox">
        <input type="checkbox"
               :checked="checkboxChecked"
               :disabled="disabled"
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
import {computed, ref, watch} from "vue";

const props = defineProps<{
  label: string,
  value: boolean,
  checked: boolean,
  help: string,
  disabled: boolean
}>()

const emit = defineEmits<{
  (e: 'update:value', val: boolean): void
}>()

const value = ref<boolean>(props.value)

const checkboxChecked = computed<boolean>(() => {
  if (!props.disabled) {
    return props.checked
  } else {
    return false
  }
})

watch<boolean>(() => value.value, (after: boolean) => {
  emit('update:value', after)
})
</script>

<style scoped>

</style>
