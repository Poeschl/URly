package xyz.poeschl.urly.services

import io.github.oshai.kotlinlogging.KotlinLogging
import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import xyz.poeschl.urly.enums.LinkType
import xyz.poeschl.urly.repositories.Link
import java.util.*

@Service
class RedirectionService {

  companion object {
    private val LOGGER = KotlinLogging.logger { }
    private const val TOKEN_PATH = "/s/"
    private const val DEFAULT_TOKEN_LENGTH = 5
    private const val DEFAULT_LONG_TOKEN_LENGTH = 1023
    private const val LONG_PREFIX = "l"
    private const val SHORT_PREFIX = "s"
  }

  @Value("\${URLY_HASH_SECRET:'Not very secret'}")
  private val hashSecret = ""

  private val shortHashIds = Hashids(hashSecret + LONG_PREFIX, DEFAULT_TOKEN_LENGTH)
  private val longHashIds = Hashids(hashSecret + SHORT_PREFIX, DEFAULT_LONG_TOKEN_LENGTH)

  fun getRedirectPathForLink(link: Link): String {
    return TOKEN_PATH + getTokenForLink(link)
  }

  fun getLinkIdOfToken(token: String): Long? {
    return when {
      token.startsWith(SHORT_PREFIX, true) -> shortHashIds.decode(token.removePrefix(SHORT_PREFIX)).firstOrNull()
      token.startsWith(LONG_PREFIX, true) -> longHashIds.decode(token.removePrefix(LONG_PREFIX)).firstOrNull()
      else -> {
        LOGGER.info { "Token with no prefix detected '$token'" }
        null
      }
    }
  }

  private fun getTokenForLink(link: Link): String {
    return when (link.type) {
      LinkType.SHORT -> SHORT_PREFIX + shortHashIds.encode(link.id!!)
      LinkType.LONG -> LONG_PREFIX + longHashIds.encode(link.id!!)
    }
  }
}
