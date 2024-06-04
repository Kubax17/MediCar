package pl.kucharski.autoservice.room

import RoomForeignKeysCallback
import android.content.Context
import androidx.room.*
import pl.kucharski.autoservice.TransactionsDao
import pl.kucharski.autoservice.room.Transaction


@Database(
    entities = [Transaction::class, User::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun TransactionsDao(): TransactionsDao
    abstract fun UsersDao(): UsersDao

    companion object {
        private var instance: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                "app-database"
            )
                .addCallback(RoomForeignKeysCallback())
                .build()
        }

        fun deleteInstance() {
            instance = null
        }
    }
}