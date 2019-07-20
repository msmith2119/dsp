package dsp;
/*
 * Created on Apr 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.*; 
import java.util.*;
import flanagan.complex.*; 
import com.sigmethod.*;
/**
 * @author msmith
  *
  * TODO To change the template for this generated type comment go to
  * Window - Preferences - Java - Code Style - Code Templates
  */
 public class CustomFilter extends Element  {


     public CustomFilter(double fs,int nx, int ny, String eqn){
       this.fs = fs
       xbuf = new SampleBuffer(nx) 
       ybuf = new SampleBuffer(ny) 
       this.eqn = eqn

}


	 protected double fs;	
	 protected SampleBuffer xbuf  
	 protected SampleBuffer ybuf  
         protected String eqn         
	 


	 public SampleBuffer getXbuf() { return xbuf;}
	 public SampleBuffer getYbuf() { return ybuf;}




        public short evalShort(short sin){
	TypeConverter converter = TypeConverter.getInstance()
         double d = converter.convertShortValue(sin)
	 double y = evaluate(d)
	 short s = converter.convertDoubleValue(y)
	 return s
        }
	public double evaluate(double din){
		
          
		double value = getOutput(din);
		
	    return value;
		
	}
	 
        public double filterCalc() { 
	//  return 0.5*xbuf.get(0) + 0.5*xbuf.get(500)
          return Eval.xy(xbuf,ybuf,eqn)
       } 

	public  double getOutput (double input){ 
		

			
	           xbuf.push(input) 
	           ybuf.push(0.0)

		   double y = filterCalc();              


	           ybuf.set(0,y)
	           return y; 
	           
	}
	           
	
	

     public void plotFreq(int n, boolean logscale ) { 
     }


       
     public void plotImpulse(int n) { 


     	    	 double[] y = new double[n]; 
		 
		 y[0] = getOutput(1.0)

		 for(int i = 1; i < n; i++ )  {

		 	 y[i] = getOutput(0.0);
		 }

		
		String xlabel = "time(sec)"
		String ylabel = "ampl"
		  double[] time = new double[n] 
  		
		  for(int i = 0 ;i < n; i++ ) { 
     		  	  time[i] = i/fs
     			  }
		
  PlotUtils.plotSingle("impulse",xlabel,ylabel,"v",time,y) 
       }

	

      Complex evalPoly(SparseArray a, Complex z ) { 

      	      int[] indexes = a.getIndexes()
	      int m = indexes.length
      	      int n = a.getMaxIndex()
      	      Complex S =  new Complex(a.getValue(n),0.0)
	      for(int i = n-1;i >=0 ; i--) { 
	      	      S = S.times(z) + new Complex(a.getValue(i),0)
	      }
	      return S
      }
      

}
