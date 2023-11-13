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
  
    private var dir_cr =  Array<Int>(800){10}  // array of directions current drawpencil (0,1,2,3)
    private var dir_crn =  Array<Int>(20){10}  // array of of directions current drawpencil after n_normalization: compressing -delete repeated 0,1,2,3 and 10 inside
    private var dir_rr = Array(20) { Array(60){ Array<Int>(10){10} } }  // array of directions for n_etalons(40) for every sample(10pcs: 0,1,...9,+..=)
    private var dir_res =  Array(20){ Array<Int>(60){0} }  // array quantity of  difference between directions: current(crn_) and each of etalons (rr)
    // crx_ , cry_ - import from drawpencil.kt as companion object
    private var oldx = 0.00f 
    private var oldy = 0.00f 
    private var oldxdy = 0.00f
    private var crn_ = Array<Float>(800){0.00f}  // array of of crx_[j] + cry_[j] after n_normalization
    private var crnx_ = Array<Float>(800){0.00f}  // array of of crx_[j]  after n_normalization
    private var crny_ = Array<Float>(800){0.00f}  // array of of cry_[j] after n_normalization

    private var crnnx_ = Array<Float>(800){0.00f}
    private var crnny_ = Array<Float>(800){0.00f}
    
    
    private var crnxdy_ = Array<Float>(800){0.00f}  // array of of crxdy_[j] after n_normalizatio
    
    private var canglen_ = Array<Float>(800){0.00f} 
    private var canglenpi_ = Array<Float>(800){0.00f} 
    private var canglenn_ = Array<Float>(800){-0.1f} 
    private var canglennpi_ = Array<Float>(800){-0.1f} 
    private var rcanglenn_ = Array(60) { Array(20){ Array<Float>(10){-0.1f} } }  
    
    
    private var cin = 0
    private var cinn = 0
    
    
    private var rr =  Array(20) { Array(60){ Array<Float>(20){10.0f} } }  //  array of value for n_etalons(30) for every sample(10pcs)c
    private var rrx =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  //  array of value for x n_etalons(30) for every sample(10pcs)
    private var rry =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  //  array of value for y n_etalons(30) for every sample(10pcs)
    private var rangle =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  // 
    
    private var xdyrr =  Array(20) { Array(60){ Array<Float>(20){0.0f} } }  // 

   
    private var dir_resmin =  Array(800){ Array<Int>(2){20} }  // array of index dir_rr  for each etalon with min difference(coincedence): couple - dir_resmin[0][0]=jj; dir_resmin[0][1]=jjj
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


    /*       
 
// Filling sample array
// 0
     jj = 0                  

     xdyrr[0][0]=arrayOf<Float>(  -0.112f,  3.17f,  -0.133f,  5.191f,  -0.121f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)


 


    


     
 // Filling sample array
// 1
      jj = 1                  

     xdyrr[1][0]=arrayOf<Float>(  -1.107f,  -0.423f,  -0.133f,  5.191f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
     xdyrr[1][1]=arrayOf<Float>(  -1.102f,  -0.759f,   9.189f,  4.347f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

   
   


     
     
     
// Filling sample array 
// 2
      jj = 2                  

     xdyrr[2][0]=arrayOf<Float>(  -0.593f,  20.649f, -0.202f, 0.612f, -0.314f,  4.387f, 0.0f, 0.0f, 0.0f, 0.0f)
     


 




            
      
// Filling sample array 
// 3
      jj = 3                  

    xdyrr[3][0]=arrayOf<Float>(  -1.172f,  3.521f, -0.215f, 3.998f, -0.412f,  4.628f, 0.0f, 0.0f, 0.0f, 0.0f)

  


     

      
// Filling sample array 
// 4
      jj = 4                  
  
 xdyrr[4][0]=arrayOf<Float>(  -0.452f,  2.78f, -2.276f, 0.796f, -0.581f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
 xdyrr[4][1]=arrayOf<Float>(  -0.452f,  0.846f, -2.686f, 0.628f, -0.796f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
 xdyrr[4][2]=arrayOf<Float>(  -0.325f,  0.855f, 1.304f,  0.0f,   0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
 xdyrr[4][3]=arrayOf<Float>(  -0.418f,  2.121f, 0.841f,  0.0f,   0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      
      
// Filling sample array 
// 5
      jj = 5    
            
   xdyrr[5][0]=arrayOf<Float>(  -0.311f,  -1.656f,  3.339f,  -0.576f,  3.518f,  -0.276f,  3.781f, -9.297f, 4.674f, 0.0f)
   xdyrr[5][1]=arrayOf<Float>(  -0.159f,  -2.521f,  3.59f,   -0.238f,  2.08f,   -0.251f,  7.656f,  0.0f,   0.0f, 0.0f)
   xdyrr[5][2]=arrayOf<Float>(  -0.266f,  -0.78f,  2.677f,  -0.354f,  3.389f,   -0.248f,  0.0f,   0.0f,    0.0f, 0.0f)
     
      

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
  
  



*/
      

// Filling sample array 
// +
      jj = 10                

   
      rcanglenn_[10][0]=arrayOf<Float>(   179.0f,  138.0f,  100.0f, -0.1f, -0.1f, -0.1f,-0.1f,-0.1f,-0.1f,-0.1f )  
      
      
// Filling sample array 
// -
      jj = 11               
  
        rcanglenn_[11][0]=arrayOf<Float>( 174.0f, -0.1f,-0.1f,-0.1f,-0.1f,-0.1f,-0.1f,-0.1f,-0.1f,-0.1f )
      


       
/*

      
// Filling sample array 
// x
      jj = 12               

        xdyrr[12][0]=arrayOf<Float>(  0.655f,  -0.031f,  -1.595f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)



      
// Filling sample array 
// /
      jj = 13              

      xdyrr[13][0]=arrayOf<Float>(  -1.518f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[13][1]=arrayOf<Float>(  -0.861f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

      
  


      

// Filling sample array 
// =
      jj = 14            

      xdyrr[14][0]=arrayOf<Float>(  -8.153f,  6.229f, -2.17f,   2.869f, -6.353f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[14][1]=arrayOf<Float>(  -9.307f,  3.091f, -2.251f, -6.968f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[14][2]=arrayOf<Float>(   2.748f, -5.078f, -2.904f,  4.506f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[14][3]=arrayOf<Float>( -10.148f, -1.546f,  4.577f, -6.626f, 2.58f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[14][0]=arrayOf<Float>(  -2.203f,  4.465f,  0.0f,    0.0f,   0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)

 
      
      
// Filling sample array 
// ,
      jj = 15            

      xdyrr[15][0]=arrayOf<Float>(  1.745f,  0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
      xdyrr[15][1]=arrayOf<Float>(  1.447f,  -0.224f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f)



    */

      
      



      
      
      
      btnPencil.setOnClickListener {
       
          
          
          // Untuk mengganti dari false menjadi true
                isPencilIconClicked = !isPencilIconClicked
     
                if (isPencilIconClicked) { // ini untuk mengecek apakah isPencilIconClicked sudah true valuenya

                    
// sign of 4 directions level   (crx_[j] cry_[j]) to dir_crxy[0-3] = level 0-3 directions Int

                     

      
    // normalize cr[j] - erase repeats and  ??? 10 inside


                    
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
        ( signx_[j] == signx_[kk]  &&   signy_[j] == signy_[kk]  &&   (Math.abs( (cangle_[j]) - (cangle_[kk]) ) >= 25) ) ||
        ( signx_[j] != signx_[kk]  &&   signy_[j] != signy_[kk]  &&   (Math.abs( (cangle_[j]) - (cangle_[kk]) ) >= 25) ) ||
        ( signx_[j] != signx_[kk]  &&   signy_[j] == signy_[kk]  &&   ( (180 - Math.abs( (cangle_[j]) - (cangle_[kk]) )) >= 25) ) ||
        ( signx_[j] == signx_[kk]  &&   signy_[j] != signy_[kk]  &&   ( (180 - Math.abs( (cangle_[j]) - (cangle_[kk]) )) >= 25) ) 
        
       )
                             
                                         {   
                                          
                                         if ( cin<=0 || Math.abs(xcrx_[j]-xcrx_[kk]) > resxy || Math.abs(ycry_[j]-ycry_[kk]) > resxy )
                                             {    
                                         kk=j
                                         crnnx_[cinn]= crx_[j]; crnny_[cinn]= cry_[j]; 
                                         canglenn_[cinn]= cangle_[j]
                                         canglennpi_[cinn]= canglepi_[j]    
                                             cinn=cinn+1
                                      
                                             }
                                         }
                                                                            
                                 j=j+1
                      } 

j=cin
if (cinn==0) {   crnnx_[cinn]= crx_[j]; crnny_[cinn]= cry_[j]; 
                                         canglenn_[cinn]= cangle_[j]
                                         canglennpi_[cinn]= canglepi_[j]    
             }

              

// output as text current painted number in direction sequence
        
       j=0;
       if (cinn > 0)  cinn=cinn - 1
while (j >=0 && j<=cinn) {
if (j<=cinn) { aaa[j] =  "  [" + j.toString() + "]=" + crnnx_[j].toString() + "," + crnny_[j].toString() + ";;" + canglenn_[j].toString() + "/" + canglennpi_[j].toString()  }
    // + crnnx_[j].toString() + "," + crnny_[j].toString() + ";;" 
    // + canglenn_[j].toString() + "/" + canglennpi_[j].toString() 
 // aaa[j] =  "  [" + j.toString() + "]="  +  canglenn_[j].toString() 
//  aaacr[j] =  "[" + j.toString() + "]=" + "[" + crx_[j].toString() + "," + cry_[j].toString() + "] "  

    //  aaacr[j] =  "[" + j.toString() + "]=" + dir_cr[j].toString() + "  "
    
                    j=j+1
                     }





   jj=0
   minres = 1000.00f  
while (jj >=0 && jj<=19)  // index of symbols(numbers and operations)  0, 1 ..
{
          jjj=0

           minres = 1000.00f 
    while (jjj >=0 && jjj<=59) // quantity of variants for each/all numbers
            {
    
                j=0
               
                while (j >=0 && j<=9 && (canglenn_[j]!=-0.1f || rcanglenn_[jj] [jjj] [j] !=-0.1f) ) {  
                  
                 if (  Math.abs ( canglenn_[j]  - rcanglenn_[jj] [jjj] [j] )  >   Math.abs ( canglennpi_[j]  - rcanglenn_[jj] [jjj] [j] ) )     

                       {   resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   canglennpi_[j]  - rcanglenn_[jj] [jjj] [j] )  }

                    else { resmin[jj] [jjj] =  resmin[jj] [jjj] + Math.abs (   canglenn_[j]  - rcanglenn_[jj] [jjj] [j] )    }                        
                         
                      
                    
                     resmin[jj] [jjj] = ((resmin[jj] [jjj] * 100.0).roundToInt() / 100.0).toFloat() 
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
  
           

     
                
       // "  " + aresnum +  "  " + aresnum1 + "\n" +
                //     "res=[" + res0 + "][" + res1 +  "]"   +  
            //     " dir_resmin=" + min + ",    [" + dir_resmin[0][0] + "]" + "[" + dir_resmin[0][1] + "]" + "/[" + dir_resmin[1][0] + "]" + "[" + dir_resmin[1][1] + "]" + "/[" + dir_resmin[2][0] + "]" + "[" + dir_resmin[2][1] + "]"    +  "/[" + dir_resmin[3][0] + "]" + "[" + dir_resmin[3][1] + "]" +  
         textviewid.text =   "  " + aresnum +  "  " + aresnum1 + "\n" +
                    "\n" +  aaa[0] + " " +aaa[1] + " " + aaa[2] + " " + aaa[3] + " " + aaa[4] + " " + aaa[5] + " " + aaa[6] + " " + aaa[7] + " " + aaa[8] + " " + aaa[9] + " " + aaa[10] + " " + aaa[11] + " " + aaa[12] + " " + aaa[13] + " " + aaa[14] + " " + aaa[15] + " " + aaa[16] + " " + aaa[17] + " " + aaa[18] + " " + aaa[19] + " " + aaa[20] + " " + aaa[21] + " " + aaa[22] + " " + aaa[23] + 
                     "\n" + "  ci=" + ci.toString() + "  cin=" + cin.toString() +  "  cinn=" + cinn.toString() +
                     "\n" +  "aresmin=" + aresmin  
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
