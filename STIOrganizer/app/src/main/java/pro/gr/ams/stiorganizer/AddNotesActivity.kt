package pro.gr.ams.stiorganizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotesActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        usersDBHelper = UsersDBHelper(this)

        var bundle :Bundle ?= intent.extras
        var account = bundle!!.getString("account")

        addBTTN.setOnClickListener({
            try {

                val title = titleDTTXT.text.toString()
                val content = contentDTTXT.text.toString()

                if (title.length == 0 || content.length == 0) {
                    Toast.makeText(this, "Complete the fields!", Toast.LENGTH_LONG).show()
                }
                else {

                    usersDBHelper.insertUdata(UsersDBHelper.UdataInsertModel(title, content, account))

                    Toast.makeText(this, "Note created!", Toast.LENGTH_LONG).show()

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
