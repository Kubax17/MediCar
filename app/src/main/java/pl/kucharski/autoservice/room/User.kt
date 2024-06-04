/*
Tabela w BD służąca do przechowywania informacji o użytkownikach
 */
package pl.kucharski.autoservice.room


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID") val userID: Int =0,
    @ColumnInfo var username:String,
    @ColumnInfo var password:String
)
{
    @Suppress("unused")
    constructor(username: String, password: String) : this(0, username, password)
}

