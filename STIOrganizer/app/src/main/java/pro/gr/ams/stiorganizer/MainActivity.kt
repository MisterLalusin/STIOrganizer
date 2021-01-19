package pro.gr.ams.stiorganizer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersDBHelper = UsersDBHelper(this)

        var bundle :Bundle ?= intent.extras
        var account = bundle!!.getString("account")

        var users = usersDBHelper.readUser(account)

        users.forEach {
            userName.setText(it.name.toString())
            campusTXTVW.setText(it.campus.toString())
        }

        viewNotesLNRLYT.setOnClickListener({
            val i = Intent(this, NotesActivity::class.java)
            i.putExtra("account", account)
            startActivity(i)
        })

        logoutLNRLYT.setOnClickListener({
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        })

        addNotesLNRLYT.setOnClickListener({
            val i = Intent(this, AddNotesActivity::class.java)
            i.putExtra("account", account)
            startActivity(i)
        })

    }
}
