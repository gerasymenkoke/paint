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


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

     // from poperednikiv
    var context=0
    var attrs=0
    var defStyleAttr=0
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
    private var dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
    private var dir_rr = Array(20) { Array(20){ Array<Int>(10){10} } }  // array of directions for n_etalons(20) for every sample(10pcs: 0,1,...9,+..=)
    private var dir_res =  Array(20){ Array<Int>(20){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
    // crx_ , cry_ - import from drawpencil.kt as companion object
    private var crn_ = Array<Float>(20){0.0f}  // array of of crx_[j] + cry_[j] after n_normalization
    private var rr =  Array(20) { Array(20){ Array<Float>(20){10.0f} } }  //  array of value for n_etalons(20) for every sample(10pcs)
   
    private var dir_resmin =  Array(20){ Array<Int>(10){10} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
    private var  resmin =  Array(20){ Array<Float>(10){10.0f} } // array of values for each etalon(2 pcs)  with min difference(coincedence)
    private var iresmin =  Array<Int>(20){10}  // array of index for of values resmin array with min difference(coincedence) 
    private var res =    Array<Int>(20){0}  // array of  counters for  each etalon from iresmin array
   
    private var min = 10 // temporary variable min = dir_res[jj][jjj] 
    private var minres = 100.0f // temporary variable  resmin[jj] [j] < minres
   
    private var max=0   // res [jj] > max res temporary for max coincedence finding
    private var res0 = 0 // res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]  - temporary for max coincedence finding: res0, res1  - etalon number and index its variant in rr array 
                         // in array with min difference(coincedence), res1 - number  
    private var res1 = 0 // look up
    
       
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
     rr[0][0] = arrayOf<Float>(  0.5f,  0.08f,  -0.28f,  -0.46f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][1] = arrayOf<Int>(  0,  3,  2,  1, 10, 10, 10, 10, 10, 10)
     rr[0][1] = arrayOf<Float>(  0.33f,  -0.31f,  -0.4f,  -0.11f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][2] = arrayOf<Int>(  1,  2,  3,  0, 10, 10, 10, 10, 10, 10) 
     
     dir_rr[0][3] = arrayOf<Int>(  1,  0,  3,  2, 10, 10, 10, 10, 10, 10)   
     rr[0][3] = arrayOf<Float>(  0.05f,  0.31f,  0.16f,  -0.24f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][4] = arrayOf<Int>(  2,  3,  1,  0, 10, 10, 10, 10, 10, 10) 
     
     dir_rr[0][5] = arrayOf<Int>(  2,  1,  0,  3, 10, 10, 10, 10, 10, 10) 
     
     dir_rr[0][6] = arrayOf<Int>(  3,  2,  1,  0, 10, 10, 10, 10, 10, 10) 
     
     dir_rr[0][7] = arrayOf<Int>(  3,  0,  1,  2, 10, 10, 10, 10, 10, 10) 
     rr[0][7] = arrayOf<Float>(  -0.2f,  0.4f,  0.16f,  -0.37f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][8] = arrayOf<Int>(  3,  0,  1,  3, 10, 10, 10, 10, 10, 10) 
     rr[0][8] = arrayOf<Float>(  -0.21f,  0.33f,  -0.06f,  -0.24f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     
     
 // Filling sample array
// 1
      jj = 1                  
     dir_rr[1][0] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     
     dir_rr[1][1] = arrayOf<Int>(  3,  0, 10, 10, 10, 10, 10, 10, 10, 10)
     
     dir_rr[1][2] = arrayOf<Int>(  3,  1,  0, 10, 10, 10, 10, 10, 10, 10)
     
          
     dir_rr[1][3] = arrayOf<Int>(  3,  1,  3, 10, 10, 10, 10, 10, 10, 10)
     rr[1][3] = arrayOf<Float>(  -0.55f,  0.56f,  0.22f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][4] = arrayOf<Int>(  3,  1,  1,  3, 10, 10, 10, 10, 10, 10)                   
     
     dir_rr[1][5] = arrayOf<Int>(  3,  0,  1, 10, 10, 10, 10, 10, 10, 10)      
     
     dir_rr[1][6] = arrayOf<Int>(  3,  1,  2,  0, 10, 10, 10, 10, 10, 10)  
     rr[1][6] = arrayOf<Float>(  -0.77f,  0.35f,  -0.12f,  0.26f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][7] = arrayOf<Int>(  0, 10, 10, 10, 10, 10, 10, 10, 10, 10)
       
     dir_rr[1][8] = arrayOf<Int>(  1, 10, 10, 10, 10, 10, 10, 10, 10, 10)
     
     dir_rr[1][9] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][9] = arrayOf<Float>(  -0.42f,  0.16f,  -0.08f,   0.05f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
     
     dir_rr[1][10] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     
     dir_rr[1][11] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][11] = arrayOf<Float>(  -0.25f,  0.08f,  0.25f,  0.13f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][12] = arrayOf<Int>( 3,  1,  0,  3, 10, 10, 10, 10, 10, 10)
     rr[1][11] = arrayOf<Float>(  -0.5f,  0.54f,  0.36f,  0.15f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][13] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][13] = arrayOf<Float>(  -0.90f,  0.13f,  0.25f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][14] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][11] = arrayOf<Float>(  -1.82f,  0.39f,  0.22f,  0.16f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][15] = arrayOf<Int>( 3,  1,  0,  1,  2,  3,  0, 10, 10, 10)   
     rr[1][15] = arrayOf<Float>(  -0.38f,  0.32f,  0.17f,  0.08f, -0.13f, 0.04f,  0.07f,  0.0f,  0.0f,  0.0f)


     
     
// Filling sample array 
// 2
      jj = 2                  
  
      dir_rr[2][0] = arrayOf<Int>( 3,  0,  1,  3,  0, 10, 10, 10, 10, 10) 

      
      dir_rr[2][1] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)
      rr[2][1] = arrayOf<Float>(  -0.24f,  -0.1f,  0.09f,  0.24f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][2] = arrayOf<Int>( 3,  0,  1,  2,  3,  0, 10, 10, 10, 10) 
      rr[2][2] = arrayOf<Float>(  -0.04f,  0.24f,  0.19f,  -0.08f, 0.06f, 0.23f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][3] = arrayOf<Int>( 3,  0,  1,  0,  3,  0, 10, 10, 10, 10) 
      rr[2][3] = arrayOf<Float>(  -0.79f,  0.58f,  0.30f,   0.19f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][4] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][4] = arrayOf<Float>(  -0.46f,  0.31f,  0.02f,   0.14f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][5] = arrayOf<Int>( 3,  1,  2,  0, 10, 10, 10, 10, 10, 10)
      rr[2][5] = arrayOf<Float>(  -0.32f,  0.51f, -0.34f,   0.45f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][6] = arrayOf<Int>( 3,  0,  1,  2,  0, 10, 10, 10, 10, 10)
      rr[2][6] = arrayOf<Float>(  -0.75f,  0.71f,  0.01f,  -0.20f, 0.33f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  
      
      dir_rr[2][7] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][7] = arrayOf<Float>(  -0.33f,  0.26f,  0.35f,  0.18f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][8] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][8] = arrayOf<Float>(  -0.41f,  0.63f,  -0.01f,   0.17f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][9] = arrayOf<Int>( 2,  3,  0,  1,  3,  0, 10, 10, 10, 10)
      rr[2][9] = arrayOf<Float>(  -0.2f,  -0.36f,  0.33f,   0.16f, 0.06f, 0.15f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][10] = arrayOf<Int>( 2,  3,  0,  1,  3,  0, 10, 10, 10, 10)
      rr[2][10] = arrayOf<Float>(  -0.48f,  -0.28f,  0.34f,   0.16f, 0.02f, 0.21f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][11] = arrayOf<Int>( 2,  3,  0,  1,  2,  3, 0, 10, 10, 10)
      rr[2][11] = arrayOf<Float>(  -0.48f,  -1.24f,  0.54f,   0.43f, -0.07f, 0.15f,  0.28f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][12] = arrayOf<Int>( 1,  3,  0,  1,  2,  3, 0, 10, 10, 10)
      rr[2][12] = arrayOf<Float>(  -0.2f,  -0.19f,  0.61f,   0.16f, -0.04f, 0.1f,  0.26f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][13] = arrayOf<Int>( 2,  3,  0,  1,  3,  0, 10, 10, 10, 10)
      rr[2][13] = arrayOf<Float>(  -0.2f,  -0.27f,  0.27f,   0.2f, -0.04f, 0.18f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][14] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)
      rr[2][14] = arrayOf<Float>(  -0.36f,  0.56f,  0.04f,  0.46f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][15] = arrayOf<Int>( 1,  2,  3,  0,  1,  3,  0, 10, 10, 10)
      rr[2][15] = arrayOf<Float>(  -0.07f, -0.21f,  -0.46f,  0.43f, 0.24f, 0.05f,  0.25f,  0.0f,  0.0f,  0.0f)



      

            
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
                                                                           
                                                   if  ( iresmin[j] == jj  ) {     res [jj] = res [jj] + 1    }  
                                  j = j + 1 
                           }
                           
                         jj=jj+1
   
                   }

// choice max near by  from res[]
 var max=0
                   jj=0
                while (jj >=0 && jj<=i-1)  
                   {                  
                       j=0     
                          while (j >=0 && j<=9)        //   
                           {
                                                                              
                                if  (iresmin[j] == jj  ) {     res [jj] = res [jj] + 1    }  
                                  j = j + 1 
                           }

                           if  ( res [jj] > max) { res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]   
                                                   max = res [jj]  
                                                 }
                          jj=jj+1
   
                   }




                   



 




 
 
                  

textviewid.text =    "res=[" + res0 + "][" + res1 +  "]\n"    +    " dir_resmin=" + min + ",    [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  "/[" + dir_resmin[3][0] + "]" + "[" + dir_resmin[3][1] + "]"    + 
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
