

import dsp.* 
import java.util.regex.*


 class ModAmpBlock extends Block  { 

String name 
Block input
String inNet
double max
double gain

 public static Pattern  PATT =   ~/ModAmp\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 


  public ModAmpBlock () {} 


 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="ModAmp" 
      String paramsString =  matcher.group(1)
      Map params = getParams(paramsString) 
      inNet = matcher.group(2)
      outNet = matcher.group(3)
      max = getParam("max",params,1.0)
      gain = getParam("gain",params,1.0) 
}
	else { println "no match" }  
  
}
   public double getSample() {
    
    double xin = input.eval()
    	 double y = 0 
       y = max*Math.tanh(gain*xin)
  return y
}



 public void bind(Circuit circuit) { 
 	input = circuit.nodeMap[inNet]
	
}


  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("max="+max+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}