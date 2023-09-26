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
// import com.ferodev.simplepaint.canvas.DrawPencil.Companion.shift



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
    private var iii = 0
    private var k=0
    private var old=0
   
    private var aaa = Array<String>(100){"0"} // text Array for result monitoring
    private var aaacr = Array<String>(800){"0"} // text Array for result monitoring
  
    private var dir_cr =  Array<Int>(800){10}  // array of directions current drawpencil (0,1,2,3)
    private var dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
    private var dir_rr = Array(20) { Array(60){ Array<Int>(10){10} } }  // array of directions for n_etalons(40) for every sample(10pcs: 0,1,...9,+..=)
    private var dir_res =  Array(20){ Array<Int>(60){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
    // crx_ , cry_ - import from drawpencil.kt as companion object
    private var crn_ = Array<Float>(20){0.00f}  // array of of crx_[j] + cry_[j] after n_normalization
    private var crnx_ = Array<Float>(20){0.00f}  // array of of crx_[j]  after n_normalization
    private var crny_ = Array<Float>(20){0.00f}  // array of of cry_[j] after n_normalization

    
    private var rr =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for n_etalons(30) for every sample(10pcs)c
    private var rrx =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for x n_etalons(30) for every sample(10pcs)
    private var rry =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for y n_etalons(30) for every sample(10pcs)
   
    private var dir_resmin =  Array(800){ Array<Int>(2){20} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
    private var  resmin =  Array(60){ Array<Float>(10){10.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminx =  Array(60){ Array<Float>(10){10.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminy =  Array(60){ Array<Float>(10){10.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    
    private var  aresmin = " "
    private var iresmin =  Array<Int>(60){10}  // array of index for of values resmin array with min difference(coincedence) 
    
    private var res =    Array<Int>(60){0}  // array of  counters for  each etalon from iresmin array
   
    private var min = 10 // temporary variable min = dir_res[jj][jjj] 
    private var minres = 100.00f // temporary variable  resmin[jj] [j] < minres
   
    private var max=0   // res [jj] > max res temporary for max coincedence finding
    private var res0 = 0 // res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]  - temporary for max coincedence finding: res0, res1  - etalon number and index its variant in rr array 
                         // in array with min difference(coincedence), res1 - number  
    private var res1 = 0 // look up
    private var l = 0 // counter for different res0 in resnum array
    private var resnum =  Array<Int>(60){30}  // result each cycle adding  as Int Array
    private var sresnum =  Array<Int>(60){30}  // result each cycle adding  as Int Array
    private var asresnum = " "  
 
     
    private var iresnum = 0.0f  // result last operation

    
    private var xiresnum = 0.0f  // result last operation x or /
    private var aresnum = " "                 // result each cycle adding as String
    private var aresnum1 = ""  

    
    private var dec10=1
    private var valuedec=0
    private var shift1=0
    private var sign=0
    private var f=0
    private var ff=0
    private var ffr=0
    private var xsign=1
    private var xxsign=1
    private var dot=0
    private var repeat=0
    
    private var jdec = Array<Int>(60){0}
    
       
         companion object {
        var path = Path()
        var paintBrush = Paint()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
        var shift = 0      
                          }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)
     
         
        supportActionBar?.hide()

      

        
        binding.apply {


           
 
            
// Filling sample array
// 0
     jj = 0                  
     
     dir_rr[0][0] = arrayOf<Int>(  1,  0,  3,  2,   1,  10, 10, 10, 10, 10)
     rrx[0][0] = arrayOf<Float>(  -0.04f,  0.01f,  0.04f,  -0.01f,  -0.03f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     rry[0][0] = arrayOf<Float>(   0.1f,   0.08f,  -0.01f,  -0.04f,  0.01f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][1] = arrayOf<Int>(  2,  3,  0,  1,   2,   3,  10, 10, 10, 10)
     rrx[0][1] = arrayOf<Float>(  -0.01f,  0.01f,  0.05f,  -0.01f,  -0.05f,  0.03f, 0.0f, 0.0f, 0.0f, 0.0f)
     rry[0][1] = arrayOf<Float>(  -0.4f,  -0.11f,  0.03f,   0.06f,  -0.02f, -0.04f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][2] = arrayOf<Int>(  2,  1,  0,  3,   2,  10, 10, 10, 10, 10)
     rrx[0][2] = arrayOf<Float>(  -0.01f,  -0.05f,  0.01f,  0.04f,  -0.01f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     rry[0][2] = arrayOf<Float>(  -0.05f,   0.02f,  0.07f,  -0.01f, -0.04f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)






    


     
 // Filling sample array
// 1
      jj = 1                  
     
  




     
     
// Filling sample array 
// 2
      jj = 2                  
  
    


      
// Filling sample array 
// 3
      jj = 3                  
  
   



      
// Filling sample array 
// 4
      jj = 4                  
  




      
      
// Filling sample array 
// 5
      jj = 5                  
  
     
      

// Filling sample array 
// 6
      jj = 6                  
  



      

      
// Filling sample array 
// 7
      jj = 7                  
  
  



      

    
// Filling sample array 
// 8
      jj = 8                 
  
      
      





      
      

// Filling sample array 
// 9
      jj = 9                 
  
  




      

// Filling sample array 
// +
      jj = 10                
  
      dir_rr[10][0] = arrayOf<Int>( 0,  2,  1,  10,  10,  10,  10,  10, 10, 10) 
      rrx[10][0] = arrayOf<Float>(  0.09f,  -0.03f,  -0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[10][0] = arrayOf<Float>(  0.01f,  -0.82f,   0.15f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][1] = arrayOf<Int>( 1,  3,  10,  10,  10,  10,  10,  10, 10, 10) 
      rrx[10][1] = arrayOf<Float>(  -0.46f,  0.09f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[10][1] = arrayOf<Float>(  0.49f,  -0.01f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
  
      dir_rr[10][2] = arrayOf<Int>( 3,  0,  2,   0,   1,   0,  10,  10, 10, 10) 
      rrx[10][2] = arrayOf<Float>(  0.11f,  0.09f,   -0.32f,  0.01f,  -0.01f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[10][2] = arrayOf<Float>(  -0.01f,  0.01f,  -3.59f,  0.18f,   0.23f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f)



      

      
      
// Filling sample array 
// -
      jj = 11               
  

        dir_rr[11][0] = arrayOf<Int>( 0,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
        rrx[11][0] = arrayOf<Float>(  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
        rry[11][0] = arrayOf<Float>(  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

        dir_rr[11][1] = arrayOf<Int>( 3,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
        rrx[11][1] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
        rry[11][1] = arrayOf<Float>( -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 



        


      
// Filling sample array 
// x
      jj = 12               
  
        dir_rr[12][0] = arrayOf<Int>( 0,  2,  1,  10,  10,  10,  10,  10, 10, 10) 
        rrx[12][0] = arrayOf<Float>(  0.03f,  -0.11f,  -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
        rry[12][0] = arrayOf<Float>(  0.09f,  -1.54f,   0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

        dir_rr[12][1] = arrayOf<Int>( 0,  3,  1,  10,  10,  10,  10,  10, 10, 10) 
        rrx[12][1] = arrayOf<Float>(  0.03f,  0.09f,  -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
        rry[12][1] = arrayOf<Float>(  0.04f,  -1.07f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
  
        dir_rr[12][2] = arrayOf<Int>( 1,  3,  0,  10,  10,  10,  10,  10, 10, 10) 
        rrx[12][2] = arrayOf<Float>( -0.03f,  0.05f,   0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
        rry[12][2] = arrayOf<Float>(  0.08f, -1.16f,   0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

        dir_rr[12][3] = arrayOf<Int>( 1,  2,  0,  10,  10,  10,  10,  10, 10, 10) 
        rrx[12][3] = arrayOf<Float>( -0.01f,  -0.32f,   0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
        rry[12][3] = arrayOf<Float>(  0.02f,  -1.41f,   0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

        


      
// Filling sample array 
// /
      jj = 13              
  
       dir_rr[13][0] = arrayOf<Int>( 3,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
       rrx[13][0] = arrayOf<Float>(  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
       rry[13][0] = arrayOf<Float>( -0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

       dir_rr[13][1] = arrayOf<Int>( 1,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
       rrx[13][1] = arrayOf<Float>(  -0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
       rry[13][1] = arrayOf<Float>(   0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
   
    
    


      

// Filling sample array 
// =
      jj = 14            
  
      dir_rr[14][0] = arrayOf<Int>( 3,  1,  3,  10,  10,  10,  10,  10, 10, 10) 
      rrx[14][0] = arrayOf<Float>(  0.07f,  -0.76f,  0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][0] = arrayOf<Float>( -0.01f,   0.28f, -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][1] = arrayOf<Int>( 0,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rrx[14][1] = arrayOf<Float>(  0.05f,  -0.63f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][1] = arrayOf<Float>(  0.01f,   0.18f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
  
      dir_rr[14][2] = arrayOf<Int>( 3,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rrx[14][2] = arrayOf<Float>(  0.05f,  -0.37f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][2] = arrayOf<Float>( -0.01f,   0.26f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][3] = arrayOf<Int>( 0,  1,  3,  10,  10,  10,  10,  10, 10, 10) 
      rrx[14][3] = arrayOf<Float>(  0.03f,  -0.45f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][3] = arrayOf<Float>(  0.01f,   0.29f, -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][4] = arrayOf<Int>( 3,  0,  1,   0,  3,   0,  10,  10, 10, 10) 
      rrx[14][4] = arrayOf<Float>(  0.07f,  0.01f,  -0.06f,  0.03f,  0.07f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][4] = arrayOf<Float>( -0.01f,  0.01f,   0.23f,  0.01f, -0.01f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][5] = arrayOf<Int>( 0,  2,  0,   10,  10,   10,  10,  10, 10, 10) 
      rrx[14][5] = arrayOf<Float>(  0.06f,  -0.35f,   0.04f,  0.0f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][5] = arrayOf<Float>(  0.01f,  -0.59f,   0.01f,  0.0f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][6] = arrayOf<Int>( 3,  0,  1,   0,  10,   10,  10,  10, 10, 10) 
      rrx[14][6] = arrayOf<Float>(  0.05f,  0.04f,   -0.51f,  0.04f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][6] = arrayOf<Float>(  -0.01f,  0.01f,   0.26f,  0.01f,   0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][7] = arrayOf<Int>( 3,  1,  3,  0,  10,  10,  10,  10, 10, 10) 
      rrx[14][7] = arrayOf<Float>(  0.05f,  -0.56f,  0.08f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[14][7] = arrayOf<Float>( -0.01f,   0.31f, -0.01f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
  

      
      
// Filling sample array 
// ,
      jj = 15            
  
      dir_rr[15][0] = arrayOf<Int>( 0,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
      rrx[15][0] = arrayOf<Float>(  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[15][0] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[15][1] = arrayOf<Int>( 0,  1,  10,  10,  10,  10,  10,  10, 10, 10) 
      rrx[15][1] = arrayOf<Float>(  0.03f,  -0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      rry[15][1] = arrayOf<Float>(  0.03f,   0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     

    

      
      
      
      btnPencil.setOnClickListener {
       
          
          
          // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
     
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya

                    
// sign of 4 directions level   (crx_[j] cry_[j]) to dir_crxy[0-3] = level 0-3 directions Int

      j=0    
                     
      while (j >=0 && j<=799) {
    
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
while (j >=0 && j<=799) {
if ( j==0  || dir_cr[j]==old || dir_cr[j] == 10 ) { j = j }
                           else { old=dir_cr[j]; dir_crn[i]=dir_cr[j]; // crn_[i]=crx_[j] + cry_[j]; - bad idea + and - annigilate
                                //  crn_[i]=( ( crn_[i] * 1000.0).roundToInt() / 1000.0).toFloat() 
                                  crnx_[i]=( ( crx_[j] * 1000.0).roundToInt() / 1000.0).toFloat() 
                                  crny_[i]=( ( cry_[j] * 1000.0).roundToInt() / 1000.0).toFloat() 
                                  i=i+1 
                                }
    j = j+1
                      }
              
                


// output as text current painted number in direction sequence
j=0                    
while (j >=0 && j<=9) {
if (j<=9) {aaa[j] =  "  [" + j.toString() + "]=" + dir_crn[j].toString() + " /" + crnx_[j].toString() + ";" + crny_[j].toString() }

//  aaacr[j] =  "[" + j.toString() + "]=" + "[" + crx_[j].toString() + "," + cry_[j].toString() + "] "  

    //  aaacr[j] =  "[" + j.toString() + "]=" + dir_cr[j].toString() + "  "
    
                    j=j+1
                     }



// result of test on comparing of numbers  direction sequences
                   
      jj=0
while (jj >=0 && jj<=19)  // index of symbols(numbers and operations)  0, 1 ..
{
          jjj=0
    while (jjj >=0 && jjj<=59) // quantity of variants for each/all numbers
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
    i=0                
    while (jj >=0 && jj<=19)  // index of numbers 0, 1, 2 ..//

 {
          jjj=0
      while (jjj >=0 && jjj<=59) // quantity of variants for each/all numbers
               {
                if ( dir_res[jj][jjj] < min )  { dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj;  min = dir_res[jj][jjj];  i=1 
                                                  iii=1; 
                                                  while ( iii<=59) { 
                                                  dir_resmin[iii][0]=20; dir_resmin[iii][1]=60  // max index coincedence etalons 20(0..19) and their variants 40(0..39) (blank index like 10 at directions)  ..
                                                       iii=iii+1
                                                                   }
                                               }
         else { if ( dir_res[jj][jjj] == min )  {   dir_resmin[i][0]=jj; dir_resmin[i][1]=jjj 
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
                   
   
 
            

resminx[jj] [j] = Math.abs ( crnx_[j]  - rrx[ii] [iii] [j] )
resminy[jj] [j] = Math.abs ( crny_[j]  - rry[ii] [iii] [j] )
resmin[jj] [j] = resminx[jj] [j] + resminy[jj] [j]  // integral estimation x and y deviations
                               j=j+1
                           }
                                             
           jj = jj + 1
                        
                     }

                    
                 j=0
          //      minres = 10.00f  
                 while (j >=0 && j<=9 && dir_crn[j] != 10 )        //   
                           {
                              jj=0
                                                     
                               while (jj >=0 && jj<=i-1)  // index of min rr_ ...
                                {
                                                           
                                    if  (resmin[jj] [j] < minres) {    minres = resmin[jj] [j]; iresmin [j] = jj } 
                                   aresmin = aresmin + "    [" + jj.toString() + "]" + "[" + j.toString() + "]=" + resmin[jj] [j].toString()

                                 
                                                                   
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
                                                                              
                                if  (iresmin[j] == jj  ) {     res [jj] = res [jj] + 1     } 
                            
                                
                                  j = j + 1 
                           }

                           if  ( res [jj] > max) { res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]   
                                                   max = res [jj]  
                                                 }
                          
                           
                          jj=jj+1
   
                   }


// output  result as string with diferent length adding new symbols each cycle

      resnum[l]=res0 
     if ( resnum[l]<=9 ) { aresnum =  aresnum + " " + aresnum1 + " " + resnum[l].toString(); aresnum1="" ; repeat=0 } 
   
     
     if( resnum[l] >= 10 && resnum[l] <= 15 ) {                                                            
                                                if ( resnum[l]==10) { aresnum1 =   " + " } 
                                                if ( resnum[l]==11) { aresnum1 =  " - " } 
                                                if ( resnum[l]==12) { aresnum1 =   " x " } 
                                                if ( resnum[l]==13) { aresnum1 =   " / " } 
                                                if ( resnum[l]==14) { if (l==0) {aresnum1 =   "="}
                                                                      if (l>0) {aresnum1 =   ""  } 
                                                                    }  
                                                if ( resnum[l]==15) { aresnum1 =  "," } 

                                                
                                                if (repeat ==1) { resnum[l-1] = resnum[l]; sresnum[l-1] = resnum[l-1];resnum[l]=30; sresnum[l] = 30; l=l-1  }     
                                                if (repeat ==0) {sresnum[l] = resnum[l] ; dot=0; repeat=1 }

                                                
                                              }
 
            else {      if (resnum[l] <= 9) {
                           j=0; 
                          while ( j <= 1)        //    ..
                              {                            
                            if(j==0) { sresnum[l] = resnum[l] }
                            if( (j==1) && ((l-1) >= 0) && (resnum[l-1] <=9) && dot==0)  { sresnum[l] = sresnum[l-1]*10 + sresnum[l]; shift1=shift1+1 } 
                            if( (j==1) && ((l-1) >= 0) && (resnum[l-1] <=9) && dot==1)  { sresnum[l] = sresnum[l-1] + sresnum[l]/10; shift1=shift1+1 } 
                            j=j+1 
                              
                              } 
                                            }
                   if (resnum[l] == 15) {   dot=1; l=l-1 }

                 }                
                         
                                                               
      if (l==0 &&  resnum[0] == 11)  {  xxsign=-1 }                              
      if(l>0) { if (resnum[l] >= 10 && ((resnum[l-1] <= 9) || (f==1) ) ) { sign = 1; shift1=0 ; ff=1 } }
      if( resnum[l] <= 9 && ff==0 )  { iresnum=xxsign*sresnum[l].toFloat(); xxsign=1 }              
         

   
       
                
      if(sign == 1  && resnum[l-1-shift1]==10 ) {   iresnum= iresnum + xiresnum; xiresnum=0.00f; xsign=1
                                                  if (shift1==0) { iresnum= iresnum +  resnum[l].toFloat() } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum +  ( sresnum[l].toFloat() - sresnum[l-1].toFloat() )  } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==11) {  iresnum= iresnum + xiresnum; xiresnum=0.00f; xsign=-1
                                                  if (shift1==0) { iresnum= iresnum - sresnum[l].toFloat() } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum - ( sresnum[l].toFloat() - sresnum[l-1].toFloat() ) } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==12) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2].toFloat() * sresnum[l].toFloat();ffr=1 
                                                     iresnum= iresnum - xsign * sresnum[l-2].toFloat()  
                                                                          } 
      
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum * sresnum[l].toFloat() } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum= xiresnum *  (sresnum[l].toFloat() / sresnum[l-1].toFloat() ) } 
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==13) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2].toFloat() / sresnum[l].toFloat() ; ffr=1 
                                                      iresnum= iresnum - xsign * sresnum[l-2].toFloat()                 
                                                                          } 
         
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum / sresnum[l].toFloat() } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum= xiresnum /  sresnum[l].toFloat() * sresnum[l-1].toFloat()  }  
                                                  
                                                } 
     


     
      if(l>=1 && resnum[l]==14) { iresnum = iresnum + xiresnum; aresnum =  aresnum + "="  + iresnum.toString()  ; resnum[l+1]=iresnum.toInt(); f=1;  sresnum[l+1]=resnum[l+1]; sign=0; xiresnum=0.0f;  ffr=0; xsign=1;  l=l+1; repeat=0 }
             

     
                
    textviewid.text =     "  " + aresnum +  "  " + aresnum1 + "\n" +
                     "res=[" + res0 + "][" + res1 +  "]"   +  
                 " dir_resmin=" + min + ",    [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  "/[" + dir_resmin[3][0] + "]" + "[" + dir_resmin[3][1] + "]" +  
                 "\n" +  aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9]  +
                " iresnum=" + iresnum +  " xiresnum=" + xiresnum +   " shift1=" + shift1 +  " sign=" + sign + " l=" + l + " f=" + f + " ff=" + ff + " ffr=" + ffr + " dot=" + dot +  " repeat=" + repeat +
                "resnum=" + resnum[0] + " " + resnum[1] + " " + resnum[2] + " " + resnum[3] + " " + resnum[4] + " " + resnum[5] + " " + resnum[6] + " " + resnum[7] + " " + sresnum[8] + " " + sresnum[9] +
                "sresnum=" + sresnum[0] + " " + sresnum[1] + " " + sresnum[2] + " " + sresnum[3] + " " + sresnum[4] + " " + sresnum[5] + " " + sresnum[6] + " " + sresnum[7] +  " " + sresnum[8] + " " + sresnum[9] 
                    
                //     "\n" +  "iresmin=" + iresmin[0] + " " + iresmin[1] + " " + iresmin[2] + " " + iresmin[3] +
                //     "\n" +  "aresmin=" + aresmin +
                //     "\n" +  "minres=" + minres 
                   //    "\n" + "sresnum[l-2]=" + sresnum[l-3] +  "  sresnum[l]=" + sresnum[l-1]    
                    


       


                    
                    //aaacr[0] + " " +aaacr[40] + " " + aaacr[80] + " " + aaacr[120] + " " + aaacr[160] + " " + aaacr[200] + " " + aaacr[240] + " " + aaacr[280] + " " + aaacr[320] + " " + aaacr[360]  +
                      //  " " + aaacr[400] + " " + aaacr[440] + " " + aaacr[460] + " " + aaacr[500] + " " + aaacr[540] + " " + aaacr[580] + " " + aaacr[640] + " " + aaacr[680] + " " + aaacr[720] + " " + aaacr[760]      

                    l=l+1
                    
                    shift=1
                    
                    btnPencil.setImageResource(R.drawable.ic_selected_pencil)
                    btnPencil.setBackgroundResource(R.drawable.background_cards)
                  
                     drawPencil.visibility = View.VISIBLE
                             
                     
            
      // initial value initialization needed for all arrays to begin new symbol on next step


   j = 0
   i = 0
    jj = 0  
    jjj = 0  
    iii = 0
    k=0
    old=0



                    
                                 crx_ = Array<Float>(800){0.0f}
                                 cry_ = Array<Float>(800){0.0f} 
                                 dir_cr =  Array<Int>(800){10} 
        dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
        dir_res =  Array(20){ Array<Int>(60){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
        crn_ = Array<Float>(20){0.0f}  // array of of crx_[j] + cry_[j] after n_normalization

        dir_resmin =  Array(800){ Array<Int>(2){20} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
            resmin =  Array(60){ Array<Float>(10){10.0f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
            aresmin = " "
          iresmin =  Array<Int>(60){10}  // array of index for of values resmin array with min difference(coincedence) 
              res =    Array<Int>(60){0}  // array of  counters for  each etalon from iresmin array

              min = 10 // temporary variable min = dir_res[jj][jjj] 
           minres = 100.0f // temporary variable  resmin[jj] [j] < minres
   
    max=0   // res [jj] > max res temporary for max coincedence finding
    res0 = 0 // res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]  - temporary for max coincedence finding: res0, res1  - etalon number and index its variant in rr array 
                         // in array with min difference(coincedence), res1 - number  
    res1 = 0 // look up


                 

                      


         isPencilIconClicked = !isPencilIconClicked


     
                
                }
                
                
                else {
                   
                    // textviewid.text =   "shift=" + shift 
                    //btnPencil.setImageResource(R.drawable.ic_unselected_pencil)
                    //btnPencil.setBackgroundResource(R.drawable.background_card)

                     }
            }

              
        }
        } 
   
   
}
