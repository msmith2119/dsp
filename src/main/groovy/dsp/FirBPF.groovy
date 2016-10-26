package dsp

public class FirBPF extends AnalogFilter {

	
	 
	
	public FirBPF(double fs, double fc, double bw, int n) {

		super();
		this.fs = fs
		SparseArray a = new SparseArray(n+1);
		SparseArray b = new SparseArray(1);

		calcCoeff(a, b, fc, bw,n);

		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex() + 1);
		ybuf = new SampleBuffer(b.getMaxIndex() + 1);
	}
private void calcCoeff(SparseArray a,SparseArray b,double fc,double bw,int n){
		
	    double m1 = 2*Math.PI*(fc + bw)/fs	
	    double m2 = 2*Math.PI*(fc - bw)/fs
	    int p = n/2 
	        double[] c = new double[n+1]
		for(int i = 0; i <= n; i++){
			if(i==p) { c[i] = 4*bw/fs } 
			else {  
			c[i]= (Math.sin(m1*(i-p)) - Math.sin(m2*(i-p)))/(Math.PI*(i-p))
			}
		}

		for(int i = 0; i <= n; i++ ){
			a.setElem(i,i,c[i])
		}
		 b.setElem(0, 0, 1);
 		
       
	
	}
}
 
