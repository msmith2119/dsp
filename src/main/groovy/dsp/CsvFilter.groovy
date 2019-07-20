package dsp

public class CsvFilter extends AnalogFilter {

	
	 
	
	public CsvFilter(double fs,double[] h) {

		super();
		this.fs = fs
		SparseArray a = new SparseArray(h.length);
		SparseArray b = new SparseArray(1);

		calcCoeff(a, b, h);

		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex() + 1);
		ybuf = new SampleBuffer(b.getMaxIndex() + 1);
	}
private void calcCoeff(SparseArray a,SparseArray b,double[] h){
		

		for(int i = 0; i < h.length; i++ ) { 
			a.setElem(i,i,h[i])
			}
		 b.setElem(0, 0, 1);
 		
       
	
	}
}
 
