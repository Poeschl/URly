import axios from "axios";
import type DefenderConfig from "@/models/DefenderConfig";
import type CheckRequest from "@/models/CheckRequest";

export default class RedirectionService {

  private baseRedirectionUrl = "/s"

  checkLink = (request: CheckRequest): Promise<DefenderConfig> => {
    return axios.post(`${this.baseRedirectionUrl}/defender`, request)
      .then(data => data.data)
  }
}
