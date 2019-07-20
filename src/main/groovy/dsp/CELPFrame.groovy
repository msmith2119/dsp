


class CELPFrame { 

 int n
 int k
 double[] y
 double[] yprev
 double[] yest 
 double[] R 
 double[] a
 double Ek 
 double lambda 
 static  int SUBFRAME_SIZE = 10
 double[][] code = new double[2][SUBFRAME_SIZE] 
 


   void init(double[] y, int n) { 
      k = 1
      this.n = n 
      this.y = y 
      R = CELPUtils.Rvect(y,n+1)
      a = new double[n+1]
      a[0] = 1 
      a[1] = -R[1]/R[0]
      Ek  = R[0] + R[1]*a[1] 

      code = new double[SUBFRAME_SIZE]
      for(int i = 0; i < SUBFRAME_SIZE;i++){
      	      code[i] = 0.05*(2*Math.random()  -1)
      }

      code[0] = [0,0,0,0,0,0,0,0,0,0]
      code[1] = [0.1,0,0,0,0,0,0,0,0,0]
 
      yprev = new double[n+1]
  } 


   public double[]   genPrevSamples() { 
      double[] yy = new double[n+1]
      for(int i = 1; i < yy.length; i++){
        yy[i] = y[y.length-i]
      }
      return yy 
}
  void calc() { 

    while(k < n){
    	    kPlusOne()
    }

 }
  void kPlusOne() { 
  int L = k/2 + k%2
  lambda = -CELPUtils.lsum(a,R,k)/Ek
  for(int j = L ; j >0 ; j--){
     double temp = a[j] + lambda*a[k-j+1]
     a[k-j+1] = a[k-j+1] + lambda*a[j]
     a[j] = temp
  }  	   
    a[k+1] = lambda
    Ek = (1-lambda*lambda)*Ek
    k++
 }

 public void estimate(){
 
	yest =  new double[y.length]
	System.arraycopy(y,0,yest,0,k)
	yest = CELPUtils.eval(a,yest)
}

public void noise() { 
     yest = CELPUtils.noisefill(a,y.length)

 }
 

public double frameError(int frame, double[] yy) { 

   double s = 0 

  for(int i = 0; i < SUBFRAME_SIZE; i++){
  	  double error = y[frame + i] - yy[i]
	  s+=error*error
    }
  
   return Math.sqrt(s)
    
}
public void processSubFrames() { 

yest =  new double[y.length]

int numFrames = y.length/SUBFRAME_SIZE

for(int i = 0; i <numFrames ; i++){

  double[] f1 = calcSubFrame(i,0)
  double e1 = frameError(i,f1)
  double[] f2 = calcSubFrame(i,1)
  double e2 = frameError(i,f2)
  if(e2 < e1 ){
  setFrame(i,f2)
}
  else { 
//println "picked 2"
  setFrame(i,f1)
 }
  

}

}

public void setFrame(int frame, double[] vals) { 

for(int i = 0; i < vals.length; i++){
	yest[frame*SUBFRAME_SIZE + i] = vals[i]
}

}
public double getSample(int index){

 if(index < 0 ){
       return yprev[-index]
  }
   return y[index]
}


public calcSubFrame( int frame, int codeIndex){
     
	double[] yy = new double[SUBFRAME_SIZE]

	

       for(int i = 0; i < SUBFRAME_SIZE; i++){
       	    double sum = 0 
	    for(int j = 1; j<a.length; j++){
	    	    sum +=a[j]*getSample(frame*SUBFRAME_SIZE+i-j)
	    }
       	    
	    yy[i] = -sum + code[codeIndex][i]
       }

       return yy
       

}
public processSubFrame( int frame, int codeIndex){


       for(int i = 0; i < SUBFRAME_SIZE; i++){
       	    double sum = 0 
	    for(int j = 1; j<a.length; j++){
	    	    sum +=a[j]*getSample(frame*SUBFRAME_SIZE+i-j)
	    }
       	    
	    yest[frame*SUBFRAME_SIZE+i] = -sum + code[codeIndex][i]
       }
       

}
 
   public  String toString()  { 

   	   StringBuffer sb = new StringBuffer()
	   sb.append("n="+n+"\n")
	   sb.append("k="+k+"\n")
	   sb.append("a="+a+"\n")
	   sb.append("Ek="+Ek+"\n")
	   sb.append("lambda="+lambda+"\n") 
	   sb.append("R="+R)
   }


 }

 
