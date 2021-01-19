package pro.gr.ams.stiorganizer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        usersDBHelper = UsersDBHelper(this)

        registerBTTN.setOnClickListener({
            val account = accountDTTXT.text.toString()
            val name = nameDTTXT.text.toString()
            val pass = passwordDTTXT.text.toString()
            val campus = campusDTTXT.text.toString()
            val program = programDTTXT.text.toString()

            if (account.length == 0 || pass.length == 0 || campus.length == 0 || program.length == 0) {
                Toast.makeText(this, "Complete The Required Fields", Toast.LENGTH_LONG).show()
            }
            else {
                var users = usersDBHelper.checkUserExist(account)
                var data = "";
                users.forEach {
                    data = it.userid.toString()
                }
                if (data.length > 0) {
                    Toast.makeText(this, "Account Already Exist!", Toast.LENGTH_LONG).show()
                }
                else {
                    usersDBHelper.insertUser(UsersDBHelper.AccountsModel(account, name, pass, campus, program))
                    Toast.makeText(this, "Registration Sucessfull", Toast.LENGTH_LONG).show()
                    val i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        })

    }

    override fun onBackPressed() {
        val i = Intent(this,LoginActivity::class.java)
        startActivity(i)
        finish()
        super.onBackPressed()
    }
}
