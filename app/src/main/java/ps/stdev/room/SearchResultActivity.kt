package ps.stdev.room

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SearchResultActivity : AppCompatActivity(),BookListAdapter.OnDeleteClickListener{
    private lateinit var searchViewModel: SearchViewModel
    private var bookListAdapter : BookListAdapter? = null
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        fab.visibility = View.INVISIBLE

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        bookListAdapter = BookListAdapter(this,this)
        recycler_view.adapter = bookListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent){
        if (Intent.ACTION_SEARCH == intent.action){
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            Log.i(TAG, "handleIntent: Search Query is $searchQuery")
            searchViewModel.searchTitleOrDetails("%$searchQuery%")?.observe(this, Observer {books->
                books?.let {
                    bookListAdapter?.setBooks(books)
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchResultActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val id = data?.getStringExtra(EditBookActivity.ID)
            val title = data?.getStringExtra(EditBookActivity.UPDATED_TITLE)
            val details = data?.getStringExtra(EditBookActivity.UPDATED_DETAIL)
            val description = data?.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
            val currentTime = Calendar.getInstance().time

            val book = Book(id!!, title!!, details!!,description!!,currentTime)

            //Code to update
            searchViewModel.update(book)
            Toast.makeText(this,"Updated ", Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(this,"Not Saved ", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteClickListener(myBook: Book) {
        searchViewModel.delete(myBook)
        Toast.makeText(this,"Deleted! ",Toast.LENGTH_LONG).show()
    }

    companion object{
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }

}