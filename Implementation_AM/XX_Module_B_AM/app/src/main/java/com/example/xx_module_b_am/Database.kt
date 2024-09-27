package com.example.xx_module_b_am

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity
data class Note(
    val subject: String,
    val description: String,
    val imageUri: String,
    val date: Long,
    val favourite: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    suspend fun selectAll(): List<Note>

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note:  Note)

    @Query("SELECT *  FROM Note WHERE id = :id")
    suspend fun select(id: Int): Note
}

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase(): RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

object CoreDatabase {
    private lateinit var db: AppDatabase

    fun getDb(context: Context): AppDatabase {
        if(!::db.isInitialized) {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "Database").build()
        }
        return db

    }
}
