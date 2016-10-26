
package dsp 


public class DelayFilter  extends AnalogFilter { 
 
          public  int delay

        public DelayFilter ( double fs, int delay) { 
 		super()
		this.delay = delay
 		this.fs = fs;
		SparseArray a = new SparseArray(1);
		SparseArray b = new SparseArray(1);
		a.setElem(0,delay,1)
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