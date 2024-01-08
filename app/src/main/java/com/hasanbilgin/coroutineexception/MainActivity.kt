package com.hasanbilgin.coroutineexception

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //yaşam döngüsünün sahibi  yani MainActivity fragment olsayda fragment derdim
//        lifecycleScope.launch {
//            try {
////                //alttaki kod hata verdirtir
////                //bu şekilde try catch yakalıcaktır ama launch içinde olursa
////                throw Exception("error")
//                launch {
//                    //launch içinde hata verirse lifecycleScope arar ve çöker
//                    throw Exception("error")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }

        //doğru yazılışı
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception: " + throwable.localizedMessage)
        }
//        lifecycleScope.launch(handler) {
//            throw Exception("Error1")
//        }
//        lifecycleScope.launch(handler) {
//            //lifecycleScope içinde launch hata verirse  diğer launchler çalıştırılmaz direk hata verdirtir
//            launch {
//                throw Exception("Error2")
//            }
//            launch {
//                //yarım sn
//                delay(500L)
//                println("this is executed")
//            }
//        }

        //launch dışına supervisorSccpe konursa düzeltir yani hata verse bile diğer lunch program devam eder
        lifecycleScope.launch(handler) {
            supervisorScope {
                launch {
                    throw Exception("Error2")
                }
                launch {
                    //yarım sn
                    delay(500L)
                    println("this is executed")
                }
            }
        }
        //kullanımıda  handlere ekleyebiliriz
        CoroutineScope(Dispatchers.Main+ handler).launch {
            launch {
                throw Exception("error in a coroutines scope")
            }

        }

    }
}