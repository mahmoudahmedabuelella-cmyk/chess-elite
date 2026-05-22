package com.chess.elite.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pgnString: String,
    val result: String,
    val gameMode: String
)

@Dao
interface ChessDao {
    @Query("SELECT * FROM games ORDER BY id DESC")
    fun getAllGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: GameEntity)
}

@Database(entities = [GameEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun chessDao(): ChessDao
}