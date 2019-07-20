

import dsp.* 
import java.util.regex.*

class VTCBlock extends Block { 


String name 
Block input
String inNet
List vin
List vout

 public static Pattern  PATT =   ~/VTC\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public VTCBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="VTC" 
      String paramsString =  matcher.group(1)
     Map params  = getParams(paramsString)
      inNet = matcher.group(2)
      outNet = matcher.group(3)
      String csvFile = getStringParam("csv",params)
      load(csvFile) 
}
	else { println "no match" }  
  
}


 public void load(String  fname ) { 
 vin   = new ArrayList<Double>()
 vout = new ArrayList<Double>()	
new File(fname).splitEachLine(",") {f ->
  vin.add(Double.valueOf(f[0]))
  vout.add(Double.valueOf(f[1]))
}
 }
 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}
 public double getSample() { 
 double xin = input.eval()
   int n = vin.size()
   //println "vout.size="+vout.size() 
   double dv = (vin[n-1] - vin[0])/(n-1)
   int m = (xin -vin[0])/dv
   if(m >=n-2) m=n-2 
   if(m <0 ) m = 0
   //println "m="+m
   double sl = (vout[m+1]  - vout[m])/dv 
   double vx = vout[m] + sl*(xin-vin[m])
 return vx
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
