

import dsp.* 
import java.util.regex.*


class DerivBlock extends Block { 


String name 
AnalogFilter filter 
Block input
String inNet


 public static Pattern  PATT =   ~/Deriv\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public DerivBlock() {} 

 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Deriv" 
      String paramsString =  matcher.group(1)
      inNet = matcher.group(2)
      outNet = matcher.group(3) 
      Map params  = getParams(paramsString)
       filter = new Differentiator(Block.fs)
       
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