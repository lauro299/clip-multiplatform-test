package mx.clip.db.dataSources

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import mx.clip.db.ClipDataBase
import mx.clip.test.sqldelight.mx.clip.db.CatalogItem
import mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer

class ClipDataSourceImp(private val dataBase: ClipDataBase):ClipDataSource {
    override fun getPlayers(): Flow<List<HockeyPlayer>> {
        return dataBase?.playerQueries?.selectAll(
            mapper = {number, name -> HockeyPlayer(number, name)}
        )?.asFlow()?.mapToList() ?: flowOf(emptyList())
    }

    override fun insertPlayer(player: HockeyPlayer) {
        dataBase?.playerQueries?.insertItem(player.full_name, player.player_number)
    }
}

class ClipItemCatalogDataSourceImp(private val dataBase: ClipDataBase): ClipItemCatalog{
    override fun delete(item: CatalogItem) {
        dataBase.catalogQueries.deleteItem(item.id)
    }

    override fun getItems(): Flow<List<CatalogItem>> {
        return dataBase.catalogQueries.selectAll(
            mapper = { id, name, price-> CatalogItem(id, name, price)
            }).asFlow().mapToList()
    }

    override fun insert(item: CatalogItem) {
        dataBase.catalogQueries.insertItem(item.name, item.price)
    }
}
