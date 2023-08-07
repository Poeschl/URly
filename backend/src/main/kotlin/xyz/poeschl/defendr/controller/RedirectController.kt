package xyz.poeschl.defendr.controller

import org.springframework.http.ResponseEntity
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
    private val NOT_FOUND_REDIRECT = "https://http.cat/status/404"
  }

  @GetMapping("/r/{token}")
  fun redirectShortcode(@PathVariable token: String): String {
    val linkId = redirectionService.getLinkIdOfToken(token)

    val url = if (linkId != null) {
      linkRepository.findById(linkId).map(Link::originalUrl).orElse(NOT_FOUND_REDIRECT)
    } else {
      NOT_FOUND_REDIRECT
    }

    return "redirect:${url}"
  }

  @GetMapping("/l/{token}")
  fun redirectLongCode(@PathVariable token: String): ResponseEntity<Void> {
    return ResponseEntity.status(418).build()
  }

  @GetMapping("/d/{token}")
  fun redirectDefender(@PathVariable token: String): ResponseEntity<Void> {
    return ResponseEntity.status(418).build()
  }
}
