package mx.clip.db

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.db.SqlDriver
import mx.clip.db.domain.newInstance
import mx.clip.db.domain.schema
import mx.clip.test.sqldelight.mx.clip.db.CatalogQueries
import mx.clip.test.sqldelight.mx.clip.db.PlayerQueries

interface ClipDataBase : Transacter {
  val playerQueries: PlayerQueries

  val catalogQueries: CatalogQueries

  companion object {
    val Schema: SqlDriver.Schema
      get() = ClipDataBase::class.schema

    operator fun invoke(driver: SqlDriver): ClipDataBase = ClipDataBase::class.newInstance(driver)}
}
