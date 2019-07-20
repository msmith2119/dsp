


class CELPUtils { 


static   double Rprod(double[] y, int l) { 

  double sum = 0
  for(int i = 0; i < y.length-l; i++ ) { 
    sum = sum + y[i]*y[i+l] 
 }

  return  sum     


}  
  
    static double[] Rvect(double[] y, int k ) { 
	
   double[] r = new double[k]  
   for ( int i = 0; i < k ; i++ ) { 
      r[i] = Rprod(y,i) 
   } 
     return r

  }

 static   double lsum(double[] a, double[] r, int n) { 

         double sum = r[n+1] 
        for(int j = 1; j < n+1; j++ ) { 
		double e = a[j]*r[n+1-j]
		sum+=e 
        }
 	return sum 
   }

 static double[] eval(double[] a, double[] y){

	   int k = a.length-1
	   for(int i = k ; i < y.length; i++){
	   	double sum = 0
	   	for(int j = 1; j <=k ;j++){
   			sum+=a[j]*y[i-j]
	     }
	     y[i] = -sum
	     }
	     return y
	     }



static double[] noisefill(double[] a, int n){

       double[] y = new double[n]
       for(int i = 0; i < y.length; i++){
       	    double sum = 0 
	    for(int j = 1; j<Math.min(a.length,i); j++){
	    	    sum +=a[j]*y[i-j]
	    }
       	    
	    y[i] = -sum + 0.05*(2*Math.random()  -1)
       }
       return y
}

 }



