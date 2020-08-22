package ps.stdev.room

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class BookListAdapter(val context: Context, private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    interface OnDeleteClickListener{
        fun onDeleteClickListener(myBook: Book)
    }

    private var bookList: List<Book> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount(): Int = bookList.size

    fun setBooks(books:List<Book>){
        bookList = books
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.setData(book.title,book.details,position,book.description!!,book.lastUpdated!!)
        holder.setListeners()
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos : Int = 0

        fun setData(title: String, details : String, position: Int,description: String?,lastUpdated: Date?){
            itemView.text_title_view.text = title
            itemView.text_details_view.text = details
            itemView.text_details_view.text = description
            itemView.text_last_updated.text = getFormattedDate(lastUpdated!!)
            this.pos = position
        }

        fun setListeners() {
            itemView.setOnClickListener {
               val intent = Intent(context,EditBookActivity::class.java)
                intent.putExtra("id",bookList[pos].id)
                intent.putExtra("title",bookList[pos].title)
                intent.putExtra("details",bookList[pos].details)
                intent.putExtra("description",bookList[pos].description)
                intent.putExtra("lastUpdated",getFormattedDate(bookList[pos].lastUpdated!!))
                (context as Activity).startActivityForResult(intent,MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE)
            }

            itemView.image_delete.setOnClickListener {
                onDeleteClickListener.onDeleteClickListener(bookList[pos])
            }
        }

        private fun getFormattedDate(lastUpdated: Date): String {
            var time = "Last Updated : "
            time += lastUpdated.let {
                val sdf = SimpleDateFormat("HH:mm d MMM, yyy",Locale.getDefault())
                sdf.format(lastUpdated)
            } ?: "Not found"
            return time
        }
    }
}