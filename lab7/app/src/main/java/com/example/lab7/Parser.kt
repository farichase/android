package com.example.lab7

import android.content.Context
import androidx.room.*

@Entity(tableName = "books")
data class Parser(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val book: String,
    val author: String,
)

@Dao
interface ParserDao {
    @Query("SELECT * FROM books")
    suspend fun getAll(): List<Parser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg msg: Parser)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun delete(id: Int)
}

@Database(version = 1, entities = [Parser::class])
abstract class ParserDB : RoomDatabase() {
    abstract fun tableDao() : ParserDao

    companion object {
        @Volatile
        private var INSTANCE: ParserDB? = null

        fun getInst(context: Context): ParserDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParserDB::class.java,
                        "books_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
