package mx.edu.ittepic.ladm_u2_practica1_loteria

import android.R.*
import android.annotation.SuppressLint
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mx.edu.ittepic.ladm_u2_practica1_loteria.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    var cartas = arrayOf(R.drawable.c1,
        R.drawable.c2,
        R.drawable.c3,
        R.drawable.c4,
        R.drawable.c5,
        R.drawable.c6,
        R.drawable.c7,
        R.drawable.c8,
        R.drawable.c9,
        R.drawable.c10,
        R.drawable.c11,
        R.drawable.c12,
        R.drawable.c13,
        R.drawable.c14,
        R.drawable.c15,
        R.drawable.c16,
        R.drawable.c17,
        R.drawable.c18,
        R.drawable.c19,
        R.drawable.c20,
        R.drawable.c21,
        R.drawable.c22,
        R.drawable.c23,
        R.drawable.c24,
        R.drawable.c25,
        R.drawable.c26,
        R.drawable.c27,
        R.drawable.c28,
        R.drawable.c29,
        R.drawable.c30,
        R.drawable.c31,
        R.drawable.c32,
        R.drawable.c33,
        R.drawable.c34,
        R.drawable.c35,
        R.drawable.c36,
        R.drawable.c37,
        R.drawable.c38,
        R.drawable.c39,
        R.drawable.c40,
        R.drawable.c41,
        R.drawable.c42,
        R.drawable.c43,
        R.drawable.c44,
        R.drawable.c45,
        R.drawable.c46,
        R.drawable.c47,
        R.drawable.c48,
        R.drawable.c49,
        R.drawable.c50,
        R.drawable.c51,
        R.drawable.c52,
        R.drawable.c53,
        R.drawable.c54,
    )
    var voces = arrayOf(R.raw.c1,
        R.raw.c2,
        R.raw.c3,
        R.raw.c4,
        R.raw.c5,
        R.raw.c6,
        R.raw.c7,
        R.raw.c8,
        R.raw.c9,
        R.raw.c10,
        R.raw.c11,
        R.raw.c12,
        R.raw.c13,
        R.raw.c14,
        R.raw.c15,
        R.raw.c16,
        R.raw.c17,
        R.raw.c18,
        R.raw.c19,
        R.raw.c20,
        R.raw.c21,
        R.raw.c22,
        R.raw.c23,
        R.raw.c24,
        R.raw.c25,
        R.raw.c26,
        R.raw.c27,
        R.raw.c28,
        R.raw.c29,
        R.raw.c30,
        R.raw.c31,
        R.raw.c32,
        R.raw.c33,
        R.raw.c34,
        R.raw.c35,
        R.raw.c36,
        R.raw.c37,
        R.raw.c38,
        R.raw.c39,
        R.raw.c40,
        R.raw.c41,
        R.raw.c42,
        R.raw.c43,
        R.raw.c44,
        R.raw.c45,
        R.raw.c46,
        R.raw.c47,
        R.raw.c48,
        R.raw.c49,
        R.raw.c50,
        R.raw.c51,
        R.raw.c52,
        R.raw.c53,
        R.raw.c54)
    lateinit var mp: MediaPlayer
    var baraja = ArrayList<Int>()
    var barajaLeida = ArrayList<Int>()
    var juego = lecturaBaraja(this)
    var leyendo=false
    lateinit var afd :AssetFileDescriptor
    lateinit var binding: ActivityMainBinding
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        barajear()
        binding.btnDet.isEnabled=false
        binding.btnComp.isEnabled=false

        try {
            mp.setDataSource(resources.openRawResourceFd(voces[5]))
            mp.prepare()
            mp.start()
        }catch (e:Exception){}
        binding.btnIni.setOnClickListener {
            binding.btnIni.isEnabled=false
            leyendo=true
            juego.start()
            binding.btnDet.isEnabled=true

        }
        binding.btnDet.setOnClickListener {
            leyendo=false
            binding.btnDet.isEnabled=false
            binding.btnComp.isEnabled=true

        }
        binding.btnComp.setOnClickListener {
            var msj = "Las cartas que se han leído son:\n"
            if(barajaLeida.isNotEmpty()){
                for(c in barajaLeida){
                    msj=msj+ (c+1)+","
                }
            }
            binding.txtCarta.setText(msj.substring(0,msj.length-1))

        }

    }//onCreate


    fun barajear()= runBlocking {
        launch {
            var gen = Random(System.currentTimeMillis())
            while(baraja.size<54){
                var num = gen.nextInt(0,54)
                if(!baraja.contains(num)){
                    baraja.add(num)
                }
            }
        }
        baraja.clear()
        barajaLeida.clear()
        println("Se ha barajeado:")
    }
}//mainAct
class lecturaBaraja(puntero:MainActivity):Thread(){
    var p = puntero
    var gen = Random(System.currentTimeMillis())
    var num=0

    @SuppressLint("NewApi")
    override fun run(){
     while(p.leyendo){
         if(p.baraja.isNotEmpty()){
             num = gen.nextInt(0,54)
             if(p.baraja.contains(num)){
                 p.barajaLeida.add(num)
                 p.baraja.removeAll { it==num }
                 p.runOnUiThread {
                     p.binding.carta.setImageResource(p.cartas[num])
                     try{
                         var mp = MediaPlayer()
                         mp.setDataSource(p.resources.openRawResourceFd(p.voces[num]))
                         mp.prepare()
                         mp.start()

                     }catch (e:Exception){
                         Toast.makeText(p.binding.root.context,e.message,Toast.LENGTH_LONG).show()
                     }

                 }//uit
                 sleep(3000)
             }//si aun en juego

         }//if not empty
     }//while
    }//run
}//lecturabaraja
