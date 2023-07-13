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
// import com.ferodev.simplepaint.MainActivity.Companion.btn


import com.ferodev.simplepaint.cons.Pencil
import com.ferodev.simplepaint.databinding.ActivityMainBinding

import kotlin.math.roundToInt
import kotlin.math.abs




class DrawPencil @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  :  View(context, attrs, defStyleAttr) {

    
  
   
  
       
  
    
 
    
    private val TOUCH_TOLERANCE = 4f

    private var mX = 0f
    private var mY = 0f

    private val dataPencil = mutableListOf<Pencil>()
    private val colorList = mutableListOf<Int>()
    private var i = 1
    private var arr = Array<Float>(10){0.0F}  
    private var x1 = 1f
    private var y1 = 1f
     private var rx1 = 1f
     private var ry1 = 1f 
     private var N = 1
    
    
     companion object {
        var xxx = "99"
        var yyy = "99"
        var aaa = Array<String>(10){"0"} 
        var zzz = 0
        var rxx = "1"
        var ryy = "1"

        var crx_ = Array<Float>(10){0.0f}
        var cry_ = Array<Float>(10){0.0f} 

        
        var rx = 0.0f
        var ry = 0.0f



         
        var j = 0
                        }
    
    
    
    
    
    
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
        
       
        
        val x = event1.x 
        val y = event1.y 
        
        xxx = x.toString()       
        yyy = y.toString()    

      
        if (j >=1 && j<=9)
             {   rx=(((x-x1)/x * 100.0).roundToInt() / 100.0).toFloat() 
                 ry=(((y-y1)/y * 100.0).roundToInt() / 100.0).toFloat() 
                
                // rx=(x-x1)
                // ry=(y-y1)

                 
                 //ry=(((y-y1)/y1 * 100.0).roundToInt() / 100.0).toFloat()
         rxx = rx.toString()       
         ryy = ry.toString()
         
      if ( ((rx==rx1) && (ry==ry1)) || ((x==x1) && (y==y1)) ) { j=j }
         
          else    {   if (N==10)
                     { crx_[j] = rx
                      cry_[j] = ry
                      x1=x
                      y1=y   
                      rx1=rx
                      ry1=ry
                      j = j + 1
                      N=1   
                     }    
                   else{ N=N+1 }  
                                   
                  }
                           
         
             }

        if (j==0)     
        {  
       
     rx=(((x-0)/x* 100.0).roundToInt() / 100.0).toFloat() 
     ry=(((y-0)/y* 100.0).roundToInt() / 100.0).toFloat() 
        crx_[j] = rx
        cry_[j] = ry
        x1=x
        y1=y
        rx1=rx
        ry1=ry
       j = j + 1 
              
        }   
       
           
      
   


        
        
        when (event1.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                  
                
                touchMove(x, y)
         
            
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
