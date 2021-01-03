package mx.clip.db.domain

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.reflect.KClass
import mx.clip.db.ClipDataBase
import mx.clip.test.sqldelight.mx.clip.db.CatalogItem
import mx.clip.test.sqldelight.mx.clip.db.CatalogQueries
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

  override val catalogQueries: CatalogQueriesImpl = CatalogQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE catalogItem(
          |    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
          |    name TEXT NOT NULL,
          |    price REAL NOT NULL
          |)
          """.trimMargin(), 0)
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

private class CatalogQueriesImpl(
  private val database: ClipDataBaseImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), CatalogQueries {
  internal val selectAll: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectAll(mapper: (
    id: Long,
    name: String,
    price: Double
  ) -> T): Query<T> = Query(604408121, selectAll, driver, "catalog.sq", "selectAll",
      "SELECT * FROM catalogItem") { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getDouble(2)!!
    )
  }

  override fun selectAll(): Query<CatalogItem> = selectAll { id, name, price ->
    mx.clip.test.sqldelight.mx.clip.db.CatalogItem(
      id,
      name,
      price
    )
  }

  override fun insertItem(name: String, price: Double) {
    driver.execute(-115817032, """INSERT INTO catalogItem(name, price) VALUES (?, ?)""", 2) {
      bindString(1, name)
      bindDouble(2, price)
    }
    notifyQueries(-115817032, {database.catalogQueries.selectAll})
  }

  override fun deleteItem(id: Long) {
    driver.execute(682606506, """DELETE FROM catalogItem WHERE id = ?""", 1) {
      bindLong(1, id)
    }
    notifyQueries(682606506, {database.catalogQueries.selectAll})
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
