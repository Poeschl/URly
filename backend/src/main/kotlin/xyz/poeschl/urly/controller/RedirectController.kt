package xyz.poeschl.urly.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.hashids.Hashids
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import xyz.poeschl.urly.dtos.CheckDto
import xyz.poeschl.urly.dtos.DefenderConfigDto
import xyz.poeschl.urly.repositories.Link
import xyz.poeschl.urly.repositories.LinkRepository
import xyz.poeschl.urly.services.PlausibleService
import xyz.poeschl.urly.services.RedirectionService
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Controller
@RequestMapping("/s")
class RedirectController(
    val redirectionService: RedirectionService,
    val plausibleService: PlausibleService,
    val linkRepository: LinkRepository
) {

  companion object {
    private const val NOT_FOUND_REDIRECT = "https://http.cat/status/404"
    private const val DEFENDER_REDIRECT_PATTERN = "/d/🕵?🌐=%s&⛵=%s"
    private val LOGGER = KotlinLogging.logger { }
  }

  private val hashId = Hashids("defender", 4)

  @GetMapping("/{token}")
  fun redirectShortcode(@PathVariable token: String, request: HttpServletRequest): ResponseEntity<Void> {
    val linkId = redirectionService.getLinkIdOfToken(token)

    linkId?.let { existingLinkId ->
      val link: Link? = linkRepository.findById(existingLinkId).orElse(null)
      link?.let { existingLink ->

        if (existingLink.tracking) {
          plausibleService.sendEventForRequest(request)
        }

        if (existingLink.defending) {
          LOGGER.debug { "Use defender for ${link.originalUrl}" }
          val encodedUrl = URLEncoder.encode(link.originalUrl, StandardCharsets.UTF_8)

          return ResponseEntity
              .status(HttpStatus.FOUND)
              .location(URI.create(DEFENDER_REDIRECT_PATTERN.format(encodedUrl, hashId.encode(link.id!!))))
              .build()
        } else {
          LOGGER.debug { "Redirecting to ${link.originalUrl}" }
          return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(existingLink.originalUrl)).build()
        }
      }
    }
    LOGGER.warn { "Rejecting token '${token}'" }
    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(NOT_FOUND_REDIRECT)).build()
  }

  @PostMapping("/defender", consumes = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseBody
  fun checkForKnownLink(@RequestBody check: CheckDto): DefenderConfigDto {
    val link = linkRepository.findFirstByIdAndOriginalUrl(hashId.decode(check.id)[0], check.url)
    return DefenderConfigDto(link.isPresent)
  }
}
