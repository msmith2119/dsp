package dsp

public class GenFilter extends AnalogFilter {

	
public double[] a
public double[] b

	 
	
	public GenFilter(double fs, double[] a, double[] b) {

		super();
		this.fs = fs
		this.a = a
		this.b = b
		SparseArray A = new SparseArray(a.length);
		SparseArray B = new SparseArray(b.length);

		calcCoeff(A, B);

		setA(A);
		setB(B);
		xbuf = new SampleBuffer(A.getMaxIndex() + 1);
		ybuf = new SampleBuffer(B.getMaxIndex() + 1);
	}
private void calcCoeff(SparseArray A,SparseArray B){
		
		 for(int i = 0; i < a.length; i++ ) { 
		 A.setElem(i,i,a[i])
            }

	    for(int i = 0;i < b.length; i++ ) { 
                 B.setElem(i,i,b[i])
	    }
	}
}
 
