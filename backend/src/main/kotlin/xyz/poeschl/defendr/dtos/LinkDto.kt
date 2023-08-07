package xyz.poeschl.defendr.dtos

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import xyz.poeschl.defendr.repositories.Link

data class LinkDto(
  val id: Long,
  val processedUrl: String,
  val originalUrl: String,
  val tracking: Boolean,
  val lengthening: Boolean,
  val defending: Boolean
)

data class NewLinkDto(
  val originalUrl: String,
  val tracking: Boolean,
  val lengthening: Boolean,
  val defending: Boolean
)

@Mapper
interface LinkDtoMapper {

  //TODO: Hash-Mapping
  @Mapping(source = "id", target = "originalUrl")
  fun toDto(link: Link): LinkDto

  @InheritInverseConfiguration
  fun fromDto(linkDto: LinkDto): Link

  @Mapping(target = "id", ignore = true)
  fun fromNewDto(newLinkDto: NewLinkDto): Link

}
