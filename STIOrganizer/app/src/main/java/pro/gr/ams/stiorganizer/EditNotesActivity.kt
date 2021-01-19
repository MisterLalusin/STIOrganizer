package pro.gr.ams.stiorganizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_notes.*

class EditNotesActivity : AppCompatActivity() {

    var dataid = ""
    var title = ""
    var content = ""

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notes)

        usersDBHelper = UsersDBHelper(this)

        var bundle :Bundle ?= intent.extras
        dataid = bundle!!.getString("dataid")
        title = bundle!!.getString("title")
        content = bundle!!.getString("content")

        titleDTTXT.setText(title)
        contentDTTXT.setText(content)


        updateBTTN.setOnClickListener({
            try {

                val title = titleDTTXT.text.toString()
                val content = contentDTTXT.text.toString()

                if (title.length == 0 || content.length == 0) {
                    Toast.makeText(this, "Complete the fields!", Toast.LENGTH_LONG).show()
                }
                else {

                    usersDBHelper.updateNotes(dataid, title, content)

                    Toast.makeText(this, "Note updated!", Toast.LENGTH_LONG).show()

                    titleDTTXT.setText("")
                    contentDTTXT.setText("")

                    finish()
                }

            }
            catch (ex : Exception) {
                Toast.makeText(this, ""+ex, Toast.LENGTH_LONG).show()
            }
        })
    }
}
