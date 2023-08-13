package xyz.poeschl.urly.repositories

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import xyz.poeschl.urly.enums.LinkType
import java.time.ZonedDateTime

@Repository
interface LinkRepository : CrudRepository<Link, Long> {
  fun findAll(sort: Sort): List<Link>

  fun existsByOriginalUrlAndDefending(string: String, defending: Boolean = true): Boolean
}

@Entity
@Table(name = "links")
data class Link(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(insertable = false) val id: Long?,
  @Column(name = "original_url") var originalUrl: String,
  @Column(name = "redirect_path") var redirectPath: String,
  @Enumerated(EnumType.STRING) @Column(name = "type") var type: LinkType,
  @Column(name = "tracking") var tracking: Boolean,
  @Column(name = "defending") var defending: Boolean,
  @CreatedDate @Column(name = "created_at") var createdAt: ZonedDateTime
  )
