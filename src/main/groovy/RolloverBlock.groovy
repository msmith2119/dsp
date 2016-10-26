

import dsp.* 
import java.util.regex.*

class RolloverBlock extends Block { 


String name 
Block input
String inNet



 public static Pattern  PATT =   ~/Rollover\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public RolloverBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Rollover" 
      String paramsString =  matcher.group(1)
      Map params = getParams(paramsString) 
      inNet = matcher.group(2)
      outNet = matcher.group(3)

}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}
 public double getSample() { 
 
  
 double xin = input.eval()
  double y = ((xin+0.5) % 1) - 0.5  
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