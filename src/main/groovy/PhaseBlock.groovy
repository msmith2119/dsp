

import dsp.* 
import java.util.regex.*

class PhaseBlock extends Block { 


String name 
Block input
String inNet
double f
double phi
double yold
int count

 public static Pattern  PATT =   ~/Phase\(([^\(\)]+)\)\s+(\w+)\s*/ 

 public PhaseBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Phase" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2)
      Map params  = getParams(paramsString)
       f = getParam("f",params)
       phi = getParam("phi",params,0)
       yold = phi - f/Block.fs
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}
 public double getSample() { 
  println "count="+count
 double y = (f/Block.fs)*count + phi
 y = Math.abs(y % 1)
 count++
 println "y="+y
 return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("outNet="+outNet+"\n");
   sb.append("f="+f+"\n")
   sb.append("phi="+phi+"\n")
  return sb.toString() 

}
}