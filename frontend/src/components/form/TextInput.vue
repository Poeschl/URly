<template>
  <div class="field">
    <label class="label">{{ props.label }}</label>
    <div class="control">
      <input
        class="input"
        type="text"
        :placeholder="props.placeholder"
        :value="value"
        @input="updateValue(($event.target as HTMLInputElement).value)"
      >
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
import {ref} from "vue";

const props = defineProps<{
  label: string,
  placeholder: string,
  value: any,
  help: string
}>()

const emit = defineEmits<{
  (e: 'update:value', val: string): void
}>()

const value = ref(props.value)

const updateValue = (newValue: string) => {
  value.value = newValue
  emit('update:value', newValue)
}
</script>

<style scoped>

</style>
