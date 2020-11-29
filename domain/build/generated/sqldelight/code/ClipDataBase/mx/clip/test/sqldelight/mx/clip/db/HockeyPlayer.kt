package mx.clip.test.sqldelight.mx.clip.db

import kotlin.Long
import kotlin.String

data class HockeyPlayer(
  val player_number: Long,
  val full_name: String
) {
  override fun toString(): String = """
  |HockeyPlayer [
  |  player_number: $player_number
  |  full_name: $full_name
  |]
  """.trimMargin()
}
