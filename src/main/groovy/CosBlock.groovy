

import dsp.* 
import java.util.regex.*

class CosBlock extends Block { 


String name 
Block input
String inNet


 public static Pattern  PATT =   ~/Cos\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public CosBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Cos" 
      String paramsString =  matcher.group(1)
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
 double y = Math.cos(2*Math.PI*xin)
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