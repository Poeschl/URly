package xyz.poeschl.defendr.repositories

import jakarta.persistence.*
import org.springframework.data.domain.Sort
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepository : CrudRepository<Link, Long> {
  fun findAll(sort: Sort): List<Link>
}

@Entity
@Table(name = "links")
data class Link(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(insertable = false) val id: Long?,
  @Column(name = "original_url") var originalUrl: String,
  @Column(name = "tracking") var tracking: Boolean,
  @Column(name = "lengthening") var lengthening: Boolean,
  @Column(name = "defending") var defending: Boolean,

  )
