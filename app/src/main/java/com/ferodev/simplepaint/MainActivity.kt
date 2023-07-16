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
import kotlin.math.roundToInt




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
    private var rraaa0 = Array<String>(100){"0"}
    private var rraaa1 = Array<String>(100){"0"}
   private var dir_crxy = Array<Int>(4){0} 
   private var dir_rrxy = Array(10){ Array<Int>(4){0.0f} } 
  
    private var dir = 0
    
     private var rrx_ = Array<Float>(10){0.0f} 
     private var rry_ = Array<Float>(10){0.0f} 

     private var sortcrx_ = Array<Float>(10){0.0f} 
     


     
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

            dir_rrxy[jj] = arrayOf<Float>( 1,  1, 1, 1)
           // rrx[jj] = arrayOf<Float>(2.0f,  0.64f, 0.19f, 0.05f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
           // rrx[jj] = rrx_
            
        //    rry[jj] = arrayOf<Float>(1.0f, -0.42f, 0.28f, 0.36f, 0.03f, -0.17f, 0.0f, 0.0f, 0.0f, 0.0f)
           // rry[jj] = rry_
            jjj=0       
             while (jjj<=3) {
                           rraaa0[jjj]= "(" +  dir_rrxy[jj][jjj].toString()  + ")"  
                           jjj=jjj+1
                           }
             // calc.text = "0: " + rraaa[0] + " " + rraaa[1] + " " + rraaa[2] + " " + rraaa[3] + " " + rraaa[4] + " " + rraaa[5] + " " + rraaa[6] + " " + rraaa[7] + " "  + rraaa[8] + " "  + rraaa[9]  
 // Filling sample array raa
// 1
      jj = 1                  

             dir_rrxy[jj] = arrayOf<Float>(0,  1, 0, 1)
            // rrx[jj] = rrx_
            
            // rry[jj] = arrayOf<Float>(1.0f,-0.81f, 0.23f, 0.58f, 0.08f,  0.00f, 0.0f,  0.00f, 0.0f, 0.0f)
           //  rry[jj] = rry_
             jjj=0        
             while (jjj<=3) {
                           rraaa1[jjj]=  dir_rrxy[jj][jjj].toString()   
                           jjj=jjj+1
                           }
              calc.text =  "0: " +  "  0-d=" + rraaa0[0] + "  1-d=" + rraaa0[1] + "  2-d=" + rraaa0[2] + "  3-d=" + rraaa0[3]  +
            "\n" + "1: " + "  0-d=" + rraaa1[0] + "  1-d=" + rraaa1[1] + "  2-d=" + rraaa1[2] + "  3-d=" + rraaa1[3]              











            
               btnPencil.setOnClickListener { 
            
                // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
 
            
                
                
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             j=0       

 

// sort of crx_[j] to sortcrx_[j]

//jj=0
                    
// while (jj >=0 && jj<=9) 
// {
    
  //  j=jj
    
  //  var jmax = 0
  //  var max=crx_[0] 
  //  j=1    
  //  while (j >=1 && j<=9) {
    
  //      if (crx_[j] > max) { jmax = j; max=crx_[j] }
        
  //                         j=j+1 
  //                        }
  //   crx_[jmax] = 0.0f 
  //  max=((max * 100.0).roundToInt() / 100.0).toFloat()  
  //  sortcrx_[jj] = max
  //   jj=jj+1
// }    



// sign of 4 directions quantity  (crx_[j] cry_[j]) to dir_crxy[0-3] = quantity0-3 directions


      j=0    
      dir=10 
     dir_crxy[0]=-1             
      while (j >=0 && j<=9) {
    
   if (crx_[j] > 0 &&  cry_[j] > 0 && dir!=0 ) { dir_crxy[0] = dir_crxy[0] + 1; dir=0 }
   if (crx_[j] < 0 &&  cry_[j] > 0 && dir!=1 ) { dir_crxy[1] = dir_crxy[1] + 1; dir=1 }
   if (crx_[j] < 0 &&  cry_[j] < 0 && dir!=2 ) { dir_crxy[2] = dir_crxy[2] + 1; dir=2 }
   if (crx_[j] > 0 &&  cry_[j] < 0 && dir!=3 ) { dir_crxy[3] = dir_crxy[3] + 1; dir=3 }
 
                          j=j+1 
 
                            }
 

                  


j=0
// output as text 
while (j >=0 && j<=3) {

aaa[j] =  "  " + j.toString() + "-d=" + dir_crxy[j].toString() 
// aaa[j] = "(" + crx_[j].toString()  + ", " +  cry_[j].toString() + ")"
                    j=j+1
                     }







 

// result of test on 0
res[0]=0.0f
res[1]=0.0f

jj=0
while (jj >=0 && jj<=1) 
{
    
    j=0
    while (j >=0 && j<=3) {
    
                          //     if ( crx_[j] > 0.0f && rrx[jj][j] > 0.0f )  
                                  res[jj] = res[jj] +  Math.abs (dir_crxy[j] - dir_rrxy[jj][j]) 
                                 
                               //   res[jj] = res[jj] +  Math.abs (cry_[j] - rry[jj][j]) 
                                          
                                        // Math.abs (crx_[j] - rrx[jj][j])  }
  
     j=j+1
                          }
     
     jj=jj+1
}    

if (res[0] < res[1]) { result=0 }
      else { result = 1 }


 
                   
//calc.text = rrx[j].toString() + "  " + rry[j].toString()
                    
                    
textviewid.text = "res="+ result + "  /res[0]=" + res[0] +"  /res[1]=" + res[1] + "/  " +aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3]   
 j = j+1   
                   
                     btnPencil.setImageResource(R.drawable.ic_selected_pencil)
                    btnPencil.setBackgroundResource(R.drawable.background_cards)
                  
                     drawPencil.visibility = View.VISIBLE
                             
                      
                
                }
                
                
                else {
                   
                     
                    btnPencil.setImageResource(R.drawable.ic_unselected_pencil)
                    btnPencil.setBackgroundResource(R.drawable.background_card)




 




                                   
                }
            }

              
        }
        } 






    
   
}
