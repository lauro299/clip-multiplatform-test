package mx.clip.test.sqldelight.mx.clip.db

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Long
import kotlin.String

interface PlayerQueries : Transacter {
  fun <T : Any> selectAll(mapper: (player_number: Long, full_name: String) -> T): Query<T>

  fun selectAll(): Query<HockeyPlayer>

  fun insertItem(full_name: String, player_number: Long)
}
