package mx.clip.db.domain

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.reflect.KClass
import mx.clip.db.ClipDataBase
import mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer
import mx.clip.test.sqldelight.mx.clip.db.PlayerQueries

internal val KClass<ClipDataBase>.schema: SqlDriver.Schema
  get() = ClipDataBaseImpl.Schema

internal fun KClass<ClipDataBase>.newInstance(driver: SqlDriver): ClipDataBase =
    ClipDataBaseImpl(driver)

private class ClipDataBaseImpl(
  driver: SqlDriver
) : TransacterImpl(driver), ClipDataBase {
  override val playerQueries: PlayerQueriesImpl = PlayerQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE hockeyPlayer(
          |  player_number INTEGER NOT NULL,
          |  full_name TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null,
          "INSERT INTO hockeyPlayer(player_number, full_name) VALUES (15, 'Test text clip')", 0)
      driver.execute(null, "CREATE INDEX hockeyPlayer_full_name ON hockeyPlayer(full_name)", 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class PlayerQueriesImpl(
  private val database: ClipDataBaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), PlayerQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectAll(mapper: (player_number: Long, full_name: String) -> T): Query<T>
      = Query(728636091, selectAll, driver, "Player.sq", "selectAll", "SELECT * FROM hockeyPlayer")
      { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!
    )
  }

  override fun selectAll(): Query<HockeyPlayer> = selectAll { player_number, full_name ->
    mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer(
      player_number,
      full_name
    )
  }

  override fun insertItem(full_name: String, player_number: Long) {
    driver.execute(-559717258,
        """INSERT OR REPLACE INTO hockeyPlayer(full_name, player_number) VALUES (?,?)""", 2) {
      bindString(1, full_name)
      bindLong(2, player_number)
    }
    notifyQueries(-559717258, {database.playerQueries.selectAll})
  }
}
