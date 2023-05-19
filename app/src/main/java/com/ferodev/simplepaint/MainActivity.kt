package com.ferodev.simplepaint


import android.graphics.Path

import androidx.appcompat.app.AppCompatActivity
import com.ferodev.simplepaint.databinding.ActivityMainBinding

     
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
  
class MainActivity : AppCompatActivity(), View.OnTouchListener {
  
    // Declaring ImageView, Bitmap, Canvas, Paint,
    // Down Coordinates and Up Coordinates
    private lateinit var mImageView: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f
  
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
  
        // Initializing the ImageView
        mImageView = findViewById(R.id.image_view_1)
  
        // Getting the current window dimensions
        val currentDisplay = windowManager.currentWindowMetrics
        val dw = currentDisplay.bounds.width()
        val dh = currentDisplay.bounds.height()
  
        // Creating a bitmap with fetched dimensions
        bitmap = Bitmap.createBitmap(dw, dh, Bitmap.Config.ARGB_8888)
          
        // Storing the canvas on the bitmap
        canvas = Canvas(bitmap)
          
        // Initializing Paint to determine 
        // stoke attributes like color and size
        paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 10F
        
        // Setting the bitmap on ImageView
        mImageView.setImageBitmap(bitmap)
          
        // Setting onTouchListener on the ImageView
        mImageView.setOnTouchListener(this)
    }
  
    // When Touch is detected on the ImageView, 
    // Initial and final coordinates are recorded
    // and a line is drawn between them.
    // ImagView is updated
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
  
            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y
                canvas.drawLine(downX, downY, upX, upY, paint)
                mImageView.invalidate()
            }
        }
        return true
    }
}
