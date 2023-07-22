package com.ferodev.simplepaint

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.View


import android.view.MotionEvent




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
   private var dir_crx = Array<Float>(4){0.0f} 
   private var dir_cry = Array<Float>(4){0.0f} 
   
   private var dir_rr = Array(10) { Array(10){ Array<Float>(4){0.0f} } }
   private var dir_cr =  Array<Float>(4){0.0f}  
   private var    res =  Array(10){ Array<Float>(4){0.0f} } 
  
    private var dir = 0
    
     private var rrx_ = Array<Float>(10){0.0f} 
     private var rry_ = Array<Float>(10){0.0f} 

     private var sortcrx_ = Array<Float>(10){0.0f} 
     


     
     private var rrx  = Array(10){ Array<Float>(10){0.0f} }
     private var rry  = Array(10){ Array<Float>(10){0.0f} }


     
     private var resx = Array<Float>(10){0.0f} 
     private var resy = Array<Float>(10){0.0f} 
     private var resxy = Array<Float>(10){0.0f} 
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
     dir_rr[0][0] = arrayOf<Float>(1.0f,  1.0f, 1.0f, 1.0f)
     
              
 // Filling sample array raa
// 1
      jj = 1                  
      dir_rr[1][0] = arrayOf<Float>(1.0f,  0.0f, 0.0f, 1.0f)
      dir_rr[1][1] = arrayOf<Float>(0.0f,  1.0f, 0.0f, 1.0f)
      dir_rr[1][2] = arrayOf<Float>(1.0f,  1.0f, 0.0f, 1.0f)
                        
// Filling sample array raa
// 2
      jj = 2                  
      dir_rr[2][0] = arrayOf<Float>(2.0f,  1.0f, 0.0f, 2.0f)
      dir_rr[2][1] = arrayOf<Float>(1.0f,  1.0f, 0.0f, 1.0f)
     //dir_rr[2][2] = arrayOf<Float>(1.0f,  1.0f, 0.0f, 1.0f)
        







            
               btnPencil.setOnClickListener { 
            
                // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
 
            
                
                
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             j=0       

 

// sign of 4 directions level   (crx_[j] cry_[j]) to dir_crxy[0-3] = level 0-3 directions Int

      j=0    
      dir=10
     dir_cr[0]=-1.0f
     //dir_cry[0]=-1.0f               
      while (j >=0 && j<=9) {
    
   if (crx_[j] > 0.0f &&  cry_[j] > 0.0f && dir!=0 ) { dir_cr[j] = 0.0f;  dir=0 }
   if (crx_[j] < 0.0f &&  cry_[j] > 0.0f && dir!=1 ) { dir_cr[j] = 1.0f;  dir=1 }
   if (crx_[j] < 0.0f &&  cry_[j] < 0.0f && dir!=2 ) { dir_cr[j] = 2.0f;  dir=2 }
   if (crx_[j] > 0.0f &&  cry_[j] < 0.0f && dir!=3 ) { dir_cr[j] = 3.0f;  dir=3 }
 
                          j=j+1 
 
                            }
                  


j=0
// output as text 
while (j >=0 && j<=9) {

aaa[j] =  "  " + j.toString() + "-d=" + dir_cr[j].toString() 
// aaa[j] = "(" + crx_[j].toString()  + ", " +  cry_[j].toString() + ")"
                    j=j+1
                     }



 

// result of test on 0

                    
      jj=0
while (jj >=0 && jj<=1)  // index of numbers 0, 1 ..
{
          jjj=0
    while (jjj >=0 && jjj<=2) // quantity of variants for each/all numbers
            {
    
                j=0
    while (j >=0 && j<=3) {   //  quantity of directions 0,1,2,3
    
                                 res[jj] [jjj] =  res[jj] [jjj] +  Math.abs (dir_cr[j] - dir_rr[jj] [jjj] [j])  
                         j=j+1
   
                           }
              jjj=jjj+1
             }    
 jj=jj+1
}    

// resxy[2] = 20.0f
if ( res[0][0] <= res[1][0] && res[0][0] <= res[1][1] ) { result=0 }
if ( res[0][0] >= res[1][0] || res[0][0] >= res[1][1] || res[0][0] >= res[1][2] ) { result=1 }     
// if (resxy[2] <= resxy[0] && resxy[2]  <= resxy[1] ) { result=2 }     




 
                   
//calc.text = rrx[j].toString() + "  " + rry[j].toString()
                    
                    
//textviewid.text = "res="+ result +  "\n" + "res[0][0]=" + res[0][0] + "\n" + "res[1][0]=" + res[1][0]   + "\n" + "res[1][1]=" + res[1][1] + "\n" + "res[1][2]=" + res[1][2] +  "\n" + aaa[0] + " " +aaa[1] + " " + aaa[2] + "  " + aaa[3]   

textviewid.text = "res="+ result +  "\n" + "res[0][0]=" + res[0][0] + "\n" + "res[1][0]=" + res[1][0]   + "\n" + "res[1][1]=" + res[1][1] + "\n" + "res[1][2]=" + res[1][2] +  "\n" + aaa[0] + " " +aaa[1] + " " + aaa[2] + "  " + aaa[3] + "  " + aaa[4] + "  " + aaa[5] + "  " + aaa[6] + "  " + aaa[7] + "  " + aaa[8] + "  " + aaa[9]  

                    
                   
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
