package com.example.mopspower
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val switchPower = findViewById<Switch>(R.id.switchPower)
        switchPower?.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) "ON" else "OFF"
            switchPower.setText(message)
            if(isChecked){
                try {
                    Thread(Runnable {
                        val readText = URL("http://MopsPower.local/on").readText()
                    }).start()

                }catch(e:Exception){
                    println(e)
                    Toast.makeText(this@MainActivity,
                            e.toString(), Toast.LENGTH_LONG).show()
                }
            }else{
                try {
                    Thread(Runnable {
                        val readText = URL("http://MopsPower.local/off").readText()
                    }).start()
                }catch(e:Exception){
                    println(e)
                    Toast.makeText(this@MainActivity,
                            e.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}