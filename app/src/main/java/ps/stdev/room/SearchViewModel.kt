package ps.stdev.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


//After adding repository
class SearchViewModel(application: Application) : AndroidViewModel(application){

    private val bookRepository = BookRepository(application)

    fun searchTitleOrDetails(searchQuery: String) : LiveData<List<Book>>? {
        return bookRepository.searchTitleOrDetails(searchQuery)
    }

    fun update(book : Book){
        bookRepository.update(book)
        //UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        bookRepository.delete(book)
        //DeleteAsyncTask(bookDao).execute(book)
    }

//    companion object {
//        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>(){
//            override fun doInBackground(vararg params: Book?): Void? {
//                bookDao.update(params[0]!!)
//                return null
//            }
//        }
//
//        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>(){
//            override fun doInBackground(vararg params: Book?): Void? {
//                bookDao.delete(params[0]!!)
//                return null
//            }
//        }
//    }
}
/*
    Before adding arepository
    ------------------------------------------------------\
    class SearchViewModel(application: Application) : AndroidViewModel(application){

    private val bookDao : BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
    }

    fun searchTitleOrDetails(searchQuery: String) : LiveData<List<Book>>? {
        return bookDao.searchTitleOrDetails(searchQuery)
    }

    fun update(book : Book){
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        DeleteAsyncTask(bookDao).execute(book)
    }

    companion object {
        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>(){
            override fun doInBackground(vararg params: Book?): Void? {
                bookDao.update(params[0]!!)
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book, Void, Void>(){
            override fun doInBackground(vararg params: Book?): Void? {
                bookDao.delete(params[0]!!)
                return null
            }
        }
    }
}

    ===================================================--
 */