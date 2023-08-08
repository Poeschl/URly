package xyz.poeschl.defendr.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import xyz.poeschl.defendr.repositories.Link
import xyz.poeschl.defendr.repositories.LinkRepository
import xyz.poeschl.defendr.services.RedirectionService

@Controller
class RedirectController(
  val redirectionService: RedirectionService,
  val linkRepository: LinkRepository
) {

  companion object {
    private const val NOT_FOUND_REDIRECT = "https://http.cat/status/404"
    private val LOGGER = KotlinLogging.logger { }
  }

  @GetMapping("/s/{token}")
  fun redirectShortcode(@PathVariable token: String): String {
    val linkId = redirectionService.getLinkIdOfToken(token)

    linkId?.let { existingLinkId ->
      val link: Link? = linkRepository.findById(existingLinkId).orElse(null)
      link?.let { existingLink ->

        LOGGER.debug { "Redirecting ${link.redirectPath}" }
        return "redirect:${existingLink.originalUrl}"
      }
    }
    LOGGER.warn { "Rejecting token '${token}'" }
    return "redirect:${NOT_FOUND_REDIRECT}"
  }
}
