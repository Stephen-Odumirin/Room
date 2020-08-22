package ps.stdev.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel


//After adding repository
class BookViewModel(application: Application) : AndroidViewModel(application) {

    //private val bookDao : BookDao //to initialize the bookdao
    val allBooks : LiveData<List<Book>> //to get the live data for all the books
    private val bookRepository = BookRepository(application)

    init {
        //val bookDb = BookRoomDatabase.getDatabase(application)
        //bookDao = bookDb!!.bookDao()
        //from dao
        allBooks = bookRepository.allBooks
    }

    fun insert(book:Book){
        bookRepository.insert(book)
        //InsertAsyncTask(bookDao).execute(book)
    }

    fun update(book: Book){
        bookRepository.update(book)
//        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        bookRepository.delete(book)
        //DeleteAsyncTask(bookDao).execute(book)
    }

//    companion object{
//        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
//            override fun doInBackground(vararg params: Book?): Void? {
//                bookDao.insert(params[0]!!)
//                return null
//            }
//
//        }
//
//        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
//            override fun doInBackground(vararg params: Book?): Void? {
//                bookDao.update(params[0]!!)
//                return null
//            }
//        }
//
//        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
//            override fun doInBackground(vararg params: Book?): Void? {
//                bookDao.delete(params[0]!!)
//                return null
//            }
//        }
//    }


}

/*
    Before adding repository

    --------------------------------------------------------------------------------
        class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao : BookDao //to initialize the bookdao
    val allBooks : LiveData<List<Book>> //to get the live data for all the books

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        //from dao
        allBooks = bookDao.allBooks
    }

    fun insert(book:Book){
        InsertAsyncTask(bookDao).execute(book)
    }

    fun update(book: Book){
        UpdateAsyncTask(bookDao).execute(book)
    }

    fun delete(book: Book){
        DeleteAsyncTask(bookDao).execute(book)
    }

    companion object{
        private class InsertAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
            override fun doInBackground(vararg params: Book?): Void? {
                bookDao.insert(params[0]!!)
                return null
            }

        }

        private class UpdateAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
            override fun doInBackground(vararg params: Book?): Void? {
                bookDao.update(params[0]!!)
                return null
            }
        }

        private class DeleteAsyncTask(private val bookDao: BookDao) : AsyncTask<Book,Void,Void>(){
            override fun doInBackground(vararg params: Book?): Void? {
                bookDao.delete(params[0]!!)
                return null
            }
        }
    }


}
    --------------------------------------------------------------------------------
 */