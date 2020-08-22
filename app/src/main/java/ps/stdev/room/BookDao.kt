package ps.stdev.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Insert
    fun insert(book: Book)

//    @Query("select * from books_table_name")
//    fun getAllBooks() : LiveData<List<Book>>

    @get : Query("select * from books_table_name") //cause my table name is book
    val allBooks : LiveData<List<Book>>

    //for searching stuffs
    @Query("select * from books_table_name where _title like :searchString or _details like :searchString or _description like :searchString")
    fun searchTitleOrDetails(searchString: String) : LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)



}