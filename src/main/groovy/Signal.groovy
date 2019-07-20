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


  public double getDuration() { return values.length*dt } 

public static Signal fromFile(String csvFile, double fs){

	CsvReader reader = new CsvReader(fs)
	reader.load(csvFile)
	Signal s = reader.getSignal()
	return s
}
public static Signal[] fromWaveFile(String waveFile){

       String name = waveFile.substring(0,waveFile.indexOf("."))
       TypeConverter converter = TypeConverter.getInstance()
       FileInputStream fis = new FileInputStream(waveFile) 
       byte[]  header = AudioUtils.readHeader(fis)
       int numChannels = (int)RiffFileHeader.getShort(header,RiffFileHeader.NUM_CHANNELS)
       println "numChannels="+numChannels
       double fs  = RiffFileHeader.getInt(header,RiffFileHeader.SAMPLE_RATE)
       Block.fs = fs
       int size = RiffFileHeader.getInt(header,RiffFileHeader.DATA_SIZE)
       int numSamples = size/(2*numChannels)
       byte[][] body = AudioUtils.readBody(fis,size,numChannels)
       double[][] y = new double[numChannels][numSamples]
       for(int i = 0; i <numChannels; i++ )  { 
       	       y[i]= converter.convertByteArray(body[i])
       }   
       Signal[] s = new Signal[numChannels] 
       for(int i = 0; i < numChannels; i++ ) { 
       	       s[i] = new Signal(name+"_c"+i,y[i],1/fs)
       }
	return s
}

public void saveWave() { 

 int fsample = (int)(1/dt)
 RiffFileHeader header = new RiffFileHeader(fsample,values.length,1)
 String fileName = name+".wav" 
BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName))
bos.write(header.render(),0,RiffFileHeader.HEADER_SIZE)	
TypeConverter converter = TypeConverter.getInstance() 					
byte[] bytes = converter.convertDoubles(values)
bos.write(bytes,0,bytes.length)
bos.close()
}


public static Signal fromArray(String name, double[][] y, double dt) { 

        int numChannels  

 }

 public Signal scale(double a) { 

   for(int i = 0; i < values.length; i++ ) { 
   	   values[i] = a*values[i]
   }
  return this
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