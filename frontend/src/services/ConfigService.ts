import axios from "axios";
import type PlausibleConfig from "@/models/PlausibleConfig";

export default class LinkService {

  private baseLinkUrl = "/rest/config"

  getPlausibleConfig = (): Promise<PlausibleConfig> => {
    return axios.get(`${this.baseLinkUrl}/plausible`)
      .then(response => response.data
      )
  }
}
