package ps.stdev.room

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_new_book.*

class NewBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_book)

        text_last_updated.visibility = View.INVISIBLE

        button_save.setOnClickListener {
            val resultIntent = Intent()

            if (text_title.text.isEmpty() || text_details.text.isEmpty()){
                setResult(Activity.RESULT_CANCELED)
            } else {
                val title = text_title.text.toString()
                val details = text_details.text.toString()

                resultIntent.putExtra(TITLE,title)
                resultIntent.putExtra(DETAILS,details)

                setResult(Activity.RESULT_OK,resultIntent)
            }

            finish()
        }
    }

    companion object{
        const val DESCRIPTION = "new_description"
        const val TITLE = "new_title"
        const val DETAILS = "new_details"
    }
}