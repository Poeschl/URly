package xyz.poeschl.urly.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import xyz.poeschl.urly.dtos.LinkDto
import xyz.poeschl.urly.dtos.LinkDtoMapper
import xyz.poeschl.urly.dtos.NewLinkDto
import xyz.poeschl.urly.exceptions.NotFoundException
import xyz.poeschl.urly.repositories.LinkRepository
import xyz.poeschl.urly.services.RedirectionService
import java.util.*

@RestController
@RequestMapping("rest/link")
@Transactional
class LinkController(
  private val linkDtoMapper: LinkDtoMapper,
  private val linkRepository: LinkRepository,
  private val redirectionService: RedirectionService
) {

  companion object {
    private val LOGGER = KotlinLogging.logger {}
  }

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getAll(): List<LinkDto> {
    val allLinks = linkRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
    return allLinks.map(linkDtoMapper::toDto)
  }

  @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getOne(@PathVariable id: Long): LinkDto {
    return linkRepository.findById(id)
      .map(linkDtoMapper::toDto)
      .orElseThrow { NotFoundException() }
  }

  @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
  @Transactional
  fun save(@RequestBody input: NewLinkDto): LinkDto {
    val fullDto = linkRepository.save(linkDtoMapper.fromNewDto(input))
    fullDto.redirectPath = redirectionService.getRedirectPathForLink(fullDto)
    val saved = linkRepository.save(fullDto)
    return linkDtoMapper.toDto(saved)
  }

  @DeleteMapping("/{id}")
  @Transactional
  fun delete(@PathVariable id: Long) {
    linkRepository.deleteById(id)
  }
}
