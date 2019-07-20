package dsp;
/*
 * Created on Apr 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author msmith
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SampleBuffer {

	
	 private int depth ;
	 private int cur_pos; 
	 private double[] samples; 
	

          
	 public SampleBuffer(int depth) { 
	 		
	 		this.depth=depth; 
	 		samples = new double[depth];
	 		cur_pos=0; 
	 		
	 }
	 
         public void load(double[] y) { 
	 int n = Math.min(depth,y.length)
	 for(int i = 0; i < n; i++) { 
	 	 samples[i] = y[i]
	 }

         }
	 public void push(double v) { 
	 		
	 	    if(depth< 1 )
	 	    	return ; 
	 	    
	 		samples[cur_pos]=v; 
	 		
	 		if(cur_pos >= depth -1)	
	 				cur_pos =0; 
	 		else 
	 				cur_pos++; 
	 		
	 }
	 
	 public double get(int i ) { 
	 		
	 	
	 		if(i >depth -1  )  
	 				return 0; 
	 		
	 		
	 		if(i <=cur_pos -1)
	 				return samples[cur_pos-i-1];
	 		
	 		
	 		
	 	  else 
	 	  		return samples[cur_pos-i-1+depth];
	 	  
}
	 public void set(int i ,double val) { 
 		
 	
 		if(i >depth -1  )  
 				return ; 
 		
 		
 		if(i <=cur_pos -1)
 				samples[cur_pos-i-1]=val;
 		
 		
 		
 	  else 
 	  		samples[cur_pos-i-1+depth]=val;
 	  
}
	 
	 public int getDepth(){return depth;}


        public String toString() { 

	StringBuffer sb = new StringBuffer() 
	
	
	for(int i = 0; i <depth; i++ ) { 
		sb.append(get(i)+" ")
       }
       
		sb.append("\n")
		return sb.toString()
        }	 
	public static void main(String[] args) {
		
		
		SampleBuffer sb = new SampleBuffer(20);
		
		for(int i = 0; i < 20;i++ ) { 
		 double v = (double)i;
		 
		 sb.push(v); 
		 
		}
		
		for(int i = 0 ;i<40;i++){
				
				double x = sb.get(i);
				
				System.out.println("i="+i+"s="+x);
		}
		
		
	}
}
