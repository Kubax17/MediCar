/*
Klasa/interfejs określająca operacje wykonywane na bazie danych
 */
package pl.kucharski.autoservice.room


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

//--------------------------------------------------------------------//


    @Query("SELECT EXISTS(SELECT 1 FROM users_table WHERE username = :username)")
    suspend fun doesUserExists(username: String) : Boolean

    @Query ("SELECT * FROM users_table WHERE username = :username")
    suspend fun getUserByUsername (username: String) : User

    @Query ("SELECT userID FROM users_table WHERE username = :username ")
    suspend fun getUserID (username:String) : Int
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users_table ORDER BY userID ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("DELETE FROM users_table")
    suspend fun deleteAllRows()

}