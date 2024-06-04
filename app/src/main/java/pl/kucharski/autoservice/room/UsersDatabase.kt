/*
Baza Danych - na razie jest tylko jedna tabela (użytkownicy)
 */
package pl.kucharski.autoservice.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.kucharski.autoservice.room.UsersDao


abstract class UsersDatabase: RoomDatabase() {

    //funkcja zwracająca interfejs
    abstract fun usersDao(): UsersDao

    //singleton BD - obiekt podczas działania aplikacji występuje tylko raz
    object DatabaseBuilder {
        private var instance: UsersDatabase? = null

        fun getInstance(context: Context): UsersDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    UsersDatabase::class.java,
                    "users-database"
                )
                    .build()
            }
            return instance!!
        }

        fun deleteInstanceOfDatabase(){
            instance = null
        }
    }
}