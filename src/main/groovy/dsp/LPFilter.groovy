package dsp;
/*
 * Created on Dec 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Morgan Smith
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LPFilter extends AnalogFilter{
	
	
	LPFilter(double fs, double fc ,double Q){
		super();
		this.fs = fs		
		SparseArray a = new SparseArray(3);
		SparseArray b = new SparseArray(3);
		
		calcCoeff(a,b,fc,Q);
		
		setA(a);
		setB(b);
		xbuf = new SampleBuffer(a.getMaxIndex()+1); 
		ybuf = new SampleBuffer(b.getMaxIndex()+1);
		
	}
	private void calcCoeff(SparseArray a,SparseArray b,double fc,double Q){
		
		double K = Math.tan(Math.PI*fc/fs);
		double den=K*K*Q+K+Q;
		 
		double c  = K*K*Q/den;
		
		double g = 0.20657
		a.setElem(0,0,g);
		a.setElem(1,1,2*g);
		a.setElem(2,2,g);
		c=(2*K*K*Q-2*Q)/den;
		
		b.setElem(0,0,1);
		c=(K*K*Q+Q-K)/den;
		b.setElem(1,1,-0.36953);
		b.setElem(2,2,0.19582);
		
		
		
			
		
	}

	public static void main(String[] args) {
		int fs = 41000;
		 double fc = 200.0;
		 int num = 2048;
		 double Q = 1.0;
		LPFilter lpf = new LPFilter(fs,fc,Q);
		lpf.writeImpulseResponse("C:\\exp\\z.txt", fs, num);
		
	}
}
