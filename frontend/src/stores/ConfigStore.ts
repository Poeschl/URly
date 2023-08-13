import {defineStore} from "pinia";
import {ref} from "vue";
import type PlausibleConfig from "@/models/PlausibleConfig";
import ConfigService from "@/services/ConfigService"

const configService = new ConfigService()
export const useConfigStore = defineStore('configStore', () => {
  const plausibleConfig = ref<PlausibleConfig>({enabled: false, dashboard: ""})

  function updateConfig() {
    configService.getPlausibleConfig()
      .then(response => {
        plausibleConfig.value = response
        return response
      })
      .catch(reason => {
        console.error(`Could not get plausible config. (${reason})`)
      })
  }

  return {plausibleConfig, updateConfig}
})
