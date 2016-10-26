import flanagan.complex.*
import flanagan.math.*
import com.sigmethod.*

class Signal { 

     String name
    double[] values
    double  dt

    public Signal(String name,int n, double dt ) { 
     this.dt = dt
     values = new double[n]
     this.name = name
}

    public Signal(String name,double[] y, double dt ) { 
    this.dt = dt 
    values = y
   this.name = name
}

public static Signal fromFile(String csvFile, double fs){

	CsvReader reader = new CsvReader(fs)
	reader.load(csvFile)
	Signal s = reader.getSignal()
	return s
}
public static Signal fromWaveFile(String waveFile){

       TypeConverter converter = TypeConverter.getInstance()
       FileInputStream fis = new FileInputStream(waveFile) 
       byte[]  header = AudioUtils.readHeader(fis)
       double fs  = RiffFileHeader.getInt(header,24)
       Block.fs = fs
       int size = RiffFileHeader.getInt(header,40)
       byte[] body = AudioUtils.readBody(fis,size) 
       double[] y = converter.convertByteArray(body)
       Signal s =  new Signal(waveFile,y,1/fs)
	return s
}


 public Signal add(Signal other) {

  int n = Math.min(values.length,other.values.length) 
  double[] y = new double[n]
 for(int i = 0; i < n ; i ++ ) { 
 	 y[i]  = values[i] + other.values[i]
 }
 Signal s = new Signal(name+"+"+other.name,y,dt)
 return s
}
  void  plot() {
  println "NAME="+name 
  String xlabel = "time(sec)" 
  String ylabel ="ampl"
  int n = values.length
  double[] time = new double[values.length] 
  for(int i = 0 ;i < n; i++ ) { 
     time[i] = i*dt
     }
  PlotUtils.plotSingle(name,xlabel,ylabel,name,time,values)

}

  void plotFreq(double fmax) { 

  String xlabel = "freq(Hz)" 
  String ylabel ="ampl"

  FourierTransform fft = new FourierTransform(values)
  
  fft.transform()
  Complex[] fy = fft.getTransformedDataAsComplex()
  double df = 1/(dt*fy.length)
  int n = fy.length/2
  double[] f = new double[fy.length]
  double[] y = new double[fy.length]
 int M = Math.min((int)fmax/df,n)
  for(int i = 0; i < M; i++ ) { 
         f[i] = df*i;
	 y[i] = fy[i].abs()
}
	

  PlotUtils.plotSingle(name,xlabel,ylabel,name,f,y)
  }

  public Closure getFunc() { 

  	 Closure f = { t -> 
	  int N = values.length
	  int n  = t/dt 
	  int M = n/N
	  n = n - M*N
	  return values[n]
  }
}
}