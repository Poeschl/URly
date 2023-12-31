package xyz.poeschl.urly.dtos

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import xyz.poeschl.urly.enums.LinkType
import xyz.poeschl.urly.repositories.Link
import java.time.ZonedDateTime

data class LinkDto(
        val id: Long,
        val redirectPath: String,
        val originalUrl: String,
        val type: LinkType,
        val tracking: Boolean,
        val defending: Boolean,
        val annoyingDefender: Boolean,
        val createdAt: ZonedDateTime
)

data class NewLinkDto(
        val originalUrl: String,
        val type: LinkType,
        val tracking: Boolean,
        val defending: Boolean,
        val annoyingDefender: Boolean
)

@Mapper
interface LinkDtoMapper {

  fun toDto(link: Link): LinkDto

  @Mapping(target = "createdAt", ignore = true)
  fun fromDto(linkDto: LinkDto): Link

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "redirectPath", constant = "")
  @Mapping(target = "createdAt", expression = "java( ZonedDateTime.now() )")
  fun fromNewDto(newLinkDto: NewLinkDto): Link
}
