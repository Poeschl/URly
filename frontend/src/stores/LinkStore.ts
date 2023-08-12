import {defineStore} from "pinia";
import {ref} from "vue";
import type Link from "@/models/Link";
import {LinkType} from "@/models/Link";

export const useLinkStore = defineStore('linkStore', () => {
  const links = ref<Link[]>([])

  function updateLinks() {
    links.value = []
    links.value = [
      {
        id: 1,
        redirectPath: '/s/s5inve09',
        originalUrl: 'https://poeschl.xyz',
        type: LinkType.SHORT,
        tracking: true,
        defending: false,
        createdAt: new Date(2023, 8, 10, 10, 10)
      },
      {
        id: 2,
        redirectPath: '/s/lsn9ß2jbß9incgp98jvßm90duß09wuv4ß9uiareßmv09u8bvmßw9vuqßt098paich0928h0ß9a8hcpna98ebzup90c8u3p98h9p3485c0ß98hvbpvhgmpaoihuvßq309vuscß8uß4c90ußq09uvß5inve09',
        originalUrl: 'https://google.de',
        type: LinkType.LONG,
        tracking: false,
        defending: true,
        createdAt: new Date(2023, 8, 10, 10, 20)
      },
      {
        id: 3,
        redirectPath: '/s/s908jg5pn',
        originalUrl: 'https://bulma.io/documentation/elements/box/ans/asd/asdfvipnwsgpoijcn/asdfgafg6516516/asfdgoihpoiubn?=234t9h',
        type: LinkType.SHORT,
        tracking: true,
        defending: true,
        createdAt: new Date(2023, 8, 10, 10, 30)
      },
      {
        id: 4,
        redirectPath: '/s/s9n0bqui',
        originalUrl: 'https://chaos.social.de',
        type: LinkType.SHORT,
        tracking: false,
        defending: false,
        createdAt: new Date(2023, 8, 11, 10, 30)
      }
    ]
  }

  return {links, updateLinks}
})
