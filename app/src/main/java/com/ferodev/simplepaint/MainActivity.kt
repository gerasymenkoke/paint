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
   
   private var dir_rr = Array(10) { Array(10){ Array<Int>(10){0} } }
   private var dir_cr =  Array<Int>(10){0}  
   private var    res =  Array(10){ Array<Int>(10){0} } 
  
    private var dir = 0
    private var min = 10
    
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
     
        
                      }
    
           
       
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)
     
              
        
        
        supportActionBar?.hide()

        binding.apply {
           
// Filling sample array raa
// 0
     jj = 0                  
     dir_rr[0][0] = arrayOf<Int>(0,  3, 0, 1, 0, 0, 0, 0, 0, 0)
     dir_rr[0][1] = arrayOf<Int>(0,  3, 0, 2, 0, 0, 0, 0, 0, 0)
              
 // Filling sample array raa
// 1
      jj = 1                  
      dir_rr[1][0] = arrayOf<Int>(0,  3, 1, 0, 0, 0, 0, 0, 0, 0)
      dir_rr[1][1] = arrayOf<Int>(0,  3, 1, 0, 2, 3, 0, 0, 0, 0)
      dir_rr[1][2] = arrayOf<Int>(0,  3, 0, 0, 0, 1, 0, 0, 0, 0)
                        
// Filling sample array raa
// 2
      jj = 2                  
      dir_rr[2][0] = arrayOf<Int>(0,  3, 0, 1, 3, 0, 0, 0, 0, 0)
      dir_rr[2][1] = arrayOf<Int>(0,  3, 1, 0, 0, 0, 0, 0, 0, 0)
      dir_rr[2][2] = arrayOf<Int>(0,  3, 0, 0, 0, 1, 0, 0, 0, 0)
        







            
               btnPencil.setOnClickListener { 
            
                // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
 
            
                
                
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             j=0       

 

// sign of 4 directions level   (crx_[j] cry_[j]) to dir_crxy[0-3] = level 0-3 directions Int

      j=0    
      dir=10
     // dir_cr[0]=-1
     //dir_cry[0]=-1.0f               
      while (j >=0 && j<=9) {
    
   if (crx_[j] > 0.0f &&  cry_[j] > 0.0f && dir!=0 ) { dir_cr[j] = 0;  dir=0 }
   if (crx_[j] < 0.0f &&  cry_[j] > 0.0f && dir!=1 ) { dir_cr[j] = 1;  dir=1 }
   if (crx_[j] < 0.0f &&  cry_[j] < 0.0f && dir!=2 ) { dir_cr[j] = 2;  dir=2 }
   if (crx_[j] > 0.0f &&  cry_[j] < 0.0f && dir!=3 ) { dir_cr[j] = 3;  dir=3 }
 
                          j=j+1 
 
                            }
                  



// output as text current painted number in direction sequence
j=0                    
while (j >=0 && j<=9) {
aaa[j] =  "  " + j.toString() + "-d=" + dir_cr[j].toString() 
                    j=j+1
                     }



// result of test on comparing of numbers  direction sequences
                   
      jj=0
while (jj >=0 && jj<=2)  // index of numbers 0, 1 ..
{
          jjj=0
    while (jjj >=0 && jjj<=9) // quantity of variants for each/all numbers
            {
    
                j=0
    while (j >=0 && j<=3) {   //  quantity of directions 0,1,2,3: South-East Sorth-West North-West North-East 
    
                                 res[jj] [jjj] =  res[jj] [jjj] +  Math.abs (dir_cr[j] - dir_rr[jj] [jjj] [j])  
                         j=j+1
   
                           }
              jjj=jjj+1
             }    
 jj=jj+1
}    



// finding min res value is max resemble differ (res[jj] [jjj] ): current paint number directions dir_cr[jj] to  dir_rr (etalons)  

    jj=0
while (jj >=0 && jj<=2)  // index of numbers 0, 1, 2 ..
{
          jjj=0
    while (jjj >=0 && jjj<=9) // quantity of variants for each/all numbers
            {
               if ( res[jj][jjj] < min )  { result=jj; min = result }
           jjj = jjj +1
            }
       jj = jj +1
}   

                  

textviewid.text = "res="+ result +  "\n"  + aaa[0] + " " +aaa[1] + " " + aaa[2] + "  " + aaa[3] + "  " + aaa[4] + "  " + aaa[5] + "  " + aaa[6] + "  " + aaa[7] + "  " + aaa[8] + "  " + aaa[9]  

                    
                   
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
