/*
Klasa decydująca jakie dane mają być przesyłane pomiędzy BD na serwerze a BD na telefonie
 */
package pl.kucharski.autoservice.room

import android.content.Context
import pl.kucharski.autoservice.room.MyDatabase
import pl.kucharski.autoservice.room.User
import pl.kucharski.autoservice.room.UsersDao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow


class UserRepository(context: Context) : UsersDao {

    //dostęp do interfejsu, któy operuje na bazie danych
    private val dao = MyDatabase.getInstance(context).UsersDao()


    suspend fun isPasswordCorrect(username: String, enteredPassword: String): Boolean {
        val user = dao.getUserByUsername(username)

        return user?.password == enteredPassword

    }

    override suspend fun getUserID(username: String): Int {
        val user = dao.getUserByUsername(username)
        if( user != null)
            return user.userID
        else {
            return -1
        }
    }

    override suspend fun doesUserExists(username: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext dao.doesUserExists(username)

    }

    override suspend fun getUserByUsername(username: String): User = withContext(Dispatchers.IO) {
        return@withContext dao.getUserByUsername(username)
    }

    override suspend fun insert(user: User) = withContext(Dispatchers.IO) {
        dao.insert(user)
    }

    override suspend fun update(user: User) = withContext(Dispatchers.IO) {
        dao.update(user)
    }

    override suspend fun delete(user: User) = withContext(Dispatchers.IO) {
        dao.delete(user)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return dao.getAllUsers()
    }

    override suspend fun deleteAllRows() = withContext(Dispatchers.IO) {
        dao.deleteAllRows()
    }


}

