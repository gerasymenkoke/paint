package my.app.canvas


import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity





import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.Path



import my.app.MainActivity.Companion.currentBrush
import my.app.MainActivity.Companion.path
import my.app.MainActivity.Companion.shift
import my.app.MainActivity.Companion.w
import my.app.MainActivity.Companion.h


import my.app.cons.Pencil
import my.app.databinding.ActivityMainBinding

import kotlin.math.roundToInt
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.PI

import androidx.core.content.res.ResourcesCompat


import android.graphics.*
import android.view.ViewConfiguration


import my.app.canvas.SerializablePaint


class Data_File @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  :  View(context, attrs, defStyleAttr) {

   
 
    
  
    
    
 
    
    private val TOUCH_TOLERANCE = 0f

    private var mX = 0f
    private var mY = 0f

    private val dataPencil = mutableListOf<Pencil>()
    private val colorList = mutableListOf<Int>()
    private var i = -1
    private var arr = Array<Float>(10){0.0F}  
    private var x1 = 0f
    private var y1 = 0f
    private var x = 0f
    private var y = 0f
     private var rx1 = 1f
     private var ry1 = 1f
     private var res = 0.0f
     private var N = 1
     var isEraserOn = false

 private val paintBrush = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = currentBrush
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 16f
        alpha = 0xff
                                      }
      


    
    
     companion object {
        var xxx = "99"
        var yyy = "99"
        var aaa = Array<String>(10){"0"} 
        var zzz = 0
        var rxx = "1"
        var ryy = "1"

        var crx_ = Array<Int>(800){0}
        var cry_ = Array<Int>(800){0} 
        var xcrx_= Array<Int>(800){0} 
        var ycry_= Array<Int>(800){0} 
        var signx_= Array<Int>(800){2} 
        var signy_= Array<Int>(800){2} 

        
        
        var crxdy_ = Array<Float>(800){1000.0f} 
        var cangle_ =Array<Int>(800){0} 
        var canglepi_ =Array<Int>(800){0}  
        var cangle = 0
        var canglepi = 0 
        var cangle1 = -1
        var ci = 0
        var jci = 0
        var rx = 0f
        var ry = 0f
        var rxf = 0.0f
        var ryf = 0.0f
        var cjj=0

      //  var shift = 0 // user's a new symbol is beging draw on the screen
        var iii = 0 
         
        var j = 0
        var jj = 0             }
    
     
    
    
    
   
    

    }

  
    




  
