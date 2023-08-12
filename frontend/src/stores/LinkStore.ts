import {defineStore} from "pinia";
import {ref} from "vue";
import type Link from "@/models/Link";
import LinkService from "@/services/LinkService";

const linkService = new LinkService()
export const useLinkStore = defineStore('linkStore', () => {
  const links = ref<Link[]>([])

  function updateLinks() {
    linkService.getAllLinks()
      .then(response => {
        links.value = []
        links.value = response
        return response
      })
      .catch(reason => {
        console.error(`Could not get link list. (${reason})`)
      })
  }

  function saveLink(link: Link): Promise<Link | void> {
    return linkService.saveLink(link)
      .then(response => {
        updateLinks()
        return response
      })
      .catch((reason) => {
        console.error(`Could not save link. (${reason})`)
      })
  }

  function deleteLink(link: Link): Promise<Link | void> {
    return linkService.deleteLink(link)
      .then(response => {
        updateLinks()
        return response
      })
      .catch((reason) => {
        console.error(`Could not delete link. (${reason})`)
      })
  }

  return {links, updateLinks, saveLink, deleteLink}
})
