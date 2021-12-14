package ir.mralizade.imdbshow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ir.mralizade.imdbshow.databinding.ActivityMainBinding
import ir.mralizade.imdbshow.utils.transactionFragment
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactionFragment()
    }

    private fun getDelayTime(): Long {
        val date = Date()
        val calender = Calendar.getInstance(TimeZone.getDefault())
        calender.time = date
        val current = calender.get(Calendar.HOUR_OF_DAY).toString().toInt()

        return if (current >= 13)
            (24 - (current - 13L))
        else
            (13L - current)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size == 2)
            finish()
        else
            super.onBackPressed()
    }
}