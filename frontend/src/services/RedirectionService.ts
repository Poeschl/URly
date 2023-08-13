import axios from "axios";

export default class RedirectionService {

  private baseRedirectionUrl = "/s"

  checkLink = (url: string): Promise<boolean> => {
    return axios.post(`${this.baseRedirectionUrl}/check`, {url: url})
      .then(data => data.data)
  }
}
