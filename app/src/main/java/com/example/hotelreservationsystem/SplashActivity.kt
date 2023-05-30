package com.example.hotelreservationsystem

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.hotelreservationsystem.Fragments.OwnerHomeFragment
import com.example.hotelreservationsystem.utils.TokenManager
import com.github.ybq.android.spinkit.style.ThreeBounce
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // managing the spinkit library
        supportActionBar?.hide()

        // hiding status bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val progressBar = findViewById<View>(R.id.spin) as ProgressBar
        val threeBounce = ThreeBounce()
        progressBar.indeterminateDrawable = threeBounce


        // setting up post delayed in lap
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                startActivity(Intent(this,MainActivity::class.java))
            },1000
        )
    }
    private fun switchFragment(fragment: Fragment) {
        var  fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }

}