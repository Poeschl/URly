package xyz.poeschl.defendr.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import xyz.poeschl.defendr.dtos.LinkDto
import xyz.poeschl.defendr.dtos.LinkDtoMapper
import xyz.poeschl.defendr.dtos.NewLinkDto
import xyz.poeschl.defendr.exceptions.NotFoundException
import xyz.poeschl.defendr.repositories.LinkRepository
import java.util.*

@RestController
@RequestMapping("rest/link")
@Transactional
class LinkController(
  private val linkDtoMapper: LinkDtoMapper,
  private val linkRepository: LinkRepository
) {

  companion object {
    private val LOGGER = KotlinLogging.logger {}
  }

  @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getAll(): List<LinkDto> {
    val allLinks = linkRepository.findAll(Sort.by("id"))
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
    val saved = linkRepository.save(linkDtoMapper.fromNewDto(input))
    return linkDtoMapper.toDto(saved)
  }

  @DeleteMapping("/{id}")
  @Transactional
  fun delete(@PathVariable id: Long) {
    linkRepository.deleteById(id)
  }
}
