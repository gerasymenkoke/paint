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
 
    private var btn = 1  // button ft
    // counters and temporaries
    private var j = 0
    private var i = 0
    private var jj = 0  
    private var jjj = 0  
    private var k=0
    private var old=0
   
    private var aaa = Array<String>(100){"0"} // text Array for result monitoring
  
    private var dir_cr =  Array<Int>(20){10}  // array of directions current drawpencil (0,1,2,3)
    private var dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after compressing -delete repeated 0,1,2,3 and 10 inside
    private var dir_rr = Array(10) { Array(20){ Array<Int>(10){10} } }  // array of etalons(20) for every sample(10pcs: 0,1,...9,+..=)
    private var dir_res =  Array(20){ Array<Int>(20){0} }  // array difference between 
    // crx_ , cry_ - import from drawpencil.kt as companion object
    private var crn_ = Array<Float>(20){0.0f} 
   
    private var res =    Array<Float>(20){0.0f}  
    private var rr =  Array(10) { Array(20){ Array<Float>(10){10.0f} } }
   
   private var dir_resmin =  Array(20){ Array<Int>(2){10} } 
   private var  resmin =  Array(20){ Array<Float>(2){10.0f} } 
   private var iresmin =  Array<Int>(20){10}  
   
    private var dir = 0
    private var min = 10
    private var minres = 100.0f
    
     private var rrx_ = Array<Float>(10){0.0f} 
     private var rry_ = Array<Float>(10){0.0f} 

     private var sortcrx_ = Array<Float>(10){0.0f} 
     


     
     private var rrx  = Array(10){ Array<Float>(10){0.0f} }
     private var rry  = Array(10){ Array<Float>(10){0.0f} }


     
     private var resx = Array<Float>(10){0.0f} 
     private var resy = Array<Float>(10){0.0f} 
     private var resxy = Array<Float>(10){0.0f} 
     private var result = 0
     private var res0 = 0
     private var res1 = 0
     
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
           
// Filling sample array
// 0
     jj = 0                  
     
     dir_rr[0][0] = arrayOf<Int>(  0,  1,  2,  3, 10, 10, 10, 10, 10, 10)
     dir_rr[0][1] = arrayOf<Int>(  0,  3,  2,  1, 10, 10, 10, 10, 10, 10)
     dir_rr[0][2] = arrayOf<Int>(  1,  2,  3,  0, 10, 10, 10, 10, 10, 10) 
     dir_rr[0][3] = arrayOf<Int>(  1,  0,  3,  2, 10, 10, 10, 10, 10, 10)   
     dir_rr[0][4] = arrayOf<Int>(  2,  3,  1,  0, 10, 10, 10, 10, 10, 10) 
     dir_rr[0][5] = arrayOf<Int>(  2,  1,  0,  3, 10, 10, 10, 10, 10, 10) 
     dir_rr[0][6] = arrayOf<Int>(  3,  2,  1,  0, 10, 10, 10, 10, 10, 10) 
     dir_rr[0][7] = arrayOf<Int>(  3,  0,  1,  2, 10, 10, 10, 10, 10, 10) 

     rr[0][0] = arrayOf<Float>(  0.0f,  1.0f,  2.0f,  3.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f, 10.0f)




     
     
 // Filling sample array
// 1
      jj = 1                  
     dir_rr[1][0] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][1] = arrayOf<Int>(  3,  0, 10, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][2] = arrayOf<Int>(  3,  1,  0, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][3] = arrayOf<Int>(  3,  1,  3, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][4] = arrayOf<Int>(  3,  1,  1,  3, 10, 10, 10, 10, 10, 10)                   
     dir_rr[1][5] = arrayOf<Int>(  3,  0,  1, 10, 10, 10, 10, 10, 10, 10)      
     dir_rr[1][6] = arrayOf<Int>(  3,  1,  2,  0, 10, 10, 10, 10, 10, 10)  
     dir_rr[1][7] = arrayOf<Int>(  0, 10, 10, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][8] = arrayOf<Int>(  1, 10, 10, 10, 10, 10, 10, 10, 10, 10)
     dir_rr[1][9] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     dir_rr[1][10] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)

     
// Filling sample array 
// 2
      jj = 2                  
  
      dir_rr[2][0] = arrayOf<Int>( 3,  0,  1,  3,  0, 10, 10, 10, 10, 10) 
      dir_rr[2][1] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
      dir_rr[2][2] = arrayOf<Int>( 3,  0,  1,  2,  3,  0, 10, 10, 10, 10) 






            
               btnPencil.setOnClickListener { 
            
                // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
 
            
                
                
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             j=0       

 

// sign of 4 directions level   (crx_[j] cry_[j]) to dir_crxy[0-3] = level 0-3 directions Int

      j=0    
                     
      while (j >=0 && j<=19) {
    
   if (crx_[j] > 0.0f &&  cry_[j] > 0.0f ) { dir_cr[j] = 0 }
   if (crx_[j] < 0.0f &&  cry_[j] > 0.0f ) { dir_cr[j] = 1 }
   if (crx_[j] < 0.0f &&  cry_[j] < 0.0f ) { dir_cr[j] = 2 }
   if (crx_[j] > 0.0f &&  cry_[j] < 0.0f ) { dir_cr[j] = 3 }
                          
                          j=j+1 
                          
                            }

      
    // normalize cr[j] - erase repeats and  ??? 10 inside

i=0 
j=0 
old = 10                    
while (j >=0 && j<=19) {
if ( j==0  || dir_cr[j]==old || dir_cr[j] == 10 ) { j = j }
                           else { old=dir_cr[j]; dir_crn[i]=dir_cr[j]; crn_[i]=crx_[j] + cry_[j]; 
                                  crn_[i]=( ( crn_[i] * 100.0).roundToInt() / 100.0).toFloat() 
                                  i=i+1 
                                }
    j = j+1
                      }
              
                


// output as text current painted number in direction sequence
j=0                    
while (j >=0 && j<=19) {
if (j<=9) {aaa[j] =  "  [" + j.toString() + "]=" + dir_crn[j].toString() + " /" + crn_[j].toString() }

  //  aaacr[j] =  "[" + j.toString() + "]=" + "[" + crx_[j].toString() + "," + cry_[j].toString() + "] "  

    //  aaacr[j] =  "[" + j.toString() + "]=" + dir_cr[j].toString() + "  "
    
                    j=j+1
                     }



// result of test on comparing of numbers  direction sequences
                   
      jj=0
while (jj >=0 && jj<=2)  // index of numbers 0, 1 ..
{
          jjj=0
    while (jjj >=0 && jjj<=19) // quantity of variants for each/all numbers
            {
    
                j=0
    while (j >=0 && j<=9) {   //  comparing sequence all current directions dir_cr 0..9 and array of etalins dir_rr; values are directions 0,1,2,3: South-East Sorth-West North-West North-East 
                                  
                                 if ( Math.abs (dir_crn[j] - dir_rr[jj] [jjj] [j] ) > 0)  {
                                                                               dir_res[jj] [jjj] =  dir_res[jj] [jjj] +  1
                                                                                        }
                         j=j+1
   
                           }
              jjj=jjj+1
             }    
 jj=jj+1
}    



// finding min res value is max resemble differ (dir_res[jj] [jjj] ): current paint number directions dir_cr[jj] to  dir_rr (etalons)  
    i=0  
    jj=0
while (jj >=0 && jj<=2)  // index of numbers 0, 1, 2 ..
{
          jjj=0
    while (jjj >=0 && jjj<=19) // quantity of variants for each/all numbers
            {
               if ( dir_res[jj][jjj] < min )  { dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj; dir_resmin[1][0]=10; dir_resmin[1][1]=10; dir_resmin[2][0]=10; dir_resmin[2][1]=10;
                                             i=1; min = dir_res[jj][jjj] 
                                           }
              else { if ( dir_res[jj][jjj] == min )  { dir_resmin[i][0]=jj; dir_resmin[i][1]=jjj 
                                             i=i+1  
                                                 } 
                   }
           jjj = jjj +1
            }
       jj = jj +1
}   


jj=0
while (jj >=0 && jj<=i-1)  
                    {
 
      
           j=0
    while (j >=0 && j<=9)         
                           {
                               var ii = dir_resmin[jj][0] 
                               var iii = dir_resmin[jj][1]   
                               resmin[jj] [j] = Math.abs ( crn_[j]  - rr[ii] [iii] [j] )
                               j=j+1
                           }
                                             
            jj = jj + 1
                        
                     }

                 j=0
                 minres = 100.0f  
                 while (j >=0 && j<=9)        //   
                           {
                              jj=0
                                                     
                               while (jj >=0 && jj<=i-1)  // index of min rr_ ...
                                {
                                  if  (resmin[jj] [j] < minres) {     iresmin [j] = jj;   minres = resmin[jj] [j]   }  
                                  jj = jj + 1 
                                }
                         j=j+1
   
                           }

                jj=0
                while (jj >=0 && jj<=i-1)  
                   {                  
                        j=0        
                       while (j >=0 && j<=9)        //   
                           {
                                                                           
                                                   if  (iresmin[j] == jj  ) {     res [jj] = res [jj] + 1.0f    }  
                                  j = j + 1 
                           }
                           
                         jj=jj+1
   
                   }

// choice max near by  from res[]
 var max=0.0f
                   jj=0
                while (jj >=0 && jj<=i-1)  
                   {                  
                       j=0     
                          while (j >=0 && j<=9)        //   
                           {
                                                                              
                                if  (iresmin[j] == jj  ) {     res [jj] = res [jj] + 1.0f    }  
                                  j = j + 1 
                           }

                           if  ( res [jj] > max) { res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]   
                                                   max = res [jj]  
                                                 }
                          jj=jj+1
   
                   }




                   



 




 
 
                  

textviewid.text =    "res=" + res0 + "," + res1 +  "\n"  + " dir_resmin=" + "  [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  
                      "\n" +  aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9]  
               //      "\n" + aaacr[0] + " " +aaacr[1] + " " + aaacr[2] + " " + aaacr[3] + " " + aaacr[4] + " " + aaacr[5] + " " + aaacr[6] + " " + aaacr[7] + " " + aaacr[8] + " " + aaacr[9]  +
               //      " " + aaacr[10] + " " + aaacr[11] + " " + aaacr[12] + " " + aaacr[13] + " " + aaacr[14] + " " + aaacr[15] + " " + aaacr[16] + " " + aaacr[17] + " " + aaacr[18] + " " + aaacr[19]
//    " res=" + "  [" + res[0][0] + "]" + "  [" + res[0][1] + "]"  + "  [" + res[0][2] + "]"   +
                   
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
