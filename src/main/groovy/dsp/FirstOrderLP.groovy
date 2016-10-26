package dsp

public class FirstOrderLP extends AnalogFilter {
	
	FirstOrderLP(double fs, double fc ){
		super();
		this.fs = fs;
		SparseArray a = new SparseArray(2);
		SparseArray b = new SparseArray(2);
		
		calcCoeff(a,b,fc);
		
		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex()+1); 
		ybuf = new SampleBuffer(b.getMaxIndex()+1);
		
	}
	private void calcCoeff(SparseArray a,SparseArray b,double fc){
		
		double K = Math.tan(Math.PI*fc/fs);
		K = 0.5
		
	 
		
		a.setElem(0,0,K);
		a.setElem(1,1,K);
	
	
		
		b.setElem(0,0,1);
		b.setElem(1,1,-0.3);
		
		
		
			
		
	}

	public static void main(String[] args){
		
		int fs = 41000;
		 double fc = 200.0;
		 int num = 2048;
		 double Q = 1.0;
		FirstOrderLP lpf = new FirstOrderLP(fs,fc);
		lpf.writeImpulseResponse("C:\\exp\\z.txt", fs, num);
		
	}

}
