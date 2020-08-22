package ps.stdev.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "books_table_name")
data class Book(
    @PrimaryKey()
    val id: String,
    @ColumnInfo(name = "_title")
    val title: String,
    @ColumnInfo(name = "_details")
    val details: String,
    @ColumnInfo(name = "_description")
    val description: String?,
    @ColumnInfo(name = "_lastUpdated")
    val lastUpdated: Date?
) {
}