

import dsp.* 
import java.util.regex.*

class CsvFilterBlock extends Block { 


String name 
AnalogFilter filter 
Block input
String inNet


 public static Pattern  PATT =   ~/CsvFilter\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public GenFilterBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="CsvFilter" 
      String paramsString =  matcher.group(1)
      inNet = matcher.group(2)
      outNet = matcher.group(3) 
      Map params  = getParams(paramsString)
      String csvFile = getStringParam("csv",params)
      double fs = getParam("fs",params)
      Block.fs = fs
      CsvReader reader = new CsvReader(fs)
      reader.load(csvFile)
      Signal  s =  reader.getSignal()
      double[] y = s.values
      double[]  h = new double[y.length-1]
      for(int i = 0;i < y.length-1;i++ ) { 
      	      h[i]=y[i]-y[i+1]
      }
      filter = new CsvFilter(fs,h)

       
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}
 public double getSample() { 
  
 double xin = input.eval()

 double y = filter.evaluate(xin)
 return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
