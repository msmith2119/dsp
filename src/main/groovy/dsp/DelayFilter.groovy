
package dsp 


public class DelayFilter  extends AnalogFilter { 
 
          public  int delay
          public double fb
        public DelayFilter ( double fs, int delay, double g,double fb) { 
 		super()
		this.delay = delay
		this.fb = fb
 		this.fs = fs;
		SparseArray a = new SparseArray(2);
		SparseArray b = new SparseArray(1);
		a.setElem(0,0,g)
		a.setElem(0,delay,fb)
		b.setElem(0,0,1)		
		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex()+1); 
		ybuf = new SampleBuffer(b.getMaxIndex()+1);
		}

     
     public double peek() { 
             return xbuf.get(delay-1)
    }
}