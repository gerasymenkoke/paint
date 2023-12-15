

package com.ferodev.simplepaint.canvas


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



import com.ferodev.simplepaint.MainActivity.Companion.currentBrush
import com.ferodev.simplepaint.MainActivity.Companion.path
import com.ferodev.simplepaint.MainActivity.Companion.shift
import com.ferodev.simplepaint.MainActivity.Companion.w
import com.ferodev.simplepaint.MainActivity.Companion.h


import com.ferodev.simplepaint.cons.Pencil
import com.ferodev.simplepaint.databinding.ActivityMainBinding

import kotlin.math.roundToInt
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.PI




class DrawPencil @JvmOverloads constructor(
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
     private var rx1 = 1
     private var ry1 = 1
     private var res = 0.0
     private var N = 1
     
    
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
        var cangle1 = -1.0
        var ci = 0
        var jci = 0
        var rx = 0
        var ry = 0
        var rxf = 0.0f
        var ryf = 0.0f
        var cjj=0

      //  var shift = 0 // user's a new symbol is beging draw on the screen
        var iii = 0 
         
        var j = 0
        var jj = 0             }
    
    
    
    
    
    
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

    fun updateColor(newColor: Int) {
        paintBrush.color = newColor
    }

    private fun touchStart(x: Float, y: Float) {
        val p = Pencil(currentBrush, path)
        dataPencil.add(p)
        colorList.add(currentBrush)
        path.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        path.lineTo(mX, mY)
    }

    override fun onTouchEvent(event1: MotionEvent): Boolean {
        
       
        
         x = event1.x
         y = event1.y  
        
        xxx = x.toString()       
        yyy = y.toString()    
    
   
                  
                 if(shift==1) { j=0; ci=0; shift=0 }

                 res=(((w+h)/2)*0.3/100) // in percents of average resolutions
        
           
             
            if   ( (ci==1 && ( (x-x1)!=0f || (y-y1)!=0f)  ) || ((j<=799) && ( x!=0f || y!=0f) && (Math.abs(x-x1) > res)  && (Math.abs(y-y1) > res) ))
        {
                
                        rx= ( ( (x-x1)/1) * 1).toInt()  
                        ry= ( ( (y-y1)/1) * 1).toInt()   
                  
               
                         
                  if ( ry==0)  {  
                  
                  rxf=(rx/0.00001f)*(180/PI).toFloat()
                  cangle=    (90 + atan(rxf)).toInt()  
                  canglepi=  (90 - atan(rxf)).toInt()  
                               }

                  
                   else { 
                  rxf=(rx/ry).toFloat()
                  cangle=   (90 + atan(rxf)*(180/PI)).toInt()  
                  canglepi= (90 - atan(rxf)).toInt()  
                        }

                     
                 
                
                  cangle_[ci] = cangle 
                  canglepi_[ci] = canglepi 
                 crx_[ci] = (rx * 1).toInt()    
                 cry_[ci] = (ry * 1).toInt()    
                 xcrx_[j] = x.toInt()  
                 ycry_[j] = y.toInt()  
                 if (rx>=0) signx_[j] = 1
                    else  signx_[j] = -1          
                 if (ry>=0) signy_[j] = 1
                    else  signy_[j] = -1 
            
                 ci=ci+1
         
                 x1=x
                 y1=y  
                
                            
                
            
              j = j + 1

        }
                     
        

                        
 
      
        
        when (event1.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart( x.toFloat(), y.toFloat() )
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                  
                
                touchMove( x.toFloat(), y.toFloat() )
         
            
                invalidate()
                                     
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        for (p in dataPencil) {
            paintBrush.color = p.color
           canvas.drawPath(p.path!!, paintBrush)
            invalidate()
        }
    }

    fun undo() {
        if (dataPencil.size != 0) {
            dataPencil.removeAt(dataPencil.size - 1)
            invalidate()
        }
    }
    
       

}
