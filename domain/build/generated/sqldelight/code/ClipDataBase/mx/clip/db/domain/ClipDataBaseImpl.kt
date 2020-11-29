package mx.clip.db.domain

import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import kotlin.Int
import kotlin.reflect.KClass
import mx.clip.db.ClipDataBase
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
) : TransacterImpl(driver), PlayerQueries
