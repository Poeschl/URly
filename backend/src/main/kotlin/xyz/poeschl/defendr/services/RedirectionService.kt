package xyz.poeschl.defendr.services

import io.github.oshai.kotlinlogging.KotlinLogging
import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import xyz.poeschl.defendr.repositories.Link

@Service
class RedirectionService {

  companion object {
    private val LOGGER = KotlinLogging.logger { }
    private const val DEFAULT_TOKEN_LENGTH = 6
  }

  @Value("\${LINKDEFENDR_HASH_SECRET:'Not very secret'}")
  private val hashSecret = ""

  private val hashIds = Hashids(hashSecret, DEFAULT_TOKEN_LENGTH)

  fun getRedirectPathForLink(link: Link): String {
    return getRedirectionPrefix(link) + getTokenForLinkId(link.id!!)
  }

  fun getLinkIdOfToken(token: String): Long? {
    return hashIds.decode(token).getOrNull(0)
  }

  private fun getTokenForLinkId(id: Long): String {
    return hashIds.encode(id)
  }

  private fun getRedirectionPrefix(link: Link): String {
    return when {
      link.lengthening -> "/l/"
      link.defending -> "/d/"
      else -> "/r/"
    }
  }
}
