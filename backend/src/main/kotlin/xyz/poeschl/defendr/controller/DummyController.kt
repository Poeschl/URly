package xyz.poeschl.defendr.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/dummy")
@Transactional
class DummyController {

  companion object {
    private val LOGGER = KotlinLogging.logger {}
  }

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  fun test(): List<String> {
    LOGGER.info { "Test" }
    return listOf("This", "is", "Mario")
  }
}
