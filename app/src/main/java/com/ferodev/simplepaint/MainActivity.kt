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
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.xcrx_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.ycry_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.signx_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.signy_


import com.ferodev.simplepaint.canvas.DrawPencil.Companion.crxdy_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.cangle_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.canglepi_
import com.ferodev.simplepaint.canvas.DrawPencil.Companion.ci




import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import android.util.DisplayMetrics



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
    private var kk=0
    private var old=0
   
    private var aaa = Array<String>(800){"0"} // text Array for result monitoring
    private var aaacr = Array<String>(800){"0"} // text Array for result monitoring
  
  //  private var dir_cr =  Array<Int>(800){10}  // array of directions current drawpencil (0,1,2,3)
  //  private var dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
  //  private var dir_rr = Array(20) { Array(60){ Array<Int>(20){10} } }  // array of directions for n_etalons(40) for every sample(10pcs: 0,1,...9,+..=)
  //  private var dir_res =  Array(20){ Array<Int>(60){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
    // crx_ , cry_ - import from drawpencil.kt as companion object
    private var oldx = 0.00f 
    private var oldy = 0.00f 
    private var oldxdy = 0.00f
    private var crn_ = Array<Float>(100){0.00f}  // array of of crx_[j] + cry_[j] after n_normalization
    private var crnx_ = Array<Float>(100){0.00f}  // array of of crx_[j]  after n_normalization
    private var crny_ = Array<Float>(100){0.00f}  // array of of cry_[j] after n_normalization

    private var crnnx_ = Array<Float>(100){0.00f}
    private var crnny_ = Array<Float>(100){0.00f}
    
    
    private var crnxdy_ = Array<Float>(100){0.00f}  // array of of crxdy_[j] after n_normalizatio
    
    private var canglen_ = Array<Float>(100){0.00f} 
    private var canglenpi_ = Array<Float>(100){0.00f} 
    private var canglenn_ = Array<Float>(100){360.0f} 
    private var canglennpi_ = Array<Float>(100){360.0f} 
    private var rcanglenn_ = Array(20) { Array(60){ Array<Float>(20){360.0f} } }  
    
    
    private var cin = 0
    private var cinn = 0
    
    
    private var rr =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for n_etalons(30) for every sample(10pcs)c
    private var rrx =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  //  array of value for x n_etalons(30) for every sample(10pcs)
    private var rry =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  //  array of value for y n_etalons(30) for every sample(10pcs)
    private var rangle =  Array(20) { Array(60){ Array<Float>(10){0.0f} } }  // 
    
    private var xdyrr =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  // 

   
  
    private var  resmin =  Array(20){ Array<Float>(60){0.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminx =  Array(20){ Array<Float>(60){0.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminy =  Array(20){ Array<Float>(60){0.00f} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    
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
    //Array<Float>(800){0.0f}
    private var ffr=0
    private var xsign=1
    private var xxsign=1
    private var dot=0
    private var repeat=0
    
    private var jdec = Array<Int>(60){0}
    private var resxy=0.0f
    
       
         companion object {
        var path = Path()
        var paintBrush = Paint()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
        var shift = 0   
        var w=0
        var h=0     
                          }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)

        supportActionBar?.hide()


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
 
        w = displayMetrics.widthPixels
        h = displayMetrics.heightPixels

   

        
        binding.apply {



 
// Filling sample array
// 0
     jj = 0                  
    rcanglenn_[0][0]=arrayOf<Float>(   86.0f,  65.0f,  46.0f, 175.0f,  136.0f, 119.0f, 104.0f,  -87.0f,  -64.0f,  -39.0f, -19.0f, -173.0f, -144.0f, -127.0f, -107.0f,  86.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][1]=arrayOf<Float>(  -59.0f, -77.0f,  95.0f, 112.0f,  128.0f, 145.0f, 161.0f,    8.0f,   49.0f,   68.0f, -93.0f, -115.0f, -131.0f, -156.0f,   -2.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][2]=arrayOf<Float>(  -42.0f, -62.0f, -80.0f,  94.0f,  122.0f, 139.0f, 166.0f,   11.0f,   32.0f,   47.0f,  66.0f,   83.0f,  -92.0f, -120.0f, -141.0f,-172.0f, 360.0f, -14.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][3]=arrayOf<Float>(   64.0f,  45.0f,  21.0f, 173.0f,  145.0f, 129.0f, 108.0f,  -83.0f,  -67.0f,  -40.0f, -16.0f, -176.0f, -119.0f,   87.0f,   64.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][4]=arrayOf<Float>(   80.0f,  59.0f,  33.0f, 175.0f,  154.0f, 128.0f, 111.0f,  -88.0f,  -67.0f,  -50.0f, -19.0f, -175.0f, -134.0f, -115.0f,  -99.0f,  87.0f,  70.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][5]=arrayOf<Float>(   84.0f,  65.0f,  40.0f,  11.0f,  162.0f, 145.0f, 128.0f,  105.0f,  -85.0f,  -69.0f, -52.0f,  -36.0f, -166.0f, -116.0f,  -98.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][6]=arrayOf<Float>(   72.0f,  52.0f, 172.0f, 142.0f,  122.0f, 104.0f, -88.0f,  -73.0f,  -56.0f,  -32.0f,-174.0f, -135.0f, -115.0f,  -98.0f,   88.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][7]=arrayOf<Float>(   83.0f,  40.0f,  17.0f, 165.0f,  148.0f, 126.0f, 109.0f,  -84.0f,  -69.0f,  -45.0f,-170.0f, -130.0f, -107.0f,   89.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][8]=arrayOf<Float>(  176.0f, 123.0f, 101.0f, -87.0f,  -64.0f, -30.0f, -13.0f, -173.0f, -128.0f, -111.0f,  86.0f,  67.0f,    45.0f,   89.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][9]=arrayOf<Float>(   76.0f,  56.0f,  35.0f,  13.0f,  167.0f, 143.0f, 127.0f,  112.0f,   96.0f,  -87.0f, -72.0f, -50.0f,  -22.0f,  -173.0f, -156.0f,-135.0f,-106.0f,  85.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][10]=arrayOf<Float>(  77.0f,  50.0f, 171.0f, 140.0f,  121.0f, 104.0f, -82.0f,  -62.0f,  -34.0f, -174.0f,-124.0f, -101.0f,  360.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][11]=arrayOf<Float>(  85.0f,  43.0f,  27.0f, 175.0f,  154.0f, 122.0f, -88.0f,  -69.0f,  -40.0f, -172.0f,-147.0f, -111.0f,   84.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][12]=arrayOf<Float>( -63.0f,  92.0f, 110.0f, 135.0f,  155.0f,   5.0f,  35.0f,   65.0f,   83.0f,  -93.0f,-114.0f, -139.0f, -167.0f,   -7.0f,  -38.0f, -58.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][13]=arrayOf<Float>( -65.0f, -80.0f,  95.0f, 119.0f,  165.0f,   6.0f,  43.0f,   61.0f,   79.0f,  -98.0f,-127.0f, -146.0f, -166.0f,   -9.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][14]=arrayOf<Float>( -55.0f, -70.0f,  94.0f, 114.0f,  136.0f, 153.0f,  11.0f,   35.0f,   55.0f,   72.0f, -96.0f, -112.0f, -129.0f, -153.0f,  -14.0f, -38.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
    rcanglenn_[0][15]=arrayOf<Float>(  53.0f,  31.0f,  15.0f, 176.0f,  150.0f, 125.0f, 100.0f,  -80.0f,  -60.0f,  -34.0f, -18.0f, -171.0f, -135.0f, -118.0f, -102.0f,  88.0f,  64.0f,  45.0f,  25.0f, 360.0f  ) 

    

  
 // Filling sample array
// 1
      jj = 1                  

   rcanglenn_[1][0]=arrayOf<Float>(   61.0f,  -78.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][1]=arrayOf<Float>(   41.0f,   61.0f,  -84.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][2]=arrayOf<Float>(   36.0f,   51.0f,   71.0f,  18.0f, 176.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][3]=arrayOf<Float>(   41.0f,   60.0f,   76.0f, 109.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][4]=arrayOf<Float>(   60.0f,   76.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][5]=arrayOf<Float>(   57.0f,   73.0f,   92.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][6]=arrayOf<Float>(   55.0f,  -71.0f,  -86.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[1][7]=arrayOf<Float>(   60.0f,  -79.0f,   92.0f, -86.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   
   
     
// Filling sample array 
// 2
      jj = 2                  

 rcanglenn_[2][0]=arrayOf<Float>(   81.0f,   41.0f,  167.0f, 128.0f, 111.0f, -86.0f, -68.0f,  -47.0f, -23.0f, -170.0f ,  35.0f, 173.0f, 147.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][1]=arrayOf<Float>(   73.0f,   30.0f,   15.0f, 170.0f, 127.0f, 102.0f, -84.0f,  -69.0f, -51.0f,  -26.0f ,-140.0f,  74.0f, 175.0f, 155.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][2]=arrayOf<Float>(   78.0f,   60.0f,   25.0f, 171.0f, 120.0f,  98.0f, -84.0f,  -68.0f, -47.0f,  -29.0f ,-165.0f, 150.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][3]=arrayOf<Float>(   31.0f,   15.0f,  173.0f, 114.0f, -87.0f, -68.0f, -51.0f,  -34.0f, -11.0f, -117.0f ,  15.0f, 175.0f, 146.0f, 167.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][4]=arrayOf<Float>(   65.0f,   42.0f,  173.0f, 126.0f, -85.0f, -66.0f, -51.0f,  -26.0f, 176.0f,  160.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][5]=arrayOf<Float>(   74.0f,   55.0f,   29.0f, 169.0f, 124.0f, 107.0f, -85.0f,  -70.0f, -52.0f,  -37.0f ,-155.0f,  72.0f, 171.0f, 144.0f, 162.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][6]=arrayOf<Float>(   51.0f,   28.0f,  175.0f, 116.0f, -85.0f, -68.0f, -52.0f,  -37.0f, -18.0f,  173.0f , 150.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][7]=arrayOf<Float>(   58.0f,   43.0f,  172.0f, 105.0f, -87.0f, -69.0f, -48.0f,  -31.0f,-171.0f,   20.0f , 171.0f, 151.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][8]=arrayOf<Float>(   64.0f,   44.0f,   29.0f, 167.0f, 132.0f, 116.0f, -84.0f,  -64.0f, -47.0f,  -26.0f , -11.0f,-164.0f,  29.0f, 171.0f, 150.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[2][9]=arrayOf<Float>(   56.0f,   39.0f,  174.0f, 124.0f, 108.0f, -83.0f, -66.0f,  -49.0f, -33.0f, -140.0f ,  78.0f,  49.0f, 171.0f, 147.0f, 169.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 




 
      
// Filling sample array 
// 3
      jj = 3                   
  rcanglenn_[3][0]=arrayOf<Float>(   63.0f,  32.0f, 172.0f, 113.0f,  -84.0f,  -59.0f, -43.0f, 175.0f, 145.0f, 111.0f, -85.0f, -33.0f,  -13.0f, -177.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][1]=arrayOf<Float>(   33.0f, 175.0f, 105.0f, -79.0f,  -63.0f,  -46.0f, -30.0f, 177.0f, 137.0f, 122.0f, -83.0f, -46.0f,  -30.0f,  -14.0f,-177.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][2]=arrayOf<Float>(   57.0f,  24.0f, 173.0f, 121.0f,  103.0f,  -85.0f, -66.0f, -51.0f, -24.0f,   6.0f, 164.0f, 144.0f,  103.0f,  -84.0f, -63.0f, -47.0f, -18.0f,-177.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][3]=arrayOf<Float>(   55.0f,  33.0f,  10.0f, 150.0f,  129.0f,  104.0f, -82.0f, -64.0f, -46.0f, 171.0f, 153.0f, 114.0f,  -83.0f,  -23.0f,-175.0f,-155.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][4]=arrayOf<Float>(   47.0f,  26.0f, 173.0f, 121.0f,  -84.0f,  -64.0f, -39.0f, 156.0f, 140.0f, 113.0f, -77.0f, -30.0f, -176.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][5]=arrayOf<Float>(   54.0f,  35.0f,  20.0f, 174.0f,   98.0f,  -77.0f, -60.0f, -42.0f, -25.0f, 172.0f, 128.0f, 109.0f,  -84.0f,  -38.0f, -16.0f,-174.0f,-154.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][6]=arrayOf<Float>(   39.0f,  11.0f, 170.0f, 140.0f,  106.0f,  -86.0f, -64.0f, -47.0f, -27.0f,   8.0f, 172.0f, 136.0f,  117.0f,  -80.0f, -44.0f, -19.0f,-177.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][7]=arrayOf<Float>(   45.0f,  26.0f, 169.0f, 103.0f,  -80.0f,  -56.0f, -37.0f, -21.0f, 175.0f, 154.0f, 136.0f, 100.0f,  -69.0f,  -48.0f, -32.0f,  -1.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][8]=arrayOf<Float>(    2.0f, -43.0f, 166.0f, -29.0f,  -47.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[3][9]=arrayOf<Float>(  174.0f, 147.0f, -37.0f, 160.0f,  176.0f,  -39.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 


  
      
// Filling sample array 
// 4
      jj = 4                  
  
  rcanglenn_[4][0]=arrayOf<Float>(  -74.0f,   -59.0f,   4.0f, -101.0f, -85.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][1]=arrayOf<Float>(  -72.0f,   -56.0f, 178.0f,   76.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][2]=arrayOf<Float>(  -74.0f,   150.0f,   4.0f,  -94.0f, -85.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][3]=arrayOf<Float>(  -78.0f,   -63.0f, 132.0f,  179.0f,-113.0f, -80.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][4]=arrayOf<Float>(  -74.0f,   148.0f, 178.0f,  -93.0f, -82.0f,  92.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][5]=arrayOf<Float>(  -70.0f,   169.0f,-116.0f,  -88.0f,  94.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][6]=arrayOf<Float>(  -87.0f,    94.0f,  52.0f,  -79.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[4][7]=arrayOf<Float>(  -60.0f,   -76.0f, -58.0f,    8.0f, 171.0f, 144.0f,-116.0f, -85.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 

  
      
// Filling sample array 
// 5
      jj = 5    
 rcanglenn_[5][0]=arrayOf<Float>(   -73.0f,  19.0f,   35.0f,  13.0f, 167.0f,  134.0f,  115.0f, -79.0f, -60.0f,  -42.0f, -23.0f, -172.0f, -157.0f,  76.0f,  12.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][1]=arrayOf<Float>(   -84.0f, -69.0f,   34.0f,  15.0f, 173.0f,  120.0f,  101.0f, -87.0f, -56.0f,  -22.0f,-174.0f, -146.0f,   80.0f,   6.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][2]=arrayOf<Float>(   -74.0f,  16.0f,  173.0f, 137.0f, 114.0f,  -86.0f,  -52.0f, -32.0f,  -7.0f, -163.0f,  78.0f,    8.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][3]=arrayOf<Float>(   -77.0f,  49.0f,   33.0f, 173.0f, 132.0f,  100.0f,  -81.0f, -56.0f, -36.0f,  -13.0f,-176.0f,   75.0f,    7.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][4]=arrayOf<Float>(   -73.0f,  33.0f,  172.0f, 134.0f, 109.0f,  -84.0f,  -54.0f, -24.0f,-177.0f,   77.0f,   6.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][5]=arrayOf<Float>(   -79.0f,  48.0f,   31.0f, 173.0f, 140.0f,  115.0f,  -87.0f, -57.0f, -27.0f,  -95.0f, 360.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][6]=arrayOf<Float>(   -74.0f,  32.0f,   12.0f, 173.0f, 146.0f,  107.0f,  -87.0f, -64.0f, -32.0f,   -9.0f,-167.0f,   76.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][7]=arrayOf<Float>(   -70.0f,  36.0f,  170.0f, 125.0f, 110.0f,  -83.0f,  -66.0f, -46.0f, -27.0f,   -6.0f,-167.0f,   71.0f,    7.0f, 164.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][8]=arrayOf<Float>(   -70.0f,  53.0f,   35.0f,  17.0f, 169.0f,  133.0f,  115.0f,  99.0f, -80.0f,  -64.0f, -39.0f,  -20.0f, -174.0f,-153.0f,  79.0f,   9.0f, 174.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][9]=arrayOf<Float>(   -82.0f, 116.0f,   12.0f, 148.0f, 101.0f,  -86.0f,  -17.0f,-178.0f,  75.0f,    8.0f, 360.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
 rcanglenn_[5][10]=arrayOf<Float>(  -86.0f,  37.0f,   22.0f, 174.0f, 118.0f,  -86.0f,  -51.0f, -33.0f, -14.0f, -175.0f,  84.0f,  166.0f,    5.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 


 

 
 
// Filling sample array 
// 6
      jj = 6                  
  rcanglenn_[6][0]=arrayOf<Float>(   -28.0f, -47.0f,  -63.0f, -78.0f,  95.0f,  115.0f, 132.0f,  149.0f,  171.0f,   10.0f,  47.0f,  79.0f,  -94.0f, -127.0f,  -155.0f,  -6.0f,  -40.0f, -57.0f, 360.0f, 360.0f  ) 
  rcanglenn_[6][1]=arrayOf<Float>(    -5.0f, -35.0f,  -50.0f, -69.0f, -85.0f,   95.0f, 119.0f,  153.0f,  171.0f,   12.0f,  38.0f,  59.0f,   79.0f,  -97.0f,  -121.0f,-155.0f,   -5.0f, -48.0f, -76.0f, 360.0f  ) 
  rcanglenn_[6][2]=arrayOf<Float>(    -4.0f, -20.0f,  -41.0f, -61.0f, -80.0f,   96.0f, 118.0f,  139.0f,  156.0f,    8.0f,  43.0f,  61.0f,   80.0f,  -96.0f,  -140.0f,-159.0f,  -10.0f, -30.0f, -47.0f, -66.0f  ) 
  rcanglenn_[6][3]=arrayOf<Float>(    -6.0f, -38.0f,  -55.0f, -72.0f,  94.0f,  115.0f, 132.0f,  159.0f,    8.0f,   41.0f,  57.0f, -96.0f, -139.0f, -158.0f,   -10.0f, -45.0f,  -67.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[6][4]=arrayOf<Float>(    -6.0f, -51.0f,  -67.0f, -84.0f,  94.0f,  125.0f, 149.0f,  164.0f,    6.0f,   55.0f,  73.0f, -94.0f, -126.0f, -166.0f,    -7.0f, -32.0f,  360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[6][5]=arrayOf<Float>(   -37.0f, -53.0f,  -69.0f,  98.0f, 117.0f,  157.0f,  10.0f,   33.0f,   48.0f,   69.0f, -96.0f,-133.0f, -153.0f,   -7.0f,   -24.0f, -47.0f,  360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[6][6]=arrayOf<Float>(    -4.0f, -20.0f,  -47.0f, -63.0f, -79.0f,   92.0f, 173.0f,   13.0f,   33.0f,   75.0f, -93.0f,-126.0f, -157.0f,   -5.0f,   360.0f, 360.0f,  360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[6][7]=arrayOf<Float>(    -9.0f, -53.0f,  -72.0f,  94.0f, 113.0f,  139.0f, 156.0f,   15.0f,   63.0f,   80.0f, -96.0f,-125.0f,   -5.0f,  -36.0f,   -57.0f, 360.0f,  360.0f, 360.0f, 360.0f, 360.0f  ) 

  
      
// Filling sample array 
// 7
      jj = 7                  
  
   rcanglenn_[7][0]=arrayOf<Float>(   83.0f,   60.0f,  167.0f,   148.0f,  169.0f,   7.0f,  64.0f, -79.0f, -108.0f, 178.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f ) 
   rcanglenn_[7][1]=arrayOf<Float>(   51.0f,  169.0f,  144.0f,    15.0f,   58.0f,  80.0f,-101.0f, 178.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][2]=arrayOf<Float>(   81.0f,  155.0f,  139.0f,   163.0f,   11.0f,  50.0f, -69.0f,-104.0f,  169.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][3]=arrayOf<Float>(   86.0f,  151.0f,   11.0f,   -72.0f, -107.0f, 172.0f, 360.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][4]=arrayOf<Float>(  -98.0f,  169.0f,   11.0f,   -72.0f, -104.0f, 360.0f, 360.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][5]=arrayOf<Float>(   87.0f,  162.0f,   14.0f,    58.0f,  -74.0f,-111.0f, 178.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][6]=arrayOf<Float>(   78.0f,  157.0f,  141.0f,   169.0f,   17.0f,  44.0f, -61.0f, -77.0f, -121.0f, 151.0f , 169.0f,   7.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f ) 
   rcanglenn_[7][7]=arrayOf<Float>(   84.0f,   69.0f,   27.0f,   164.0f,  127.0f, 150.0f, 171.0f,  16.0f,   41.0f, -72.0f ,-121.0f, 170.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f ) 
   rcanglenn_[7][8]=arrayOf<Float>(  178.0f,  -46.0f,  -64.0f,   -83.0f,  360.0f, 360.0f, 360.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][9]=arrayOf<Float>(   43.0f,  175.0f,   20.0f,   -74.0f, -106.0f, 360.0f, 360.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )
   rcanglenn_[7][10]=arrayOf<Float>(  75.0f,  170.0f,   16.0f,   -71.0f,  360.0f, 360.0f, 360.0f, 360.0f,  360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f )

   
    
// Filling sample array 
// 8
      jj = 8                 
  
    rcanglenn_[8][0]=arrayOf<Float>(  -139.0f,  83.0f,  44.0f,  26.0f,  176.0f,  -80.0f, -54.0f, -72.0f,   95.0f,  151.0f, 169.0f,  33.0f,  62.0f,  -99.0f,  -128.0f,  -145.0f, -130.0f, 360.0f, 360.0f, 360.0f  )    
    rcanglenn_[8][1]=arrayOf<Float>(  -151.0f,-135.0f,-112.0f,  80.0f,   41.0f,   23.0f, 176.0f, 136.0f,  -81.0f,  -60.0f, -82.0f, 103.0f, 118.0f,  148.0f,   169.0f,     7.0f,   48.0f,  80.0f, -94.0f,-119.0f  )    
    rcanglenn_[8][2]=arrayOf<Float>(  -151.0f,-130.0f,  81.0f,  39.0f,   19.0f,  171.0f, 104.0f, -85.0f,  -54.0f,  -76.0f,  98.0f, 129.0f, 159.0f,    5.0f,    43.0f,    79.0f,  -98.0f,-117.0f,-136.0f, 360.0f  )    
    rcanglenn_[8][3]=arrayOf<Float>(  -145.0f,-125.0f,  84.0f,  54.0f,   37.0f,  174.0f, -79.0f, -56.0f,  -74.0f,   96.0f, 157.0f,  11.0f,  35.0f,   62.0f,   -97.0f,  -125.0f,  360.0f, 360.0f, 360.0f, 360.0f  )    
    rcanglenn_[8][4]=arrayOf<Float>(  -149.0f,-131.0f,  74.0f,  50.0f,   30.0f,  171.0f, 119.0f,  98.0f,  -77.0f,  -51.0f, -69.0f,  95.0f, 140.0f,  162.0f,     9.0f,    57.0f,  -99.0f,-127.0f, 360.0f, 360.0f  )
    rcanglenn_[8][5]=arrayOf<Float>(  -137.0f,-121.0f,  72.0f,  23.0f,  171.0f,  -70.0f, -55.0f, -74.0f,  104.0f,  150.0f,   7.0f,  39.0f,  67.0f, -110.0f,  -130.0f,  360.0f,  360.0f, 360.0f, 360.0f, 360.0f  )    
    rcanglenn_[8][6]=arrayOf<Float>(    15.0f,  44.0f,  71.0f,-103.0f, -150.0f,   -5.0f, -44.0f, -60.0f,  101.0f,  151.0f, 168.0f, 149.0f, 132.0f,  103.0f,   -76.0f,  -44.0f,  -25.0f, -17.0f,-136.0f,-112.0f  )    
    rcanglenn_[8][7]=arrayOf<Float>(    18.0f,  36.0f,  52.0f,  67.0f,  -99.0f, -167.0f, -18.0f, -41.0f,  -56.0f,  104.0f, 128.0f, -70.0f, -41.0f,  -20.0f,  -176.0f, -108.0f,   78.0f,  59.0f,  35.0f, 360.0f  )    
    rcanglenn_[8][8]=arrayOf<Float>(    46.0f,  61.0f,-103.0f,-135.0f,   -4.0f,  -37.0f, -58.0f, 105.0f,  143.0f,  115.0f, -83.0f, -44.0f, -21.0f, -175.0f,  -114.0f,  -98.0f,   74.0f,  44.0f,  26.0f, 360.0f  )    


    

// Filling sample array 
// 9
      jj = 9                 
  
   rcanglenn_[9][0]=arrayOf<Float>(  10.0f,  27.0f,   44.0f,  61.0f,  79.0f,  -94.0f,  -117.0f,  -137.0f, -163.0f,  -12.0f,  -36.0f, -52.0f, -79.0f,  99.0f, 117.0f,  133.0f,  7.0f,  45.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][1]=arrayOf<Float>( 161.0f,  13.0f,   53.0f,  74.0f, -92.0f, -116.0f,  -165.0f,   -13.0f,  -32.0f,  -53.0f,  -83.0f,  95.0f, 132.0f, 149.0f,  11.0f,   31.0f, 47.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][2]=arrayOf<Float>( 143.0f, 161.0f,   13.0f,  52.0f,  67.0f,   82.0f,   -93.0f,  -114.0f, -147.0f,   -5.0f,  -23.0f, -43.0f, -59.0f, -74.0f,  96.0f,  121.0f,140.0f, 166.0f,   6.0f,  26.0f  ) 
   rcanglenn_[9][3]=arrayOf<Float>( 149.0f,   7.0f,   60.0f,  75.0f, -92.0f, -112.0f,  -138.0f,  -164.0f,   -8.0f,  -49.0f,  -74.0f,  96.0f, 119.0f, 158.0f,  16.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][4]=arrayOf<Float>( 175.0f,  24.0f,   40.0f,  61.0f,  79.0f,  -93.0f,  -117.0f,  -151.0f,  -26.0f,  -51.0f,  -77.0f,  93.0f, 120.0f, 164.0f,   8.0f,   40.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][5]=arrayOf<Float>( 171.0f,   8.0f,   55.0f,  73.0f, -92.0f, -146.0f,   -10.0f,   -41.0f,  -63.0f,   96.0f,  123.0f,   6.0f,  39.0f, 360.0f, 360.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][6]=arrayOf<Float>( 160.0f,   3.0f,   49.0f,  64.0f,  83.0f,  -93.0f,  -108.0f,  -129.0f, -167.0f,  -22.0f,  -40.0f, -62.0f, 100.0f, 122.0f, 168.0f,    9.0f, 32.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][7]=arrayOf<Float>( 158.0f,  13.0f,   52.0f,  68.0f,  85.0f,  -92.0f,  -109.0f,  -131.0f,   -8.0f,  -56.0f,  -75.0f,  94.0f, 151.0f,  13.0f, 360.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][8]=arrayOf<Float>(  21.0f,  42.0f,   59.0f,  78.0f, -94.0f, -116.0f,   -13.0f,   -59.0f,  -83.0f,   96.0f,  134.0f, 167.0f,   8.0f,  42.0f, 360.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][9]=arrayOf<Float>( 146.0f, 164.0f,    6.0f,  51.0f,  69.0f,  -92.0f,  -116.0f,   -12.0f,  -56.0f,  -80.0f,   97.0f, 129.0f, 162.0f,  10.0f,  41.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][10]=arrayOf<Float>(-47.0f, -30.0f, -171.0f,-135.0f,-115.0f,   75.0f,    27.0f,   173.0f,  133.0f,   97.0f,  -87.0f, -67.0f, -34.0f, -16.0f,-176.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][11]=arrayOf<Float>( -99.0f,-119.0f, -170.0f, -18.0f, -40.0f,  -56.0f,   -78.0f,    96.0f,  119.0f,  160.0f,    7.0f,  28.0f,  55.0f,  79.0f, -56.0f,  -18.0f,-173.0f, 360.0f, 360.0f, 360.0f  ) 
   rcanglenn_[9][12]=arrayOf<Float>(  59.0f,  77.0f,   60.0f, -97.0f,-114.0f, -146.0f,  -165.0f,   -42.0f,  -58.0f,   97.0f,  162.0f,   7.0f, 360.0f, 360.0f, 360.0f,  360.0f,360.0f, 360.0f, 360.0f, 360.0f  ) 


            
 // Filling sampl// Filling sample array 
// +
      jj = 10                

   
      rcanglenn_[10][0]=arrayOf<Float>(   1.0f,  -127.0f,   -83.0f,  92.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][1]=arrayOf<Float>( 177.0f,    75.0f,    92.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][2]=arrayOf<Float>( 177.0f,  -119.0f,   -86.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][3]=arrayOf<Float>( 176.0f,    72.0f,   -87.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][4]=arrayOf<Float>(   6.0f,    88.0f,   360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][5]=arrayOf<Float>( 177.0f,   -94.0f,   360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][6]=arrayOf<Float>(  87.0f,   138.0f,  -171.0f,  -7.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[10][7]=arrayOf<Float>(  13.0f,   177.0f,   158.0f,-123.0f, -74.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
      rcanglenn_[10][8]=arrayOf<Float>(   9.0f,    86.0f,    95.0f, -84.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      


      
// Filling sample array 
// -
      jj = 11               
  
        rcanglenn_[11][0]=arrayOf<Float>( 180.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )
        rcanglenn_[11][1]=arrayOf<Float>(   5.0f , 168.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )
     


      
// Filling sample array 
// x
      jj = 12               
  rcanglenn_[12][0]=arrayOf<Float>(   141.0f, 126.0f,  89.0f,  -40.0f, -55.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][1]=arrayOf<Float>(   142.0f,  86.0f, -47.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][2]=arrayOf<Float>(    43.0f, -13.0f, 126.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][3]=arrayOf<Float>(   134.0f, -91.0f, -46.0f,  -61.0f, -45.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][4]=arrayOf<Float>(   128.0f, -92.0f, -58.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][5]=arrayOf<Float>(    47.0f,  -8.0f, 140.0f,  125.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][6]=arrayOf<Float>(    68.0f,  46.0f,-176.0f,  109.0f, 129.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  ) 
  rcanglenn_[12][7]=arrayOf<Float>(   144.0f, 129.0f, -98.0f,  -41.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
  rcanglenn_[12][8]=arrayOf<Float>(    48.0f,-179.0f, 137.0f,  122.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  




      
// Filling sample array 
// /
      jj = 13              

      rcanglenn_[13][0]=arrayOf<Float>(   45.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[13][1]=arrayOf<Float>(  -45.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
      rcanglenn_[13][2]=arrayOf<Float>(   52.0f,  35.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f , 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
       

// Filling sample array 
// =
      jj = 14            

     rcanglenn_[14][0]=arrayOf<Float>(   1.0f,  -27.0f,   2.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[14][1]=arrayOf<Float>( 169.0f,    4.0f, -27.0f,   2.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[14][2]=arrayOf<Float>( 177.0f,  -41.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[14][3]=arrayOf<Float>(   3.0f,  163.0f, -21.0f,   5.0f, 157.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  


     
      
// Filling sample array 
// ,
      jj = 15            
     
     rcanglenn_[15][0]=arrayOf<Float>(   140.0f, 360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[15][1]=arrayOf<Float>(   100.0f, 360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[15][2]=arrayOf<Float>(   108.0f, -87.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[15][3]=arrayOf<Float>(   -86.0f, 360.0f,  360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
     rcanglenn_[15][4]=arrayOf<Float>(   122.0f, 106.0f,  -87.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f, 360.0f  )  
   

   
      
      btnPencil.setOnClickListener {
       
          
          
          // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked     
     
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya

                    
                  
i=0
j=0
oldx = 0.0f; oldy = 0.0f;   
oldxdy=1000.0f

     
        ci=ci-1 
        cin=ci
      if (cin==0) j=0  
         else j=1           
        kk=0    
        cinn=0     
     resxy = (((w+h)/2)*2/100).toFloat()
                    
while ( j <= cin  )  {
if    ( cin<=0 ||  cinn==0 ||  
        ( signx_[j] == signx_[kk]  &&   signy_[j] == signy_[kk]  &&   (Math.abs( (cangle_[j]) - (cangle_[kk]) ) >= 15) ) ||
        ( signx_[j] != signx_[kk]  &&   signy_[j] != signy_[kk]  &&   (Math.abs( (cangle_[j]) - (cangle_[kk]) ) >= 15) ) ||
        ( signx_[j] != signx_[kk]  &&   signy_[j] == signy_[kk]  &&   ( (180 - Math.abs( (cangle_[j]) - (cangle_[kk]) )) >= 15) ) ||
        ( signx_[j] == signx_[kk]  &&   signy_[j] != signy_[kk]  &&   ( (180 - Math.abs( (cangle_[j]) - (cangle_[kk]) )) >= 15) ) 
        
       )
                             
                                         {   
                                          
                                         if ( cin<=0 || Math.abs(xcrx_[j]-xcrx_[kk]) > resxy || Math.abs(ycry_[j]-ycry_[kk]) > resxy )
                                             {    
                                         kk=j
                                         crnnx_[cinn]= crx_[j]; crnny_[cinn]= cry_[j]; 
                                         canglenn_[cinn]=   (( cangle_[j] * signx_[j] * 1.0).roundToInt() / 1.0).toFloat() 
                                         canglennpi_[cinn]=(( canglepi_[j]   * 1.0).roundToInt() / 1.0).toFloat()   
                                             cinn=cinn+1
                                      
                                             }
                                         }
                                                                            
                                 j=j+1
                      } 

j=cin
if (cinn==0) {   crnnx_[cinn]= crx_[j]; crnny_[cinn]= cry_[j]; 
                                         canglenn_[cinn]= (( cangle_[j]  * 1.0).roundToInt() / 1.0).toFloat() 
                                         canglennpi_[cinn]=( ( canglepi_[j]  * 1.0).roundToInt() / 1.0).toFloat()     
             }

              

// output as text current painted number in direction sequence
        
       j=0;
   if (cinn > 0)  cinn=cinn - 1
while (j >=0 && j<=cinn) {
if (j<=cinn) { aaa[j] =   canglenn_[j].toString() + "/"  }
    // "  [" + j.toString() + "]=" + crnnx_[j].toString() + "," + crnny_[j].toString() + ";;" +
    // + crnnx_[j].toString() + "," + crnny_[j].toString() + ";;" 
    // + canglenn_[j].toString() + "/" + canglennpi_[j].toString() 
 // aaa[j] =  "  [" + j.toString() + "]="  +  canglenn_[j].toString() 
//  aaacr[j] =  "[" + j.toString() + "]=" + "[" + crx_[j].toString() + "," + cry_[j].toString() + "] "  

    //  aaacr[j] =  "[" + j.toString() + "]=" + dir_cr[j].toString() + "  "
    
                    j=j+1
                     }





   jj=0
   minres = 1000.00f  
while (jj >=0 && jj<=15)  // index of symbols(numbers and operations)  0, 1 ..
{
          jjj=0

           minres = 100000.00f 
    while (jjj >=0 && jjj<=59 && rcanglenn_[jj] [jjj] [0] !=360.0f ) // quantity of variants for each/all numbers
            {
    
                j=0
               
                while (j >=0 && j<=19 && (canglenn_[j]!=360.0f || rcanglenn_[jj] [jjj] [j] !=360.0f) ) {  

                    if ( canglenn_[j]!=360.0f && rcanglenn_[jj] [jjj] [j] !=360.0f ) {
if ( Math.abs ( canglenn_[j] - rcanglenn_[jj] [jjj] [j]) <= 90 ) {
      resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   canglenn_[j]  - rcanglenn_[jj] [jjj] [j]   )
      resmin[jj] [jjj] =  ( (resmin[jj] [jjj]  * 1.0).roundToInt() / 1.0).toFloat()                                        
                                                                 }

if ( Math.abs (canglenn_[j] - rcanglenn_[jj] [jjj] [j]) > 90 ) {
   if( (canglenn_[j] >= rcanglenn_[jj] [jjj] [j]) )  { resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   (180-canglenn_[j])  + Math.abs(rcanglenn_[jj] [jjj] [j])   )  
                                                       resmin[jj] [jjj] =  ( (resmin[jj] [jjj]  * 1.0).roundToInt() / 1.0).toFloat()   
                                                     }
   if( (canglenn_[j] <  rcanglenn_[jj] [jjj] [j]) ) { resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs  (  (180-rcanglenn_[jj] [jjj] [j])  +   Math.abs(canglenn_[j])  )
                                                       resmin[jj] [jjj] =  ( (resmin[jj] [jjj]  * 1.0).roundToInt() / 1.0).toFloat()
                                                    }
      
                                                               }
  
                                                                                       }
                    else  { resmin[jj] [jjj] =  resmin[jj] [jjj] + 3600 }
                         
                                        
                    j=j+1
                                                                                                        }


                
      if  (resmin[jj] [jjj]  < minres) {    minres = resmin[jj] [jjj] ; res0=jj; res1=jjj   }   
                                           
           jjj=jjj+1
             }  
 aresmin = aresmin + "    [" + res0.toString() + "]" + "[" + res1.toString() + "]=" + minres.toString()
            
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
                                                                      if (l>0)  {aresnum1 =   ""  } 
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
  
           

     
                
       // "  " + aresnum +  "  " + aresnum1 + "\n" +
                //     "res=[" + res0 + "][" + res1 +  "]"   +  
            //     " dir_resmin=" + min + ",    [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  "/[" + dir_resmin[3][0] + "]" + "[" + dir_resmin[3][1] + "]" +  
// "\n" + "  ci=" + ci.toString() + "  cin=" + cin.toString() +  "  cinn=" + cinn.toString() +
            
         textviewid.text =     aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9] + aaa[10] + aaa[11] + aaa[12] + aaa[13] + aaa[14] + aaa[15] + aaa[16] + aaa[17] + aaa[18] + aaa[19] +     
                              "\n" +  "aresmin=" + aresmin  
                    
                    //"  " + aresnum +  "  " + aresnum1  +
                 //   "\n" +  aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9] + " " + aaa[10] + " " + aaa[11] + " " + aaa[12] + " " + aaa[13] + " " + aaa[14] + " " + aaa[15] + " " + aaa[16] + " " + aaa[17] + " " + aaa[18] + " " + aaa[19] + " " + aaa[20] + " " + aaa[21] + " " + aaa[22] + " " + aaa[23] + 
                   
                    //"\n"  + "crx;y" + crx_[0] +  ";" +  cry_[0] + "__" + crx_[1] +  ";" +  cry_[1] + "__" + crx_[2] +  ";" +  cry_[2] + "__" +
                   


                    
  /*               " iresnum=" + iresnum +  " xiresnum=" + xiresnum +   " shift1=" + shift1 +  " sign=" + sign + " l=" + l + " f=" + f + " ff=" + ff + " ffr=" + ffr + " dot=" + dot +  " repeat=" + repeat +
                "resnum=" + resnum[0] + " " + resnum[1] + " " + resnum[2] + " " + resnum[3] + " " + resnum[4] + " " + resnum[5] + " " + resnum[6] + " " + resnum[7] + " " + sresnum[8] + " " + sresnum[9] +
                "sresnum=" + sresnum[0] + " " + sresnum[1] + " " + sresnum[2] + " " + sresnum[3] + " " + sresnum[4] + " " + sresnum[5] + " " + sresnum[6] + " " + sresnum[7] +  " " + sresnum[8] + " " + sresnum[9] 
      */              
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



                    
                                 crx_ = Array<Float>(100){0.0f}
                                 cry_ = Array<Float>(100){0.0f} 
                            //     dir_cr =  Array<Int>(100){10} 
       // dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
       // dir_res =  Array(20){ Array<Int>(60){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
        crn_ = Array<Float>(20){0.0f}  // array of of crx_[j] + cry_[j] after n_normalization

       // dir_resmin =  Array(800){ Array<Int>(2){20} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
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
