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






class DrawPencil @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  :  View(context, attrs, defStyleAttr) {

    
  
   
  
       
  
    
 
    
    private val TOUCH_TOLERANCE = 4f

    private var mX = 0f
    private var mY = 0f

    private val dataPencil = mutableListOf<Pencil>()
    private val colorList = mutableListOf<Int>()
    private val i = 1
  
    //val arrayname = arrayOf(1, 2, 3, 4, 5)
   
    
     companion object {
        var xxx = "99"
        var yyy = "99"
        var aaa = Array<String>(10){0} 
        var zzz = 0
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
       
        
        when (event1.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                  
                
                touchMove(x, y)
               xxx = x.toString()
               
                
                arr = IntArray(10) { i -> i }
                aaa = arr.toString() 
               i = i+1


                
              // btnPencil.setImageResource(R.drawable.ic_selected_pencil)
          //     com.ferodev.simplepaint.canvas.DrawPencil.draw_pencil.visibility = View.INVISIBLE
               
                
              //  zzz = zzz +1
                
                
                 //  btn.performClick()
                
                
                
                
                
                
             
              //  btnPencil.performClick()
                
             //  textviewid.text = xxx
                //print (xx)
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
