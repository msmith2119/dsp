
class SignalUtils { 



 public static Signal unitStep(String name, double a, double tau, double fs, double T) { 

     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
      y[i] = (t > tau ? a : 0 )
     }
     Signal s  = new Signal(name,y,dt)
     return s
  
}

 public static Signal randomSquare(String name, double a, double tmin, double tmax,double fs, double T) { 

     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]
    double v  =  -a 
    double  tx = tmin
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
    if(t > tx ) { 
	 v = v*(-1)
	 tx = t + tmin + (tmax - tmin)*Math.random()
     }
      y[i] =  v
     }
     Signal s  = new Signal(name,y,dt)
     return s
  
}

 public static Signal sin(String name,double a, double f, double phi,double fs, double T) { 

     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
    y[i] = a*Math.sin(2*Math.PI*f*t + phi)

     }
     Signal s  = new Signal(name,y,dt)
     return s

}
 public static Signal cos(String name,double a, double f, double fs, double T) { 

     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
    y[i] = a*Math.cos(2*Math.PI*f*t)

     }
     Signal s  = new Signal(name,y,dt)
     return s

}

 public static Signal amsin(String name,double a, double f, double m, double fm,double fs, double T) { 

     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt

    y[i] = a*(1+m*Math.sin(2*Math.PI*fm*t))*Math.sin(2*Math.PI*f*t)

     }
     Signal s  = new Signal(name,y,dt)
     return s

}

 public static Signal fmsin(String name,double a, double f, double fdev,double fm,double fs, double T) { 


     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
    double phi = 2*Math.PI*f*t - (fdev/fm)*Math.cos(2*Math.PI*fm*t)
    y[i] = a*Math.cos(phi)
     }
     Signal s  = new Signal(name,y,dt)
     return s

}
 public static Signal fm(String name,double a, double f, double fdev,Closure z,double fs, double T) { 


     double dt = 1/fs
    int n = T/dt
    double[] y = new double[n]
    double[] x = new double[2]
    x[0] = 0
    x[1] = 0  
    for(int i = 0; i < n ; i++ ) { 
    double t = i*dt
    x[0] = x[1] + z(t)*dt
    x[1] = x[0]
    double phi = 2*Math.PI*(f*t - fdev*x[0])
    y[i] = a*Math.cos(phi)
     }
     Signal s  = new Signal(name,y,dt)
     return s

}




}
