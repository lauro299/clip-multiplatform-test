package mx.clip.db.dataSources

import kotlinx.coroutines.flow.Flow
import mx.clip.test.sqldelight.mx.clip.db.HockeyPlayer

interface ClipDataSource {
    fun insertPlayer(player:HockeyPlayer)
    fun getPlayers():Flow<List<HockeyPlayer>>
}