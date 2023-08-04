package xyz.poeschl.defendr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class DefendrApplication

fun main(args: Array<String>) {
  runApplication<DefendrApplication>(*args)
}
