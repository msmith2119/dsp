

class CsvReader { 


public double fs 
public double dt 
public int m 
public String fname

List time
List y

public CsvReader(double fs) { 

dt = 1/fs
time = new ArrayList<Double>() 
y = new ArrayList<Double>() 

}

 void load(String fname) {
	 this.fname = fname 
new File(fname).splitEachLine(",") {f ->
  time.add(Double.valueOf(f[0]))
  y.add(Double.valueOf(f[1]))
}

}

 public Signal  getSignal() { 
 
int n = y.size() 
println "n="+n
 m = time.get(n-1)/dt
int p = 0
double[] v = new double[m]
for(int i = 0; i < m; i++ ) { 
  double t = dt*i 
  p = findIndex(t,p)
  //println "p="+p
  v[i] = y.get(p) 
}
Signal s = new Signal(fname,v,dt)
return s
}

int   findIndex(double t, int p ) { 

    int n = y.size()
    if(t  < time.get(0) ) { 
	return 0
	}
    if( t >= time.get(n-1) ) { 
	return  n-1 
     }
    for(int i = p; i<n-1; i++ ) { 
       if(t >= time.get(i) && t < time.get(i+1)) { 
		return i
       }
   }
 }

}
