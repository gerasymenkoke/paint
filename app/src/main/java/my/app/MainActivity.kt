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



import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.support.v4.app.ActivityCompat
//import android.support.v4.content.ContextCompat

import java.io.* 
import kotlin.io.*

import java.io.BufferedReader
import java.io.File
import java.io.InputStream


import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import android.content.Context
import android.content.Context.MODE_PRIVATE




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
   // var context=0
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
    private var sh = 0  
    private var shh = 0  
    private var ccc = 0  

    
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
    private var canglennsh_ = Array<Int>(20){0} 
    
    private var canglennpi_ = Array<Int>(100){360} 
    private var rcanglenn_ = Array(20) { Array(300){ Array<Int>(20){360} } }  
 //     private var rcanglenn0_ = Array(20) { Array(60){ Array<Int>(20){360} } }  
    
    private var cin = 0
    private var cinn = 0
    private var dj = 0
    private var dec = 0.0f

    
    
    private var rr =  Array(20) { Array(100){ Array<Int>(20){10} } }  //  array of value for n_etalons(30) for every sample(10pcs)c
    private var rrx =  Array(20) { Array(100){ Array<Int>(20){0} } }  //  array of value for x n_etalons(30) for every sample(10pcs)
    private var rry =  Array(20) { Array(100){ Array<Int>(20){0} } }  //  array of value for y n_etalons(30) for every sample(10pcs)
    private var rangle =  Array(20) { Array(100){ Array<Int>(10){0} } }  // 
    
    private var xdyrr =  Array(20) { Array(100){ Array<Int>(20){0} } }  // 

   
  
    private var  resmin =  Array(20){ Array<Int>(100){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminx =  Array(20){ Array<Int>(100){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    private var  resminy =  Array(20){ Array<Int>(100){0} } // array of values for each etalon(10 pcs)  with min difference(coincedence)
    
    private var  aresmin = " "
    private var iresmin =  Array<Int>(100){10}  // array of index for of values resmin array with min difference(coincedence) 
    
    private var res =    Array<Int>(100){0}  // array of  counters for  each etalon from iresmin array
   
    private var min = 10 // temporary variable min = dir_res[jj][jjj] 
    private var minres = 100000 // temporary variable  resmin[jj] [j] < minres
    private var minres0 = 100000 
    private var max=0   // res [jj] > max res temporary for max coincedence finding
    private var res0 = 0 // res0 = dir_resmin[jj][0] ; res1 = dir_resmin[jj][1]  - temporary for max coincedence finding: res0, res1  - etalon number and index its variant in rr array 
                         // in array with min difference(coincedence), res1 - number  
    private var res00 = 0 
    private var res11 = 0 
    
    private var res1 = 0 // look up
    private var l = 0 // counter for different res0 in resnum array
    private var ll = 0
    private var resnum =   Array<Float>(100){30.0f}  // result each cycle adding  as Int Array
    private var sresnum =  Array<Float>(100){30.0f}  // result each cycle adding  as Int Array
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
    
    private var jdec = Array<Int>(100){0}
    private var lastindex_rcanglenn = Array<Int>(15){0} 
    private var resxy=0
    private var data:String = ""  
    private var btn_numbers_hide = 0


    
       
         companion object {
        var path = Path()
        var paintBrush = Paint()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
        var shift = 0   
        var w=0
        var h=0     
                          }

  private  fun init0() {


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
    rcanglenn_[0][28]=arrayOf<Int>(-162,  -7, -38, -63,  -85,  94, 111,  127,   15,   56,  71,  360,  360,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][29]=arrayOf<Int>(-173, -18, -34, -62,   93, 112, 131,    7,   32,   49,  68,   83,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][30]=arrayOf<Int>( -39, -59, -75,  92,  109, 134, 159,    6,   45,   62,  78,  -93, -121, -149,   -6, -56, 360, 360, 360, 360  ) 
    rcanglenn_[0][31]=arrayOf<Int>( -31, -49, -64,  96,  126, 142, 158,    5,   39,   68, -95, -118, -140,  -12,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][32]=arrayOf<Int>( -37, -54, -76,  96,  120, 148, 167,    9,   30,   46,  65,   84,  -95, -113, -137,-154,  -7, -27, 360, 360  )
    rcanglenn_[0][33]=arrayOf<Int>( -32, -49, -67,  95,  120, 135, 152,  167,    4,   19,  39,   54,   73,  -93, -112,-139,-159,  -5, -22, -38  )
    rcanglenn_[0][34]=arrayOf<Int>( -24, -51, -70,  96,  117, 139,   7,   51,   67,  -91,-107, -122, -165,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][35]=arrayOf<Int>( -65, -82,  93, 109,  126, 148,  11,   51,   71,  -93,-108, -163,   -5,  -44,  -69, 360, 360, 360, 360, 360  )
    rcanglenn_[0][36]=arrayOf<Int>( -83,  91, 107, 141,   13,  73, -92,   -8,  -32,  -59, -77,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][37]=arrayOf<Int>(  51,  19, 174, 104,  -88, -72, -56,  -23, -168, -133,-114,  -92,   86,   49,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][38]=arrayOf<Int>( -51, -66,  94, 113,  129, 151, 170,   23,   47,   66, -92, -107, -147, -162,   -4, -20, -48, -63, 360, 360  )
    rcanglenn_[0][39]=arrayOf<Int>( -65, -82,  93, 109,  126, 148,  11,   51,   71,  -93,-108, -163,   -5,  -44,  -69, 360, 360, 360, 360, 360  )
    rcanglenn_[0][40]=arrayOf<Int>(  81,  45,  30,  10,  164, 138, 123,   99,  -88,  -73, -58,  -37,  -21, -175, -137,-119,-103,  87,  67, 360  )
    rcanglenn_[0][41]=arrayOf<Int>(-158,  -8, -29, -53,  -74,  93, 111,  128,  150,    4,  42,   61,   80,  -92, -112,-131, 360, 360, 360, 360  )
    rcanglenn_[0][42]=arrayOf<Int>(  -9, -28, -49, -81,   93, 115, 145,    5,   53,   72, -93, -140, -158,  -17,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][43]=arrayOf<Int>( -19, -36, -57, -74,   92, 107, 124,  141,  165,    9,  52,   71,  -94, -110, -127,-162,  -6, -21, -40, 360  )
    rcanglenn_[0][44]=arrayOf<Int>(  38,  15, 172, 135,  113, -87, -69,  -47,  -20, -176,-140, -120,  -92,   84,   69, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][45]=arrayOf<Int>(  55,  31, 173, 128,  107, -86, -70,  -55,  -33, -176,-155, -134, -117, -101,   83,  62, 360, 360, 360, 360  )
    rcanglenn_[0][46]=arrayOf<Int>(  63,  45,  10, 161,  145, 128, 105,  -87,  -64,  -49, -29, -170, -146, -119, -100,  80,  65,  50, 360, 360  ) 
    rcanglenn_[0][47]=arrayOf<Int>(-164,  -7, -30, -62,  -82,  93, 111,  130,  160,    7,  33,   53,   77,  -92, -123,-143,-159, 360, 360, 360  ) 
    rcanglenn_[0][48]=arrayOf<Int>(  94, 115, 135, 161,   14,  58,  82,  -92, -119, -143,-171,   -8,  -25,  -43,  -61,  96, 360, 360, 360, 360  )
    rcanglenn_[0][49]=arrayOf<Int>( -57, -72,  94, 112,  132, 150,   7,   49,   69,  -92,-174,  -11,  -58,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][50]=arrayOf<Int>( -29, -55, -72,  93,  117, 144,   8,   45,   71,  -91,-108, -136,   -8,  -40,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][51]=arrayOf<Int>(-155,  -8, -44, -68,   93, 118, 145,    9,   55,   70,  85,  -92, -123,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][52]=arrayOf<Int>(  -8, -28, -46, -63,  -83,  94, 116,  154,   12,   34,  56,   71,  -99, -127, -152, 360, 360, 360, 360, 360  )
    rcanglenn_[0][53]=arrayOf<Int>( -45, -66, -81,  93,  110, 164,   9,   29,   54,   69, -97, -141, -165,   -5,  -25, -69, 360, 360, 360, 360  ) 
    rcanglenn_[0][54]=arrayOf<Int>( -45, -68,  94, 116,  132, 149,  10,   69, -101, -122, 360,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][55]=arrayOf<Int>(   9,  39,  58,  76,  -95,-112,-138, -165,   -6,  -26, -42,  -69,  -86,   94,  114, 360, 360, 360, 360, 360  )
    rcanglenn_[0][56]=arrayOf<Int>( 133, 151, 166,  14,   52,  75, -94, -121, -137, -162,  -7,  -28,  -47,  -67,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][57]=arrayOf<Int>( 144,   6,  39,  58,   73, -96,-136, -151,   -3,  -22, -37,  -74,   93,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][58]=arrayOf<Int>(  30,   4, 169, 154,  134, 118, -87,  -61,  -27, -174,-147, -130, -114,  -99,   85,  60,  36, 360, 360, 360  )
    rcanglenn_[0][59]=arrayOf<Int>( -29, -52, -68,  92,  109, 129, 157,    6,   27,   45,  70,  -93, -108, -132, -151,  -6, -44, -65, 360, 360  )
    rcanglenn_[0][60]=arrayOf<Int>( -34, -60, -75,  96,  124,  38,  68,  -93, -116, -131, -10,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][61]=arrayOf<Int>(-155,-137,-120,-102,   82,  67,  48,   23,  176,  141, 120,  103,  -84,  -64,  -48, 360, 360, 360, 360, 360  )  
    rcanglenn_[0][62]=arrayOf<Int>(  43,  24, 167, 121,  100, -86, -71,  -48,  -23, -172,-134, -107,   83,   67,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][63]=arrayOf<Int>( -50, -70,  93, 111,  130, 146, 167,    3,   21,   45,  70,  -91, -107, -122, -138,-161,  -9, -26, -51, -68  )
    rcanglenn_[0][64]=arrayOf<Int>(   8, 150, 125, 110,  -83, -67, -50,  -34,  -12, -172,-118,   87,   66,   46,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][65]=arrayOf<Int>(-156,  -4, -27, -48,  -70,  94, 111,  130,  152,  172,  16,   37,   59,   77,  -93,-115,-145,  -9, 360, 360  )
    rcanglenn_[0][66]=arrayOf<Int>(  50,  35, 161, 103,  -86,-166,-149, -129, -114,  -97,  84,   69,  360,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][67]=arrayOf<Int>(  66,  45,  30, 171,  114,  95, -84,  -28, -172, -116,  88,   66,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][68]=arrayOf<Int>(  76,  60,  32, 172,  112,  92, -87,  -23, -172, -112, -97,   87,  360,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][69]=arrayOf<Int>(-172, -14, -32, -50,  -65, -84,  96,  113,  139,  159,  12,   34,   60,   78,  -92,-110,-128,-147,  -7, 360  )
    rcanglenn_[0][70]=arrayOf<Int>(  21, 175, 147, 119,  -85, -66, -47,  -24, -175, -157,-137, -100,   82,   65,   47, 360, 360, 360, 360, 360  )
    rcanglenn_[0][71]=arrayOf<Int>(  15, 177, 154, 123,  101, -85, -68,  -53,  -33, -169,-133, -115,   77,   59,   40,  22, 360, 360, 360, 360  )
    rcanglenn_[0][72]=arrayOf<Int>(  25, 172, 150, 129,  110, -85, -60,  -34,   -7, -162,-143, -123, -108,   84,   65,  48,  33, 360, 360, 360  )
    rcanglenn_[0][73]=arrayOf<Int>(  -8, -33, -54, -74,   95, 111, 128,  143,  159,    9,  54,   76,  -92, -111, -133,  -9, -40, 360, 360, 360  )
    rcanglenn_[0][74]=arrayOf<Int>( -96,-163,  -7, -23,  -43, -61, -81,  104,  119,  141, 162,   12,   31,   53,   81, -91, 360, 360, 360, 360  )
    rcanglenn_[0][75]=arrayOf<Int>( 114, 133, 155,  11,   47,  67,  83, -123, -146, -165,  -2,  -23,  -39,  -70,   92, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][76]=arrayOf<Int>(  96, 138, 155, 171,    9,  34,  52,   68,  -96, -146,  -7,  -37,  -57,  -73,   96, 360, 360, 360, 360, 360  )
    rcanglenn_[0][77]=arrayOf<Int>(-102,  85,  70,  46,   26, 170, 130,  113,  -85,  -70, -53,  -29, -168, -118, -101,  87, 360, 360, 360, 360  )  
    rcanglenn_[0][78]=arrayOf<Int>( -76,  99, 140, 155,   11,  46,  61,   81,  -99, -144,-160,   -4,  -21,  360,  360, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][79]=arrayOf<Int>(-170, -25, -50, -66,   94, 121, 141,  166,    6,   44,  59,   74,  -92, -123, -139,-154, 360, 360, 360, 360  ) 
    rcanglenn_[0][80]=arrayOf<Int>( -80,  95, 149,   5,   27,  53,  78,  -93, -114, -133, -16,  -41,  -62,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][81]=arrayOf<Int>(  56,  41,  24, 175,  123, 103, -86,  -51,  -34,  -13,-175, -153, -130,   86,   70,  50, 360, 360, 360, 360  )
    rcanglenn_[0][82]=arrayOf<Int>( -19, -43, -62, -82,   94, 118, 143,  160,    6,   38,  59,   78,  -93, -123, -147,-164, -15, 360, 360, 360  )
    rcanglenn_[0][83]=arrayOf<Int>(-124,-142,  -8, -28,  -48, -70,  92,  111,  133,  148, 166,    5,   33,   51,   71, -94,-130, 360, 360, 360  )
    rcanglenn_[0][84]=arrayOf<Int>(  -2, -24, -43, -63,  -84, 100, 117,  140,  160,    6,  29,   44,   66,  -98, -152,-170,  -8, -23, 360, 360  )
    rcanglenn_[0][85]=arrayOf<Int>(-162,  -5, -20, -42,  -59, -81,  95,  119,  147,  166,   3,   29,   44,   59,   78, -97,-119,-138,-166,  -9  )
    rcanglenn_[0][86]=arrayOf<Int>(  -7, -26, -50, -66,   95, 114, 137,  156,    6,   35,  60,   75,  -96, -165,   -1, -20, -42, 360, 360, 360  )
    rcanglenn_[0][87]=arrayOf<Int>( -33, -49, -64, -83,   96, 113, 139,  156,    8,   25,  55,   73,  -93, -108, -123,-148,-166, -12, 360, 360  ) 
    rcanglenn_[0][88]=arrayOf<Int>(  27, 171, 132, 112,  -85, -59, -31, -172, -155, -130,-113,  -95,   85,   60,   39, 360, 360, 360, 360, 360  )
    rcanglenn_[0][89]=arrayOf<Int>(-100,-153, -11, -39,  -57, -79,  94,  112,  128,  147,   5,   30,   45,   60,   82, -93,-130,-147, 360, 360  )
    rcanglenn_[0][90]=arrayOf<Int>( -30, -52, -71,  92,  111, 133, 156,  171,    7,   23,  44,   71,  -93, -108, -128,-154,-169, 360, 360, 360  )
    rcanglenn_[0][91]=arrayOf<Int>(  47,  26,  11, 166,  150, 132, 117,  102,  -84,  -66, -48,  -25,   -9, -170, -150,-126, -99,  87,  64,  44  )
    rcanglenn_[0][92]=arrayOf<Int>(  64,  49,  31, 170,  124, 106, -83,  -56,  -38,  -17,-167, -141, -121, -103,   86,  67,  38, 360, 360, 360  )
    rcanglenn_[0][93]=arrayOf<Int>(  49,  25, 175, 130,  115,  98, -84,  -67,  -38,  -18,-176, -153, -130, -109,   86,  68,  44,  11, 360, 360  )
    rcanglenn_[0][94]=arrayOf<Int>( -67,  93, 111, 133,  152,  15,  56,  -91, -107, -134,-161,  -11,  -56,  360,  360, 360, 360, 360, 360, 360  )
    rcanglenn_[0][95]=arrayOf<Int>( -11, -36, -56, -78,   95, 111, 137,  155,    4,   21,  36,   53,   70,  -93, -112,-132,-147,-171, 360, 360  )
    rcanglenn_[0][96]=arrayOf<Int>( -10, -32, -59,  92,  109, 128, 151,    3,   21,   45,  63,  -95, -112, -144,   -6, 360, 360, 360, 360, 360  ) 
    rcanglenn_[0][97]=arrayOf<Int>( -18, -33, -49, -64,  -82,  98, 116,  138,  161,    7,  31,   46,   69,  -96, -111,-137,-161,  -9, -24, 360  )
    rcanglenn_[0][98]=arrayOf<Int>( -19, -45, -65,  92,  111, 137, 152,  172,   13,   35,  53,   69,  -97, -140, -166,  -3, -22, 360, 360, 360  )
    rcanglenn_[0][99]=arrayOf<Int>(  -4, -23, -40, -65,   94, 118, 138,  160,    9,   40,  59,   76,  -93, -112, -144,-160,  -6,-169, 360, 360  )
   rcanglenn_[0][100]=arrayOf<Int>(  46,  27, 175, 138,  113,  96, -86,  -64,  -42, -175,-148, -124, -107,   80,   46, 175, 145, 360, 360, 360  )
   rcanglenn_[0][101]=arrayOf<Int>( -12, -44, -59, -77,   93, 126, 145,  161,    5,   35,  50,   69,  -92, -127, -157,  -4, -27, 360, 360, 360  )
   rcanglenn_[0][102]=arrayOf<Int>( -11, -27, -46, -63,  -82,  95, 142,  161,   10,   34,  52,   72,  -98, -118, -142,-158,  -7, -55, -80, 360  )
   rcanglenn_[0][103]=arrayOf<Int>(  29,  13, 169, 150,  135, 120, 105,  -83,  -61,  -42, -21, -177, -155, -110,   87,  55,  71,  39, 154, 110  )
   rcanglenn_[0][104]=arrayOf<Int>(-155, -10, -30, -45,  -71,  96, 121,  141,  161,    9,  34,   56,   71,  -97, -124,-143,-164, -25, 360, 360  )
   rcanglenn_[0][105]=arrayOf<Int>(  17, 173, 131, 111,  -84, -57, -36, -174, -132, -109,  87,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][106]=arrayOf<Int>(-141,-167, -11, -32,  -55, -75,  95,  116,  140,  155, 173,   22,   37,   61,   78, -95,-112,-138,-160, 360  )
   rcanglenn_[0][107]=arrayOf<Int>(-172, -10, -38, -61,   92, 130, 153,  170,    5,   24,  44,   64,  -95, -114, -139,-157,  -3, -36, 360, 360  )
   rcanglenn_[0][108]=arrayOf<Int>(  76,  45,  19, 171,  142, 127, 103,  -87,  -70,  -50, -24,   -8, -169, -150, -125,-110,  86,  70,  44, 360  )
   rcanglenn_[0][109]=arrayOf<Int>( -21, -46, -65,  93,  114, 145, 160,    7,   46,   73, -93, -114, -157,  -10,  -25, -59, 360, 360, 360, 360  )
   rcanglenn_[0][110]=arrayOf<Int>(  35,  15, 175, 152,  124,  99, -85,  -60,  -29, -170,-148, -120, -102,   81,   59,  42, 360, 360, 360, 360  )
   rcanglenn_[0][111]=arrayOf<Int>(  -8, -40, -56, -77,   95, 116, 143,  158,   10,   27,  42,   63,   79,  -94, -120,-146,  -9, -46, 360, 360  )
   rcanglenn_[0][112]=arrayOf<Int>(  53,  35, 169, 145,  111, -86, -67,  -46,  -26,   -7,-165, -141, -116,   85,   68,  44,  10,  38, 360, 360  )
   rcanglenn_[0][113]=arrayOf<Int>(  74,  58,   7, 163,  146, 131, 110,  -84,  -68,  -51, -31,  -13, -171, -132, -116,-101,  85,  70,  26, 165  )
   rcanglenn_[0][114]=arrayOf<Int>( -41, -65, -82, 100,  115, 132, 150,    8,   28,   50,  73,  -99, -122, -151, -170,  -6, 360, 360, 360, 360  )  
   rcanglenn_[0][115]=arrayOf<Int>(-156, -11, -47, -75,   93, 122, 155,  173,    8,   24,  45,   67,  -95, -116, -133,-151, -14, -55, 360, 360  )
   rcanglenn_[0][116]=arrayOf<Int>(  -7, -34, -56, -78,   93, 120, 135,  158,    7,   40,  61,   80, -102, -159,   -4, -43, 360, 360, 360, 360  )
   rcanglenn_[0][117]=arrayOf<Int>(  -4, -37, -53, -72,   95, 117, 151,    3,   40,   60,  83,  -93, -109, -146,   -8, -23, 360, 360, 360, 360  )
   rcanglenn_[0][118]=arrayOf<Int>(  -4, -26, -56, -74,   93, 150, 173,    8,   25,   43,  75,  -93, -124,   -9,  -28, 360, 360, 360, 360, 360  )
   rcanglenn_[0][119]=arrayOf<Int>(-170,  -9, -30, -54,  -74, 101, 121,  157,    8,   27,  45,   71,  -92, -111, -138,-160,  -6, 360, 360, 360  ) 
   rcanglenn_[0][120]=arrayOf<Int>(  -5, -20, -40, -59,  -75,  92, 118,  139,  160,   13,  29,   54,   72,  -94, -110,-137, -12, 360, 360, 360  )
   rcanglenn_[0][121]=arrayOf<Int>( -16, -37, -58, -76,   95, 116, 146,  170,   11,   35,  58,   78,  -97, -120, -141,-166, -14, -31, 360, 360  )
   rcanglenn_[0][122]=arrayOf<Int>(-154,  -8, -42, -63,  100, 123, 156,    2,   50,   71, -91, -107, -133, -166,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][123]=arrayOf<Int>( -83,  92, 118, 146,  168,   8,  32,   52,   67,  -93,-108, -135, -166,   -4,  -21, -36, -52, -80, 360, 360  ) 
   rcanglenn_[0][124]=arrayOf<Int>(  25, 163, 142, 123,  104, -81, -65,  -41,  -23, -176,-156, -136, -118,   86,   59,  41, 360, 360, 360, 360  )
   rcanglenn_[0][125]=arrayOf<Int>( -77,  91, 109, 152,   11,  58,  74,  -93, -119, -143,-170,  -21,  -62,  -77,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][126]=arrayOf<Int>(   9, 173, 138, 111,  -87, -69, -39, -170, -133, -115,  84,   67,   48,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][127]=arrayOf<Int>(-122,-142,-160, -10,  -41, -64, -79,   95,  116,  131, 147,  164,   12,   31,   54,  71,  87, -94,-137, 360  )
   rcanglenn_[0][128]=arrayOf<Int>(  33,   8, 164, 124,  104, -83, -65,  -40,  -18, -168,-112,   87,   71,   42,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][129]=arrayOf<Int>( -69,  99, 116, 136,  164,   9,  27,   46,   63,  -93,-122, -145, -171,  -28,  -47, 360, 360, 360, 360, 360  )
   rcanglenn_[0][130]=arrayOf<Int>(-156,  -5, -22, -40,  -59, -75, 101,  130,  154,    3,  20,   35,   54,   78,  -94,-120,-146, 360, 360, 360  ) 
   rcanglenn_[0][131]=arrayOf<Int>(  33,  15, 167, 137,  116,  98, -80,  -56,  -40,  -11,-171, -152, -135, -106,   85,  70, 360, 360, 360, 360  )
   rcanglenn_[0][132]=arrayOf<Int>( -57, -77,  93, 115,  138, 162,  15,   68,  -92, -117,-134,   -5,  -40,  -63,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][133]=arrayOf<Int>(  33,  11, 172, 152,  130, 102, -80,  -53,  -29,   -6,-167, -142, -115,   83,   65, 360, 360, 360, 360, 360  )
   rcanglenn_[0][134]=arrayOf<Int>(  10, 167, 151, 121,  -85, -69, -45,  -28, -170, -113,  87,   65,   40,  168,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][135]=arrayOf<Int>( -68,  97, 118, 137,  152,   4,  46,   67,  -93, -141,-167,   -9,  -45,  -67,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][136]=arrayOf<Int>( -49, -71,  96, 119,  137, 154, 173,   24,   42,   67, -93, -123, -145, -160,   -2, -22, -44, 360, 360, 360  )
   rcanglenn_[0][137]=arrayOf<Int>( -98,-126,-143,  -9,  -48, -65,  94,  116,  143,  170,   9,   51,   67,  -94, -111, 360, 360, 360, 360, 360  )  
   rcanglenn_[0][138]=arrayOf<Int>( -18, -45, -60,  95,  110, 125, 153,    5,   21,   50,  69,   87, -101, -157,   -5, -20, 360, 360, 360, 360  ) 
   rcanglenn_[0][139]=arrayOf<Int>( -74,  92, 109, 131,  148,  10,  56,   76,  -92, -117,-135, -151,   -5,  -81,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][140]=arrayOf<Int>(-172, -11, -37, -68,   93, 120, 137,  153,   11,   57,  76,  -96, -128,  -10,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][141]=arrayOf<Int>( -63, -80,  96, 133,  157,   4,  40,   61,  -91, -126,-151, -168,   -5,  -47,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][142]=arrayOf<Int>(  56,  34,  13, 171,  155, 130, 115,   95,  -82,  -56, -40,  -16, -175, -158, -137,-112,  85,  62, 360, 360  )
   rcanglenn_[0][143]=arrayOf<Int>( -58, -74,  92, 119,   28,  49,  72,  -91, -113,   -4, -58,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][144]=arrayOf<Int>( -62, -77,  93, 120,  141,  13,  53,   72,  -93, -142,  -8,  -40,  -71,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][145]=arrayOf<Int>(  38,  22, 174, 150,  126, -73, -54,  -39,  -23, -176,-156, -128,   82,   64,   44,  27, 360, 360, 360, 360  )
   rcanglenn_[0][146]=arrayOf<Int>(  38,  12, 176, 139, 118, 102,  -81,  -55,  -37, -167,-144, -127,   74,   49,   29, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][147]=arrayOf<Int>(  16, 175, 154, 114, -85, -70,  -45,  -27, -158, -118,  85,   67,   40,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][148]=arrayOf<Int>(  67,  21, 175, 153, 135, 116,  -79,  -62,  -46,  -30, -13, -173, -126, -105,   85,  69, 360, 360, 360, 360  )
   rcanglenn_[0][149]=arrayOf<Int>(  11, 174, 155, 110, -87, -69,  -46,  -24, -174, -111,  87,   72,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][150]=arrayOf<Int>(  65,  43,   8, 165, 133, 107,  -86,  -69,  -54,  -39, -19, -173, -146, -111,   85,  62,  42, 360, 360, 360  )
   rcanglenn_[0][151]=arrayOf<Int>(   6, 142, 105, -84, -64, -41,  -16, -175, -158, -140,-117,   79,   58,   35,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][152]=arrayOf<Int>(  20, 172, 151, 128,  98, -84,  -65,  -45,  -28, -165,-130, -109,   85,   70,   49,  33,  53, 360, 360, 360  )
   rcanglenn_[0][153]=arrayOf<Int>(  17, 171, 145, 103, -86, -69,  -53,  -37, -172, -129,-114,   84,   65,   47,   14, 161, 360, 360, 360, 360  )
   rcanglenn_[0][154]=arrayOf<Int>( -40, -62, -82,  95, 114, 129,  154,    8,   44,   60, -95, -128, -145, -160,   -7, -28, 360, 360, 360, 360  )
   rcanglenn_[0][155]=arrayOf<Int>(  40, 153, 111, -86, -62, -17, -173, -153, -136, -114,  82,   51,   33,   52,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][156]=arrayOf<Int>(-134,-152, -13, -46, -67,  93,  112,  138,  173,    9,  39,   56, -100, -118, -143,  -8, 360, 360, 360, 360  )
   rcanglenn_[0][157]=arrayOf<Int>(  16, 171, 150, 129, 109, -75,  -59,  -35,  -10, -156,-140, -106,   77,   61,   46, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][158]=arrayOf<Int>(  45,  25, 172, 112, -86, -66,  -48,  -26,   -6, -155,  85,   66,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][159]=arrayOf<Int>(-171, -31, -63, -83,  97, 126,  152,    4,   30,   53,  73,  -92, -110, -126, -159,  -4, -57, 360, 360, 360  )
   rcanglenn_[0][160]=arrayOf<Int>(  48,  17, 161, 123, 102, -86,  -68,  -45,  -22, -172,-133, -111,   84,  360,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][161]=arrayOf<Int>( -65, -84,  92, 111, 137, 152,  172,    9,   24,   44,  61,   77,  -91, -117, -136,-163,  -6, -36, -67, 360  )
   rcanglenn_[0][162]=arrayOf<Int>( -44, -63,  94, 115, 143, 159,    5,   33,   59,   83, -93, -129, -152,  -13,  -36, 360, 360, 360, 360, 360  )
   rcanglenn_[0][163]=arrayOf<Int>(  39,  10, 172, 143, 104, -79,  -59,  -37,  -22, -176,-118,   86,   67,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][164]=arrayOf<Int>( -56, -78,  94, 111, 126, 155,    6,   39,   57,   75,-105, -158,   -2,  -19,  -38, -58, 360, 360, 360, 360  )
   rcanglenn_[0][165]=arrayOf<Int>(-164,  -7, -33, -57, -80,  97,  117,  137,  158,    7,  25,   49,   67, -102, -151, 360, 360, 360, 360, 360  )
   rcanglenn_[0][166]=arrayOf<Int>(  33,  17, 170, 118, -87, -66,  -47,  -30,   -9, -171,-154,   85,   69,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][167]=arrayOf<Int>(  45,  20, 172, 141, 121,  99,  -86,  -66,  -42,  -15,-172, -149, -117, -100,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][168]=arrayOf<Int>(  70,  52,  29,  12, 170, 132,  115,   97,  -85,  -42, -19,   -4, -146, -124, -106,  84, 360, 360, 360, 360  )
   rcanglenn_[0][169]=arrayOf<Int>(  27, 177, 143, 125, 108, -83,  -40,  -21, -174, -149,-131, -108,   84,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][170]=arrayOf<Int>(-168,  -3, -24, -53, -72,  93,  124,  144,  164,    5,  25,   41,   61,   82,  -93,-114, -19, 360, 360, 360  )
   rcanglenn_[0][171]=arrayOf<Int>(  46,  24, 173, 140, 111, -84,  -64,  -43,  -16, -171,-152, -131, -113,  -97,   85,  70,  52,  25, 360, 360  )
   rcanglenn_[0][172]=arrayOf<Int>( -24, -41, -59, -78,  96, 128,  154,  173,   26,   62,  79,  -94, -115, -143,   -4, -27, -49, 360, 360, 360  )
   rcanglenn_[0][173]=arrayOf<Int>(  -7, -28, -47, -62, -83,  92,  122,  153,    3,   35,  61,   81,  -97, -150,   -5, -21, 360, 360, 360, 360  )
   rcanglenn_[0][174]=arrayOf<Int>(  -4, -44, -61, -79,  97, 119,  153,  172,    8,   23,  55,   75,  -94, -129, -163,  -6, -23, -42, -67, 360  )
   rcanglenn_[0][175]=arrayOf<Int>( -76,  94, 122, 152,  12,  64,   82,  -92, -164,   -7, -27,  -68,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][176]=arrayOf<Int>( -72,  93, 116, 165,  14,  32,   51,   72,  -91, -106,-126, -160,   -7,  -42,  -58, -78,  98, 360, 360, 360  ) 
   rcanglenn_[0][177]=arrayOf<Int>( -76,  93, 125, 143, 165,   3,   36,   61,   78, -103,-155,   -8,  -32,  -67,  -84,  92, 360, 360, 360, 360  )
   rcanglenn_[0][178]=arrayOf<Int>(  97, 116, 133, 170,   9,  30,   53,   70,  -95, -116,-140, -157,   -5,  -28,  -46, -72, 360, 360, 360, 360  )
   rcanglenn_[0][179]=arrayOf<Int>(  35,  13, 176, 161, 130, -77,  -60,  -42,  -20, -171,-141,   73,   45,  162,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][180]=arrayOf<Int>(-111,-140, -15, -44, -60, -82,   97,  121,  140,  157, 174,   22,   58,   76,  -93,-118, 360, 360, 360, 360  )
   rcanglenn_[0][181]=arrayOf<Int>( -70,  93, 120, 154,  13,  37,   55,   74,  -96, -118,-137,   -2,  -24,  -41,  -59, 360, 360, 360, 360, 360  )
   rcanglenn_[0][182]=arrayOf<Int>( -68, -85,  94, 118, 135, 159,   22,   47,   63,   80, -93, -152,  -10,  -61,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][183]=arrayOf<Int>(-157, -13, -34, -52, -74,  95,  115,  151,  174,   12,  41,   63,   81,  -92, -112,-158,  -8, 360, 360, 360  ) 
   rcanglenn_[0][184]=arrayOf<Int>( 103, 118, 140,  12,  50,  77,  -94, -126, -152,   -9, -40,  -59,  -81,   95,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][185]=arrayOf<Int>(  31,   9, 164, 134, 103, -85,  -61,  -44,  -29,   -5,-153, -119, -101,   81,   60,  44,  25, 360, 360, 360  )
   rcanglenn_[0][186]=arrayOf<Int>(  93, 110, 125, 146,   5,  21,   43,   60,   75,  -94,-115, -132, -148, -168,   -3, -28, -44, -61, -76, 360  )
   rcanglenn_[0][187]=arrayOf<Int>(  57,  41,  19, 169, 133, 113,  -85,  -58,  -30, -169,-127, -112,  -96,   86,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][188]=arrayOf<Int>(  -9, -24, -45, -63, -82,  94,  119,  146,  166,    8,  26,   48,   70,  -97, -115,-132,-150,  -6, 360, 360  )
   rcanglenn_[0][189]=arrayOf<Int>(  -2, -19, -42, -59, -83,  95,  113,  149,  165,    8,  25,   51,   66, -107, -159,-177, 360, 360, 360, 360  )
   rcanglenn_[0][190]=arrayOf<Int>(  41,  20, 176, 150, 130, 114,  -86,  -67,  -49,  -28,-176, -152, -106,   88,   70,  43,  28, 360, 360, 360  ) 
   rcanglenn_[0][191]=arrayOf<Int>(  55,  40,  19, 173, 146, 122,  106,  -84,  -61,  -45, -30,  -12, -168, -110,   84,  67,  50, 360, 360, 360  )
   rcanglenn_[0][192]=arrayOf<Int>(  -2, -47, -65, -84,  92, 112,  143,  161,    3,   21,  40,   58,   80,  -98, -131,-150, 360, 360, 360, 360  )
   rcanglenn_[0][193]=arrayOf<Int>( -47, -71,  97, 123, 146, 168,   12,   66,  -93, -132,-155, -174,  -10,  360,  360, 360, 360, 360, 360, 360  )   
   rcanglenn_[0][194]=arrayOf<Int>(  94, -87, -71, -47,-174,-109,   86,   68,   49,  360, 360,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][195]=arrayOf<Int>( -83, -66, -51, -36,-173,-107,   86,   64,   37,   13, 360,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][196]=arrayOf<Int>(-154,  -7, -61,  92, 109, 125,  156,   44,   59,   78, -91, -117,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][197]=arrayOf<Int>( -17, -32, -52, -72,  94, 115,  133,  148,    8,   60,  75,  -92, -108, -144,  -11, -58, 360, 360, 360, 360  )
   rcanglenn_[0][198]=arrayOf<Int>( -16, -41, -57, -75,  98, 117,  136,  164,    5,   20,  37,   54,   70,  -92, -116,-135,-150,-166,  -4, 360  )
   rcanglenn_[0][199]=arrayOf<Int>( -14, -40, -62, -79,  94, 113,  135,  159,    3,   50,  74,  -93, -113, -138, -157,  -8, -40, 360, 360, 360  )
   rcanglenn_[0][200]=arrayOf<Int>( -77,  93,  12,  27,  51,  73,  -92, -126, -148,  -12, -52,  -72,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][201]=arrayOf<Int>(  91, 107, 167,  16,  58,  75,  -93, -111, -130,  -16, -61,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][202]=arrayOf<Int>( -14, -39, -54, -71,  98, 120,  137,  152,   16,   68, -91, -119, -134,  360,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][203]=arrayOf<Int>(-170,  -8, -50, -69,  96, 127,  148,  166,    3,   26,  43,   64,   81,  -93, -110,-138,-157,  -9, -26, 360  )
   rcanglenn_[0][204]=arrayOf<Int>(  53,  15, 177, 156, -76, -57,  -40,  -23,   -6, -171,-153,   85,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][205]=arrayOf<Int>( -76,  92, 108, 131,  73, -94, -131, -155,  -22,  -51, 360,  360,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][206]=arrayOf<Int>(  22, 168, -86, -70, -26,-173, -153, -126, -109,  -94,  84,   68,   53,   31,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][207]=arrayOf<Int>(  32,  11, 175, 160, 136, 110,  -82,  -58,  -39,  -24,  -7, -169, -135,   75,   50,  31, 360, 360, 360, 360  )
   rcanglenn_[0][208]=arrayOf<Int>(  28,  13, 169, 139, 124, 105,  -83,  -26,  -10, -160,-133,   83,   63,   46,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][209]=arrayOf<Int>( -71,  96, 113, 130, 152, 172,   30,   45,   62,   81, -92, -122, -160,   -7,  -47, -65, 360, 360, 360, 360  )
   rcanglenn_[0][210]=arrayOf<Int>(  -5, -25, -44, -64, -82,  97,  124,  155,   12,   31,  47,   62,   83, -111, -156,  -3, -43, 360, 360, 360  )
   rcanglenn_[0][211]=arrayOf<Int>(  76,  57,  32, 160, 119,  99,  -84,  -61,  -37,  -16,-172, -118,  -95,   87,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][212]=arrayOf<Int>( -13, -40, -60, -78,  99, 125,  146,  162,    8,   63,  80,  -94, -119, -143, -158,  -2, 360, 360, 360, 360  ) 
   rcanglenn_[0][213]=arrayOf<Int>(-151,-170, -16, -42, -67,  96,  133,  149,  165,    3,  55,   73,  -91, -115, -131, 360, 360, 360, 360, 360  )
   rcanglenn_[0][214]=arrayOf<Int>(  58,  37, 165, 115, 100, -84,  -65,  -36, -172, -129,-111,  -94,   86,   69,   51, 360, 360, 360, 360, 360  )
   rcanglenn_[0][215]=arrayOf<Int>( -41, -58, -82,  97, 114, 130,  147,  170,    7,   24,  47,   82,  -96, -113, -138,-157,  -7, -40, -61, 360  ) 
   rcanglenn_[0][216]=arrayOf<Int>(  75,  57,  29,  14, 173, 156,  134,  111,  -80,  -61, -46,  -27,  -11, -172, -121,  80,  61, 360, 360, 360  ) 
   rcanglenn_[0][217]=arrayOf<Int>(  13, 171, 156, 138, 114, -85,  -62,  -36, -172, -131,-111,   83,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][218]=arrayOf<Int>(  -5, -29, -45, -76,  96, 121,  152,  168,   11,   56,  74,  -96, -111, -127, -146,-163, 360, 360, 360, 360  ) 
   rcanglenn_[0][219]=arrayOf<Int>(-108,  82,  63,  25, 164, 124,  104,  -87,  -64,  -45, -25, -170, -128, -104,   82, 360, 360, 360, 360, 360  )
   rcanglenn_[0][220]=arrayOf<Int>(  55,  40,  19, 173, 146, 122,  106,  -84,  -61,  -45, -30,  -12, -168, -110,   84,  67,  50, 360, 360, 360  )
   rcanglenn_[0][221]=arrayOf<Int>(  56,  71, -98, 144,-165, -10,  -44,  -69,   92,  117, 140,   12,   48,   73,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][222]=arrayOf<Int>( 100, -86, -40, -19,-176,-155, -132, -115,   87,   59,  43,   27,  167,  109,   93, 360, 360, 360, 360, 360  )
   rcanglenn_[0][223]=arrayOf<Int>( -40, -24,  -6,-162,-128,-106,   85,   63,   37,   15, 175,  141,  110,  -83,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][224]=arrayOf<Int>(-164,  -3, -20, -48, -65,  96,  128,  144,  162,    3,  48,   66,  -92,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][225]=arrayOf<Int>(  72,  45,  19, 170, 147, 125,  109,  -81,  -47,  -29,-175, -138, -115, -100,   85,  57, 360, 360, 360, 360  )
   rcanglenn_[0][226]=arrayOf<Int>( 115, -85, -69, -54, -32, -17, -176, -154, -136, -118, -97,   83,   56,   36,   15, 171, 142, 360, 360, 360  )
   rcanglenn_[0][227]=arrayOf<Int>( 119, 103, -86, -62, -45, -30, -171, -119, -100,   86,  60,   43,  360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][228]=arrayOf<Int>(  97, -84, -63, -43, -21,-174, -120, -105,   87,   60,  42,   12,  170,  134,  113, 360, 360, 360, 360, 360  )
   rcanglenn_[0][229]=arrayOf<Int>( -39, -22,-173,-151,-131,-114,   86,   56,   26,   9,  173,  134,  108,  -85,  -68, -52, 360, 360, 360, 360  )
   rcanglenn_[0][230]=arrayOf<Int>(  70, -94,-125,-150,-173, -13,  -48,  -65,  -81,  95,  111,  127,  149,   17,   63, 360, 360, 360, 360, 360  )
   rcanglenn_[0][231]=arrayOf<Int>(-130,-107,  87,  44,  23, 177,  157,  138,  123, 101,  -83,  -64,  -44,  -26, -168, 360, 360, 360, 360, 360  )
   rcanglenn_[0][232]=arrayOf<Int>( 126, 110, -84, -68, -50, -31,  -11, -175, -130, -96,   80,   53,   36,  173,  142, 360, 360, 360, 360, 360  )
   rcanglenn_[0][233]=arrayOf<Int>(-140,-155,-173, -12, -38, -55,  -76,   94,  116, 140,  160,    3,   39,   59,   79, -91,-126, 360, 360, 360  )
   rcanglenn_[0][234]=arrayOf<Int>(  49,  68, -96,-128,-148,  -3,  -30,  -46,  -68,  94,  133,   157,   4,   31,   48, 360, 360, 360, 360, 360  )
   rcanglenn_[0][235]=arrayOf<Int>( -62, -46, -24,-175,-142,-121,  -98,   86,   59,  41,   23,     6, 171,  152,  130, 112, -83, -59, -39, 360  )
   rcanglenn_[0][236]=arrayOf<Int>(-117,  85,  68,  40,  22, 175,  153,  133,  102, -83,  -67,   -39, -12, -174, -138, 360, 360, 360, 360, 360  )
   rcanglenn_[0][237]=arrayOf<Int>( 132, 149,  10,  41,  58,  75,  -95, -124, -153,  -4,  -30,   -46, -69,  360,  360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[0][238]=arrayOf<Int>(-146,-165,  -5, -42, -60, -77,   93,  127,  149,   6,   31,    50,  67,  -95, -149,-165, 360, 360, 360, 360  )
   rcanglenn_[0][239]=arrayOf<Int>( 103, -84, -68, -52, -28,  -7, -170, -151, -113, -97,   83,    54,  25,  176,  152, 109, -86, 360, 360, 360  )
   rcanglenn_[0][240]=arrayOf<Int>(   -8, -49, -79,  94, 111, 132,  156,   13,   52,  68,  -91,  -106,-131, -168,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][241]=arrayOf<Int>(  -17,-175,-148,-129,-107,  85,   54,   24,    9, 158,  142,   116, 101,  -87,  -70, 360, 360, 360, 360, 360  )
   rcanglenn_[0][242]=arrayOf<Int>(  115, 141, 163,   9,  34,  53,   71,   86,  -99,-114, -130,  -147,-168,   -5,  -45, -62, -77,  91, 360, 360  ) 
   rcanglenn_[0][243]=arrayOf<Int>(  -22,-175,-143,-118,  85,  66,   45,   21,  172, 144,  124,   -81, -54,  -36,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][244]=arrayOf<Int>(   15,  35,  53,  68, -93,-113, -142, -161,   -7, -30,  -54,   -75,  91,  128,  146, 161, 360, 360, 360, 360  )
   rcanglenn_[0][245]=arrayOf<Int>(   61,  77, -94,-116,-138,-161,   -8,  -33,  -57, -72,   94,   114, 137,    8,   49,  65, 360, 360, 360, 360  )
   rcanglenn_[0][246]=arrayOf<Int>(   54,  70,  87, -96,-121,-160,   -9,  -30,  -49, -72,   93,   108, 124,  142,   10,  47,  63, 360, 360, 360  )
   rcanglenn_[0][247]=arrayOf<Int>(  -27,  -8,-171,-155,-134,-110,   79,   63,   37,  20,  172,   156, 140,  125,  105, -85, -65, 360, 360, 360  )
   rcanglenn_[0][248]=arrayOf<Int>(   63,  82,-107,-137,-164, -10,  -25,  -47,  -63, -85,  102,   138, 155,  170,    7,  45,  67, 360, 360, 360  )
   rcanglenn_[0][249]=arrayOf<Int>(  -82, -66, -40, -18,-168,-151, -133, -110,   86,  68,   37,    18, 177,  150,  131, -86, 360, 360, 360, 360  )
   rcanglenn_[0][250]=arrayOf<Int>(  -63, -43, -28,  -8,-154,-124, -109,   82,   67,  51,   28,    10, 172,  152,  132, 105, -75, -53, 360, 360  )
   rcanglenn_[0][251]=arrayOf<Int>( -176,-152,-130,-112,  85,  60,   38,   20,  174, 153,  135,   119,  97,  -82,  -62, -45, 360, 360, 360, 360  )
   rcanglenn_[0][252]=arrayOf<Int>(   53,  74, -95,-118,-146,-162,   -3,  -24,  -45, -69,   93,   131,  10,   33,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][253]=arrayOf<Int>(  -49, -64, -81,  97, 121, 140,  166,   10,   34,  49,   67,   -94,-127, -149,   -9, -25, -41, 360, 360, 360  )
   rcanglenn_[0][254]=arrayOf<Int>(   48, 123, 104, -61, -25,-134, -106,  360,  360,  360, 360,   360, 360,  360,  360, 360, 360, 360, 360, 360  )
   rcanglenn_[0][255]=arrayOf<Int>(  -17, -34, -55, -74,  95, 119,  145,  161,    6,   54,  69,   -92,-166,   -8,  -23, -52, 360, 360, 360, 360  ) 
  
  }
    


 private  fun init14() {
    
    // Filling sample array
// 1
                       

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
   rcanglenn_[1][16]=arrayOf<Int>(  49,  -71,  -87,  94, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][17]=arrayOf<Int>(  43,   60, -106, -67, -86, -16,   1, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
   rcanglenn_[1][18]=arrayOf<Int>(  30,   51,   68, -84, -61, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][19]=arrayOf<Int>(  36,   55,   70, -87, -44, 177, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][20]=arrayOf<Int>(  53,  -80,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][21]=arrayOf<Int>(  45,   63,  -87, -31,  12, 176, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][22]=arrayOf<Int>(  60,  -80,   91,  -9, 174, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][23]=arrayOf<Int>(  36,   53,   73, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][24]=arrayOf<Int>(  58,  -85,   92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][25]=arrayOf<Int>(  32,   48,   66, -81,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][26]=arrayOf<Int>(  62,   77,  -36,   5, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][27]=arrayOf<Int>(  37,   59,  -78,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][28]=arrayOf<Int>(  41,   57,  -77,  93, -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
   rcanglenn_[1][29]=arrayOf<Int>(  51,   67,  -84, 175, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][30]=arrayOf<Int>(  50,   69,   84,-162,   3, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][31]=arrayOf<Int>(  41,   58,   74, -95, -72,  91,  -4, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][32]=arrayOf<Int>(  49,   73,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][33]=arrayOf<Int>(  63,   83,   91, -28,   4, 166, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][34]=arrayOf<Int>(  66,   47,   63,  81, -64,  93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][35]=arrayOf<Int>(  45,   62, -132, -67, -82,  91, -23, 177, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
   rcanglenn_[1][36]=arrayOf<Int>(  55,   74,   -4, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][37]=arrayOf<Int>(  63,  -79,  -11, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][38]=arrayOf<Int>(  34,   53,  -75, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
   rcanglenn_[1][39]=arrayOf<Int>(  55,   36,   55,  72, -88, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][40]=arrayOf<Int>( 176, -116,   52,  75,  94, -84, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][41]=arrayOf<Int>(  51,   76,  -36, -75,  90,-173,   8, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][42]=arrayOf<Int>(  52,  -85,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   rcanglenn_[1][43]=arrayOf<Int>(  57,  -82,  -49, 178, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
   rcanglenn_[1][44]=arrayOf<Int>(  60,  -75,   91,-131,-173, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
   
   
     
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
 rcanglenn_[2][14]=arrayOf<Int>(-137, -105,   75,  19, 165, 136, 117,   97,  -80, -60, -45,   7, 159, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][15]=arrayOf<Int>(-127, -110,   82,  11, 171, 116, -82,  -61,  -46, -25,-170,  73, 174, 152, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][16]=arrayOf<Int>(  65,   48,   17, 169, 103, -87, -68,  -47,  -26,-172,  59, 169, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][17]=arrayOf<Int>(  50,   32,  171, 124,  97, -85, -65,  -45,  -29, -13 ,-173,  26, 175, 150, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][18]=arrayOf<Int>(  53,   32,  172, 141, 105, -81, -60,  -42, -167, 152,  20, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][19]=arrayOf<Int>(  75,   42,   22, 168, 121, -86, -69,  -51, -173,  61, 174, 158, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][20]=arrayOf<Int>(  52,   22,  169, 122, -86, -71, -51,  -24, -170, -104,  35, 174, 158, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][21]=arrayOf<Int>(  65,   47,   26, 174, 121, -87, -67,  -52,  -37,   -6,  64, 175, 159,  43, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][22]=arrayOf<Int>(  32,   16,  155, -86, -70, -50, -24, -152,   78,   14, 169, 151, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][23]=arrayOf<Int>(  53,   30,  169, 120, -81, -59, -43,  173,  360,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][24]=arrayOf<Int>(  69,   42,   10, 137, 119,  99, -80,  -63,  -44,   -16,-160,  71, 168, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][25]=arrayOf<Int>(  65,   46,  174, 108, -83, -65, -40, -164, -114,   171, 150, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][26]=arrayOf<Int>(  58,   35,  168, 138, 103, -86, -65,  -43,  -27,  -169,-134,  63,  13, 171, 155, 137, 164, 360, 360, 360  )
 rcanglenn_[2][27]=arrayOf<Int>(  47,  169,  130, -81, -65, -49,-160,   77,  174,   158, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][28]=arrayOf<Int>(  59,   35,   12, 142, 109, -86, -64,  -46,  -24,  -174,-107,  80, 167, 147, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][29]=arrayOf<Int>(  81,   63,   37, 165, 113, -84, -67,  -51,  171, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][30]=arrayOf<Int>(-101,   74,   28, 161, 112, -85, -65,  -45, -170,  78, 168,  57, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][31]=arrayOf<Int>(-108,   78,   52,  28, 170, 145, 118,   99,  -85, -68 ,-53, -37, -14,  44,  29, 170, 360, 360, 360, 360  )
 rcanglenn_[2][32]=arrayOf<Int>(-168, -139,   76,  50,  26, 174, 150,  116,   96, -85, -70, -55, -26,-175, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][33]=arrayOf<Int>(-134, -104,   86,  14, 171, 151, 127,   97,  -84, -66 ,-43, -27,-169,  69, 176, 153, 360, 360, 360, 360  )
 rcanglenn_[2][34]=arrayOf<Int>(  63,   42,  163, 121, 101, -86, -69,  -46,  -29, -12 , 47,  12, 172, 157, 142,  11, 360, 360, 360, 360  )
 rcanglenn_[2][35]=arrayOf<Int>(-175,   71,   51,  14, 167, 115, -84,  -63,  -46, -169 ,33, 173, 156, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][36]=arrayOf<Int>(-104,   83,   59,  27, 169, 142, 105,  -78,  -60,  -41,-26,-174,  15, 174, 158, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][37]=arrayOf<Int>(-123,   66,   37,  22, 172, 119,  99,  -87,  -71,  -55 ,-40, -20, -165,  75, 170, 149, 360, 360, 360, 360  )
 rcanglenn_[2][38]=arrayOf<Int>(  41,   20,  172, 141, 101, -82, -65,  -49, -170, -125 ,  7, 170,  136, 158,  14, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][39]=arrayOf<Int>(  29,  173,  115,  94, -83, -65, -42, -169,   73,    8, 168, 150,   40, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][40]=arrayOf<Int>(  27,  170,  -76, -55, 142, 166, 360,  360,  360,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )   
 rcanglenn_[2][41]=arrayOf<Int>(  50,   25,  173, 100, -87, -72, -57,  -37,  -19,    56,  38, 170, 154, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][42]=arrayOf<Int>(  61,   27,  166, 104, -81, -64, -47,  -30,  174, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][43]=arrayOf<Int>(  76,   56,   17, 163, 124, 108, -86,  -67, -48,  -24,-166, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][44]=arrayOf<Int>(  79,   62,   29, 173, 139, 114,  96,  -79, -60,  -40, -23,-170, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][45]=arrayOf<Int>(  25,  175,  151, -81, -62, -44, -29,   60,  13,  173, 155, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][46]=arrayOf<Int>(  66,   50,   27, 173, 135, -82, -62,  -47,-171,  156, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][47]=arrayOf<Int>(  47,   18,  176, 148, -86, -68, -51,   -9,  71,  174, 148,   9, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][48]=arrayOf<Int>(  20,  154,  -84, -68, -51,-149,  67,  170, 151,  169, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][49]=arrayOf<Int>(  49,   22,  169, 122, -83, -60, -39, -171, 156,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][50]=arrayOf<Int>(  -7, -171, -115,  83,  45,  30,  14,  168, 122,  103, -86, -70, -49, -31, -16,-170, 155, 360, 360, 360  )
 rcanglenn_[2][51]=arrayOf<Int>(  66,   47,   23, 170, 120, -86, -68,  -51, -35,  -20, 176, 158, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][52]=arrayOf<Int>( -97,   82,   51, 168, 111, -84, -67,  -52, -26, -170, 153, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][53]=arrayOf<Int>(  51,   32,  173, 152, 129, 112, -78,  -58, -41,  -58, 178, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][54]=arrayOf<Int>(  77,   55,   20, 176, 117,  98, -83,  -57, -39,  176, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][55]=arrayOf<Int>(  54,   36,   12, 169, 126, 102, -84,  -69, -47,  -27, -12,-169,-119,  55, 174, 159, 360, 360, 360, 360  )
 rcanglenn_[2][56]=arrayOf<Int>(  14,  166,  -77, -62, -47, -31, 177,   14,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][57]=arrayOf<Int>(-161,   66,  173, -85, -68, -53, -25,   67,  173, 158, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[2][58]=arrayOf<Int>(  31,  163,  102, -85, -70, -45, -30, -168,   14, 169, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][59]=arrayOf<Int>(  40,   22,  172, 103, -83, -61, -46,  -30, -172,  12, 174, 158,  22, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[2][60]=arrayOf<Int>(  24,  167,  123, 105, -77, -58, -43,   24,  170, 151, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[2][61]=arrayOf<Int>(  47,   28,    8, 170, 119,  98, -84,  -69,  -53, -37, -17,  66,   6, 166, 360, 360, 360, 360, 360, 360  )

 

 
      
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
  rcanglenn_[3][16]=arrayOf<Int>( 177, 159, 134, -69,  -42,  -18, 172, 147, -77, -34, -15, 360,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][17]=arrayOf<Int>(  37,  11, 172, -80,  -57,  157, 173, 156, -62, -36, 360, 360,  360,  360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][18]=arrayOf<Int>(  53,  18, 172, -78,  -63,  -48,  10, 174, 154, -83, -53, -26,   -9,  360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][19]=arrayOf<Int>(  72,  36, 173, 102,  -72,  -50, 170, 154, 135, -78, -38, -14,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][20]=arrayOf<Int>(  21, 174, -77, -48,  -33,   15, 176, 111,  96, -81, -60, -45,  -30,  -12, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][21]=arrayOf<Int>(  31, 169, -80, -58,  -43,   11, 175, -82, -60, -42, -21,  -5,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][22]=arrayOf<Int>(   4, 147, -45, 165,  137,  -80, -32, -16, 360, 360, 360, 360,  360,  360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][23]=arrayOf<Int>(  31,  12, 169, 128,  -79,  -35, -19, -50, 172, 154, 139, -77,  -40,  -22, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][24]=arrayOf<Int>(  72,  53,  20,   5,  170,  110, -79, -64, -48,   9, 172, 153,  114,  -78, -33, -13, 360, 360, 360, 360  ) 
  rcanglenn_[3][25]=arrayOf<Int>(  44,  25,   8, 171,  147,  115, -66, -42, -27, 171, 149, 119,  -65,  -44, -26, 360, 360, 360, 360, 360  )  
  rcanglenn_[3][26]=arrayOf<Int>(  43,  18, 173, 148,  -72,  -53, -38, 163, 146, -71, -51, -32,  -15,   70, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][27]=arrayOf<Int>( 177,  14, -50, -30,  177,  125, -77, -42, -18, 360, 360, 360,  360,  360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[3][28]=arrayOf<Int>(  50,  25, 168, 123,   98,  -87, -72, -52, -32,-168,-113,  82,   28,   13, 172, 145, 124, -80, -30, -11  ) 
  rcanglenn_[3][29]=arrayOf<Int>(-106,  78,  47,  19,  166,  143, 112, -83, -52, 130, 170, 155,  -81,  -43, -26, 360, 360, 360, 360, 360  ) 
  rcanglenn_[3][30]=arrayOf<Int>(  26,   9, -76, -54,  117,  176, 158, -73, -48, -32, -16, 360,  360,  360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][31]=arrayOf<Int>(   2, 118, -42,   6,  156,  137, -76, -52, -36,-176,-152, 360,  360,  360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[3][32]=arrayOf<Int>(  52,  36,  12, 168,  113,  -81, -62, -44, -24,-174,  47,   7,  153,  -80, -36, -13, 360, 360, 360, 360  ) 
  rcanglenn_[3][33]=arrayOf<Int>(  48,  22, 168, 113,  -82,  -57, -37, 170, 152, 132, -72, -43,  -25, -176,-155, 360, 360, 360, 360, 360  )
  rcanglenn_[3][34]=arrayOf<Int>(  69,  53,  18, 162,  135,  -63, -46,  24, 177, -73, -48, -26,  360,  360, 360, 360, 360, 360, 360, 360  ) 


  
      
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
  rcanglenn_[4][11]=arrayOf<Int>( -72,   128,   4,   75,  93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][12]=arrayOf<Int>( -86,   168, -114, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[4][13]=arrayOf<Int>( -70,   -53,  158,   8,  51, -79, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][14]=arrayOf<Int>( -72,     7, -107, -72, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][15]=arrayOf<Int>( -61,     6,   50, -83, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][16]=arrayOf<Int>( -79,     7,   73, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][17]=arrayOf<Int>( -75,   -57,  -74, 114,   3,  72, -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][18]=arrayOf<Int>( -59,   126,  172,-116, -70, -86, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[4][19]=arrayOf<Int>( -78,   -62,  116, 176,  87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][20]=arrayOf<Int>( -77,   102,    9, 170, 151, -93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][21]=arrayOf<Int>( -82,    92,  -88,   5, 170,-118, -78,  55, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][22]=arrayOf<Int>( -74,   -58,  134,  12, 174,-121, -80, 138, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][23]=arrayOf<Int>( -71,     8,  171,-126,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][24]=arrayOf<Int>( -63,     3,  167,-129, -78, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][25]=arrayOf<Int>(  92,   176,   56, -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][26]=arrayOf<Int>( -88,   111,  163,   5, -67,-108,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[4][27]=arrayOf<Int>(  91,   -83,  -68, 116, 166,-123,  96, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[4][28]=arrayOf<Int>( -75,   -60,  167,   7, 166, -99, -82, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 



  
  }


   private  fun init59() {
  
      
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
 rcanglenn_[5][16]=arrayOf<Int>(   96, -87,   10, 175, 127,  -83,  -68, -41, -25, -151, -95,   11,  174, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][17]=arrayOf<Int>(  -79,  30,  172, 115, -84,  -47,  -27, -12,-173,   71,   7,  170,  360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[5][18]=arrayOf<Int>(  -75,  58,   19, 173, 144,  120,  -87, -64, -44,  -26,  -6, -168,   81,   5, 117, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][19]=arrayOf<Int>(  -81,  57,   35,  18, 173,  133,  113,  98, -83,  -65, -31,   -9, -173,  81, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][20]=arrayOf<Int>(  -76, 137,   30,  13, 171,  118,   99, -84, -69,  -51, -30, -172, -150,  81, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][21]=arrayOf<Int>(  -84, -66,  -47,  31, 174,  146,  118, -84, -41,  -26,  -7,   86,    6, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][22]=arrayOf<Int>(  -81,  63,   18, 174, 132,  109,  -77, -44, -24,  -94, 171,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][23]=arrayOf<Int>(  -83, -68,   20, 174, 143,  117,   99, -79, -64,  -49, -29,   87,    8, 161, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][24]=arrayOf<Int>(  -83, -67,  -51,  74,  59,   36,   20, 173, 141,  121, 104,  -83,  -68, -52, -35, -15,-173,-111,  75, 360  ) 
 rcanglenn_[5][25]=arrayOf<Int>(  -80,  31,  173, 137, -84,  -51,  -34, -12,  89,   13, 360,  360,  360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][26]=arrayOf<Int>(  -80, -63,   43,  20, 166,  140,  120,  98, -77,  -56, -40,  -21, -177,  74,   4, 168, 360, 360, 360, 360  ) 
 rcanglenn_[5][27]=arrayOf<Int>(  -82, -65,   49,  25, 174,  153,  137, 121,  98,  -73, -52,  -31,  -14,-176,  80,   4, 360, 360, 360, 360  )  
 rcanglenn_[5][28]=arrayOf<Int>(  -83, -65,   43,  26, 173,  143,  121, 102, -78,  -40, -17,   83,   12, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[5][29]=arrayOf<Int>(  -84,  56,   33,   9, 168,  147,  120, 105, -83,  -54, -32,   -7,  -94, 360, 360, 360, 360, 360, 360, 360  )   
 rcanglenn_[5][30]=arrayOf<Int>(   92,  48,   19, 174, 150,  124,  105, -83, -54,  -38, -17, -174,   84, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][31]=arrayOf<Int>(   98,  33,   11, 167, 134,  105,  -81, -57, -38,  -17, -94,    2,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][32]=arrayOf<Int>(  -84, -69,   40,  12, 168,  118,  102, -84, -48,  -25,  -7, -166,   71, 144, 174, 149, 360, 360, 360, 360  ) 
 rcanglenn_[5][33]=arrayOf<Int>(   94,  24,  170, 148, 118,  103,  -71, -32, -15, -101,   8,  360,  360, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][34]=arrayOf<Int>(   95, 151,   34, 173, 148,  113,  -83, -65, -50,  -28,  -5,   85,  175, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][35]=arrayOf<Int>(   92, -87,   31,   6, 136,  -78,  -59, -40, -18,   85, 360,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][36]=arrayOf<Int>(  -79,  52,   37,  21, 170,  123,  108, -85, -63,  -34, -19, -171,   79,   6, 169, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][37]=arrayOf<Int>(  -78, -62,   23, 175, 154,  130,  -86, -67, -40,  -16,-174,   76,   12, 173, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][38]=arrayOf<Int>(   92, -85,   18, 174, 131,  105,  -85, -58, -36,  -13,-178,   81,    8, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][39]=arrayOf<Int>(  -86, -71,   28, 175, 153,  137,   97, -78, -50,  -27,  -9,   86,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][40]=arrayOf<Int>(  126, 105,   29, 176, 149,  112,   97, -82, -59,  -34,-105,    9,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][41]=arrayOf<Int>(  -83,  91,  122,  17, 172,  140,  109, -84, -60,  -45, -24,   -6, -163,  87, 175, 160, 360, 360, 360, 360  ) 
 rcanglenn_[5][42]=arrayOf<Int>(   91,  29,    7, 138,  96,  -77,  -38, -19,  -3,   79,  18,  175,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][43]=arrayOf<Int>(  -76, -61,  -78,  54,  28,  166,  139, 110, -86,  -54, -27,   -5, -165,-137,  71,   5, 158, 360, 360, 360  ) 
 rcanglenn_[5][44]=arrayOf<Int>(  -63,  33,   16, 174, 136,  108,  -83, -64, -48,  -33, -18, -174, -148,  63,  26,   9, 360, 360, 360, 360  ) 
 rcanglenn_[5][45]=arrayOf<Int>(   93, 128,   11, 169, 141,  119,  -79, -60, -45,  -28, -13, -173,   80,  28,   7, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][46]=arrayOf<Int>(  -65,  25,  172, 113,  96,  -84,  -66, -45, -18,  -91,   4,  360,  360, 360, 360, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][47]=arrayOf<Int>(  -79,  58,  176, 143, 118,  -85,  -70, -45, -20,   83,  13,  360,  360, 360, 360, 360, 360, 360, 360, 360  )  
 rcanglenn_[5][48]=arrayOf<Int>(   92, -87,  -68,  24, 175,  155,  130, 109, -78,  -56, -31,   -7, -133,  75,   3, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][49]=arrayOf<Int>(  -75,  14,  171, 138, 113,  -86,  -68, -47, -22, -172,-157,   76,    3, 360, 360, 360, 360, 360, 360, 360  )
 rcanglenn_[5][50]=arrayOf<Int>(  -74, -57,   32,  13, 174,  146,  114, -81, -53,  -31,  -9, -135,   75, 152,   5, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][51]=arrayOf<Int>(  -85,  99,   40,  22, 172,  136,  104, -87, -66,  -43, -19,   -4, -164,  81, 169, 360, 360, 360, 360, 360  ) 
 rcanglenn_[5][52]=arrayOf<Int>(  -83, -65,   43,  19, 167,  126,  100, -86, -68,  -42,-176,   83,    9, 171, 360, 360, 360, 360, 360, 360  )

 
 
 
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
  rcanglenn_[6][9]=arrayOf<Int>(   -43, -60,  -81,  93, 119,  149,  18,   46,   62, -101,-155, -10,  -34,  -58,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][10]=arrayOf<Int>(   27, 174,  110,  95, -85,  -65, -49,  -22, -171, -126, -98,  85,   68,   50,   27,  173,  126, 360, 360, 360  ) 
  rcanglenn_[6][11]=arrayOf<Int>(   35,  19,  171, 142, 118,  100, -84,  -36,  -13, -170,-134,-114,  -93,   85,   65,   26,  172, 360, 360, 360  ) 
  rcanglenn_[6][12]=arrayOf<Int>(   -3, -24,  -56, -74,  95,  122, 146,  166,    9,   24,  39,  60,  -94, -131, -146,   -7,  -39, 360, 360, 360  ) 
  rcanglenn_[6][13]=arrayOf<Int>( -106,-167,  -33, -52, -67,  -82,  97,  131,  155,   14,  49,  70,  -98, -156,   -5,  -27,  -45, 360, 360, 360  ) 
  rcanglenn_[6][14]=arrayOf<Int>(  -40, -62,  -77,  93, 108,  134,  10,   47,   63,  -98,-149,  -7,  -42,  360,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][15]=arrayOf<Int>( -164, -15,  -38, -54, -76,   92, 111,  126,  143,  171,  23,  42,   58,   74,   -96,-118, -158,  -4, -23, -38  ) 
  rcanglenn_[6][16]=arrayOf<Int>(  -13, -49,  -72,  93, 169,    8,  48,   73,  -93, -153,-168,  -5,  -25,  360,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][17]=arrayOf<Int>(   41,  21,  171, 141, 123,  -80, -51,  -33,  -15, -169,-136,-121,   86,   69,    54,  34,  173, 130, 360, 360  ) 
  rcanglenn_[6][18]=arrayOf<Int>(   43,  26,   11, 167, 132,   99, -85,  -61,  -36, -175,-115, -98,   80,   63,    44,  29,  360, 360, 360, 360  ) 
  rcanglenn_[6][19]=arrayOf<Int>(   34,  15,  171, 113, -85,  -45,-174, -156, -123,   85,  66,  44,   13,  173,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][20]=arrayOf<Int>(   24,   6,  155, 119,  95,  -77, -48,  -21, -176, -120, -98,  86,   65,   48,    32, 360,  360, 360, 360, 360  ) 
  rcanglenn_[6][21]=arrayOf<Int>(   21, 173,  110,  95, -79,  -64, -46, -166, -143, -123,-102,  83,   66,   21,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][22]=arrayOf<Int>(   31, 168,  116, -83, -49,  -24,-166, -136, -111,   85,  66,  51,   30,   15,   173, 360,  360, 360, 360, 360  )
  rcanglenn_[6][23]=arrayOf<Int>(   -6, -32,  -51, -68,  93,  120, 142,    7,   47,   64,-106,-169,   -9,  -40,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][24]=arrayOf<Int>( -165, -10,  -31, -61, -83,   94, 113,  129,  155,   10,  53,  76,  -93, -165,    -9, -31,  360, 360, 360, 360  )  
  rcanglenn_[6][25]=arrayOf<Int>(   29, 170,  130, 114, -86,  -55, -24, -169, -125, -106,  81,  66,   48,   32,    13, 170,  360, 360, 360, 360  )
  rcanglenn_[6][26]=arrayOf<Int>( -160, -15,  -41, -66, -82,   94, 115,  153,  169,    7,  29,  50,   70, -100,  -165,  -3,  -40, 360, 360, 360  ) 
  rcanglenn_[6][27]=arrayOf<Int>( -175, -11,  -37, -62, -80,   96, 119,  136,  152,    5,  31,  50,   76, -100,  -148,-170,   -7, -28, 360, 360  ) 
  rcanglenn_[6][28]=arrayOf<Int>( -160,  -5,  -35, -50, -67,   94, 119,  135,  155,   13,  44,  59, -102, -136,  -165,  -8,  -24, -49, -28, 360  ) 
  rcanglenn_[6][29]=arrayOf<Int>( -101,-130,  -14, -49, -66,   93, 114,  148,    9,   52,  71,-101, -133, -165,   -11, -32,  360, 360, 360, 360  )
  rcanglenn_[6][31]=arrayOf<Int>( -118,-157, -172, -11, -34,  -59, -79,   95,  122,  166,   4,  37,   58,   78,   -94,-152, -171,  -7, -40, 360  )
  rcanglenn_[6][32]=arrayOf<Int>(   -5, -22,  -39, -60, -78,   94, 113,  132,  156,    9,  41,  62,  -95, -117,  -158,  -5,  360, 360, 360, 360  ) 
  rcanglenn_[6][33]=arrayOf<Int>(  -40, -60,  -82,  93, 124,   14,  53,   70, -102,   -4, -25, -62,  360,  360,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][34]=arrayOf<Int>( -126,-151, -169,  -9, -49,  -72,  93,  113,  134,  159,   6,  38,   54,   73,   -95,-131, -156,  -4, -37, 360  ) 
  rcanglenn_[6][35]=arrayOf<Int>( -139,-156,   -4, -24, -43,  -58, -75,   92,  118,  137, 152,   5,   24,   47,    67,  82, -100,-117,-135,-154  )  
  rcanglenn_[6][36]=arrayOf<Int>( -160,  -3,  -42, -66,  94,  113, 133,    6,   48,   67,  82,-161,   -6,  -21,   -47, -63,  360, 360, 360, 360  )  
  rcanglenn_[6][37]=arrayOf<Int>(   75, -95, -147,  -6, -27,  -54, -72,   94,  121,  137, 159,   4,   64,   81,   -95,-155,   -4, -19, -34, 360  )
  rcanglenn_[6][38]=arrayOf<Int>( -127,-143, -165, -14, -34,  -60, -83,   93,  110,  131, 157,  10,   52,   75,  -103,-160,   -7, -25, -42, 360  )
  rcanglenn_[6][39]=arrayOf<Int>(   54,  30,  174, 130, -84,  -69, -51,  -31,  -15, -175,-103,  84,   57,   33,    15, 360,  360, 360, 360, 360  )
  rcanglenn_[6][40]=arrayOf<Int>(   59,  29,  174, 109, -84,  -62, -43,  -13, -162, -116,  87,  63,   47,   24,   169, 360,  360, 360, 360, 360  )
  rcanglenn_[6][41]=arrayOf<Int>( -115,-132, -153, -10, -45,  -65, -81,   94,  134,  157,   5,  27,   49,   64,  -105,-141, -169,  -6, -27, 360  ) 
  rcanglenn_[6][42]=arrayOf<Int>( -118,-134, -162, -11, -39,  -61, -81,   99,  122,  145,  15,  47,   68,  -95,  -139, -10,  -27, -42, 360, 360  )  
  rcanglenn_[6][43]=arrayOf<Int>(   22, 172,  119, 102, -84,  -50, -19, -173, -146, -107,  86,  59,   39,  164,   101, 360,  360, 360, 360, 360  )
  rcanglenn_[6][44]=arrayOf<Int>( -142,-159,  -13, -53, -70,   93, 123,  144,  164,    4,  29,  51,   72,  -96,  -111,-141, -159, 360, 360, 360  )
  rcanglenn_[6][45]=arrayOf<Int>( -138,-161,   -4, -25, -49,  -74,  92,  121,  141,  160,   2,  29,   58,   76,   -98,-160,   -3, -26, 360, 360  )
  rcanglenn_[6][46]=arrayOf<Int>(   47,  30,  171, 117, 102,  -85, -69,  -53,  -35,  -11,-173,-149, -114,  -97,    84,  65,   40,  14, 137, 360  ) 
  rcanglenn_[6][47]=arrayOf<Int>(   42,  22,  164,  91, -82,  -59,-173, -148, -119,  -95,  81,  63,   42,  360,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][48]=arrayOf<Int>(   70,  53,   18, 165, 144,  126, 103,  -84,  -64,  -48, -25,-170, -124, -108,    86,  66,   46,  28, 170, 360  ) 
  rcanglenn_[6][49]=arrayOf<Int>(   56,  22,  172, 106, -85,  -63, -34, -165, -131, -111,  80,  62,   45,   26,   166, 360,  360, 360, 360, 360  ) 
  rcanglenn_[6][51]=arrayOf<Int>( -138,-155, -170, -30, -46,  -62, -86,   99,  121,  136, 155,   8,   39,   57,   -93,-124, -166,  -5, -40, -67  ) 
  rcanglenn_[6][52]=arrayOf<Int>( -150,-166,   -5, -23, -38,  -62, -79,   92,  108,  137, 163,   5,   33,   65,    80, -94,   -3, -26, -41, 360  ) 
  rcanglenn_[6][53]=arrayOf<Int>(   -5, -27,  -52, -68, -85,   95, 117,  144,  163,    9,  50,  69,  -93, -156,    -3, -18,  -36, 360, 360, 360  )
  rcanglenn_[6][54]=arrayOf<Int>( -165,  -3,  -23, -47, -71,   93, 111,  134,  152,    7,  24,  47,   69, -106,  -134,-152, -171,  -8, -29, 360  ) 
  rcanglenn_[6][55]=arrayOf<Int>( -104,-127, -156, -10, -27,  -54, -70,   93,  110,  131, 160,   5,   39,   61,    80,-102, -151,-175, -23, -42  ) 
  rcanglenn_[6][56]=arrayOf<Int>(   75,-100, -171, -16, -40,  -60, -81,   93,  117,  139, 168,  16,   31,   47,    77,-100, -128,-148,-170,  -5  ) 
  rcanglenn_[6][57]=arrayOf<Int>( -145,-171,   -8, -27, -45,  -63,  92,  111,  134,  151,  12,  42,   58,  -99,  -163,  -3,  -19, -36, -60, -79  ) 
  rcanglenn_[6][58]=arrayOf<Int>( -170, -10,  -46, -61, -76,   92, 108,  123,  139,  164,   6,  36,   58,   83,   -99,-121, -150,  -8, -29, 360  )
  rcanglenn_[6][59]=arrayOf<Int>( -158,  -7,  -27, -51, -68,  -84,  97,  119,  143,   4,  20,   39,  57,    79,   -98,-160,   -5, -23, -38, 360  )
  rcanglenn_[6][60]=arrayOf<Int>( -162,  -8,  -42, -64,  94,  111, 133,  161,    9,  26,  44,   62,-103,  -156,    -3, -20,  -39, 360, 360, 360  ) 
  rcanglenn_[6][61]=arrayOf<Int>( -163,  -5,  -41, -57, -78,  100, 128,  157,    7,  34,  56,  -96,-162,    -4,   -22, -40,  -68, 360, 360, 360  ) 
  rcanglenn_[6][62]=arrayOf<Int>(   39,  17,  174, 127, 108,  -86, -65,  -50,  -17,-166,-131, -109,  83,    66,    37,  19,  172, 360, 360, 360  )
  rcanglenn_[6][63]=arrayOf<Int>( -170, -16,  -37, -52, -71,   93, 109,  134,  161,  14,  31,   46,  65,   -98,  -167,  -4,  -27, -52, 360, 360  ) 
  rcanglenn_[6][64]=arrayOf<Int>(   25, 173,  143, 108, -85,  -64, -40, -159, -116, -96,  86,   70,  55,    32,   171, 360,  360, 360, 360, 360  )
  rcanglenn_[6][65]=arrayOf<Int>( -176, -12,  -40, -66,  92,  108, 128,  166,    6,  26,  45,   66,-105,  -170,    -6, -31,  -67, 360, 360, 360  )
  rcanglenn_[6][66]=arrayOf<Int>( -162, -10,  -34, -52, -72,   93, 109,  136,  160,   5,  49,   77, -95,  -169,    -5, -23,  -46, 360, 360, 360  )
  rcanglenn_[6][67]=arrayOf<Int>(   42,  19,  170, 111, -83,  -67, -43, -173, -144, -120, 83,   67,  51,    26,     6, 360,  360, 360, 360, 360  )
  rcanglenn_[6][68]=arrayOf<Int>(   29, 168,  120, -83, -60,  -35,-161, -135, -104,   87, 70,   48,  30,   174,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][69]=arrayOf<Int>(   66,  32,  163, 118, 102,  -86, -66,  -14, -144, -114,-98,   82,  64,    37,    22, 124,  360, 360, 360, 360  )
  rcanglenn_[6][70]=arrayOf<Int>( -126,  -7,  -34, -52, -71,   96, 126,  153,    5,   33, 58, -117,-144,  -162,    -4, -23,  360, 360, 360, 360  )
  rcanglenn_[6][71]=arrayOf<Int>(   50,  23,  166, 131, 108,  -84, -66,  -36,  -14, -168,-121,-103,  85,    70,    53,  38,  175, 360, 360, 360  )
  rcanglenn_[6][72]=arrayOf<Int>( -172, -22,  -37, -53, -72,   94, 114,  148,    6,   49,  64,  80,-124,  -165,   -14, -35,  -62, 360, 360, 360  )
  rcanglenn_[6][73]=arrayOf<Int>( -157,  -8,  -29, -47, -62,  -79,  97,  118,  140,  163,   5,  29,  48,    64,  -139,-173,  -21, -49, 360, 360  )
  rcanglenn_[6][74]=arrayOf<Int>(   27,  12,  170, 121,  96,  -74, -56,  -33, -169, -139,-114,  86,  71,    51,    31, 174,  156, 360, 360, 360  ) 
  rcanglenn_[6][75]=arrayOf<Int>(   25, 176,  124, 102, -82,  -62, -42, -168, -120, -101,  86,  39,  16,   360,   360, 360,  360, 360, 360, 360  )
  rcanglenn_[6][76]=arrayOf<Int>(   51,  33,   10, 170, 139,  110,  94,  -81,  -62,  -36, -10,-171,-124,  -101,    87,  70,   49,  29, 360, 360  ) 
  rcanglenn_[6][77]=arrayOf<Int>(   27, 172,  142, 117, 100,  -84, -63,  -47,  -15, -168,-135,-120,-101,    83,    61,  36,   17, 360, 360, 360  ) 
  rcanglenn_[6][78]=arrayOf<Int>(   33,  12,  169, 129, 110,  -86, -66,  -46,  -26, -164,-124,-106,  85,    60,    41, 360,  360, 360, 360, 360  )


  
  
      
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
   rcanglenn_[7][13]=arrayOf<Int>(  80,   57,  171,   146,  164,   9,  72, -14,  -52, -72 , -92, 158, 173, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][14]=arrayOf<Int>(  68,  165,  146,     9,   48, -63, -78,-128,    4,  44 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][15]=arrayOf<Int>(  82,  175,  155,    16,   53, -68, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][16]=arrayOf<Int>(-114,   79,   26,   171,  154, 173,  10,-125,  -61, -77 ,-118, 360, 360, 360, 360, 360, 360, 360, 360, 360 )  
   rcanglenn_[7][17]=arrayOf<Int>(  71,   10,  158,     7,   63, -79,-109, 171,  155, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][18]=arrayOf<Int>(  16,  -60,  -75,  -125,  176, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][19]=arrayOf<Int>(-110,   71,  172,   149,   11,  75,-105, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][20]=arrayOf<Int>( 172,   21,   65,   -80, -105, 172,   7, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][21]=arrayOf<Int>(  67,  152,  118,     7,   52, -68, -84,-144, -103, 159 ,   3, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][22]=arrayOf<Int>(  84,   11,  170,    15,  -64, -80,-103, 174,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][23]=arrayOf<Int>(  74,   55,   32,   167,  141,  11,  46,-136,  -67, -83 ,-109,   3, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][24]=arrayOf<Int>( 173,   16,  -70,   360,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][25]=arrayOf<Int>(  86,   63,  172,   134,   10,  62, -35, -60,  -79,-102 , 177, 360, 360, 360, 360, 360, 360, 360, 360, 360 )  
   rcanglenn_[7][26]=arrayOf<Int>( 172,   33,   49,   -73, -110, 174, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][27]=arrayOf<Int>( 173,  158,   22,    56,   71, -54, -71,-103,  167, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][28]=arrayOf<Int>(  41,  173,   11,    38,  -54, -70, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][29]=arrayOf<Int>(  76,   58,   32,   170,  145, 166,  14,  60,   75, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][30]=arrayOf<Int>( 176,   36,   80,  -131,  176, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][31]=arrayOf<Int>(   3,  154,  -53,   -70,  100, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][32]=arrayOf<Int>(  70,   40,  170,   153,  136, -63, -78,-115,    2, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][33]=arrayOf<Int>(  46,   30,   11,   169,  149,  11, -63, -80,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][34]=arrayOf<Int>(-118,   70,  177,    19,   54, -74, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][35]=arrayOf<Int>(  85,  167,   16,    50,  -70, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][36]=arrayOf<Int>(-101,   82,  171,   148,   16,  72, -52, -74, -105,   3 , 156, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][37]=arrayOf<Int>(-103,   79,   32,   174,  155,  25, -54, -70,  -87,-121 ,  20, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][38]=arrayOf<Int>(  69,   11,  164,    40,  -57, -76, 120, 168,    3, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][39]=arrayOf<Int>(  49,   32,  175,   160,   12,-117, -57, -73, -118,   1 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][40]=arrayOf<Int>(   1,  -72, -114,   170,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][41]=arrayOf<Int>(  77,   14,  -66,   -82,  360, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][42]=arrayOf<Int>(  19,  172,    8,  -130,  -59, -74,-111,   5,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][43]=arrayOf<Int>(-108,   83,   35,   165,    5,-147, -47, -72,  151, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][44]=arrayOf<Int>(  80,   57,   38,    15,  176,  59, -23, -49,  -71, -86,  175, 360, 360, 360, 360, 360, 360, 360, 360, 360 )  
   rcanglenn_[7][45]=arrayOf<Int>( -97,   80,   64,    15,  165, 150,   9, -55,  -70, -85 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )  
   rcanglenn_[7][46]=arrayOf<Int>(  56,   11,  170,    23, -116, -56, -71,-105,    4, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][47]=arrayOf<Int>(  43,  171,    8,  -115,  -57, -74, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][48]=arrayOf<Int>(  76,   18,  176,    12,  -33, -53, -69, 102,  145, 163 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][49]=arrayOf<Int>(  37,  172,   10,   -58,  -73,  95, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][50]=arrayOf<Int>(  68,   32,  163,   142,    9, -64, -82,  93,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][51]=arrayOf<Int>(-145,  175,   25,   -52,  -68, -86, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][52]=arrayOf<Int>(   7,   23,  -63,   -79,  -99, 168, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][53]=arrayOf<Int>(  83,   48,  163,   133,  155,  12,  51, -66,  -85, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][54]=arrayOf<Int>(  73,   46,   30,   168,  131,  24,  73, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][55]=arrayOf<Int>(  62,   11,  172,     9,   50, -65, -80, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][56]=arrayOf<Int>(-104,   85,   65,    41,  153, 106, 137,  10,   45,  61 , -78,-101, 169, 360, 360, 360, 360, 360, 360, 360 )
   rcanglenn_[7][57]=arrayOf<Int>( 164,   11,   65,   -82,   97, 360, 360, 360,  360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 ) 
   rcanglenn_[7][58]=arrayOf<Int>(  81,   10,  155,     6,   66, -42, -63, -81,   94, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360 )
   
   
// Filling sample array 
// 8
      jj = 8                 
  
    rcanglenn_[8][0]=arrayOf<Int>(  -139,  83,  44,  26,  176,  -80, -54, -72,   95,  151, 169,  33,  62,  -99,  -128,  -145, -130, 360, 360, 360  )    
    rcanglenn_[8][1]=arrayOf<Int>(  -151,-135,-112,  80,   41,   23, 176, 136,  -81,  -60, -82, 103, 118,  148,   169,     7,   48,  80, -94,-119  )    
    rcanglenn_[8][2]=arrayOf<Int>(  -151,-130,  81,  39,   19,  171, 104, -85,  -54,  -76,  98, 129, 159,    5,    43,    79,  -98,-117,-136, 360  )    
    rcanglenn_[8][3]=arrayOf<Int>(  -145,-125,  84,  54,   37,  174, -79, -56,  -74,   96, 157,  11,  35,   62,   -97,  -125,  360, 360, 360, 360  )    
    rcanglenn_[8][4]=arrayOf<Int>(  -149,-131,  74,  50,   30,  171, 119,  98,  -77,  -51, -69,  95, 140,  162,     9,    57,  -99,-127, 360, 360  )
    rcanglenn_[8][5]=arrayOf<Int>(  -137,-121,  72,  23,  171,  -70, -55, -74,  104,  150,   7,  39,  67, -110,  -130,   360,  360, 360, 360, 360  )    
    rcanglenn_[8][6]=arrayOf<Int>(    15,  44,  71,-103, -150,   -5, -44, -60,  101,  151, 168, 149, 132,  103,   -76,   -44,  -25, -17,-136,-112  )    
    rcanglenn_[8][7]=arrayOf<Int>(    18,  36,  52,  67,  -99, -167, -18, -41,  -56,  104, 128, -70, -41,  -20,  -176,  -108,   78,  59,  35, 360  )    
    rcanglenn_[8][8]=arrayOf<Int>(    46,  61,-103,-135,   -4,  -37, -58, 105,  143,  115, -83, -44, -21, -175,  -114,   -98,   74,  44,  26, 360  )    
    rcanglenn_[8][9]=arrayOf<Int>(    44,  61, -96, -16,  -51,  -73,  96, 144,  123,  -86, -53, -28, -12, -175,   83,     65,  360, 360, 360, 360  )    
    rcanglenn_[8][10]=arrayOf<Int>( -137,-117,  81,  50,   16,  171, 137, -73,  -57,  -80, 126, 153,   8,   32,   73,    -93, -131, 360, 360, 360  )    
    rcanglenn_[8][11]=arrayOf<Int>( -158,  76,  25, 176,  135,  -59, -76,  97,  122,    5,  35,  69, -97, -120, -139,   -156,  360, 360, 360, 360  )    
    rcanglenn_[8][12]=arrayOf<Int>( -143,-126,  66,  30,  145,  -73, -54,  96,  150,    8,  49,  69, -95, -135,  360,    360,  360, 360, 360, 360  )    
    rcanglenn_[8][13]=arrayOf<Int>(   77,  59,  32, 171,  153,  -81, -56,  96,  123,  148, 165,   8,  40,   62,   82,   -101, -118,-145,-123, 360  ) 
    rcanglenn_[8][14]=arrayOf<Int>(   -7, -34, -51, 102,  133,  152, 131, 103,  -85,  -53, -34,  -8,-155, -104,   81,     63,   82, 360, 360, 360  ) 
    rcanglenn_[8][15]=arrayOf<Int>( -149,-125, -98,  78,   51,   28, 170, -84,  -68,  -34,  -7, -31, -53,  -72,   95,    147,  171,   7,  48,  63  )
    rcanglenn_[8][16]=arrayOf<Int>( -147,  -6, -36, -54,  -72,  107, 151, 132,  108,  -78, -54, -35, -10, -166, -137,     75,   58,  73, 360, 360  )
    rcanglenn_[8][17]=arrayOf<Int>(  -24, -42, -65, 103,  143,  119, -81, -45,  -25, -170,-153,  69,  43,   61,   76,   -101, -132, 360, 360, 360  )    
    rcanglenn_[8][18]=arrayOf<Int>(  -22, -42, -71,  98,  134,  154, 139, -74,  -38,  -18,-174,  83,  49,   65,   82,   -133,  360, 360, 360, 360  )
    rcanglenn_[8][19]=arrayOf<Int>(   36,  52,  71,-125, -147,   -7, -31, -48,  104,  148, 132, 100, -68,  -37,  -21,   -171,   83,  57, 360, 360  )
    rcanglenn_[8][20]=arrayOf<Int>(   25,  42,  57,-106, -163,  -10, -29, 100,  120,  146, 117, -83, -22, -171, -138,   -104,   80,  58, 360, 360  )
    rcanglenn_[8][21]=arrayOf<Int>(   -5, -33, -56,  98,  137,  114, -76, -46,  -21, -171,  65,  35,  56,   73,  -94,   -124,  360, 360, 360, 360  )
    rcanglenn_[8][22]=arrayOf<Int>(  155, 131, 115, -81,  -45,  -21,-175,  84,   54,   31,  51,  70, -96, -166,   -4,    -26,  -42, -75, 101, 142  )    
    rcanglenn_[8][23]=arrayOf<Int>( -175, -21, -39, -59,  100,  148, 163, 140,  116,  -77, -58, -37, -11, -169, -125,     83,   53,  38,  53,  71  )
    rcanglenn_[8][24]=arrayOf<Int>( -109,  83,  61,  34,   19,  173, -72, -54,   99,  125, 159,   8,  37,   57,   83,   -113, -134,-154, 360, 360  ) 
    rcanglenn_[8][25]=arrayOf<Int>(  167, 150, 134, 110,  -78,  -50, -24,  -9, -168, -142,-119,  83,  66,   39,   60,     75,  -98,-150,-169,  -5  )    
    rcanglenn_[8][26]=arrayOf<Int>( -165,-125,  73,  31,  171,  -82, -52, -36,  -20,  -49, -77,  99, 147,  168,    5,     48,   77, -95,-117,-154  )    
    rcanglenn_[8][27]=arrayOf<Int>( -167,-150,  71,  39,   22,  168, -75, -39,  -57,   95, 145, 167,   7,   26,   49,     65,  -93,-133,-155,  -2  )    
    rcanglenn_[8][28]=arrayOf<Int>( -167,-148,-130,-106,   81,   47,  19, 174,  151,  -62, -40, -56, -71,   99,  156,      6,   26,  41,  61, -95  )    

    
    

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
   rcanglenn_[9][16]=arrayOf<Int>( 135, 155,   11,  54,  69,   85,   -98,  -115, -143, -167,  -12, -44, -65, -80, 107,  128,   6,  23,  39, 360  )  
   rcanglenn_[9][17]=arrayOf<Int>( 172,   7,   72, -93,-111, -131,    -7,   -42,  -63,  -82,   94, 115, 137, 166,   6,   25, 360, 360, 360, 360  ) 
   rcanglenn_[9][18]=arrayOf<Int>(-169,-142, -114, -97,  82,   60,    36,    12,  166,  127,  109, -85, -64, -47, -32,  -11, 360, 360, 360, 360  )
   rcanglenn_[9][19]=arrayOf<Int>(  53,  75,  -93,-120, -13,  -38,   -54,    97,  131,  156,    7,  46, 360, 360, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][20]=arrayOf<Int>( -45, -23, -176,-135,-113,   82,    43,    26,  170,  109,  -83,  94, -85, -61, -25, -177,-143, 360, 360, 360  ) 
   rcanglenn_[9][21]=arrayOf<Int>( 155,  24,   60,  79, -91, -114,  -135,   -11,  -55,  -73,   98, 132, 166,   7, 360,  360, 360, 360, 360, 360  )
   rcanglenn_[9][22]=arrayOf<Int>(  52,  71,  -98,  -8, -34,  -56,   114,   164,    4,  360,  360, 360, 360, 360, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][23]=arrayOf<Int>(  12,  37,   53,  75, -91, -114,   -14,   -42,  -62,  -82,  102, 118, 360, 360, 360,  360,360, 360, 360, 360  ) 
   rcanglenn_[9][24]=arrayOf<Int>(  36,  52,   69,  84,-100,  -31,   -51,   -70,  103,  144,   16,  42, 360, 360, 360,  360,360, 360, 360, 360  ) 
   rcanglenn_[9][25]=arrayOf<Int>( -30, -12, -160,-125,-103,   73,    31,   175,  147,  -85,   94, -83, -29,  -7,-168, -146, 360, 360, 360, 360  )   
   rcanglenn_[9][26]=arrayOf<Int>(-176,-138, -112,  75,  48,   22,   172,   130,  104,  -86,  -41, -16,-177,-162, 360,  360, 360, 360, 360, 360  )
   rcanglenn_[9][27]=arrayOf<Int>(  56,  71,  -91,-149, -21,  -45,   -63,    99,  173,   18,  360, 360, 360, 360, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][28]=arrayOf<Int>( 160, 141,  166,  14,  57,   74,   -92,  -115, -159,   -7,  -51, -69,  95, 154,   3,  360, 360, 360, 360, 360  )
   rcanglenn_[9][29]=arrayOf<Int>( 146, 169,    5,  32,  56,   73,   -96,  -115, -151, -168,  -10, -38, 112, 161,   3,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][30]=arrayOf<Int>( 108, 127,  142, 168,  38,   53,    69,   -92, -109, -128, -147,  -8, -50, -73,  99,  131, 147, 166, 360, 360  ) 
   rcanglenn_[9][31]=arrayOf<Int>( 145, 164,   11,  29,  58,   77,   -99,  -147,   -5,  -52,  -67, 104, 140, 162,  13,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][32]=arrayOf<Int>(-175,-149, -133,-115,  84,   59,    21,   170,  100,  -81,  -59, -35, -13,-174, 360,  360, 360, 360, 360, 360  )
   rcanglenn_[9][33]=arrayOf<Int>(-170,-155, -121,  82,  57,  172,   152,   111,  -86,  -64,  -80, -56, -33,-174,-151,   76, 360, 360, 360, 360  )
   rcanglenn_[9][34]=arrayOf<Int>( 135, 155,   10,  41,  58,   79,   -92,  -114, -141,   -6,  -24, -52,  96, 150, 168,    6, 360, 360, 360, 360  )
   rcanglenn_[9][35]=arrayOf<Int>( -27,-171, -137,  78,  56,   34,   170,   139,  112,  -83,  -66, -26,  -9, 360, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][36]=arrayOf<Int>(-177,-156, -118,  80,  46,   30,   171,   126,   97,  -80,  -65, -46, -16,-172, 360,  360, 360, 360, 360, 360  )
   rcanglenn_[9][37]=arrayOf<Int>( 160,   9,   24,  46,  64,   81,   -93,  -111, -152,   -9,  -42,  98, 150,   2, 360,  360, 360, 360, 360, 360  ) 
   rcanglenn_[9][38]=arrayOf<Int>( 165,  13,   53,  72, -95, -122,  -142,  -171,  -19,  -45,  -63, -81, 101, 130, 155,    4,  20,  43, 360, 360  ) 
   rcanglenn_[9][39]=arrayOf<Int>( 172,  28,   53,  76, -95, -110,  -136,    -7,  -50,  -74,   97, 116, 132, 149,   5,  360, 360, 360, 360, 360  )
   
      
   
    }



 private  fun init1015()   {
            
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
      rcanglenn_[10][12]=arrayOf<Int>( 12,   176,  -144,  96, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )   
      rcanglenn_[10][13]=arrayOf<Int>(  7,  -135,    93, -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][14]=arrayOf<Int>( 91,   174,  -138, -79,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][15]=arrayOf<Int>(175,  -140,   -70, -85,  92, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][16]=arrayOf<Int>(  4,    23,   164,-144,  91, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][17]=arrayOf<Int>( 52,   -83,   360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][18]=arrayOf<Int>( 11,   171,  -136,  97, -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][19]=arrayOf<Int>(173,    13,   177,-134, -87,  93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][20]=arrayOf<Int>(177,  -111,   -61, -81, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][21]=arrayOf<Int>(  9,   170,  -127, -86,  93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][22]=arrayOf<Int>(  5,    60,   -76,  91, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][23]=arrayOf<Int>( 171,   10,  -134, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][24]=arrayOf<Int>( 178,  159,   -96, -58, -74, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][25]=arrayOf<Int>(   7,  168,  -140, -82,  91, -86, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][26]=arrayOf<Int>( 178,  141,   -71, -89, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][27]=arrayOf<Int>(   7,   47,   -68, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][28]=arrayOf<Int>(  93, -120,     2, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][29]=arrayOf<Int>( 174,  144,   -78, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[10][30]=arrayOf<Int>(  29,   57,   -83, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
      rcanglenn_[10][31]=arrayOf<Int>(  38,  -83,    93, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
      rcanglenn_[10][32]=arrayOf<Int>(  91,    1,  -113, -82, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )

      
      
      
// Filling sample array 
// -
      jj = 11               
  
        rcanglenn_[11][0]=arrayOf<Int>( 180 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
        rcanglenn_[11][1]=arrayOf<Int>(   5 , 168, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
     //   rcanglenn_[11][2]=arrayOf<Int>( 174 ,   9, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )


      
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
  rcanglenn_[12][12]=arrayOf<Int>(   63,  47, -12,  138, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][13]=arrayOf<Int>(  139, 124,  79,  -25, -42,  68, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][14]=arrayOf<Int>(  151, 136, 153, -100, -39, -54, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][15]=arrayOf<Int>(   63,  40, -16,  142, 125, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][16]=arrayOf<Int>(   64,  49,  65,   -9, 139, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][17]=arrayOf<Int>(   55,-167, 123,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][18]=arrayOf<Int>(  -43, -59,  88,  127, 108, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][19]=arrayOf<Int>(   24,  39,  57, -171,  98, 118, 134, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[12][20]=arrayOf<Int>(  129,  86, -50,  -67, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][21]=arrayOf<Int>(   35,  52,-171,  127, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][22]=arrayOf<Int>(   51,  68,  53,  -14, 128, 147, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][23]=arrayOf<Int>(   46,  28,  47, -170, 128, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[12][24]=arrayOf<Int>(   58,  43,-176,  109, 126, 141, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
  rcanglenn_[12][25]=arrayOf<Int>( -154,-134,   2,  -55, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
  rcanglenn_[12][26]=arrayOf<Int>(   84,  66,  45,  -13, 124, 155, 137, 122, 147, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )
  rcanglenn_[12][27]=arrayOf<Int>(   81,  57,  37,   -2, 150, 132, 115, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 

  
      
// Filling sample array 
// /
      jj = 13              

      rcanglenn_[13][0]=arrayOf<Int>(   45, 360, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][1]=arrayOf<Int>(  -45, 360, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][2]=arrayOf<Int>(   52,  35, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][3]=arrayOf<Int>(   42,  58,  39, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )   
      rcanglenn_[13][4]=arrayOf<Int>(   72,  56, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
      rcanglenn_[13][5]=arrayOf<Int>(   46,  63, 360, 360, 360, 360, 360, 360, 360, 360 , 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  



      

      
// Filling sample array 
// =
      jj = 14            

     rcanglenn_[14][0]=arrayOf<Int>(   1,  -27,   2, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][1]=arrayOf<Int>( 169,    4, -27,   2, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][2]=arrayOf<Int>( 177,  -41, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][3]=arrayOf<Int>(   3,  163, -21,   5, 157, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][4]=arrayOf<Int>(  92,    6, 171, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][5]=arrayOf<Int>(  91,    4, 134, -24,   5, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][6]=arrayOf<Int>(  91,  171,  12, -27,   6, 169, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[14][7]=arrayOf<Int>( 175,   16, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
     rcanglenn_[14][8]=arrayOf<Int>(  13,  176, -37, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
     rcanglenn_[14][9]=arrayOf<Int>(   8,   97,   3, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
     rcanglenn_[14][10]=arrayOf<Int>(101,    4, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  ) 
     rcanglenn_[14][11]=arrayOf<Int>(176,  -27, 145, 169,   6,  21, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  

      
// Filling sample array 
// ,
      jj = 15            
     
     rcanglenn_[15][0]=arrayOf<Int>(   140, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][1]=arrayOf<Int>(   100, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][2]=arrayOf<Int>(   108, -87,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][3]=arrayOf<Int>(   -86, 360,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][4]=arrayOf<Int>(   122, 106,  -87, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][5]=arrayOf<Int>(   -84, -65,  360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  
     rcanglenn_[15][6]=arrayOf<Int>(    99, -82,  -67, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360, 360  )  




 }
  




























    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)

        supportActionBar?.hide()

       
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
 
        w = displayMetrics.widthPixels
        h = displayMetrics.heightPixels

        init0()
        init14()
        init59()
        init1015()
              
        
        binding.apply {



 
   
      
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

                    //  btnSave.setImageResource(R.drawable.ic_selected_pencil)

                   //        drawLine.visibility = View.VISIBLE
                 //       btnArrow.setImageResource(R.drawable.ic_unselected_line)

                   

                 
                      
                      
                      
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
    canglennsh_=Array<Int>(20){0} 
                  
     
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

        aaa = Array<String>(25){"0"}  // for 8 has to be make spare 25 instead 20 because 8 sometimes cinn > 20 
        data = ""  // String of aaa Array for file.txt  saving
       j=0
   if (cinn > 0)  cinn=cinn - 1
while (j >=0 && j<=cinn) {
 aaa[j] =   canglenn_[j].toString() + ","
 data = data + aaa[j] // String of aaa Array for file.txt  saving
             
                  j=j+1
                         }

        j=cinn+1
while (j >=cinn && j<=19) {
if(j<19) {data = data + "360," } // add "360/" as blank to String of aaa Array for file.txt  saving
if(j==19) {data = data + "360" }
                  j=j+1
                        }
data = data + "\n" + "\n"  // addd new line + blank line divider symbol for comfortable view





   aresmin = " "

  resmin =  Array(20){ Array<Int>(300){0} }
  res0=0; res00=0
                    
   jj=0
   minres0 = 100000
                              
while (jj >=0 && jj<=15)  // index of symbols(numbers and operations)  0, 1 ..
{
          jjj=0

           minres = 100000
      while (jjj >=0 && jjj<=299 && rcanglenn_[jj] [jjj] [0] !=360) // quantity of variants for each/all numbers
           {

               
      // it has to be sequence loop-shifting (sh) for all 0,..,shh<=19 indexes for which (canglenn_[j]!=360 && rcanglenn_[jj] [jjj] [j] !=360)
                ccc=0
                sh=0 
               shh=19 // is changed inside while circle during first sh=0 loop 
            while (sh>=0 && sh<=shh && ccc<=1) {     // ccc - label of direct loop ccc=0 and reverse loop ccc=1
                                        
                                     if (ccc==0) {  j=0
                                   while ( j >=0 && j<=shh) {
                                                  if ( (j+sh)<=shh) { canglennsh_[j]=canglenn_[j+sh] }
                                                  if ( (j+sh)>shh)  { canglennsh_[j]=canglenn_[shh-j] }
                                                j=j+1
                                                           }
                                                }

                                     
                                   if (ccc==1) {  j=0
                                   while ( j >=0 && j<=shh) {
                                                  if ( (j+sh)<=shh)  { canglennsh_[j+sh]=canglenn_[j] }
                                                  if ( (j+sh)>shh)   { canglennsh_[shh-j]=canglenn_[j] }
                                                j=j+1
                                                                        }
                                                  
                                       
                                                }
                                     

                                   if (sh==shh && ccc==0) { ccc=1; sh=0 }
                                          
               
               j=0
                                                        

               
                while (j >=0 && j<=shh ) {  

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
                    else  { 
                    if ( canglenn_[j]==360 && rcanglenn_[jj] [jjj] [j] ==360 && ccc==0)  {shh=j; j=19} //empty operation - both and symmetrically  0==360 end of circle 
                        else {resmin[jj] [jjj] =  resmin[jj] [jjj] + 3600} //  asymmetrically  0==360
                          }
                         
                                        
                    j=j+1
                
                
                
               }


    if  (resmin[jj] [jjj]  < minres) {    minres = resmin[jj] [jjj]  ; res0=jj; res1=jjj;   }   // seek minres inside sh loop and  inside 0..299 variants
                
                   sh=sh+1
               }  //sh - loop-shifting while circle
               
                
 //     if  (resmin[jj] [jjj]  < minres) {    minres = resmin[jj] [jjj] ; res0=jj; res1=jjj;   }   // seek minres inside 0..299 variants

                lastindex_rcanglenn[jj] = jjj // count of index quantity for each of 15 numbers: rcanglenn[x, y] ,  i.e. it's y                          
           jjj=jjj+1
             }  




            
 aresmin = aresmin + " [" + res0.toString() +  "," + res1.toString() + "]=" + minres.toString()

            if ( minres < minres0)    { res00=res0; res11=res1; minres0=minres }  // seek minres0 inside 0..15 variants
 jj=jj + 1    
    
} 

      
          


// output  result as string with diferent length adding new symbols each cycle
      dec = 1.0f
      resnum[l]=res00.toFloat()
     if ( resnum[l]<=9 ) {       if ( (resnum[l] - resnum[l].toInt() ) > 0 ) {
                              aresnum =  aresnum +  aresnum1 + resnum[l].toString(); aresnum1="" ; repeat=0
                                                                         }
                                 else { aresnum =  aresnum +  aresnum1 + (resnum[l].toInt()).toString(); aresnum1="" ; repeat=0 }
                        } 
   
     
     if( resnum[l] >= 10 && resnum[l] <= 15 ) {                                                            
                                                if ( resnum[l]==10.0f) { aresnum1 =   "+" } 
                                                if ( resnum[l]==11.0f) { aresnum1 =  "-" } 
                                                if ( resnum[l]==12.0f) { aresnum1 =   "x" } 
                                                if ( resnum[l]==13.0f) { aresnum1 =   "/" } 
                                                if ( resnum[l]==14.0f) { if (l==0) {aresnum1 =   "="}
                                                                      if (l>0)  {aresnum1 =   ""  } 
                                                                    }  
                                                if ( resnum[l]==15.0f) { aresnum1 =  "," } 

                                                
                                                if (repeat ==1) { resnum[l-1] = resnum[l]; sresnum[l-1] = resnum[l-1];resnum[l]=30.0f; sresnum[l] = 30.0f; l=l-1  }     
                                                if (repeat ==0) {sresnum[l] = resnum[l] ; dot=0; repeat=1 }
                                                if (resnum[l] == 15.0f) {   dot=1; l=l-1 }
                                                   
                                                
                                              }
 
            else {                      
                           if (resnum[l] <= 9) {
                           j=0; 
                          while ( j <= 1)        //    ..
                              {                            
                            if(j==0) { sresnum[l] = resnum[l] }
                            if( (j==1) && ((l-1) >= 0) && (resnum[l-1] <=9) && dot==0)  { sresnum[l] = sresnum[l-1]*10 + resnum[l]; shift1=shift1+1 } 
                            if( (j==1) && ((l-1) >= 0) && (resnum[l-1] <=9) && dot==1)  { 
                                
                                while ( dj <= shift1 ) { dec=dec/10; dj=dj+1 }
                               
                                sresnum[l] = sresnum[l-1] +  resnum[l]*dec; shift1=shift1+1 
                                 dj=0; dec=1.0f
                            } 
                               
                            j=j+1 
                              
                              } 
                                            }
                   // if (resnum[l] == 15.0f) {   dot=1; l=l-1 }

                 }                
                        



                                                             
      if (l==0 &&  resnum[0] == 11.0f)  {  xxsign=-1 }                              
      if(l>0) { if (resnum[l] >= 10.0f && ((resnum[l-1] <= 9.0f) || (f==1) ) ) { sign = 1; shift1=0 ; ff=1 } }
      if( resnum[l] <= 9 && ff==0)  { iresnum=xxsign*sresnum[l]; xxsign=1 }              
         

   
       
                
      if(sign == 1  && resnum[l-1-shift1]==10.0f ) { iresnum= iresnum + xiresnum; xiresnum=0.0f; xsign=1
                                                  if (shift1==0 && dot == 0) { iresnum= iresnum +  resnum[l] } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum +  ( sresnum[l] - sresnum[l-1])   } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==11.0f) {  iresnum= iresnum + xiresnum; xiresnum=0.0f; xsign=-1
                                                  if (shift1==0 && dot == 0) { iresnum= iresnum - resnum[l] } 
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { iresnum= iresnum - ( sresnum[l] - sresnum[l-1] )  } 
                                                  ffr=0
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==12.0f) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2] * sresnum[l] ; ffr=1 
                                                     iresnum= iresnum - xsign * sresnum[l-2]  
                                                                          } 
      
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum * sresnum[l] } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum=   sresnum[l-shift1-2] * sresnum[l]  }
          
                                                                                   //  { xiresnum= xiresnum *  (sresnum[l] / sresnum[l-1] ) } 
                                                }
      
      if(sign == 1  && resnum[l-1-shift1]==13.0f) { if (shift1==0 && ffr==0) { xiresnum= xsign * sresnum[l-2] / sresnum[l] ; ffr=1 
                                                      iresnum= iresnum - xsign * sresnum[l-2]             
                                                                          } 
         
                                                 else { if (shift1==0 && ffr==1) { xiresnum= xiresnum / sresnum[l] } }
                                                  if (shift1 > 0 && resnum[l] <= 9 ) { xiresnum=  sresnum[l-shift1-2] / sresnum[l]  }  
                                                  
                                                } 
     


     
      if(l>=1 && resnum[l]==14.0f) { iresnum = iresnum + xiresnum; 
                                     if  ( Math.abs(iresnum - iresnum.toInt() ) > 0 ) 
          {   aresnum =  aresnum + "= "  + iresnum.toString()  ; resnum[l+1]=iresnum; sresnum[l+1]=resnum[l+1];  f=1;   sign=0; xiresnum=0.0f;  ffr=0; xsign=1;  l=l+1; repeat=0     }                       
          else
          {   aresnum =  aresnum + "= "  + (iresnum.toInt()).toString()  ; resnum[l+1]=iresnum; sresnum[l+1]=resnum[l+1];  f=1;   sign=0; xiresnum=0.0f;  ffr=0; xsign=1;  l=l+1; repeat=0   }
                                   }
  
           

     
                
     
            
         textviewid.text =   "  " + aresnum +  aresnum1 + "\n" +  
                             aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9] + aaa[10] + aaa[11] + aaa[12] + aaa[13] + aaa[14] + aaa[15] + aaa[16] + aaa[17] + aaa[18] + aaa[19]  +   
                             "\n" +  "cinn=" + cinn.toString() + "  " + "aresmin=" + aresmin   
                           
                           
             /*              " iresnum=" + iresnum +  " xiresnum=" + xiresnum +   " shift1=" + shift1 +  " sign=" + sign + " l=" + l + " f=" + f +  "dot=" + dot +
                "resnum=" + resnum[0] + " " + resnum[1] + " " + resnum[2] + " " + resnum[3] + " " + resnum[4] + " " + resnum[5] + 
                "sresnum=" + sresnum[0] + " " + sresnum[1] + " " + sresnum[2] + " " + sresnum[3] + " " + sresnum[4] + " " + sresnum[5] 
                           
              */



                           
                           //   "aresmin=" + aresmin  +  " " +

         l = l+1  // counter for next step


                           
                   
      // initial value initialization needed for all arrays to begin new symbol on next step - shift to upper in program bodyimport android.view.View
   
      
                                     }
      

                                    
     
                
            
                
                
                
            } // end   
            
            
            
          


      btnArrow.setOnClickListener {

         if (btn_numbers_hide == 0) { btn0.visibility = View.VISIBLE;   btn1.visibility = View.VISIBLE;  btn2.visibility = View.VISIBLE;  btn3.visibility = View.VISIBLE;  btn4.visibility = View.VISIBLE;
                                       btn5.visibility = View.VISIBLE;  btn6.visibility = View.VISIBLE;  btn7.visibility = View.VISIBLE;  btn8.visibility = View.VISIBLE;
                                       btn9.visibility = View.VISIBLE;   btn10.visibility = View.VISIBLE;  btn11.visibility = View.VISIBLE;  btn12.visibility = View.VISIBLE;  btn13.visibility = View.VISIBLE;
                                       btn14.visibility = View.VISIBLE;  btn15.visibility = View.VISIBLE;  btn16.visibility = View.VISIBLE;  btn17.visibility = View.VISIBLE;
                                      btn_numbers_hide = 1 }  
          else   { btn0.visibility = View.GONE; btn1.visibility = View.GONE; btn2.visibility = View.GONE; btn3.visibility = View.GONE; btn4.visibility = View.GONE; 
                   btn5.visibility = View.GONE; btn6.visibility = View.GONE; btn7.visibility = View.GONE; btn8.visibility = View.GONE; 
                   btn9.visibility = View.GONE; btn10.visibility = View.GONE; btn11.visibility = View.GONE; btn12.visibility = View.GONE; btn13.visibility = View.GONE; 
                   btn14.visibility = View.GONE; btn15.visibility = View.GONE; btn16.visibility = View.GONE; btn17.visibility = View.GONE;
              
                  btn_numbers_hide = 0 }

                                      
      
      }





        // functions

 fun btn_action(number: String) {

   data =  number + " vs " + res00.toString() +  "  [" + res00.toString() + "," + res11.toString() + "]" + "=" + minres0.toString() +
     "\n" +  "rcanglenn_[" +  res00.toString()  + "][" + lastindex_rcanglenn[res00] + "]" + " =arrayOf<Int>(" + data + ")" // what we want vs got as min of aresmin array 

     lastindex_rcanglenn[res00] = lastindex_rcanglenn[res00] +1 
    
// keep in storage  data which contains  aaa[j] as String  if result mismatch by hand command via button save on touchscreen
// crete and write to file
   
   // write  to external storage=sddisk
var myExternalFile:File = File(getExternalFilesDir(""), "d")
 j=0
            val fileOutputStream:FileOutputStream
            try {
                fileOutputStream = FileOutputStream( myExternalFile,  true )  // "true"  appends data to existing file or create new
                fileOutputStream.write(data.toByteArray())                    // without "true" (without second parameter) - rewrite existing f
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }catch (e: NumberFormatException){
                e.printStackTrace()
            }catch (e: IOException){
                e.printStackTrace()
            }catch (e: Exception){
                e.printStackTrace()
            }
        
   
  btn0.visibility = View.GONE;btn1.visibility = View.GONE;btn2.visibility = View.GONE; btn3.visibility = View.GONE; btn4.visibility = View.GONE; 
  btn5.visibility = View.GONE; btn6.visibility = View.GONE; btn7.visibility = View.GONE; btn8.visibility = View.GONE;
  btn9.visibility = View.GONE; btn10.visibility = View.GONE; btn11.visibility = View.GONE; btn12.visibility = View.GONE; btn13.visibility = View.GONE; 
  btn14.visibility = View.GONE; btn15.visibility = View.GONE; btn16.visibility = View.GONE; btn17.visibility = View.GONE; 
        btn_numbers_hide = 0
    }




      

        btn0.setOnClickListener { btn_action("0") }
        btn1.setOnClickListener { btn_action("1") }
        btn2.setOnClickListener { btn_action("2") } 
      
        btn3.setOnClickListener { btn_action("3") }     
        btn4.setOnClickListener { btn_action("4") }
        btn5.setOnClickListener { btn_action("5") }
        btn6.setOnClickListener { btn_action("6") } 
 
        btn7.setOnClickListener { btn_action("7") } 
        btn8.setOnClickListener { btn_action("8") }
        btn9.setOnClickListener { btn_action("9") }
 
        btn10.setOnClickListener { btn_action("10") } 
        btn11.setOnClickListener { btn_action("11") } 
        btn12.setOnClickListener { btn_action("12") }
        btn13.setOnClickListener { btn_action("13") }

        btn14.setOnClickListener { btn_action("14") } 
        btn15.setOnClickListener { btn_action("15") } 
        btn16.setOnClickListener { btn_action("16") }
        btn17.setOnClickListener { btn_action("17") }
       

      
              
        }   // end binding.apply
        }  // end onCreate
   
   
} // end MainActivity
