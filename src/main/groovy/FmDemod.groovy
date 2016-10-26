
import dsp.* 

class FmDemod { 

    public SampleBuffer filtIBuf
    public SampleBuffer filtQBuf 
    public SampleBuffer filtOBuf 
    public SampleBuffer filtBPFBuf 
    public SampleBuffer delayLineI
    public SampleBuffer delayLineQ
    public double[]  lpf_fq 
    public double[]  lpf_fo
    public double[]  bpf
    public double fsample 
    public double fcarrier
    public int M
    public int n
    public int N 
    public double dphi

    public static double FQ_BW = 100.00 
    public static double FO_BW = 100.00
    public static double FBPF_BW = 100.0   
    public static int FQ_M = 100
    public static int FO_M = 100
    public static int BPF_N = 100
   public FmDemod(double fs, double fc) { 
       this.fsample = fs 
       this.fcarrier = fc
       N = (int)(fsample/fcarrier)
       dphi = 2*Math.PI*fcarrier/fsample
       lpf_fq = calcCoeff(FQ_BW,FQ_M)
       lpf_fo = calcCoeff(FO_BW,FO_M)
       bpf = calcBPFCoeff(fc,FBPF_BW,BPF_N)
       filtIBuf  = new SampleBuffer(2*FQ_M+1) 
       filtQBuf  = new SampleBuffer(2*FQ_M+1)
       filtOBuf  = new SampleBuffer(2*FO_M+1)
       filtBPFBuf  = new SampleBuffer(BPF_N+1)
       delayLineI = new SampleBuffer(3)
       delayLineQ = new SampleBuffer(3)
   }
  public double[]  process(double[] xin) { 
  	 
	 
        double[] y = new double[xin.length]
        double i1 
	double q1 
	for(int i = 0; i < xin.length; i++ ) {
		double x = filtBPF(xin[i]) 
                double phi = phase() 
		i1 = filtI(x*Math.cos(phi))
		q1 = filtQ(x*Math.sin(phi))
		
		delayLineI.push(i1)
		delayLineQ.push(q1)
		double qp = delayLineQ.get(0) - delayLineQ.get(2)
		double ip = delayLineI.get(0) - delayLineI.get(2)
		double plus = qp*delayLineI.get(1) 
		double minus = ip*delayLineQ.get(1) 
		double ph = plus - minus 
                y[i] = 20*filtO(ph)
		//y[i] = ph
        }
        
         //Signal s = new Signal("yy",y,1/fsample)
	 //s.plotFreq(fsample/2)
	 return y 
  }

     double filtI(double xin) {
     	    int m = 2*FQ_M + 1
	    filtIBuf.push(xin) 
	    double s = 0 
	    for(int i = 0; i < m; i++ ) {
	    s+=lpf_fq[i]*filtIBuf.get(i)
	    }
	    return s 
    }
     double filtQ(double xin) {
     	    int m = 2*FQ_M + 1
	    filtQBuf.push(xin) 
	    double s = 0 
	    for(int i = 0; i < m; i++ ) {
	    s+=lpf_fq[i]*filtQBuf.get(i)
	    }
	    return s 
    }
     double filtO(double xin) {
     	    int m = 2*FO_M + 1
	    filtOBuf.push(xin) 
	    double s = 0 
	    for(int i = 0; i < m; i++ ) {
	    s+=lpf_fo[i]*filtOBuf.get(i)
	    }
	    return s 
    }
     double filtBPF(double xin) {
     	    
	    filtBPFBuf.push(xin) 
	    double s = 0 
	    for(int i = 0; i <=BPF_N; i++ ) {
	    s+=bpf[i]*filtBPFBuf.get(i)
	    }
	    return s 
    }
     double phase() { 

     	    double phi =  dphi*n 
	    n++ 
	    if(n>=N) { 
	    	     n = 0
		     }
              return phi
     }


private  double[]  calcCoeff(double f,int size){
		
	        double nu = 2*f/fsample;
	        double[] c = new double[size+1]
		double[] coeff = new double[2*size+1]
		for(int i = 0; i <= size; i++){
			if(i==0) { c[i] = nu } 
			else {  
			c[i]= Math.sin(nu*i*Math.PI)/(i*Math.PI);
			}
		}

		for(int i = 0; i <= 2*size; i++ ){
			int k = Math.abs(size-i)
			coeff[i] = c[k]
		}
	  return coeff	
	}
private  double[]  calcBPFCoeff(double fc,double bw,int n){
		
	    double fs = fsample
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
		return c
	
	}


	
}
