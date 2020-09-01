package ps.stdev.room

import android.app.Activity
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() ,BookListAdapter.OnDeleteClickListener {

    private lateinit var bookViewModel: BookViewModel
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var bookListAdapter : BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mRecyclerView = findViewById(R.id.recycler_view)

        bookListAdapter = BookListAdapter(this,this)
        recycler_view.adapter = bookListAdapter
        recycler_view.layoutManager = LinearLayoutManager(this) //i can also add this to the layout file


        bookViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)

        bookViewModel.allBooks.observe(this,androidx.lifecycle.Observer{books->

            books?.let{//let is used cause we dont want to return null
                bookListAdapter.setBooks(books)
            }
        })
//        bookViewModel.allBooks.observe(this,Observer{books->
//            books?.let{
//                bookListAdapter.setBooks(books)
//            }
//        })


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            findNavController(R.id.nav_host_fragment).navigate(R.id.action_mainBookActivity_to_FirstFragment)
            val intent = Intent(this,NewBookActivity::class.java)
            startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE)
        }
    }

    companion object{
        private const val NEW_NOTE_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            saveNote(data)

        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            updateNote(data)
        }

        else {
            Toast.makeText(this,"Not `Saved",Toast.LENGTH_LONG).show()
        }
    }

    private fun updateNote(data: Intent?) {
        val id = data?.getStringExtra(EditBookActivity.ID)
        val title = data?.getStringExtra(EditBookActivity.UPDATED_TITLE)
        val details = data?.getStringExtra(EditBookActivity.UPDATED_DETAIL)
        val description = data?.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
        val currentTime = Calendar.getInstance().time

        val book = Book(id!!, title!!, details!!, description!!, currentTime)

        //Code to update
        bookViewModel.update(book)
        Toast.makeText(this, "Updated ", Toast.LENGTH_LONG).show()
    }

    private fun saveNote(data: Intent?) {
        val id = UUID.randomUUID().toString()
        val title = data?.getStringExtra(NewBookActivity.TITLE)
        val details = data?.getStringExtra(NewBookActivity.DETAILS)
        val description = data?.getStringExtra(NewBookActivity.DESCRIPTION)
        val currentTime = Calendar.getInstance().time

        val book = Book(id, title!!, details!!, description!!, currentTime)
        bookViewModel.insert(book = book)

        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_search)

        //Get the search view and set the searchable configuration
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        //val searchView = MenuItemCompat.getActionView(menuItem) as SearchView

        //setting the searchResult activity to show the result
        val componentName = ComponentName(this,SearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)
        handleIntent(intent)

        return true
    }

    private fun handleIntent(intent: Intent){
        if (Intent.ACTION_SEARCH == intent.action){
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)
            //Log.i(TAG, "handleIntent: Search Query is $searchQuery")
            bookViewModel.searchTitleOrDetails("%$searchQuery%")?.observe(this, androidx.lifecycle.Observer { books->
                books?.let {
                    bookListAdapter?.setBooks(books)
                }
            })
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_search -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onDeleteClickListener(myBook: Book) {
        bookViewModel.delete(myBook)
        Toast.makeText(this,"Deleted!",Toast.LENGTH_LONG).show()
    }
}