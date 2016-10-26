

import dsp.* 
import java.util.regex.*

class Mod1Block extends Block { 


String name 
Block input
String inNet


 public static Pattern  PATT =   ~/Mod1\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public Mod1Block() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Mod1" 
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
double y = xin % 1
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
