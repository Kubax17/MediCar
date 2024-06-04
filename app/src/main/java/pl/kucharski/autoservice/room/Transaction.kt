package pl.kucharski.autoservice.room

import androidx.room.*
import pl.kucharski.autoservice.ProductType
import java.math.BigDecimal
import java.util.*


@Entity(tableName = "transactions_table",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userID"],
        childColumns = ["tUserID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
@TypeConverters(DateConverter::class)
data class Transaction(
    @PrimaryKey (autoGenerate = true)
    val transactionID: Int = 0,
    @ColumnInfo(name = "tUserID") val tUserID: Int,
    @ColumnInfo var title: String,
    @ColumnInfo var price: Double,
    @ColumnInfo var productType: ProductType,
    @ColumnInfo

    var date: Date
)
{
    @Suppress("unused")
    constructor(tUserID: Int,title: String, price: Double, productType :ProductType, date : Date) : this(0,tUserID,title, price, productType, date)
}






