package dsp

public class FirLPF extends AnalogFilter {

	
	 
	
	public FirLPF(double fs, double fc, int n) {

		super();
		this.fs = fs
		SparseArray a = new SparseArray(2*n+1);
		SparseArray b = new SparseArray(1);

		calcCoeff(a, b, fc, n);

		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex() + 1);
		ybuf = new SampleBuffer(b.getMaxIndex() + 1);
	}
private void calcCoeff(SparseArray a,SparseArray b,double fc,int n){
		
	    double nu = 2*fc/fs;
	        double[] c = new double[n+1]
		for(int i = 0; i <= n; i++){
			if(i==0) { c[i] = nu } 
			else {  
			c[i]= Math.sin(nu*i*Math.PI)/(i*Math.PI);
			}
		}
		for(int i = 0; i <=n ; i++ ) { 
		  println "c["+i+"]="+c[i]
		}
		for(int i = 0; i <= 2*n; i++ ){
			int k = Math.abs(n-i)
			a.setElem(i,i,c[k])
		}
		 b.setElem(0, 0, 1);
 		
       
	
	}
}
 
