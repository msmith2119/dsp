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
 public class AnalogFilter extends Element  {

	 public SparseArray A 
	 public SparseArray B
	 protected double fs;	
	 protected SampleBuffer xbuf  
	 protected SampleBuffer ybuf  

	 private double value;




	 public AnalogFilter() { 



	 }
	 public AnalogFilter(SparseArray a,SparseArray b){ 

		 A=a; 
		 B=b;

		 xbuf = new SampleBuffer(a.getMaxIndex()+1); 
		 ybuf = new SampleBuffer(b.getMaxIndex()+1);



	 }

	 public void setA(SparseArray a ){ A=a; }
	 public void setB(SparseArray b) { B=b;}
	 public SparseArray getA()  { return A;}
	 public SparseArray getB() { return B;}
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
		
          
		value = getOutput(din);
		
	    return value;
		
	}
	 

	
	 
	public  double getOutput (double input){ 
		
			double sx = 0; 
			double sy = 0; 
			
	           xbuf.push(input) 
	           ybuf.push(0.0)

	           
	           int[] indexes = A.getIndexes();
	           
	           for(int i = 0; i < indexes.length; i++){
	           	int ind = indexes[i];
	           	
	           	double old = xbuf.get(ind);
	           	
	           	int index = indexes[i];
	           	double a = A.getElem(i);
	           	double x = xbuf.get(indexes[i]);
	           	
	           	sx+=A.getElem(i)*xbuf.get(indexes[i]);
	           	
	           }

	           indexes = B.getIndexes(); 
	           double b0 = B.getElem(0);
	           for(int i = 1; i <indexes.length;i++ ) {
	           	
	           	sy+=B.getElem(i)*ybuf.get(indexes[i]);
	           	
	           }
	           
	           

	           
	          
	           double y = (sx-sy)/b0; 

	           ybuf.set(0,y)
	           return y; 
	           
	}
	           
	
	

     public void plotFreq(int n, boolean logscale ) { 

      int M = n/2
      double df  = fs/n
      println "FS="+fs
      double[] f = new double[M]
      double[] yp = new double[M]

     for(int i = 0; i < M; i++ ) { 
       
       double om = 2*Math.PI*i/n 
       Complex z = new Complex(Math.cos(om),-Math.sin(om))
       Complex a = evalPoly(A,z)
       Complex b = evalPoly(B,z)
       Complex y = a.over(b)
 
	f[i]=df*i
	yp[i] = (logscale ? 20*Math.log10(y.abs()):y.abs())      
      }

      String xlabel = "freq(Hz)"
      String ylabel = "mag"

  PlotUtils.plotSingle("gain",xlabel,ylabel,"v",f,yp) 
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
