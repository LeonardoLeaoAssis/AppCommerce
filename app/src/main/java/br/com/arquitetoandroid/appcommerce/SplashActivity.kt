package br.com.arquitetoandroid.appcommerce

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.arquitetoandroid.appcommerce.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Thread(Runnable {
//            Thread.sleep(3000)
//
//            startActivity(intent)
//            finish()
//        }).start()

//        Handler().postDelayed(Runnable {
//            startActivity(intent)
//            finish()
//        }, 3000)

        Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                val intent: Intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                finish()
            }

    }
}
