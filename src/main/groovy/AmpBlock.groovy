

import dsp.* 
import java.util.regex.*

class AmpBlock extends Block { 


String name 
Block input
String inNet
double gain


 public static Pattern  PATT =   ~/Amp\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public Mod1Block() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Amp" 
      String paramsString =  matcher.group(1)
      Map params = getParams(paramsString) 
      inNet = matcher.group(2)
      outNet = matcher.group(3)
      gain = getParam("gain",params) 
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}
 public double getSample() { 
  
 double xin = input.eval()
 double y = gain*xin
 return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("gain="+gain+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}