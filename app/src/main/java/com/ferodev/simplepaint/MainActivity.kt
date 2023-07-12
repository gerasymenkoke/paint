package com.ferodev.simplepaint

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.View


import android.view.MotionEvent


// import androidx.core.view


import android.widget.ImageButton

import androidx.appcompat.app.AppCompatActivity
import com.ferodev.simplepaint.databinding.ActivityMainBinding


import android.text.Editable
import android.text.TextWatcher
import android.database.ContentObserver 

import com.ferodev.simplepaint.canvas.DrawPencil.Companion.xxx
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.yyy
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.zzz

import com.ferodev.simplepaint.canvas.DrawPencil.Companion.rxx
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.ryy

import com.ferodev.simplepaint.canvas.DrawPencil.Companion.rx
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.ry

import com.ferodev.simplepaint.canvas.DrawPencil.Companion.crx_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.cry_

import kotlin.math.abs




//import com.ferodev.simplepaint.canvas.DrawPencil.Companion.j





// import kotlinx.coroutines.*






class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var isPencilIconClicked = false
    private var isArrowIconClicked = false
    private var isRectangleIconClicked = false
    private var isCircleIconClicked = false
    private var isPaletteIconClicked = false
  // private var xxx = "vvv"
    private var btn = 1
  //  private var image =  findViewById(R.id.btnPencil)
  private var j = 0
   private var jj = 0  
       private var jjj = 0  
    private var aaa = Array<String>(100){"0"}
    private var rraaa = Array<String>(100){"0"}
    
    private var n_rxy = Array<Int>(10){0} 
    
    
     private var rrx_ = Array<Float>(10){0.0f} 
     private var rry_ = Array<Float>(10){0.0f} 




     
     private var rrx  = Array(10){ Array<Float>(10){0.0f} }
     private var rry  = Array(10){ Array<Float>(10){0.0f} }

     private var res = Array<Float>(10){0.0f} 
     private var result = 0
     
 //   private var aa = Array<Float>(100){"0"} 



    
  var context=0
    var attrs=0
    var defStyleAttr=0


    



    
         companion object {
        var path = Path()
        var paintBrush = Paint()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
     //   var  textviewid = "vvv"    
       // var btn =  1  
        
                      }
    
           
       
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)
     
              
        
        
        supportActionBar?.hide()

        binding.apply {
           
// Filling sample array raa
// 0
     jj = 0                  

            rrx[jj] = arrayOf<Float>(1.0f, 0.17f, 0.08f, -0.02f, -0.7f, -0.15f, 0.0f, 0.0f, 0.0f, 0.0f)
           // rrx[jj] = rrx_
            
            rry[jj] = arrayOf<Float>(1.0f, -0.42f, 0.28f, 0.36f, 0.03f, -0.17f, 0.0f, 0.0f, 0.0f, 0.0f)
           // rry[jj] = rry_
            jjj=0       
             while (jjj<=9) {
                           rraaa[jjj]= "(" +   rrx[jj][jjj].toString() + "," + rry[jj][jjj].toString() + ")"  
                           jjj=jjj+1
                          }
             calc.text = "0: " + rraaa[0] + " " + rraaa[1] + " " + rraaa[2] + " " + rraaa[3] + " " + rraaa[4] + " " + rraaa[5] + " " + rraaa[6] + " " + rraaa[7] + " "  + rraaa[8] + " "  + rraaa[9]  
 // Filling sample array raa
// 1
      jj = 1                  

             rrx[jj] = arrayOf<Float>(1.0f, 0.18f, 0.01f, -0.03f, -0.02f, -0.17f, 0.24f, 0.06f, 0.0f, 0.0f)
            // rrx[jj] = rrx_
            
             rry[jj] = arrayOf<Float>(1.0f, -0.47f,0.34f, 0.49f, 0.26f,    0.00f, 0.0f,  0.01f, 0.0f, 0.0f)
           //  rry[jj] = rry_
             jjj=0        
             while (jjj<=9) {
                           rraaa[jjj]= "(" +   rrx[jj][jjj].toString() + "," + rry[jj][jjj].toString() + ")"  
                           jjj=jjj+1
                          }
              calc.text = "1: " + rraaa[0] + " " + rraaa[1] + " " + rraaa[2] + " " + rraaa[3] + " " + rraaa[4] + " " + rraaa[5] + " " + rraaa[6] + " " + rraaa[7] + " "  + rraaa[8] + " "  + rraaa[9]             











            
               btnPencil.setOnClickListener { 
            
                // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
 
            
                
                
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             j=0       

 while (j >=0 && j<=9) {
                    
aaa[j] = "(" + crx_[j].toString() + "," + cry_[j].toString() + ")"
     j=j+1
                     }



// result of test on 0
res[0]=0.0f
res[1]=0.0f

jj=0
while (jj >=0 && jj<=1) 
{
    
    j=0
    while (j >=0 && j<=9) {
    
    if ( (crx_[j] > 0 && rrx[jj][j] > 0) ||  (crx_[j] < 0 && rrx[jj][j] < 0)  )   { j=j } 
                                    else  { res[jj] = res[jj] + 1 }
                                        // Math.abs (crx_[j] - rrx[jj][j])  }
   if (( cry_[j]  > 0 && rry[jj][j] > 0) ||  (cry_[j] < 0  && rry[jj][j] < 0) )   { j=j } 
                                    else  {res[jj] = res[jj] + 1 } 
                                        // Math.abs (cry_[j] - rry[jj][j])   }
     j=j+1
                          }
     
     jj=jj+1
}    

if (res[0] < res[1]) { result=0 }
      else { result = 1 }


 
                   
//calc.text = rrx[j].toString() + "  " + rry[j].toString()
                    
                    
textviewid.text = "res="+ result + "  /res[0]=" + res[0] +"  /res[1]=" + res[1] + "  " +aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " "  + aaa[8] + " "  + aaa[9]  
 j = j+1   
                   
                     btnPencil.setImageResource(R.drawable.ic_selected_pencil)
                    btnPencil.setBackgroundResource(R.drawable.background_cards)
                  
                     drawPencil.visibility = View.VISIBLE
                             
                      
                
                }
                
                
                else {
                   
                     
                    btnPencil.setImageResource(R.drawable.ic_unselected_pencil)
                    btnPencil.setBackgroundResource(R.drawable.background_card)




 j=0       

 while (j >=0 && j<=9) {
                    
aaa[j] = "(" + crx_[j].toString() + "," + cry_[j].toString() + ")"
     j=j+1
                     }



             

//calc.text = j.toString()



                    
textviewid.text = aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " "  + aaa[8] + " "  + aaa[9]    
 j = j+1 



                                   
                }
            }

              
        }
        } 






    
   
}
