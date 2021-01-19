package pro.gr.ams.stiorganizer

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper
    var accountRefresh = "";
    var editClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        usersDBHelper = UsersDBHelper(this)

        var bundle :Bundle ?= intent.extras
        var account = bundle!!.getString("account")
        accountRefresh = account

        try {
            var userData = usersDBHelper.readAllUData()

            userData.forEach {

                if (it.user.equals(account)) {

                    addView(it.dataid.toString(), it.title.toString(), it.content.toString(), it.user.toString())

                }
            }
        }
        catch (ex : Exception) {
            Toast.makeText(this, ""+ex, Toast.LENGTH_LONG).show()
        }

        backMGVW.setOnClickListener({
            finish()
        })
    }

    private fun addView(dataid: String, title: String, content: String, user: String) {
        var  dynParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        dynParams.setMargins(50,8,50,8)

        var  infoParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        infoParams.setMargins(50,0,50,12)

        val dynamicTXTVW = TextView(this)
        dynamicTXTVW.textSize = 20f
        dynamicTXTVW.setTextColor(Color.BLACK)
        dynamicTXTVW.layoutParams = dynParams ;

        val infoTXTVW = TextView(this)
        infoTXTVW.textSize = 12f
        infoTXTVW.setTextColor(Color.BLACK)
        infoTXTVW.layoutParams = infoParams ;

        val separatorTXTVW = TextView(this)
        separatorTXTVW.height = 2
        separatorTXTVW.setBackgroundColor(Color.GRAY)

        addViewLNRLYT.addView(dynamicTXTVW)
        addViewLNRLYT.addView(infoTXTVW)
        addViewLNRLYT.addView(separatorTXTVW)
        dynamicTXTVW.text = title
        infoTXTVW.text = content

        dynamicTXTVW.setOnClickListener({
            val i = Intent(this, EditNotesActivity::class.java)
            i.putExtra("dataid", dataid)
            i.putExtra("title", title)
            i.putExtra("content", content)
            startActivity(i)
            editClicked = true
        })
        infoTXTVW.setOnClickListener({
            val i = Intent(this, EditNotesActivity::class.java)
            i.putExtra("dataid", dataid)
            i.putExtra("title", title)
            i.putExtra("content", content)
            startActivity(i)
            editClicked = true
        })
    }

    override fun onResume() {
        if (editClicked == true) {
            val i = Intent(this, NotesActivity::class.java)
            i.putExtra("account", accountRefresh)
            startActivity(i)
            finish()
        }
        super.onResume()
    }

}
