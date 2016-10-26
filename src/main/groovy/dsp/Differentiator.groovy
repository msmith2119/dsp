


package dsp 

public class Differentiator extends AnalogFilter { 

public Differentiator(double fs) {
 		super()
 		this.fs = fs;
		SparseArray a = new SparseArray(4);
		SparseArray b = new SparseArray(1);
		
		calcCoeff(a,b);
		
		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex()+1); 
		ybuf = new SampleBuffer(b.getMaxIndex()+1);


}


	private void calcCoeff(SparseArray a, SparseArray b ) {

		a.setElem(0,0,-1/16)
		a.setElem(1,2,1) 
		a.setElem(2,4,-1) 
		a.setElem(3,6,1/16) 

                b.setElem(0,0,1) 
	}
}
