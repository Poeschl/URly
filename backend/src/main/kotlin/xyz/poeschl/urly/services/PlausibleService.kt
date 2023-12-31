package xyz.poeschl.urly.services

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import xyz.poeschl.urly.dtos.PlausibleConfigDto

@Service
class PlausibleService {

  companion object {
    private val LOGGER = KotlinLogging.logger { }
    private const val PLAUSIBLE_EVENT_PATH_PATTERN = "%s/api/event"
  }

  @Value("\${URLY_PLAUSIBLE_ENABLED:false}")
  private val enabled = false

  @Value("\${URLY_PLAUSIBLE_DOMAIN:}")
  private val domain = ""

  @Value("\${URLY_PLAUSIBLE_API_HOST:https://plausible.io}")
  private val apiHost = "https://plausible.io"

  private val restTemplate = RestTemplate()

  @PostConstruct
  fun annonce() {
    if (enabled) {
      LOGGER.info { "Link tracking enabled with domain '${domain}' and api host '${apiHost}'" }
    } else {
      LOGGER.info { "!!! Link tracking is not possible, since the Plausible integration is disabled !!!" }
    }
  }

  fun sendEventForRequest(request: HttpServletRequest) {
    if (!enabled) {
      return
    }

    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    headers.add("User-Agent", request.getHeader("User-Agent"))
    headers.add("X-Forwarded-For", request.getHeader("X-Forwarded-For") ?: request.remoteHost)

    val event = PlausibleEvent(request.requestURL.toString(), domain, request.getHeader("referer").orEmpty())
    val entity = HttpEntity(event, headers)

    val response =
        restTemplate.exchange(PLAUSIBLE_EVENT_PATH_PATTERN.format(apiHost), HttpMethod.POST, entity, Void::class.java)
    LOGGER.debug { "Sent tracking event" }

    if (!response.statusCode.is2xxSuccessful) {
      LOGGER.warn { "Tracking event was not sent successfully! \n ${response.body}" }
    }
  }

  fun getPlausibleInfo(): PlausibleConfigDto {
    val dashboardUrl = if (enabled) {
      "$apiHost/$domain"
    } else {
      ""
    }

    return PlausibleConfigDto(enabled, dashboardUrl)
  }

  data class PlausibleEvent(
      val url: String,
      val domain: String,
      val referrer: String,
      val name: String = "pageview"
  )
}
