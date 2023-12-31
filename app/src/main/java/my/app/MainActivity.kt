package my.app

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Bundle
import android.view.View

import android.view.ViewGroup

import android.view.LayoutInflater
import android.widget.*


import android.graphics.Bitmap






import android.view.MotionEvent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import my.app.databinding.ActivityMainBinding
import android.text.Editable
import android.text.TextWatcher
import android.database.ContentObserver 
import my.app.canvas.DrawPencil.Companion.xxx
import my.app.canvas.DrawPencil.Companion.yyy
import my.app.canvas.DrawPencil.Companion.zzz
import my.app.canvas.DrawPencil.Companion.rxx
import my.app.canvas.DrawPencil.Companion.ryy
import my.app.canvas.DrawPencil.Companion.rx
import my.app.canvas.DrawPencil.Companion.ry
import my.app.canvas.DrawPencil.Companion.crx_
import my.app.canvas.DrawPencil.Companion.cry_
import my.app.canvas.DrawPencil.Companion.xcrx_
import my.app.canvas.DrawPencil.Companion.ycry_
import my.app.canvas.DrawPencil.Companion.signx_
import my.app.canvas.DrawPencil.Companion.signy_


import my.app.canvas.DrawPencil.Companion.crxdy_
import my.app.canvas.DrawPencil.Companion.cangle_
import my.app.canvas.DrawPencil.Companion.canglepi_ 
import my.app.canvas.DrawPencil.Companion.ci




import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import android.util.DisplayMetrics

import my.app.canvas.DrawPencil





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
 
    private var oldx = 0
    private var oldy = 0 
    private var oldxdy = 0
    private var crn_ = Array<Int>(100){0}  // array of of crx_[j] + cry_[j] after n_normalization
    private var crnx_ = Array<Int>(100){0}  // array of of crx_[j]  after n_normalization
    private var crny_ = Array<Int>(100){0}  // array of of cry_[j] after n_normalization

    private var crnnx_ = Array<Int>(100){0}
    private var crnny_ = Array<Int>(100){0}
    
    
    private var crnxdy_ = Array<Int>(100){0}  // array of of crxdy_[j] after n_normalizatio
    
    private var canglen_ = Array<Int>(100){0} 
    private var canglenpi_ = Array<Int>(100){0} 
    private var canglenn_ = Array<Int>(100){360} 
    private var canglennpi_ = Array<Int>(100){360} 
    private var rcanglenn_ = Array(20) { Array(60){ Array<Int>(20){360} } }  
    
    
    private var cin = 0
    private var cinn = 0
    
    
    private var rr =  Array(20) { Array(60){ Array<Int>(20){10} } }  //  array of value for n_etalons(30) for every sample(10pcs)c
    private var rrx =  Array(20) { Array(60){ Array<Int>(20){0} } }  //  array of value for x n_etalons(30) for every sample(10pcs)
    private var rry =  Array(20) { Array(60){ Array<Int>(20){0} } }  //  array of value for y n_etalons(30) for every sample(10pcs)
    private var rangle =  Array(20) { Array(60){ Array<Int>(10){0} } }  // 
    
    private var xdyrr =  Array(20) { Array(60){ Array<Int>(20){0} } }  // 

   
  
    private var  resmin =  Array(20){ Array<Int>(60){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminx =  Array(20){ Array<Int>(60){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminy =  Array(20){ Array<Int>(60){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    
    private var  aresmin = " "
    private var iresmin =  Array<Int>(60){10}  // array of index for of values resmin array with min difference(coincedence) 
    
    private var res =    Array<Int>(60){0}  // array of  counters for  each etalon from iresmin array
   
    private var min = 10 // temporary variable min = dir_res[jj][jjj] 
    private var minres = 100000 // temporary variable  resmin[jj] [j] < minres
    private var minres0 = 100000 
    private var max=0   // res [jj] > max res temporary for max coincedence finding
    private var res0 = 0 // res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]  - temporary for max coincedence finding: res0, res1  - etalon number and index its variant in rr array 
                         // in array with min difference(coincedence), res1 - number  
    private var res00 = 0 
    private var res1 = 0 // look up
    private var l = 0 // counter for different res0 in resnum array
    private var ll = 0
    private var resnum =   Array<Float>(60){30.0f}  // result each cycle adding  as Int Array
    private var sresnum =  Array<Float>(60){30.0f}  // result each cycle adding  as Int Array
    private var asresnum = " "  
 
     
    private var iresnum = 0.0f // result last operation

    
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
    private var resxy=0
 



        
    
       
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
    rcanglenn_[0][0]=arrayOf<Int>(   86,  65,  46, 175,  136, 119, 104,  -87,  -64,  -39, -19, -173, -144, -127, -107,  86, 360, 360, 360, 360  ) 
    rcanglenn_[0][1]=arrayOf<Int>(  -59, -77,  95, 112,  128, 145, 161,    8,   49,   68, -93, -115, -131, -156,   -2, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][2]=arrayOf<Int>(  -42, -62, -80,  94,  122, 139, 166,   11,   32,   47,  66,   83,  -92, -120, -141,-172, 360, 360, 360, 360  ) 
    rcanglenn_[0][3]=arrayOf<Int>(   64,  45,  21, 173,  145, 129, 108,  -83,  -67,  -40, -16, -176, -119,   87,   64, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][4]=arrayOf<Int>(   80,  59,  33, 175,  154, 128, 111,  -88,  -67,  -50, -19, -175, -134, -115,  -99,  87,  70, 360, 360, 360  ) 
    rcanglenn_[0][5]=arrayOf<Int>(   84,  65,  40,  11,  162, 145, 128,  105,  -85,  -69, -52,  -36, -166, -116,  -98, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][6]=arrayOf<Int>(   72,  52, 172, 142,  122, 104, -88,  -73,  -56,  -32,-174, -135, -115,  -98,   88, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][7]=arrayOf<Int>(   83,  40,  17, 165,  148, 126, 109,  -84,  -69,  -45,-170, -130, -107,   89,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][8]=arrayOf<Int>(  176, 123, 101, -87,  -64, -30, -13, -173, -128, -111,  86,  67,    45,   89,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][9]=arrayOf<Int>(   76,  56,  35,  13,  167, 143, 127,  112,   96,  -87, -72, -50,   -22, -173, -156,-135,-106,  85, 360, 360  ) 
    rcanglenn_[0][10]=arrayOf<Int>(  77,  50, 171, 140,  121, 104, -82,  -62,  -34, -174,-124, -101,  360,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][11]=arrayOf<Int>(  85,  43,  27, 175,  154, 122, -88,  -69,  -40, -172,-147, -111,   84,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][12]=arrayOf<Int>( -63,  92, 110, 135,  155,   5,  35,   65,   83,  -93,-114, -139, -167,   -7,  -38, -58, 360, 360, 360, 360  ) 
    rcanglenn_[0][13]=arrayOf<Int>( -65, -80,  95, 119,  165,   6,  43,   61,   79,  -98,-127, -146, -166,   -9,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][14]=arrayOf<Int>( -55, -70,  94, 114,  136, 153,  11,   35,   55,   72, -96, -112, -129, -153,  -14, -38, 360, 360, 360, 360  ) 
    rcanglenn_[0][15]=arrayOf<Int>(  53,  31,  15, 176,  150, 125, 100,  -80,  -60,  -34, -18, -171, -135, -118, -102,  88,  64,  45,  25, 360  ) 
    rcanglenn_[0][16]=arrayOf<Int>(  80,  63,  43,  13,  173, 135, 118,   98,  -85,  -69, -44,  -15, -172, -148, -126,-110,  84, 360, 360, 360  ) 
    rcanglenn_[0][17]=arrayOf<Int>( -57, -76,  96, 114,  132,  16,  59,   76,  -95, -111,-145,  -13,  -40,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][18]=arrayOf<Int>( -26, -43, -59, -75,   95, 110, 131,  161,    9,   30,  58,   75,  -93, -134, -168,  -9, 360, 360, 360, 360  ) 
    rcanglenn_[0][19]=arrayOf<Int>( -59, -74,  94, 113,  131,   5,  46,   68,  -91, -146,  -7,  -31,  -56,  -75,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][20]=arrayOf<Int>( -73,  94, 117, 134,  151,   6,  52,   70,  -93, -114,-147, -173,  -11,  -32,  -53, 360, 360, 360, 360, 360  )
    rcanglenn_[0][21]=arrayOf<Int>( -46, -62, -84,  94,  114, 136,   8,   40,   55,   74, -92, -131, -162,   -6,  -25, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][22]=arrayOf<Int>( -41, -61, -80,  93,  108, 134, 156,    9,   39,   55,  78,  -98, -120, -136, -152, -13, -45, 360, 360, 360  ) 
    rcanglenn_[0][23]=arrayOf<Int>( -55, -70,  94, 111,    8,  44,  61,  -94, -120, -140,-168,   -6,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][24]=arrayOf<Int>( -53, -68,  95, 111,  127, 153, 170,    8,   50,   71, -98, -126, -162,  -12,  -38, -66, 360, 360, 360, 360  ) 
    rcanglenn_[0][25]=arrayOf<Int>(  -7, -33, -56, -77,   97, 116, 131,  158,   17,   53,  73,  -95, -113, -160,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][26]=arrayOf<Int>( -75,  96, 112, 131,  170,  13,  30,   49,   66,   85, -92, -112, -130, -152,   -6, -38, -54, 360, 360, 360  ) 
    rcanglenn_[0][27]=arrayOf<Int>(  62,  41, 170, 121,  -85, -66, -50,  -19, -166, -118,-100,   87,   64,  360,  360, 360, 360, 360, 360, 360  )

    




    

  
 // Filling sample array
// 1
      jj = 1                  

   rcanglenn_[1][0]=arrayOf<Int>(   61,  -78,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][1]=arrayOf<Int>(   41,   61,  -84, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][2]=arrayOf<Int>(   36,   51,   71,  18, 176, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][3]=arrayOf<Int>(   41,   60,   76, 109, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][4]=arrayOf<Int>(   60,   76,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][5]=arrayOf<Int>(   57,   73,   92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][6]=arrayOf<Int>(   55,  -71,  -86, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][7]=arrayOf<Int>(   60,  -79,   92, -86, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][8]=arrayOf<Int>(   47, -122,  -76, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][9]=arrayOf<Int>(   51,   67,  -85, 110, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][10]=arrayOf<Int>(  46,   65, -140, -74, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][11]=arrayOf<Int>(  42,   63,  -43, -79, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][12]=arrayOf<Int>(  51,   66,  -43, -70, -86,  91, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][13]=arrayOf<Int>(  43,   60,  -36, -74, 114, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][14]=arrayOf<Int>(  37,   55,  105, -83, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][15]=arrayOf<Int>(  32,   55,  -27, -58, -76, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 

   
   
     
// Filling sample array 
// 2
      jj = 2                  

 rcanglenn_[2][0]=arrayOf<Int>(   81,   41,  167, 128, 111, -86, -68,  -47, -23, -170 ,  35, 173, 147, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][1]=arrayOf<Int>(   73,   30,   15, 170, 127, 102, -84,  -69, -51,  -26 ,-140,  74, 175, 155, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][2]=arrayOf<Int>(   78,   60,   25, 171, 120,  98, -84,  -68, -47,  -29 ,-165, 150, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][3]=arrayOf<Int>(   31,   15,  173, 114, -87, -68, -51,  -34, -11, -117 ,  15, 175, 146, 167, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][4]=arrayOf<Int>(   65,   42,  173, 126, -85, -66, -51,  -26, 176,  160 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][5]=arrayOf<Int>(   74,   55,   29, 169, 124, 107, -85,  -70, -52,  -37 ,-155,  72, 171, 144, 162, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][6]=arrayOf<Int>(   51,   28,  175, 116, -85, -68, -52,  -37, -18,  173 , 150, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][7]=arrayOf<Int>(   58,   43,  172, 105, -87, -69, -48,  -31,-171,   20 , 171, 151, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][8]=arrayOf<Int>(   64,   44,   29, 167, 132, 116, -84,  -64, -47,  -26 , -11,-164,  29, 171, 150, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][9]=arrayOf<Int>(   56,   39,  174, 124, 108, -83, -66,  -49, -33, -140 ,  78,  49, 171, 147, 169, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][10]=arrayOf<Int>(  78,   41,  166, 131, 109, -82, -63,  -36, -168,  75 ,  26, 167, 131,  45, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][11]=arrayOf<Int>(  55,   32,   11, 173, 137, 107, -84,  -68,  -52, -36,  -14,-118,  53,  31,  15, 171, 154, 360, 360, 360  ) 
 rcanglenn_[2][12]=arrayOf<Int>(  77,   58,   20, 173, 127, 109, -87,  -67,  -49, -33 ,-171,-117,  37, 175, 156,  20, 360, 360, 360, 360  )
 rcanglenn_[2][13]=arrayOf<Int>(  34,  170,  105, -80, -60, -41, -22,  172,  153, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 






 
      
// Filling sample array 
// 3
      jj = 3                   
  rcanglenn_[3][0]=arrayOf<Int>(   63,  32, 172, 113,  -84,  -59, -43, 175, 145, 111, -85, -33,  -13, -177, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][1]=arrayOf<Int>(   33, 175, 105, -79,  -63,  -46, -30, 177, 137, 122, -83, -46,  -30,  -14,-177, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][2]=arrayOf<Int>(   57,  24, 173, 121,  103,  -85, -66, -51, -24,   6, 164, 144,  103,  -84, -63, -47, -18,-177, 360, 360  ) 
  rcanglenn_[3][3]=arrayOf<Int>(   55,  33,  10, 150,  129,  104, -82, -64, -46, 171, 153, 114,  -83,  -23,-175,-155, 360, 360, 360, 360  ) 
  rcanglenn_[3][4]=arrayOf<Int>(   47,  26, 173, 121,  -84,  -64, -39, 156, 140, 113, -77, -30, -176,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][5]=arrayOf<Int>(   54,  35,  20, 174,   98,  -77, -60, -42, -25, 172, 128, 109,  -84,  -38, -16,-174,-154, 360, 360, 360  ) 
  rcanglenn_[3][6]=arrayOf<Int>(   39,  11, 170, 140,  106,  -86, -64, -47, -27,   8, 172, 136,  117,  -80, -44, -19,-177, 360, 360, 360  ) 
  rcanglenn_[3][7]=arrayOf<Int>(   45,  26, 169, 103,  -80,  -56, -37, -21, 175, 154, 136, 100,  -69,  -48, -32,  -1, 360, 360, 360, 360  ) 
  rcanglenn_[3][8]=arrayOf<Int>(    2, -43, 166, -29,  -47,  360, 360, 360, 360, 360, 360, 360,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][9]=arrayOf<Int>(  174, 147, -37, 160,  176,  -39, 360, 360, 360, 360, 360, 360,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][10]=arrayOf<Int>(  56,  31, 172, 111,  -83,  -59, -39, 173, 150, 127, 105, -83,  -45,  -23,  -8, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][11]=arrayOf<Int>(  78,  37,  14, 168,  112,  -82, -55, -40, 173, 150, -84, -48,  -29,  -13, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][12]=arrayOf<Int>(  41,  20, 175, -82,  -54,  -36,  14, 169, 141, -82, -34,-178,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][13]=arrayOf<Int>(  50,  34, 164, 118,  -81,  -63, -45, -24, 171, 132, 112, -78,  -23,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][14]=arrayOf<Int>(  54,  35,  15, 171,  107,  -80, -58, -43,  28,   7, 163, 145,  115,   99, -65, -43, -16,-174,-155, 360  ) 
  rcanglenn_[3][15]=arrayOf<Int>(  81,  62,  41,  20,    5,  160, 134, -70, -51, -35, 176, 160,  129,  -78, -38, -23,  -5, 360, 360, 360  ) 



  
      
// Filling sample array 
// 4
      jj = 4                  
  
  rcanglenn_[4][0]=arrayOf<Int>(  -74,   -59,   4, -101, -85, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][1]=arrayOf<Int>(  -72,   -56, 178,   76, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][2]=arrayOf<Int>(  -74,   150,   4,  -94, -85, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][3]=arrayOf<Int>(  -78,   -63, 132,  179,-113, -80, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][4]=arrayOf<Int>(  -74,   148, 178,  -93, -82,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][5]=arrayOf<Int>(  -70,   169,-116,  -88,  94, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][6]=arrayOf<Int>(  -87,    94,  52,  -79, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][7]=arrayOf<Int>(  -60,   -76, -58,    8, 171, 144,-116, -85, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][8]=arrayOf<Int>(  -73,   -58, 131,   35, -81, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][9]=arrayOf<Int>(  -66,   145, 175, -126,  92, -89, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][10]=arrayOf<Int>( -80,   -64,   8,  172,-128, -83, -67, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 

  
  
  
      
// Filling sample array 
// 5
      jj = 5    
 rcanglenn_[5][0]=arrayOf<Int>(   -73,  19,   35,  13, 167,  134,  115, -79, -60,  -42, -23, -172, -157,  76,  12, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][1]=arrayOf<Int>(   -84, -69,   34,  15, 173,  120,  101, -87, -56,  -22,-174, -146,   80,   6, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][2]=arrayOf<Int>(   -74,  16,  173, 137, 114,  -86,  -52, -32,  -7, -163,  78,    8,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][3]=arrayOf<Int>(   -77,  49,   33, 173, 132,  100,  -81, -56, -36,  -13,-176,   75,    7, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][4]=arrayOf<Int>(   -73,  33,  172, 134, 109,  -84,  -54, -24,-177,   77,   6,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][5]=arrayOf<Int>(   -79,  48,   31, 173, 140,  115,  -87, -57, -27,  -95, 360,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][6]=arrayOf<Int>(   -74,  32,   12, 173, 146,  107,  -87, -64, -32,   -9,-167,   76,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][7]=arrayOf<Int>(   -70,  36,  170, 125, 110,  -83,  -66, -46, -27,   -6,-167,   71,    7, 164, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][8]=arrayOf<Int>(   -70,  53,   35,  17, 169,  133,  115,  99, -80,  -64, -39,  -20, -174,-153,  79,   9, 174, 360, 360, 360  ) 
 rcanglenn_[5][9]=arrayOf<Int>(   -82, 116,   12, 148, 101,  -86,  -17,-178,  75,    8, 360,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][10]=arrayOf<Int>(  -86,  37,   22, 174, 118,  -86,  -51, -33, -14, -175,  84,  166,    5, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][11]=arrayOf<Int>(  -78,  40,   11, 169, 122,  103,  -79, -64, -44,  -20,-176,   76,   13, 177, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][12]=arrayOf<Int>(  -73,  26,  169, -79, -60,  -44,  -26,  79,   9,  360, 360,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][13]=arrayOf<Int>(  112,  96,   32, 172, 133,   99,  -85, -63, -36,  -16,-174,  -99,   17, 176, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[5][14]=arrayOf<Int>(  -75,  52,   22, 172, 140,  114,  -75, -18,-176,   80, 166,    3,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][15]=arrayOf<Int>(  -75,  37,   22, 175, 104,  -78,  -55, -32,-135,   85,  22,    7,  360, 360, 360, 360, 360, 360, 360, 360  )  


 
 
// Filling sample array 
// 6
      jj = 6                  
  rcanglenn_[6][0]=arrayOf<Int>(   -28, -47,  -63, -78,  95,  115, 132,  149,  171,   10,  47,  79,  -94, -127,  -155,  -6,  -40, -57, 360, 360  ) 
  rcanglenn_[6][1]=arrayOf<Int>(    -5, -35,  -50, -69, -85,   95, 119,  153,  171,   12,  38,  59,   79,  -97,  -121,-155,   -5, -48, -76, 360  ) 
  rcanglenn_[6][2]=arrayOf<Int>(    -4, -20,  -41, -61, -80,   96, 118,  139,  156,    8,  43,  61,   80,  -96,  -140,-159,  -10, -30, -47, -66  ) 
  rcanglenn_[6][3]=arrayOf<Int>(    -6, -38,  -55, -72,  94,  115, 132,  159,    8,   41,  57, -96, -139, -158,   -10, -45,  -67, 360, 360, 360  ) 
  rcanglenn_[6][4]=arrayOf<Int>(    -6, -51,  -67, -84,  94,  125, 149,  164,    6,   55,  73, -94, -126, -166,    -7, -32,  360, 360, 360, 360  ) 
  rcanglenn_[6][5]=arrayOf<Int>(   -37, -53,  -69,  98, 117,  157,  10,   33,   48,   69, -96,-133, -153,   -7,   -24, -47,  360, 360, 360, 360  ) 
  rcanglenn_[6][6]=arrayOf<Int>(    -4, -20,  -47, -63, -79,   92, 173,   13,   33,   75, -93,-126, -157,   -5,   360, 360,  360, 360, 360, 360  ) 
  rcanglenn_[6][7]=arrayOf<Int>(    -9, -53,  -72,  94, 113,  139, 156,   15,   63,   80, -96,-125,   -5,  -36,   -57, 360,  360, 360, 360, 360  ) 
  rcanglenn_[6][8]=arrayOf<Int>(   -37, -52,  -67, -87,  95,  129, 167,   29,   47,   63,-101,-127, -164,  -20,   -49, 360,  360, 360, 360, 360  ) 




  
      
// Filling sample array 
// 7
      jj = 7                  
  
   rcanglenn_[7][0]=arrayOf<Int>(   83,   60,  167,   148,  169,   7,  64, -79, -108, 178 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][1]=arrayOf<Int>(   51,  169,  144,    15,   58,  80,-101, 178,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][2]=arrayOf<Int>(   81,  155,  139,   163,   11,  50, -69,-104,  169, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][3]=arrayOf<Int>(   86,  151,   11,   -72, -107, 172, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][4]=arrayOf<Int>(  -98,  169,   11,   -72, -104, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][5]=arrayOf<Int>(   87,  162,   14,    58,  -74,-111, 178, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][6]=arrayOf<Int>(   78,  157,  141,   169,   17,  44, -61, -77, -121, 151 , 169,   7, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][7]=arrayOf<Int>(   84,   69,   27,   164,  127, 150, 171,  16,   41, -72 ,-121, 170, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][8]=arrayOf<Int>(  178,  -46,  -64,   -83,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][9]=arrayOf<Int>(   43,  175,   20,   -74, -106, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][10]=arrayOf<Int>(  75,  170,   16,   -71,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][11]=arrayOf<Int>( 167,    4,   68,   -99,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][12]=arrayOf<Int>(-106,   85,  140,   166,   29, -71,-100,   2,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )



   

// Filling sample array 
// 8
      jj = 8                 
  
    rcanglenn_[8][0]=arrayOf<Int>(  -139,  83,  44,  26,  176,  -80, -54, -72,   95,  151, 169,  33,  62,  -99,  -128,  -145, -130, 360, 360, 360  )    
    rcanglenn_[8][1]=arrayOf<Int>(  -151,-135,-112,  80,   41,   23, 176, 136,  -81,  -60, -82, 103, 118,  148,   169,     7,   48,  80, -94,-119  )    
    rcanglenn_[8][2]=arrayOf<Int>(  -151,-130,  81,  39,   19,  171, 104, -85,  -54,  -76,  98, 129, 159,    5,    43,    79,  -98,-117,-136, 360  )    
    rcanglenn_[8][3]=arrayOf<Int>(  -145,-125,  84,  54,   37,  174, -79, -56,  -74,   96, 157,  11,  35,   62,   -97,  -125,  360, 360, 360, 360  )    
    rcanglenn_[8][4]=arrayOf<Int>(  -149,-131,  74,  50,   30,  171, 119,  98,  -77,  -51, -69,  95, 140,  162,     9,    57,  -99,-127, 360, 360  )
    rcanglenn_[8][5]=arrayOf<Int>(  -137,-121,  72,  23,  171,  -70, -55, -74,  104,  150,   7,  39,  67, -110,  -130,  360,  360, 360, 360, 360  )    
    rcanglenn_[8][6]=arrayOf<Int>(    15,  44,  71,-103, -150,   -5, -44, -60,  101,  151, 168, 149, 132,  103,   -76,  -44,  -25, -17,-136,-112  )    
    rcanglenn_[8][7]=arrayOf<Int>(    18,  36,  52,  67,  -99, -167, -18, -41,  -56,  104, 128, -70, -41,  -20,  -176, -108,   78,  59,  35, 360  )    
    rcanglenn_[8][8]=arrayOf<Int>(    46,  61,-103,-135,   -4,  -37, -58, 105,  143,  115, -83, -44, -21, -175,  -114,  -98,   74,  44,  26, 360  )    
    rcanglenn_[8][9]=arrayOf<Int>(    44,  61, -96, -16,  -51,  -73,  96, 144,  123,  -86, -53, -28, -12, -175,   83,    65,  360, 360, 360, 360  )    
    rcanglenn_[8][10]=arrayOf<Int>( -137,-117,  81,  50,   16,  171, 137, -73,  -57,  -80, 126, 153,   8,   32,   73,   -93, -131, 360, 360, 360  )    
    rcanglenn_[8][11]=arrayOf<Int>( -158,  76,  25, 176,  135,  -59, -76,  97,  122,    5,  35,  69, -97, -120, -139,  -156,  360, 360, 360, 360  )    
    rcanglenn_[8][12]=arrayOf<Int>( -143,-126,  66,  30,  145,  -73, -54,  96,  150,    8,  49,  69, -95, -135,  360,  360,  360, 360, 360, 360  )    




    

// Filling sample array 
// 9
      jj = 9                 
  
   rcanglenn_[9][0]=arrayOf<Int>(  10,  27,   44,  61,  79,  -94,  -117,  -137, -163,  -12,  -36, -52, -79,  99, 117,  133,   7,  45, 360, 360  ) 
   rcanglenn_[9][1]=arrayOf<Int>( 161,  13,   53,  74, -92, -116,  -165,   -13,  -32,  -53,  -83,  95, 132, 149,  11,   31,  47, 360, 360, 360  ) 
   rcanglenn_[9][2]=arrayOf<Int>( 143, 161,   13,  52,  67,   82,   -93,  -114, -147,   -5,  -23, -43, -59, -74,  96,  121, 140, 166,   6,  26  ) 
   rcanglenn_[9][3]=arrayOf<Int>( 149,   7,   60,  75, -92, -112,  -138,  -164,   -8,  -49,  -74,  96, 119, 158,  16,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][4]=arrayOf<Int>( 175,  24,   40,  61,  79,  -93,  -117,  -151,  -26,  -51,  -77,  93, 120, 164,   8,   40, 360, 360, 360, 360  ) 
   rcanglenn_[9][5]=arrayOf<Int>( 171,   8,   55,  73, -92, -146,   -10,   -41,  -63,   96,  123,   6,  39, 360, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][6]=arrayOf<Int>( 160,   3,   49,  64,  83,  -93,  -108,  -129, -167,  -22,  -40, -62, 100, 122, 168,    9,  32, 360, 360, 360  ) 
   rcanglenn_[9][7]=arrayOf<Int>( 158,  13,   52,  68,  85,  -92,  -109,  -131,   -8,  -56,  -75,  94, 151,  13, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][8]=arrayOf<Int>(  21,  42,   59,  78, -94, -116,   -13,   -59,  -83,   96,  134, 167,   8,  42, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][9]=arrayOf<Int>( 146, 164,    6,  51,  69,  -92,  -116,   -12,  -56,  -80,   97, 129, 162,  10,  41,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][10]=arrayOf<Int>(-47, -30, -171,-135,-115,   75,    27,   173,  133,   97,  -87, -67, -34, -16,-176,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][11]=arrayOf<Int>(-99,-119, -170, -18, -40,  -56,   -78,    96,  119,  160,    7,  28,  55,  79, -56,  -18,-173, 360, 360, 360  ) 
   rcanglenn_[9][12]=arrayOf<Int>(  59,  77,   60, -97,-114, -146,  -165,   -42,  -58,   97,  162,   7, 360, 360, 360,  360,360, 360, 360, 360  ) 
   rcanglenn_[9][13]=arrayOf<Int>(  39,  55,   70, -92,-125,  -31,   -59,   104,  158,   22,   39, 360, 360, 360, 360,  360,360, 360, 360, 360  ) 
   rcanglenn_[9][14]=arrayOf<Int>(  44,  59,   78, -93,-128, -159,    -6,   -39,  -54,  -72,   97, 145, 172,  11,  28,  360,360, 360, 360, 360  ) 
   rcanglenn_[9][15]=arrayOf<Int>(  51,  71,  -94,-133,-160,   -8,   -28,   -55,   97,  135,  157,   6,  30, 360, 360,  360, 360, 360, 360, 360  ) 
   




   
            
 // Filling sampl// Filling sample array 
// +
      jj = 10                

   
      rcanglenn_[10][0]=arrayOf<Int>(   1,  -127,   -83,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][1]=arrayOf<Int>( 177,    75,    92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][2]=arrayOf<Int>( 177,  -119,   -86, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][3]=arrayOf<Int>( 176,    72,   -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][4]=arrayOf<Int>(   6,    88,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][5]=arrayOf<Int>( 177,   -94,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][6]=arrayOf<Int>(  87,   138,  -171,  -7, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][7]=arrayOf<Int>(  13,   177,   158,-123, -74, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][8]=arrayOf<Int>(   9,    86,    95, -84, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][9]=arrayOf<Int>( 175,    17,  -137, -74, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )   
      rcanglenn_[10][10]=arrayOf<Int>( 45,   -81,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][11]=arrayOf<Int>(176,  -108,    91, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 


      
      
// Filling sample array 
// -
      jj = 11               
  
        rcanglenn_[11][0]=arrayOf<Int>( 180 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
        rcanglenn_[11][1]=arrayOf<Int>(   5 , 168, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
     


      
// Filling sample array 
// x
      jj = 12               
  rcanglenn_[12][0]=arrayOf<Int>(   141, 126,  89,  -40, -55, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][1]=arrayOf<Int>(   142,  86, -47,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][2]=arrayOf<Int>(    43, -13, 126,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][3]=arrayOf<Int>(   134, -91, -46,  -61, -45, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][4]=arrayOf<Int>(   128, -92, -58,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][5]=arrayOf<Int>(    47,  -8, 140,  125, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][6]=arrayOf<Int>(    68,  46,-176,  109, 129, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][7]=arrayOf<Int>(   144, 129, -98,  -41, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][8]=arrayOf<Int>(    48,-179, 137,  122, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][9]=arrayOf<Int>(   132, 115,  83,  -52, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][10]=arrayOf<Int>(  129, -93, -53,  -70, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][11]=arrayOf<Int>(  141, 121,-172,   47, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  




  
      
// Filling sample array 
// /
      jj = 13              

      rcanglenn_[13][0]=arrayOf<Int>(   45, 360, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][1]=arrayOf<Int>(  -45, 360, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][2]=arrayOf<Int>(   52,  35, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
       

// Filling sample array 
// =
      jj = 14            

     rcanglenn_[14][0]=arrayOf<Int>(   1,  -27,   2, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][1]=arrayOf<Int>( 169,    4, -27,   2, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][2]=arrayOf<Int>( 177,  -41, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][3]=arrayOf<Int>(   3,  163, -21,   5, 157, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][4]=arrayOf<Int>(  92,    6, 171, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  


     
      
// Filling sample array 
// ,
      jj = 15            
     
     rcanglenn_[15][0]=arrayOf<Int>(   140, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][1]=arrayOf<Int>(   100, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][2]=arrayOf<Int>(   108, -87,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][3]=arrayOf<Int>(   -86, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][4]=arrayOf<Int>(   122, 106,  -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  



   


   
      
      btnPencil.setOnClickListener {
       
          
          
          // Untuk mengganti dari false menjadi true
        //        isPencilIconClicked = !isPencilIconClicked     
     
          //      if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya
             //    shift=shift+1
                    
                     if (shift==1) {
                     //    cangle_ = Array<Int>(800){0} 
                     //    canglepi_ = Array<Int>(800){0} 
                     //    crx_ = Array<Int>(800){0}
                     //    cry_ = Array<Int>(800){0}


                         
                         shift=0

                         // drawPencil.setForegroundResource(R.drawable.ic_launcher_foreground)
                         
                         // drawPencil.setImageResource(R.drawable.ic_launcher_foreground)
                         
                       //  drawPencil.setBackgroundResource(R.drawable.background_card)
                         
                       // drawPencil.visibility = View.GONE
                   
                                  }
                
                
                       else {
                //    btnPencil.setImageResource(R.drawable.ic_selected_pencil)
               //     btnPencil.setBackgroundResource(R.drawable.background_cards)
                  
                      drawPencil.visibility = View.VISIBLE
                      btnPencil.setImageResource(R.drawable.ic_selected_pencil)
                    //  drawPencil.setBackgroundResource(R.drawable.background_cards)     
                          shift=1
            
                 
                 
                    

                     
                  
i=0
j=0
oldx = 0; oldy = 0;   
oldxdy=1000

    crnnx_ = Array<Int>(100){0}
    crnny_ = Array<Int>(100){0}
       
    crnxdy_ = Array<Int>(100){0}  // array of of crxdy_[j] after n_normalizatio
    
    canglen_ = Array<Int>(100){0} 
    canglenpi_ = Array<Int>(100){0} 
    canglenn_ = Array<Int>(100){360} 
    canglennpi_ = Array<Int>(100){360} 
   
                  
     
        ci=ci-1 
        cin=ci
      if (cin==0) j=0  
         else j=1           
        kk=0    
        cinn=0     
     resxy = (((w+h)/2)*2/100)
                    
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
                                         canglenn_[cinn]=   ( cangle_[j] * signx_[j] * 1.0).roundToInt()  
                                         canglennpi_[cinn]=( canglepi_[j]   * 1.0).roundToInt()    
                                             cinn=cinn+1
                                      
                                             }
                                         }
                                                                            
                                 j=j+1
                      } 

j=cin
if (cinn==0) {   crnnx_[cinn]= crx_[j]; crnny_[cinn]= cry_[j]; 
                                         canglenn_[cinn]= ( cangle_[j]  * 1.0).roundToInt() 
                                         canglennpi_[cinn]= ( canglepi_[j]  * 1.0).roundToInt() 
             }

              

// output as text current painted number in direction sequence
        aaa = Array<String>(20){"0"}
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



   aresmin = " "

  resmin =  Array(20){ Array<Int>(60){0} }
  
                    
   jj=0
   minres0 = 100000
while (jj >=0 && jj<=15)  // index of symbols(numbers and operations)  0, 1 ..
{
          jjj=0

           minres = 100000
    while (jjj >=0 && jjj<=59 && rcanglenn_[jj] [jjj] [0] !=360 ) // quantity of variants for each/all numbers
            {
    
                j=0
               
                while (j >=0 && j<=19 && (canglenn_[j]!=360 || rcanglenn_[jj] [jjj] [j] !=360) ) {  

                    if ( canglenn_[j]!=360 && rcanglenn_[jj] [jjj] [j] !=360) {
if ( Math.abs ( canglenn_[j] - rcanglenn_[jj] [jjj] [j]) <= 90 ) {
      resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   canglenn_[j]  - rcanglenn_[jj] [jjj] [j]   )
      resmin[jj] [jjj] =   (resmin[jj] [jjj]  * 1.0).roundToInt()                                     
                                                                 }

if ( Math.abs (canglenn_[j] - rcanglenn_[jj] [jjj] [j]) > 90 ) {
   if( (canglenn_[j] >= rcanglenn_[jj] [jjj] [j]) )  { resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   (180-canglenn_[j])  + Math.abs(rcanglenn_[jj] [jjj] [j])   )  
                                                       resmin[jj] [jjj] =   (resmin[jj] [jjj]  * 1.0).roundToInt()  
                                                     }
   if( (canglenn_[j] <  rcanglenn_[jj] [jjj] [j]) ) { resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs  (  (180-rcanglenn_[jj] [jjj] [j])  +   Math.abs(canglenn_[j])  )
                                                       resmin[jj] [jjj] =  (resmin[jj] [jjj]  * 1.0).roundToInt() 
                                                    }
      
                                                               }
  
                                                                                       }
                    else  { resmin[jj] [jjj] =  resmin[jj] [jjj] + 3600 }
                         
                                        
                    j=j+1
                                                                                                        }


                
      if  (resmin[jj] [jjj]  < minres) {    minres = resmin[jj] [jjj] ; res0=jj; res1=jjj;   }   
                                           
           jjj=jjj+1
             }  
 aresmin = aresmin + " [" + res0.toString() +  "," + res1.toString() + "]=" + minres.toString()

            if ( minres < minres0)    { res00=res0; minres0=minres }
 jj=jj + 1    
    
} 

      
          


// output  result as string with diferent length adding new symbols each cycle

      resnum[l]=res00.toFloat()
     if ( resnum[l]<=9 ) { aresnum =  aresnum +  aresnum1 + resnum[l].toString(); aresnum1="" ; repeat=0 } 
   
     
     if( resnum[l] >= 10 && resnum[l] <= 15 ) {                                                            
                                                if ( resnum[l]==10.0f) { aresnum1 =   "+" } 
                                                if ( resnum[l]==11.0f) { aresnum1 =  "-" } 
                                                if ( resnum[l]==12.0f) { aresnum1 =   "x" } 
                                                if ( resnum[l]==13.0f) { aresnum1 =   "/" } 
                                                if ( resnum[l]==14.0f) { if (l==0) {aresnum1 =   "="}
                                                                      if (l>0)  {aresnum1 =   ""  } 
                                                                    }  
                                                if ( resnum[l]==15.0f) { aresnum1 =  "," } 

                                                
                                                if (repeat ==1) { resnum[l-1] = resnum[l]; sresnum[l-1] = resnum[l-1];resnum[l]=30.0f; sresnum[l] = 30; l=l-1  }     
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
                   if (resnum[l] == 15.0f) {   dot=1; l=l-1 }

                 }                
                        



                                                             
      if (l==0 &&  resnum[0] == 11.0f)  {  xxsign=-1 }                              
      if(l>0) { if (resnum[l] >= 10.0f && ((resnum[l-1] <= 9.0f) || (f==1) ) ) { sign = 1; shift1=0 ; ff=1 } }
      if( resnum[l] <= 9 && ff==0 )  { iresnum=xxsign*sresnum[l]; xxsign=1 }              
         

   
       
                
      if(sign == 1  && resnum[l-1-shift1]==10.0f ) { iresnum= iresnum + xiresnum; xiresnum=0.0f; xsign=1
                                                  if (shift1==0) { iresnum= iresnum +  resnum[l] } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum +  ( sresnum[l] - sresnum[l-1])  } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==11.0f) {  iresnum= iresnum + xiresnum; xiresnum=0.0f; xsign=-1
                                                  if (shift1==0) { iresnum= iresnum - sresnum[l] } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum - ( sresnum[l] - sresnum[l-1] ) } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==12.0f) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2].toFloat() * sresnum[l].toFloat();ffr=1 
                                                     iresnum= iresnum - xsign * sresnum[l-2]  
                                                                          } 
      
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum * sresnum[l] } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum= xiresnum *  (sresnum[l] / sresnum[l-1] ) } 
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==13.0f) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2].toFloat() / sresnum[l].toFloat() ; ffr=1 
                                                      iresnum= iresnum - xsign * sresnum[l-2]             
                                                                          } 
         
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum / sresnum[l] } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum= xiresnum /  sresnum[l] * sresnum[l-1]  }  
                                                  
                                                } 
     


     
      if(l>=1 && resnum[l]==14.0f) { iresnum = iresnum + xiresnum; 
                                  if ( ( iresnum - iresnum.toInt() ) > 0.000000000000000001f ) {  
                                  aresnum =  aresnum + "= "  + iresnum.toString()  ; resnum[l+1]=iresnum; f=1;  sresnum[l+1]=resnum[l+1]; sign=0; xiresnum=0.0f;  ffr=0; xsign=1;  l=l+1; repeat=0 }
                                  
                                  else
          {aresnum =  aresnum + "= "  + ( iresnum.toInt() ).toString()  ; resnum[l+1]=iresnum.roundToInt(); f=1;  sresnum[l+1]=resnum[l+1]; sign=0; xiresnum=0.0f;  ffr=0; xsign=1;  l=l+1; repeat=0 }

                                 }
  
           

     
                
     
            
         textviewid.text =   "  " + aresnum +  aresnum1 + "\n" +  
                             aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9] + aaa[10] + aaa[11] + aaa[12] + aaa[13] + aaa[14] + aaa[15] + aaa[16] + aaa[17] + aaa[18] + aaa[19]  +   
                               "\n" +  "aresmin=" + aresmin  +  "   " +  "iresnum=" + iresnum          
                            

         l = l+1  // counter for next step


                           
                   
      // initial value initialization needed for all arrays to begin new symbol on next step - shift to upper in program bodyimport android.view.View
   
      
                                     }
      

                                    
     
                
            
                
                
                
            }

              
        }
        } 
   
   
}
