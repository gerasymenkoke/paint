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

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter




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

import my.app.canvas.Data_File.Companion.rcanglenn_
import my.app.canvas.Data_File.Companion.init0
import my.app.canvas.Data_File.Companion.init14
import my.app.canvas.Data_File.Companion.init59
import my.app.canvas.Data_File.Companion.init1015


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
//    private var rcanglenn_ = Array(20) { Array(300){ Array<Int>(20){360} } }  
    private var rcanglenn0_ = Array(20) { Array(60){ Array<Int>(20){360} } }  
    
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
    private var lastindex_rcanglenn = Array<Int>(17){0} 
    private var shift_lastindex_rcanglenn = Array<Int>(17){0} 
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

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             
        setContentView(binding.root)

        supportActionBar?.hide()

       
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
 
        w = displayMetrics.widthPixels
        h = displayMetrics.heightPixels



         



        
        
        binding.apply {



       init0()
       init14()
       init59()
       init1015()

 
   
      
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

                lastindex_rcanglenn[jj] = jjj // count of index quantity for each of 15 variants: rcanglenn[x, y] ,  i.e. it's y                          
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

 fun btn_action(number: String,  number00:Int) {

  

  //   shift_lastindex_rcanglenn[number00] = shift_lastindex_rcanglenn[number00] + 1 


                var mylastindexFile:File = File(getExternalFilesDir(""), "d" + number00.toString())

             //   val filename = fileName.text.toString()  
                if(filename.toString()!="" && filename.toString().trim()!=""){  
                   // var fileInputStream: FileInputStream? = null  
                   var fileInputStream = openFileInput(mylastindexFile)  
                   var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)  
                   val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)  
                   val stringBuilder: StringBuilder = StringBuilder()  
                   var text: String? = null  
                   while ({ text = bufferedReader.readLine(); text }() != null) {  
                       stringBuilder.append(text)  
                   }  
                   //Displaying data on EditText  

                     lastindex_rcanglenn[number00] =  stringBuilder
                    
                    //fileData.setText(stringBuilder.toString()).toString()  
               }else{  
                 
                    lastindex_rcanglenn[number00] = lastindex_rcanglenn[number00] + 1
                    
                    var mylastindexFile:File = File(getExternalFilesDir(""), "d" + number00.toString())
 
            val fileOutputStream:FileOutputStream
            try {
                fileOutputStream = FileOutputStream(mylastindexFile )  // "true"  appends data to existing file or create new
                fileOutputStream.write(lastindex_rcanglenn[number00].toString().toByteArray())                    // without "true" (without second parameter) - rewrite existing f
            } catch (e: FileNotFoundException){
                e.printStackTrace()
            }catch (e: NumberFormatException){
                e.printStackTrace()
            }catch (e: IOException){
                e.printStackTrace()
            }catch (e: Exception){
                e.printStackTrace()
            }
                    
                    //Toast.makeText(applicationContext,"file name cannot be blank",Toast.LENGTH_LONG).show()  
               }  
         


  
 
  
//     lastindex_rcanglenn[number00] = lastindex_rcanglenn[number00] + 1

     var formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
     var current = LocalDateTime.now().format(formatter)
     
   data =  "//" + number + " vs " + res00.toString() +  "  [" + res00.toString() + "," + res11.toString() + "]" + "=" + minres0.toString() + 
     "   " + current.toString() +   
     "\n" +  "rcanglenn_[" +  number00.toString()  + "][" + lastindex_rcanglenn[number00].toString() + "]" + "=arrayOf<Int>(" + data + ")" +  "\n" + "\n" // what we want vs got as min of aresmin array 

    // lastindex_rcanglenn[res00] = lastindex_rcanglenn[res00] +1 
    
// keep in storage  data which contains  aaa[j] as String  if result mismatch by hand command via button save on touchscreen
// crete and write to file
   
   // write  to external storage=sddisk
var myExternalFile:File = File(getExternalFilesDir(""), "d")
 
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




      

        btn0.setOnClickListener { btn_action("0", 0) }
        btn1.setOnClickListener { btn_action("1", 1) }
        btn2.setOnClickListener { btn_action("2", 2) } 
      
        btn3.setOnClickListener { btn_action("3", 3) }     
        btn4.setOnClickListener { btn_action("4", 4) }
        btn5.setOnClickListener { btn_action("5", 5) }
        btn6.setOnClickListener { btn_action("6", 6) } 
 
        btn7.setOnClickListener { btn_action("7", 7) } 
        btn8.setOnClickListener { btn_action("8", 8) }
        btn9.setOnClickListener { btn_action("9", 9) }
 
        btn10.setOnClickListener { btn_action("10", 10) } 
        btn11.setOnClickListener { btn_action("11", 11) } 
        btn12.setOnClickListener { btn_action("12", 12) }
        btn13.setOnClickListener { btn_action("13", 13) }

        btn14.setOnClickListener { btn_action("14", 14) } 
        btn15.setOnClickListener { btn_action("15", 15) } 
        btn16.setOnClickListener { btn_action("16", 16) }
        btn17.setOnClickListener { btn_action("17", 17) }
       

      
              
        }   // end binding.apply
        }  // end onCreate
   
   
} // end MainActivity
