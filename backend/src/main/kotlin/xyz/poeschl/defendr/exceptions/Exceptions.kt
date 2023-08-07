package xyz.poeschl.defendr.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundException(reason: String = "") : ResponseStatusException(HttpStatus.NOT_FOUND, reason)
