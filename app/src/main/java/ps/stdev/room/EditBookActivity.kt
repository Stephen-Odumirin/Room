package ps.stdev.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_book.*
import java.util.*

class EditBookActivity : AppCompatActivity() {

    var id : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        val bundle : Bundle? = intent.extras

        bundle?.let {
            id = bundle.getString("id")
            val title = bundle.getString("title")
            val details = bundle.getString("details")
            val description = bundle.getString("description")
            val lastUpdated = bundle.getString("lastUpdated")

            text_id.text = id
            text_title.setText(title)
            text_details.setText(details)
            text_description.setText(description)
            text_last_updated.text = lastUpdated
        }

        button_save.setOnClickListener {
            val updatedTitle = text_title.text.toString()
            val updatedDetails = text_details.text.toString()
            val updatedDescription = text_description.text.toString()
            val currentTime = Calendar.getInstance().time

            val resultIntent = Intent()
            resultIntent.putExtra(ID,id)
            resultIntent.putExtra(UPDATED_TITLE,updatedTitle)
            resultIntent.putExtra(UPDATED_DETAIL,updatedDetails)
            resultIntent.putExtra(UPDATED_DESCRIPTION,updatedDescription)
            resultIntent.putExtra(UPDATED_TIME,currentTime)

            setResult(Activity.RESULT_OK,resultIntent)
            finish()
        }

        button_cancel.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val ID = "book_id"
        const val UPDATED_TITLE = "book_title"
        const val UPDATED_DETAIL = "book_details"
        const val UPDATED_DESCRIPTION = "book_description"
        const val UPDATED_TIME = "book_time"
    }

}