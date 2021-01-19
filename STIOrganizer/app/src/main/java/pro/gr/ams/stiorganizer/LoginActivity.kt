package pro.gr.ams.stiorganizer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usersDBHelper = UsersDBHelper(this)

        loginBTTN.setOnClickListener({
            val account = accountDTTXT.text.toString()
            val pass = passwordDTTXT.text.toString()

            if (account.length == 0 || pass.length == 0) {
                Toast.makeText(this, "Complete The Required Fields", Toast.LENGTH_LONG).show()
            }
            else {
                var users = usersDBHelper.authenticateUser(account, pass)

                var data = "";
                users.forEach {
                    data = it.userid.toString()
                }

                if (data.length > 0) {
                    Toast.makeText(this, "Log In Successfull", Toast.LENGTH_LONG).show()

                    val i = Intent(this, MainActivity::class.java)
                    i.putExtra("account", account)
                    startActivity(i)
                    finish()

                } else {
                    Toast.makeText(this, "Log In Failed", Toast.LENGTH_LONG).show()
                }
            }
        })

        registerBTTN.setOnClickListener({
            val i = Intent(this, RegistrationActivity::class.java)
            startActivity(i)
            finish()
        })
    }
}
