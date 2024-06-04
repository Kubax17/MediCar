/*
Klasa pozwalająca wykonywać operacje na BD w aktywności rejestracji
 */
package pl.kucharski.autoservice.room

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import pl.kucharski.autoservice.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsersViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = UserRepository(app.applicationContext)

    suspend fun insertUserToDatabase(username: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val user = User(username, password)
                repo.insert(user)
            }
        }
    }

    suspend fun isPasswordCorrect(username: String, enteredPassword:String): Boolean {
        return repo.isPasswordCorrect(username, enteredPassword)
    }

    suspend fun  getUserID (username: String) : Int  {
        return repo.getUserID(username)
    }

    suspend fun doesUserExists(username: String): Boolean {
        return repo.doesUserExists(username)
    }

    //funkcja wykonywana w tle
    @WorkerThread
    fun getAllUsers(): Flow<List<User>> {
        return repo.getAllUsers()
    }
}