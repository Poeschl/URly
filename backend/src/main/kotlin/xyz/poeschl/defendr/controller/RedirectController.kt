package xyz.poeschl.defendr.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import xyz.poeschl.defendr.repositories.Link
import xyz.poeschl.defendr.repositories.LinkRepository
import xyz.poeschl.defendr.services.RedirectionService
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Controller
class RedirectController(
  val redirectionService: RedirectionService,
  val linkRepository: LinkRepository
) {

  companion object {
    private const val NOT_FOUND_REDIRECT = "https://http.cat/status/404"
    private const val DEFENDER_REDIRECT_PATTERN = "/🕵?🌐=%s"
    private val LOGGER = KotlinLogging.logger { }
  }

  @GetMapping("/s/{token}")
  fun redirectShortcode(@PathVariable token: String): ResponseEntity<Void> {
    val linkId = redirectionService.getLinkIdOfToken(token)

    linkId?.let { existingLinkId ->
      val link: Link? = linkRepository.findById(existingLinkId).orElse(null)
      link?.let { existingLink ->

        if (existingLink.defending) {
          LOGGER.debug { "Use defender for ${link.redirectPath}" }
          val encodedUrl = URLEncoder.encode(link.originalUrl, StandardCharsets.UTF_8)

          return ResponseEntity
            .status(HttpStatus.FOUND)
            .location(URI.create(DEFENDER_REDIRECT_PATTERN.format(encodedUrl)))
            .build()
        } else {
          LOGGER.debug { "Redirecting ${link.redirectPath}" }
          return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(existingLink.originalUrl)).build()
        }
      }
    }
    LOGGER.warn { "Rejecting token '${token}'" }
    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(NOT_FOUND_REDIRECT)).build()
  }
}
