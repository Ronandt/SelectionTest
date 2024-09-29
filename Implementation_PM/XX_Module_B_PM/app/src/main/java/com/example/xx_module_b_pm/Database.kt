package com.example.xx_module_b_pm

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Flow

@Entity
data class CartItem(
    val title: String,
    val description: String,
    val price: Float,
    val imageName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0



)

@Dao
interface CartItemDao {
    @Query("SELECT * FROM cartitem")
    fun selectAll(): kotlinx.coroutines.flow.Flow<List<CartItem>>

    @Delete
    fun delete(cartItem: CartItem)

    @Insert
    fun insert(cartItem: CartItem)

    @Delete
    fun massDelete(cartItem: List<CartItem>)
}
@Database(version = 1, entities = [CartItem::class])
abstract class CoreDatabase(): RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}


object AppDatabase {
    private lateinit var db: CoreDatabase

    fun getDb(context: Context): CoreDatabase{
        if(!::db.isInitialized) {
           db =  Room.databaseBuilder(context, CoreDatabase::class.java, "database").build()
        }
        return db
    }
}



