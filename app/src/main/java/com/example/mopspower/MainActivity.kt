package com.example.mopspower
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val switchPower = findViewById<Switch>(R.id.switchPower)
        val host: String = "http://MopsPower.local"
        Timer().schedule(timerTask {
            Thread(Runnable {
                try {
                    val readText = URL("$host/status").readText()
                    println(readText =="1")
                    if(readText == "1"){
                        runOnUiThread {
                            switchPower.text = "On"
                            switchPower.isChecked=true
                        }
                    }else{
                        runOnUiThread {
                            switchPower.text = "Off"
                            switchPower.isChecked=false
                        }
                    }
                }catch (e:java.lang.Exception){
                    Toast.makeText(this@MainActivity,
                        e.toString(), Toast.LENGTH_LONG).show()
                }
            }).start()
        },0,5000)
        switchPower?.setOnCheckedChangeListener { _, isChecked ->
            println(host)
            val message = if (isChecked) "ON" else "OFF"
            switchPower.text = message
            if(isChecked){
                Thread(Runnable {
                    try {
                        val readText = URL("$host/on").readText()
                    }catch(e:Exception){
                        println(e)
                        runOnUiThread {
                            Toast.makeText(this@MainActivity,
                                e.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }).start()
            }else{
                Thread(Runnable {
                    try {
                        val readText = URL("$host/off").readText()
                    }catch(e:Exception){
                        println(e)
                        runOnUiThread {
                            Toast.makeText(this@MainActivity,
                                e.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }).start()
            }
        }
    }
}