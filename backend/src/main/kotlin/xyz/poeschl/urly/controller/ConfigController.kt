package xyz.poeschl.urly.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.poeschl.urly.dtos.PlausibleInfoDto
import xyz.poeschl.urly.services.PlausibleService

@RestController
@RequestMapping("rest/config")
class ConfigController(
    private val plausibleService: PlausibleService
) {

  @GetMapping("/plausible", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getPlausibleInfo(): PlausibleInfoDto {
    return plausibleService.getPlausibleInfo()
  }
}
