package pro.gr.ams.stiorganizer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}
