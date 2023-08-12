import type Link from "@/models/Link";
import axios from "axios";

export default class LinkService {

  private baseLinkUrl = "/rest/link"

  getAllLinks = (): Promise<Link[]> => {
    return axios.get(this.baseLinkUrl)
      .then(response =>
        response.data
          .map((origin: Link) => {
            return {
              "id": origin.id,
              "redirectPath": origin.redirectPath,
              "originalUrl": origin.originalUrl,
              "type": origin.type,
              "tracking": origin.tracking,
              "defending": origin.defending,
              "createdAt": new Date(origin.createdAt)
            }
          })
      )
  }

  saveLink = (link: Link): Promise<Link> => {
    return axios.post(this.baseLinkUrl, link)
      .then(response => {
        const link = response.data
        return {
          "id": link.id,
          "redirectPath": link.redirectPath,
          "originalUrl": link.originalUrl,
          "type": link.type,
          "tracking": link.tracking,
          "defending": link.defending,
          "createdAt": new Date(link.createdAt)
        }
      })
  }

  deleteLink = (link: Link): Promise<Link> => {
    return axios.delete(`${this.baseLinkUrl}/${link.id}`)
  }
}
