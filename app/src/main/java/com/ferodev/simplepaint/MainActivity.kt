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
    private var rr =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for n_etalons(30) for every sample(10pcs)
   
    private var dir_resmin =  Array(800){ Array<Int>(2){20} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
    private var  resmin =  Array(60){ Array<Float>(10){10.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
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
    private var resnum =  Array<Int>(60){10}  // result each cycle adding  as Int Array
    private var sresnum =  Array<Int>(60){100}  // result each cycle adding  as Int Array


     
    private var iresnum = 0.00f  // result last operation
    private var aresnum = " "                 // result each cycle adding as String

    private var dec10=1
    private var valuedec=0
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
     
     dir_rr[0][0] = arrayOf<Int>(  3,  0,  1,  2,  10,  10, 10, 10, 10, 10)
     rr[0][0] = arrayOf<Float>(  -0.03f,  0.03f,  0.03f,  -0.03f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][1] = arrayOf<Int>(  0,  1,  2,  3,  10, 10, 10, 10, 10, 10)
     rr[0][1] = arrayOf<Float>(  0.06f,  0.08f,  -0.05f,  -0.05f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][2] = arrayOf<Int>(  1,  2,  3,  0,  10, 10, 10, 10, 10, 10)
     rr[0][2] = arrayOf<Float>(  0.04f,  -0.04f,  -0.08f,  0.03f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][3] = arrayOf<Int>(  2,  3,  0,  1,  10, 10, 10, 10, 10, 10)
     rr[0][3] = arrayOf<Float>(  -0.05f,  -0.06f,  0.03f,  0.02f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][4] = arrayOf<Int>(  0,  3,  2,  1,  10, 10, 10, 10, 10, 10)
     rr[0][4] = arrayOf<Float>(   0.09f,  0.01f,  -0.07f,  -0.02f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

     dir_rr[0][5] = arrayOf<Int>(  3,  2,  1,  0,  10, 10, 10, 10, 10, 10)
     rr[0][5] = arrayOf<Float>(   0.01f,  -0.07f,  -0.02f,  0.07f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][6] = arrayOf<Int>(  2,  1,  0,  3,  10, 10, 10, 10, 10, 10)
     rr[0][6] = arrayOf<Float>(   -0.05f,  -0.01f,  0.05f,  0.01f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][7] = arrayOf<Int>(  1,  0,  3,  2, 10,  10, 10, 10, 10, 10)
     rr[0][7] = arrayOf<Float>(   0.01f,  0.06f,  0.03f,  -0.05f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    
         
     dir_rr[0][8] = arrayOf<Int>(  1,  0,  3,  2, 10, 10, 10, 10, 10, 10)   
     rr[0][8] = arrayOf<Float>(  0.05f,  0.31f,  0.16f,  -0.24f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
         
     dir_rr[0][9] = arrayOf<Int>(  2,  1,  0,  3, 10, 10, 10, 10, 10, 10) 
     rr[0][9] = arrayOf<Float>(  -0.2f,  -0.09f,  0.27f,  0.08f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][10] = arrayOf<Int>(  3,  2,  1,  0, 10, 10, 10, 10, 10, 10) 
     rr[0][10] = arrayOf<Float>(  0.07f,  -0.28f,  0.0f,  0.42f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][11] = arrayOf<Int>(  3,  0,  1,  2, 10, 10, 10, 10, 10, 10) 
     rr[0][11] = arrayOf<Float>(  -0.2f,  0.4f,  0.16f,  -0.37f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][12] = arrayOf<Int>(  3,  0,  1,  2,  3, 10, 10, 10, 10, 10) 
     rr[0][12] = arrayOf<Float>(  -0.1f,  0.28f,  0.01f,  -0.47f, -0.38f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][13] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][13] = arrayOf<Float>(  0.35f,  0.3f,  0.03f,  -0.46f, -0.12f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     dir_rr[0][14] = arrayOf<Int>(  2,  3,  0,  1,  10, 10, 10, 10, 10, 10)   
     rr[0][14] = arrayOf<Float>(  -0.27f,  -0.46f,  0.48f,  0.25f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][15] = arrayOf<Int>(  2,  3,  0,  1,  2, 10, 10, 10, 10, 10)   
     rr[0][15] = arrayOf<Float>(  -0.36f,  -0.46f,  0.37f,  0.22f, -0.3f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
    
     dir_rr[0][16] = arrayOf<Int>(  2,  1,  0,  3,  2, 10, 10, 10, 10, 10)   
     rr[0][16] = arrayOf<Float>(  -0.3f,  0.08f,  0.27f,  0.14f, -0.27f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][17] = arrayOf<Int>(  2,  3,  0,  1,  2,  3,  1, 10, 10, 10)   
     rr[0][17] = arrayOf<Float>(  -0.1f,  -0.12f,  0.08f,  0.07f, -0.06f, -0.03f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][18] = arrayOf<Int>(  1,  0,  3,  2,  1,  3, 10, 10, 10, 10)   
     rr[0][18] = arrayOf<Float>(  -0.02f,  0.08f,  0.03f,  -0.13f, -0.01f, -0.01f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][19] = arrayOf<Int>( 3,  2,  1,  0,  3,  10,  10,  10, 10, 10) 
     rr[0][19] = arrayOf<Float>(  -0.07f,  -0.08f,  -0.04f,  0.11f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[0][20] = arrayOf<Int>( 2,  1,  0,  3,  2,  1, 10, 10, 10, 10) 
     rr[0][20] = arrayOf<Float>(  -0.03f,  -0.02f,  0.05f,  0.03f, -0.09f, -0.02f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[0][21] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][21] = arrayOf<Float>(  0.07f,  0.11f,  0.02f,  -0.09f,  0.00f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][22] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][22] = arrayOf<Float>(  -0.03f,  0.11f,  0.02f,  -0.06f,  -0.01f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][23] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][23] = arrayOf<Float>(  -0.01f,  0.08f,  0.07f,  -0.11f,  -0.03f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][24] = arrayOf<Int>(  3,  0,  1,  3, 10, 10, 10, 10, 10, 10) 
     rr[0][24] = arrayOf<Float>(  -0.21f,  0.33f,  -0.06f,  -0.24f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][25] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][25] = arrayOf<Float>(  0.00f,  0.08f,  0.05f,  -0.08f, -0.04f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][26] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][26] = arrayOf<Float>(  0.09f,  0.05f,  0.03f,  -0.05f,  -0.01f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][27] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][27] = arrayOf<Float>(  -0.02f,  0.09f,  0.04f,  -0.1f,  -0.01f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     dir_rr[0][28] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
     rr[0][28] = arrayOf<Float>(  0.01f,  0.08f,  0.03f,  -0.07f,  0.00f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

     
  
     
 // Filling sample array
// 1
      jj = 1                  
     
            
          
     dir_rr[1][0] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     rr[1][0] = arrayOf<Float>(  -0.54f,  0.41f,  0.0f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][1] = arrayOf<Int>(  3,  0, 10, 10, 10, 10, 10, 10, 10, 10)
     rr[1][1] = arrayOf<Float>(  -0.42f,  0.53f,  0.0f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][2] = arrayOf<Int>(  3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][2] = arrayOf<Float>(  0.01f,  0.03f,  0.01f,  0.04f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][3] = arrayOf<Int>(  3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][3] = arrayOf<Float>(  0.0f,  0.06f,  -0.16f,  0.02f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
         
     dir_rr[1][4] = arrayOf<Int>(  3,  1,  2,  0, 10, 10, 10, 10, 10, 10)  
     rr[1][4] = arrayOf<Float>(  -0.77f,  0.35f,  -0.12f,  0.26f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][5] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][5] = arrayOf<Float>(  -0.42f,  0.16f,  -0.08f,   0.05f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
     
     dir_rr[1][6] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][6] = arrayOf<Float>(  -0.39f,  0.36f,  -0.08f,   0.14f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
     
     dir_rr[1][7] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][7] = arrayOf<Float>(  -0.25f,  0.08f,  0.25f,  0.13f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][8] = arrayOf<Int>( 3,  1,  0,  3, 10, 10, 10, 10, 10, 10)
     rr[1][8] = arrayOf<Float>(  -0.5f,  0.54f,  0.36f,  0.15f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][9] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][9] = arrayOf<Float>(  -0.90f,  0.13f,  0.25f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][10] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][10] = arrayOf<Float>(  -1.82f,  0.39f,  0.22f,  0.16f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][11] = arrayOf<Int>( 3,  1,  0,  1,  2,  3,  0, 10, 10, 10)   
     rr[1][11] = arrayOf<Float>(  -0.38f,  0.32f,  0.17f,  0.08f, -0.13f, 0.04f,  0.07f,  0.0f,  0.0f,  0.0f)
   
     dir_rr[1][12] = arrayOf<Int>( 3,  1,  2,  3,  0, 10, 10, 10, 10, 10)
     rr[1][12] = arrayOf<Float>(  -0.48f,  0.47f,  -0.03f,  0.21f, 0.05f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][13] = arrayOf<Int>(  3,  1,  0,  2,  0, 10, 10, 10, 10, 10)
     rr[1][13] = arrayOf<Float>(  -1.23f,  0.71f,  0.25f,  -0.08f, 0.16f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][14] = arrayOf<Int>( 3,  0,  1,  2,  0, 10, 10, 10, 10, 10)
     rr[1][14] = arrayOf<Float>(  -0.48f,  0.05f,  0.41f,  -0.13f, 0.22f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  

     dir_rr[1][15] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][15] = arrayOf<Float>(  -0.67f,  0.57f,  -0.1f,   0.06f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][16] = arrayOf<Int>(  3,  1,  0,  2,  3,  0, 10, 10, 10, 10)
     rr[1][16] = arrayOf<Float>(  -0.51f,  0.39f,  0.08f,  -0.07f, 0.05f, 0.05f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][17] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][17] = arrayOf<Float>(  -0.52f,  0.17f,  0.45f,   0.16f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][18] = arrayOf<Int>(  3,  0,  1,  0,  3,  0, 10, 10, 10, 10)
     rr[1][18] = arrayOf<Float>(  -0.69f,  0.21f,  0.4f,   0.06f, 0.09f, 0.18f,  0.0f,  0.0f,  0.0f,  0.0f)
     
     dir_rr[1][19] = arrayOf<Int>( 3,  1,  2,  3,  10, 10, 10, 10, 10, 10)
     rr[1][19] = arrayOf<Float>(  -1.04f,  0.49f,  -0.15f,  0.28f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][20] = arrayOf<Int>(  3,  1,  2,  0, 10, 10, 10, 10, 10, 10)  
     rr[1][20] = arrayOf<Float>(  -0.29f,  0.51f,  -0.11f,  0.21f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][21] = arrayOf<Int>(  3,  1,  2,  3, 10, 10, 10, 10, 10, 10)  
     rr[1][21] = arrayOf<Float>(  -0.41f,  0.49f,  -0.19f,  0.14f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][22] = arrayOf<Int>(  3,  0,  1,  0, 2, 10, 10, 10, 10, 10)
     rr[1][22] = arrayOf<Float>(  -0.46f,  0.58f,  0.42f,   0.14f, -0.12f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][23] = arrayOf<Int>( 3,  1,  2,  3, 10,  10, 10, 10, 10, 10)
     rr[1][23] = arrayOf<Float>(  -0.21f,  0.58f,  -0.03f,  0.1f, 0.05f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][24] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][24] = arrayOf<Float>(  -0.49f,  0.53f,  0.22f,  0.09f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][25] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][25] = arrayOf<Float>(  -0.22f, 0.65f,  -0.16f,  0.15f,  0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][26] = arrayOf<Int>( 3,  0,  1,  3, 10, 10, 10, 10, 10, 10)
     rr[1][26] = arrayOf<Float>(  -0.01f, 0.07f,  0.07f,  0.03f,  0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][27] = arrayOf<Int>( 3,  1,  3,  0, 10, 10, 10, 10, 10, 10)   
     rr[1][27] = arrayOf<Float>(  -0.01f,  0.24f,  0.08f,  0.05f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][28] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][28] = arrayOf<Float>(  0.0f,  0.14f,  0.07f,   0.11f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][29] = arrayOf<Int>(  3,  0,  2,  0,  3, 10, 10, 10, 10, 10)
     rr[1][29] = arrayOf<Float>(  -0.03f,  0.17f,  -0.05f,   0.06f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][30] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][30] = arrayOf<Float>(  -0.03f,  0.05f,  0.1f,   0.09f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][31] = arrayOf<Int>(  3,  1,  3, 10, 10, 10, 10, 10, 10, 10)
     rr[1][31] = arrayOf<Float>(  -0.55f,  0.56f,  0.22f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
     
     dir_rr[1][32] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][32] = arrayOf<Float>(  -0.41f,  0.09f,  0.37f,   0.14f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
              
     dir_rr[1][33] = arrayOf<Int>(  3,  0,  1, 10, 10, 10, 10, 10, 10, 10)      
     rr[1][33] = arrayOf<Float>(  -0.39f,  0.02f,  -0.04f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][34] = arrayOf<Int>(  3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
     rr[1][34] = arrayOf<Float>(  0.02f,  0.12f,  0.14f,   0.04f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

     dir_rr[1][35] = arrayOf<Int>(  3,  1,  3, 10, 10, 10, 10, 10, 10, 10)
     rr[1][35] = arrayOf<Float>(  0.01f,  0.24f,  0.00f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][36] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     rr[1][36] = arrayOf<Float>(  -0.01f,  0.14f,  0.0f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][37] = arrayOf<Int>(  3,  1,  0, 10, 10, 10, 10, 10, 10, 10)
     rr[1][37] = arrayOf<Float>(  0.00f,  0.02f,  0.07f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 
      
     dir_rr[1][38] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     rr[1][38] = arrayOf<Float>(  0.03f,  0.09f,  0.0f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][39] = arrayOf<Int>(  3,  1,  0, 10, 10, 10, 10, 10, 10, 10)
     rr[1][39] = arrayOf<Float>(  0.02f,  0.08f,  0.06f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][40] = arrayOf<Int>(  3,  1,  0, 10, 10, 10, 10, 10, 10, 10)
     rr[1][40] = arrayOf<Float>(  0.06f,  0.13f,  0.02f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

     dir_rr[1][41] = arrayOf<Int>(  3,  1, 10, 10, 10, 10, 10, 10, 10, 10)
     rr[1][41] = arrayOf<Float>(  0.07f,  0.12f,  0.0f,  0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 


     
     
// Filling sample array 
// 2
      jj = 2                  
  
      dir_rr[2][0] = arrayOf<Int>( 3,  0,  1,  3,  0, 10, 10, 10, 10, 10) 
      rr[2][0] = arrayOf<Float>(  -0.66f,  0.53f,  0.35f,  -0.04f, 0.24f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
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
      
      dir_rr[2][16] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][16] = arrayOf<Float>(  -0.63f,  0.64f,  0.16f,   0.09f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][17] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][17] = arrayOf<Float>(  -0.37f,  0.17f,  0.21f,   0.13f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][18] = arrayOf<Int>( 3,  0,  1,  10, 10, 10, 10, 10, 10, 10)
      rr[2][18] = arrayOf<Float>(  -0.4f,  0.17f,  0.22f,   0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[2][19] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][19] = arrayOf<Float>(  -0.16f,  0.35f,  0.25f,   0.23f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][20] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][20] = arrayOf<Float>(  -0.07f,  0.14f,  0.14f,   0.2f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
 
      dir_rr[2][21] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][21] = arrayOf<Float>(  -0.43f,  0.4f,  0.3f,   0.05f, 0.00f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)   

      dir_rr[2][22] = arrayOf<Int>( 3,  0,  1,  2,  0, 10, 10, 10, 10, 10)
      rr[2][22] = arrayOf<Float>(  -0.22f,  0.37f,  0.32f,  -0.07f, 0.17f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  

      dir_rr[2][23] = arrayOf<Int>( 3,  0,  1,  2,  0, 10, 10, 10, 10, 10)
      rr[2][23] = arrayOf<Float>(  -0.67f,  0.57f,  0.51f,  -0.06f, 0.31f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  

      dir_rr[2][24] = arrayOf<Int>( 3,  0,  1,  0, 10, 10, 10, 10, 10, 10)
      rr[2][24] = arrayOf<Float>(  -0.04f,  0.11f,  0.2f,   0.15f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][25] = arrayOf<Int>( 3,  0,  1,  2,  0, 10, 10, 10, 10, 10)
      rr[2][25] = arrayOf<Float>(  -0.26f,  0.2f,  0.16f,  -0.05f, 0.14f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  

      dir_rr[2][26] = arrayOf<Int>( 2,  3,  0,  1,  0, 10, 10, 10, 10, 10)
      rr[2][26] = arrayOf<Float>(  -0.29f,  -0.68f,  0.23f,  0.27f, 0.16f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)  

      dir_rr[2][27] = arrayOf<Int>( 2,  3,  0,  1,  2,  0, 10, 10, 10, 10)
      rr[2][27] = arrayOf<Float>(  -0.28f,  0.06f,  0.43f,  0.27f, -0.15f, 0.29f,  0.0f,  0.0f,  0.0f,  0.0f)  
  
      dir_rr[2][28] = arrayOf<Int>( 3,  0,  1,  10, 10, 10, 10, 10, 10, 10)
      rr[2][28] = arrayOf<Float>(  -0.26f,  0.32f,  0.39f,   0.0f, 0.0f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][29] = arrayOf<Int>( 3,  0,  1,  2,  3,  0,  3, 10, 10, 10) 
      rr[2][29] = arrayOf<Float>(  -0.08f,  0.07f,  0.11f,  -0.03f, 0.00f, 0.03f,  0.00f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][30] = arrayOf<Int>( 2,  3,  0,  1,  2,  3, 0, 3, 10, 10)
      rr[2][30] = arrayOf<Float>(  -0.02f,  -0.08f,  0.06f,   0.12f, -0.02f, -0.01f,  0.05f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][31] = arrayOf<Int>( 3,  0,  1,  2,  3,  0, 10, 10, 10, 10) 
      rr[2][31] = arrayOf<Float>(  -0.08f,  0.04f,  0.08f,  -0.02f, -0.01f, 0.05f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[2][32] = arrayOf<Int>( 2,  3,  0,  1,  3,  10, 10, 10, 10, 10)
      rr[2][32] = arrayOf<Float>(  -0.02f,  -0.05f,  0.04f,   0.08f,  0.01f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      


      

// Filling sample array 
// 3
      jj = 3                  
  
     dir_rr[3][0] = arrayOf<Int>( 3,  0,  1,  3,  0,  1,  2, 10, 10, 10) 
     rr[3][0] = arrayOf<Float>(  -0.04f,  0.03f,  0.12f,  0.04f, 0.04f, 0.03f,  -0.02f,  0.0f,  0.0f,  0.0f)

     dir_rr[3][1] = arrayOf<Int>( 3,  0,  1,  0,  3,  0,  1, 10, 10, 10) 
     rr[3][1] = arrayOf<Float>(  0.07f,  0.02f,  0.07f,  0.04f, 0.03f, 0.03f,  0.02f,  0.0f,  0.0f,  0.0f)

     dir_rr[3][2] = arrayOf<Int>( 2,  3,  0,  1,  3,  0,  1, 10, 10, 10) 
     rr[3][2] = arrayOf<Float>(  -0.03f,  -0.09f,  0.08f,  0.16f, 0.00f, 0.08f,  0.05f,  0.0f,  0.0f,  0.0f)






      
// Filling sample array 
// 4
      jj = 4                  
  
      dir_rr[4][0] = arrayOf<Int>( 1,  0,  3,  1,  10,  10,  10, 10, 10, 10) 
      rr[4][0] = arrayOf<Float>(  0.27f,  0.03f,  -0.35f,  0.02f, 0.04f, 0.03f,  -0.02f,  0.0f,  0.0f,  0.0f)

      dir_rr[4][1] = arrayOf<Int>( 1,  0,  3,  2,  1,  10,  10, 10, 10, 10) 
      rr[4][1] = arrayOf<Float>(  0.07f,  0.05f,  0.06f,  -0.32f,  0.03f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[4][2] = arrayOf<Int>( 1,  0,  3,  0,  2,  1,  0, 10, 10, 10) 
      rr[4][2] = arrayOf<Float>(  0.03f,  0.03f,  0.07f,  0.02f, -0.38f,  0.04f,  0.05f,  0.0f,  0.0f,  0.0f)

      dir_rr[4][3] = arrayOf<Int>( 3,  1,  0,  2,  1,  10,  10, 10, 10, 10) 
      rr[4][3] = arrayOf<Float>(  -0.04f,  0.07f,  0.03f,  -0.35f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[4][4] = arrayOf<Int>(  1,  0,  3,  2,  1, 10, 10, 10, 10, 10)   
      rr[4][4] = arrayOf<Float>(  0.08f,  0.03f,  0.08f,  -0.47f,  0.13f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][5] = arrayOf<Int>(  3,  1,  0,  3,  0,  2,  1, 10, 10, 10)   
      rr[4][5] = arrayOf<Float>(  -0.05f,  0.09f,  0.06f,   0.08f,  0.04f,  -0.38f, 0.07f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][6] = arrayOf<Int>(  1,  0,  3,  2,  1,  0,  10, 10, 10, 10)   
      rr[4][6] = arrayOf<Float>(  0.14f,  0.07f,  0.01f,   -0.36f,  0.02f,  0.04f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][7] = arrayOf<Int>(  0,  3,  2,  1,  0,  10,  10, 10, 10, 10)   
      rr[4][7] = arrayOf<Float>(  0.16f,  0.03f,  -0.37f,   0.04f,  0.02f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][8] = arrayOf<Int>(  1, 0,  3,  2,  0,  1, 10, 10, 10, 10)   
      rr[4][8] = arrayOf<Float>(  0.09f,  0.03f,  0.05f,   -0.32f,  0.07f,  0.04f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][9] = arrayOf<Int>(  1, 0,  2,  10,  10,  10, 10, 10, 10, 10)   
      rr[4][9] = arrayOf<Float>(  0.18f,  0.06f,  -0.33f,   0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[4][10] = arrayOf<Int>(  1, 0,  2,   1,  10,  10, 10, 10, 10, 10)   
      rr[4][11] = arrayOf<Float>(  0.08f,  0.07f,  -0.5f,   0.03f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      


      
// Filling sample array 
// 5
      jj = 5                  
  
      dir_rr[5][0] = arrayOf<Int>( 1,  3,  0,  1,  2,  3,  0,  3, 10, 10) 
      rr[5][0] = arrayOf<Float>(  0.13f,  -0.02f,  0.03f,  0.02f,  -0.04f,  -2.39f,  0.06f,  0.04f,  0.0f,  0.0f)

      dir_rr[5][1] = arrayOf<Int>( 1,  0,  3,  0,  1,  2,  3,  0, 10, 10) 
      rr[5][1] = arrayOf<Float>(  0.11f,  0.03f,  0.02f,  0.06f,  0.06f,  -0.04f,  -2.71f,  0.02f,  0.0f,  0.0f)

      dir_rr[5][2] = arrayOf<Int>( 1,  3,  0,  1,  2,  3,  2,  3,  0, 10) 
      rr[5][2] = arrayOf<Float>(  0.09f,  0.01f,  0.04f,  0.02f,  -0.06f,  -2.16f,  -0.02f,  0.01f,  0.04f,  0.0f)
      
      dir_rr[5][3] = arrayOf<Int>( 1,  3,  0,  1,  2,  3,  10, 10, 10, 10) 
      rr[5][3] = arrayOf<Float>(  0.14f,  -0.01f,  0.03f,  0.01f,  -0.05f, -1.73f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[5][4] = arrayOf<Int>( 1,  3,  0,  1,  2,  3,  0, 10, 10, 10) 
      rr[5][4] = arrayOf<Float>(  0.03f,  -0.04f,  0.06f,  0.05f,  -0.08f, -0.01f,  0.02f,  0.0f,  0.0f,  0.0f)

      dir_rr[5][5] = arrayOf<Int>( 1,  3,  0,  1,  3,  0,  3, 10, 10, 10) 
      rr[5][5] = arrayOf<Float>(  0.07f,  0.00f,  0.04f,  0.06f,  -3.59f,  0.06f,  0.01f,  0.0f,  0.0f,  0.0f)

      dir_rr[5][6] = arrayOf<Int>( 1,  3,  0,  1,  3,  0,  10, 10, 10, 10) 
      rr[5][6] = arrayOf<Float>(  0.13f,  -0.01f,  0.05f,  0.05f,  -2.03f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[5][7] = arrayOf<Int>( 1,  2,  3,  0,  1,  2,  3, 10, 10, 10) 
      rr[5][7] = arrayOf<Float>(  0.22f,  -0.04f,  -0.05f,  0.03f,  0.04f,  -0.07f,  -5.69f,  0.0f,  0.0f,  0.0f)
      

      

// Filling sample array 
// 6
      jj = 6                  
  
      dir_rr[6][0] = arrayOf<Int>( 1,  0,  3,  2,  1,  10,  10,  10, 10, 10) 
      rr[6][0] = arrayOf<Float>(  0.0f,  0.08f,  0.03f,  -0.05f,  -0.02f,  0f,  0f,  0f,  0.0f,  0.0f)

      dir_rr[6][1] = arrayOf<Int>( 2,  1,  0,  3,  2,  1,  10,  10, 10, 10) 
      rr[6][1] = arrayOf<Float>(  -0.04f,  -0.04f,  0.08f,  0.01f,  -0.04f,  -0.03f,  0f,  0f,  0.0f,  0.0f)

      dir_rr[6][2] = arrayOf<Int>( 1,  0,  3,  2,  1,  10,  10, 10, 10, 10) 
      rr[6][2] = arrayOf<Float>(  -0.04f,  0.05f,  0.03f,  -0.04f,  -0.04f, 0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[6][3] = arrayOf<Int>( 2,  1,  0,  3,  2,  1,  10, 10, 10, 10) 
      rr[6][3] = arrayOf<Float>(  -0.05f,  -0.02f,  0.06f,  0.02f,  -0.03f, -0.01f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[6][4] = arrayOf<Int>( 1,  0,  3,  2,  1,  10,  10,  10, 10, 10) 
      rr[6][4] = arrayOf<Float>(  0.04f,  0.09f,  0.02f,  -0.04f,  -0.06f,  0f,  0f,  0f,  0.0f,  0.0f)

      dir_rr[6][5] = arrayOf<Int>( 2,  1,  0,  3,  2,  1,  10, 10, 10, 10) 
      rr[6][5] = arrayOf<Float>(  -0.03f,  -0.02f,  0.09f,  0.03f,  -0.03f, -0.03f,  0.0f,  0.0f,  0.0f,  0.0f)



      
// Filling sample array 
// 7
      jj = 7                  
  
      dir_rr[7][0] = arrayOf<Int>( 3,  0,  3,  1,  2,  3,  0,  10, 10, 10) 
      rr[7][0] = arrayOf<Float>(  -0.07f,  0.09f,  0.01f,  0.06f,  -0.46f,  0.04f,  0.02f,  0f,  0.0f,  0.0f)

      dir_rr[7][1] = arrayOf<Int>( 3,  0,  3,  1,  10,  10,  10,  10, 10, 10) 
      rr[7][1] = arrayOf<Float>(  -0.11f,  0.04f,  0.01f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[7][2] = arrayOf<Int>( 2,  3,  0,  3,  1,  2,  3,  0, 10, 10) 
      rr[7][2] = arrayOf<Float>(  -0.06f,  -0.1f,  0.06f,  0.02f,  0.1f,  -0.44f,  0.01f,  0.04f,  0.0f,  0.0f)

      dir_rr[7][3] = arrayOf<Int>( 2,  3,  0,  3,  1,  10,  10,  10, 10, 10) 
      rr[7][3] = arrayOf<Float>(  -0.09f,  -0.14f,  0.06f,  0.02f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[7][4] = arrayOf<Int>( 3,  0,  1,  10,  10,  10,  10,  10, 10, 10) 
      rr[7][4] = arrayOf<Float>(  0.06f,  0.09f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[7][5] = arrayOf<Int>( 3,  0,  3,  2,  1,  2,  0,  10, 10, 10) 
      rr[7][5] = arrayOf<Float>(  -0.08f,  0.03f,  0.01f,  -0.03f,  0.02f,  -0.58f,  0.03f,  0.0f,  0.0f,  0.0f)








      

    
// Filling sample array 
// 8
      jj = 8                 
  
      dir_rr[8][0] = arrayOf<Int>( 2,  3,  0,  1,  0,  3,  2,  10, 10, 10) 
      rr[8][0] = arrayOf<Float>(  -0.05f,  -0.04f,  0.04f,  0.04f,  0.03f,  0.01f,  -0.04f,  0.0f,  0.0f,  0.0f)

      dir_rr[8][1] = arrayOf<Int>( 3,  2,  1,  0,  1,  2,  3,  1, 10, 10) 
      rr[8][1] = arrayOf<Float>(  0.03f,  -0.05f,  -0.02f,  0.09f,  0.03f,  -0.04f,  -0.02f,  0.0f,  0.0f,  0.0f)

      dir_rr[8][2] = arrayOf<Int>( 1,  2,  3,  0,  1,  0,  3,  2, 10, 10) 
      rr[8][2] = arrayOf<Float>(  -0.03f,  -0.07f,  -0.08f,  0.07f,  0.18f,  0.06f,  0.03f,  -0.05f,  0.0f,  0.0f)

      dir_rr[8][3] = arrayOf<Int>( 1,  2,  3,  0,  1,  0,  3,  2, 10, 10) 
      rr[8][3] = arrayOf<Float>(  -0.04f,  -0.05f,  -0.07f,  0.06f,  0.17f,  0.04f,  0.02f,  -0.03f,  0.0f,  0.0f)

      dir_rr[8][4] = arrayOf<Int>( 3,  0,  1,  0,  3,  2,  0,  10, 10, 10) 
      rr[8][4] = arrayOf<Float>(  -0.08f,  0.04f,  0.15f,  0.03f,  0.02f,  -0.04f,  0.02f,  0.0f,  0.0f,  0.0f)

      

      

// Filling sample array 
// 9
      jj = 9                 
  
      dir_rr[9][0] = arrayOf<Int>( 3,  2,  1,  0,  3,  10,  10,  10, 10, 10) 
      rr[9][0] = arrayOf<Float>(  0.03f,  -0.09f,  -0.02f,  0.06f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[9][1] = arrayOf<Int>( 0,  3,  2,  1,  0,  3,  10,  10, 10, 10) 
      rr[9][1] = arrayOf<Float>(  0.05f,  0.04f,  -0.11f,  -0.03f,  0.08f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[9][2] = arrayOf<Int>( 1,  2,  3,  0,  1,  2,  10,  10, 10, 10) 
      rr[9][2] = arrayOf<Float>(  0.04f,  -0.06f,  -0.1f,  0.03f,  0.09f,  -0.01f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[9][3] = arrayOf<Int>( 3,  2,  1,  0,  3,  10,  10,  10, 10, 10) 
      rr[9][3] = arrayOf<Float>(  0.06f,  -0.23f,  -0.01f,  0.1f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[9][4] = arrayOf<Int>( 3,  2,  1,  0,  10,  10,  10,  10, 10, 10) 
      rr[9][4] = arrayOf<Float>(  0.04f,  -0.18f,  -0.03f,  0.08f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      dir_rr[9][5] = arrayOf<Int>( 3,  2,  1,  0,  3,  10,  10,  10, 10, 10) 
      rr[9][5] = arrayOf<Float>(  0.06f,  -0.17f,  0.03f,  0.1f,  0.01f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[9][6] = arrayOf<Int>( 0,  3,  2,  1,  0,  10,  10,  10, 10, 10) 
      rr[9][6] = arrayOf<Float>(  0.06f,  0.02f,  -0.08f,  -0.04f,  0.11f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

// Filling sample array 
// +
      jj = 10                
  
      dir_rr[10][0] = arrayOf<Int>( 3,  0,  2,  0,  1,  10,  10,  10, 10, 10) 
      rr[10][0] = arrayOf<Float>(  0.05f,  0.03f,  -0.96f,  0.07f,  0.08f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][1] = arrayOf<Int>( 3,  2,  1,  10,  10,  10,  10,  10, 10, 10) 
      rr[10][1] = arrayOf<Float>(  0.02f,  -0.79f,  0.09f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][2] = arrayOf<Int>( 0,  2,  1,  10,  10,  10,  10,  10, 10, 10) 
      rr[10][2] = arrayOf<Float>(  0.04f,  -1.11f,  0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][3] = arrayOf<Int>(  0,  3,  2,  1,  10, 10, 10, 10, 10, 10)
      rr[10][3] = arrayOf<Float>(   0.02f,  0.00f,  -0.43f,  0.09f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][4] = arrayOf<Int>(  3,  0,  2,  1,  10, 10, 10, 10, 10, 10)
      rr[10][4] = arrayOf<Float>(   0.06f,  0.06f,  -0.71f,  0.03f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)      

      dir_rr[10][5] = arrayOf<Int>(  3,  2,  0,  0,  10, 10, 10, 10, 10, 10)
      rr[10][5] = arrayOf<Float>(   0.05f,  -0.65f,  0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][6] = arrayOf<Int>(  0,  2,  0,  1,  10, 10, 10, 10, 10, 10)
      rr[10][6] = arrayOf<Float>(   0.06f,  -0.71f,  0.08f,  0.06f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 
    
      dir_rr[10][7] = arrayOf<Int>(  2,  0,  1,  0,  1, 10, 10, 10, 10, 10)
      rr[10][7] = arrayOf<Float>(   -0.62f,  0.08f,  0.06f,  0.07f,  0.03f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
      dir_rr[10][8] = arrayOf<Int>(  0,  3,  0,  2,  1,  0, 10, 10, 10, 10)
      rr[10][8] = arrayOf<Float>(   0.18f,  0.14f,  0.05f,  -0.86f,  0.06f, 0.04f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[10][9] = arrayOf<Int>(  0,  3,  2,  1,  10, 10, 10, 10, 10, 10)
      rr[10][9] = arrayOf<Float>(   0.04f,  0.04f,  -0.29f,  0.02f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][10] = arrayOf<Int>(  2,  0,  10,  10,  10, 10, 10, 10, 10, 10)
      rr[10][10] = arrayOf<Float>(   -0.66f,  0.07f,  0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][11] = arrayOf<Int>(  3,  0,  2,  10,  10, 10, 10, 10, 10, 10)
      rr[10][11] = arrayOf<Float>(   0.03f,  0.04f,  -1.25f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][12] = arrayOf<Int>(  1,  2,  0,  10,  10, 10, 10, 10, 10, 10)
      rr[10][12] = arrayOf<Float>(   0.05f,  -0.44f,  0.03f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][13] = arrayOf<Int>(  2,  1,  10,  10,  10, 10, 10, 10, 10, 10)
      rr[10][13] = arrayOf<Float>(   -0.57f,  0.05f,  0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[10][14] = arrayOf<Int>(  3,  0,   3,  2,  10, 10, 10, 10, 10, 10)
      rr[10][14] = arrayOf<Float>(   0.01f,  0.04f,  0.01f,  -1.27f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[10][15] = arrayOf<Int>( 0,  2,  1,  0,  10,  10,  10,  10, 10, 10) 
      rr[10][15] = arrayOf<Float>(  0.09f,  -0.54f,  0.03f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][16] = arrayOf<Int>( 3,  0,  2,  0,  10,  10,  10,  10, 10, 10) 
      rr[10][16] = arrayOf<Float>(  0.1f,  0.25f,  -0.75f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][17] = arrayOf<Int>( 0,  3,  2,  10,  10,  10,  10,  10, 10, 10) 
      rr[10][17] = arrayOf<Float>(  0.09f,  0.03f,  -0.6f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][18] = arrayOf<Int>(  3,  2,  0,  10,  10, 10, 10, 10, 10, 10)
      rr[10][18] = arrayOf<Float>(   0.04f,  -0.96f,  0.07f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f) 

      dir_rr[10][19] = arrayOf<Int>( 0,  3,  2,   1,  0,  10,  10,  10, 10, 10) 
      rr[10][19] = arrayOf<Float>(  0.06f,  0.03f,  -0.71f,  0.03f,  0.1f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[10][20] = arrayOf<Int>(  0,  3,  2,  1,  10, 10, 10, 10, 10, 10)
      rr[10][20] = arrayOf<Float>(   0.08f,  0.02f,  -1.42f,  0.16f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)  

      dir_rr[10][21] = arrayOf<Int>(  3,  2,  10,  10,  10, 10, 10, 10, 10, 10)
      rr[10][21] = arrayOf<Float>(   0.01f,  -0.97f,  0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)  

      dir_rr[10][22] = arrayOf<Int>(  0,  3,  2,  1,  10, 10, 10, 10, 10, 10)
      rr[10][22] = arrayOf<Float>(   0.07f,  0.02f,  -1.38f,  0.02f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      dir_rr[10][23] = arrayOf<Int>(  0,  1,  10,  10,  10, 10, 10, 10, 10, 10)
      rr[10][23] = arrayOf<Float>(   0.08f,  -0.55f,  0.0f,  0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     
     



      
// Filling sample array 
// -
      jj = 11               
  

        dir_rr[11][0] = arrayOf<Int>( 3,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
        rr[11][0] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

        dir_rr[11][1] = arrayOf<Int>(  0, 10, 10, 10, 10, 10, 10, 10, 10, 10)
        rr[11][1] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

        dir_rr[11][2] = arrayOf<Int>(  1, 10, 10, 10, 10, 10, 10, 10, 10, 10)
        rr[11][2] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

        dir_rr[11][3] = arrayOf<Int>(  1, 10, 10, 10, 10, 10, 10, 10, 10, 10)
        rr[11][3] = arrayOf<Float>(  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 

        dir_rr[11][4] = arrayOf<Int>( 3,  0,  10,  10,  10,  10,  10,  10, 10, 10) 
        rr[11][4] = arrayOf<Float>(  0.08f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

        dir_rr[11][5] = arrayOf<Int>(  0,  3, 10, 10, 10, 10, 10, 10, 10, 10)
        rr[11][5] = arrayOf<Float>(  0.07f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f) 


      
// Filling sample array 
// x
      jj = 12               
  
      dir_rr[12][0] = arrayOf<Int>( 3,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rr[12][0] = arrayOf<Float>(  0.02f,  -0.25f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[12][1] = arrayOf<Int>( 1,  3,  0,  10,  10,  10,  10,  10, 10, 10) 
      rr[12][1] = arrayOf<Float>(  0.02f,  -1.31f,  0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[12][2] = arrayOf<Int>( 0,  2,  1,  10,  10,  10,  10,  10, 10, 10) 
      rr[12][2] = arrayOf<Float>(  0.05f,  -1.1f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[12][3] = arrayOf<Int>( 0,  3,  1,  10,  10,  10,  10,  10, 10, 10) 
      rr[12][3] = arrayOf<Float>(  0.02f,  -1.08f,  0.00f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)






      
// Filling sample array 
// /
      jj = 13              
  
       dir_rr[13][0] = arrayOf<Int>( 1,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
        rr[13][0] = arrayOf<Float>(  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

       dir_rr[13][1] = arrayOf<Int>( 3,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
       rr[13][1] = arrayOf<Float>(  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

       dir_rr[13][2] = arrayOf<Int>( 1,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
       rr[13][2] = arrayOf<Float>(  -0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

       dir_rr[13][3] = arrayOf<Int>( 3,  10,  10,  10,  10,  10,  10,  10, 10, 10) 
       rr[13][3] = arrayOf<Float>(  -0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)




      

// Filling sample array 
// =
      jj = 14            
  
      dir_rr[14][0] = arrayOf<Int>( 1,  3,  10,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][0] = arrayOf<Float>(  -1.18f,  0.08f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][1] = arrayOf<Int>( 0,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][1] = arrayOf<Float>(  0.06f,  -0.89f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][2] = arrayOf<Int>( 0,  3,  1,  0,  3,  0,  10,  10, 10, 10) 
      rr[14][2] = arrayOf<Float>(  0.06f,  0.04f,  -0.6f,  0.03f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][3] = arrayOf<Int>( 3,  1,  3,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][3] = arrayOf<Float>(  0.02f,  -0.23f,  0.04f,  0.03f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][4] = arrayOf<Int>( 3,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][4] = arrayOf<Float>(  0.02f,  -0.21f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][5] = arrayOf<Int>( 0,  1,  3,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][5] = arrayOf<Float>(  0.05f,  -0.16f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][6] = arrayOf<Int>( 3,  0,  1,  0,  10,  10,  10,  10, 10, 10) 
      rr[14][6] = arrayOf<Float>(  0.03f,  0.03f,  -0.15f,  0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][7] = arrayOf<Int>( 0,  1,  3,  0,  10,  10,  10,  10, 10, 10) 
      rr[14][7] = arrayOf<Float>(  0.06f,  -0.34f,  0.03f,  0.07f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][8] = arrayOf<Int>( 3,  1,  10,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][8] = arrayOf<Float>(  0.06f,  -0.35f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][9] = arrayOf<Int>( 0,  1,  0,  3,  10,  10,  10,  10, 10, 10) 
      rr[14][9] = arrayOf<Float>(  0.03f,  0.03f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][10] = arrayOf<Int>( 0,  3,  1,  0,  10,  10,  10,  10, 10, 10) 
      rr[14][10] = arrayOf<Float>(  0.16f,  0.02f,  -1.03f,  0.07f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][11] = arrayOf<Int>( 0,  3,  0,  1,  3,   0,  10,  10, 10, 10) 
      rr[14][11] = arrayOf<Float>(  0.02f,  0.03f,  0.04f,  0.34f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][12] = arrayOf<Int>( 3,  0,  3,  1,  0,   10,  10,  10, 10, 10) 
      rr[14][12] = arrayOf<Float>(  0.02f,  0.06f,  0.01f,  -0.34f,  0.06f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][13] = arrayOf<Int>( 1,  0,  10,  10,  10,   10,  10,  10, 10, 10) 
      rr[14][13] = arrayOf<Float>(  -0.23f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
    
      dir_rr[14][14] = arrayOf<Int>( 3,  0,  1,  3,  10,  10,  10,  10, 10, 10) 
      rr[14][14] = arrayOf<Float>(  0.04f,  0.04f,  -0.21f,  0.04f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][15] = arrayOf<Int>( 3,  1,  10,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][15] = arrayOf<Float>(  0.04f,  -0.21f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][16] = arrayOf<Int>( 3,  1,  0,  10,  10,  10,  10,  10, 10, 10) 
      rr[14][16] = arrayOf<Float>(  0.04f,  -0.53f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][17] = arrayOf<Int>( 3,  0,  1,  0,  3,  0,  10,  10, 10, 10) 
      rr[14][17] = arrayOf<Float>(  0.06f,  0.07f,  -0.03f,  0.04f,  0.04f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][18] = arrayOf<Int>( 3,  1,  0,  3,  10,  10,  10,  10, 10, 10) 
      rr[14][18] = arrayOf<Float>(  0.04f,  -0.27f,  0.04f,  0.02f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][19] = arrayOf<Int>( 0,  3,  0,  1,  0,  3,   0,  10, 10, 10) 
      rr[14][19] = arrayOf<Float>(  0.08f,  0.04f,  0.02f,  -0.02f,  0.04f,  0.04f,  0.05f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][20] = arrayOf<Int>( 3,  0,  1,  0,  10,  10,   10,  10, 10, 10) 
      rr[14][20] = arrayOf<Float>(  0.08f,  0.05f,  -0.5f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][21] = arrayOf<Int>( 3,  0,  1,  0,  10,  10,   10,  10, 10, 10) 
      rr[14][21] = arrayOf<Float>(  0.08f,  0.1f,  -0.53f,  0.09f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][22] = arrayOf<Int>( 3,  1,  3,  0,  10,  10,   10,  10, 10, 10) 
      rr[14][22] = arrayOf<Float>(  0.03f,  -0.08f,  0.02f,  0.05f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)

      dir_rr[14][23] = arrayOf<Int>( 3,  0,  1,  0,  10,  10,  10,  10, 10, 10) 
      rr[14][23] = arrayOf<Float>(  0.05f,  0.06f,  -0.46f,  0.03f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f,  0.0f)
      
      
    
      
      
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
                           else { old=dir_cr[j]; dir_crn[i]=dir_cr[j]; crn_[i]=crx_[j] + cry_[j]; 
                                  crn_[i]=( ( crn_[i] * 1000.0).roundToInt() / 1000.0).toFloat() 
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
                   
   
 
            

resmin[jj] [j] = Math.abs ( crn_[j]  - rr[ii] [iii] [j] )
                               j=j+1
                           }
                                             
           jj = jj + 1
                        
                     }

                    
                 j=0
                minres = 10.00f  
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
     if ( resnum[l]<=9) { aresnum =  aresnum + " " + resnum[l].toString() } 
     if ( resnum[l]==10) { aresnum =  aresnum + " + " } 
     if ( resnum[l]==11) { aresnum =  aresnum + " - " } 
     if ( resnum[l]==12) { aresnum =  aresnum + " x " } 
     if ( resnum[l]==13) { aresnum =  aresnum + " / " } 
     if ( resnum[l]==14) { aresnum =  aresnum + " = " } 
     
      if(resnum[l]<=9) {  sresnum[l] = resnum[l] 
                           j=0
                          while ( (l-j) > 0 && sresnum[l-j] != 100)        //   dec * 100 + *10+ ..
                           {                            
                            sresnum[l] = sresnum[l-j]*dec10 + sresnum[l] ; dec10= dec10*10; j=j+1; 
                          }   
                           valuedec = sresnum[l]
                           j=l-j-1 
                           while ( j <= l-1)        //   
                           {                            
                            sresnum[j] = valuedec; j=j+1; 
                          }   


                    
                    dec10=10
         
                        }
                                                               
                else { 
                        sresnum[l]=100   
                    
                     }
                          
                    
                
      if(l>=2 && resnum[l-1]==10) { iresnum=(sresnum[l-2] + sresnum[l]).toFloat() } 
      if(l>=2 && resnum[l-1]==11) { iresnum=(sresnum[l-2] - sresnum[l]).toFloat() } 
      if(l>=2 && resnum[l-1]==12) { iresnum=(sresnum[l-2] * sresnum[l]).toFloat() } 
      if(l>=2 && resnum[l-1]==13) { iresnum=(sresnum[l-2] / sresnum[l]).toFloat() } 
     
      if(l>=1 && resnum[l]==14) { aresnum =  aresnum  + iresnum.toString() }
             
      l=l+1
     
                  
//
textviewid.text =     "  " + aresnum +  "\n" +
                     "res=[" + res0 + "][" + res1 +  "]\n"   +  
                    " dir_resmin=" + min + ",    [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  "/[" + dir_resmin[3][0] + "]" + "[" + dir_resmin[3][1] + "]"   
                //    "\n" + "sresnum[l-2]=" + sresnum[l-3] +  "  sresnum[l]=" + sresnum[l-1]
                    
                //    "\n" +  aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9]  +
                //     "\n" +  "iresmin=" + iresmin[0] + " " + iresmin[1] + " " + iresmin[2] + " " + iresmin[3] +
                //     "\n" +  "aresmin=" + aresmin +
                //     "\n" +  "minres=" + minres 
                    
                    





                    
                    //aaacr[0] + " " +aaacr[40] + " " + aaacr[80] + " " + aaacr[120] + " " + aaacr[160] + " " + aaacr[200] + " " + aaacr[240] + " " + aaacr[280] + " " + aaacr[320] + " " + aaacr[360]  +
                      //  " " + aaacr[400] + " " + aaacr[440] + " " + aaacr[460] + " " + aaacr[500] + " " + aaacr[540] + " " + aaacr[580] + " " + aaacr[640] + " " + aaacr[680] + " " + aaacr[720] + " " + aaacr[760]      

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
