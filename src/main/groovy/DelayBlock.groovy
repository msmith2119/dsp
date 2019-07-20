

import dsp.* 
import java.util.regex.*

class DelayBlock extends Block { 


String name 
int N 
double fb
double a
AnalogFilter filter 
Block input
String inNet


 public static Pattern  PATT =   ~/Delay\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public DelayBlock() {} 

 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Delay" 
      String paramsString =  matcher.group(1)
      inNet = matcher.group(2)
      outNet = matcher.group(3) 
      Map params  = getParams(paramsString)
       N = (int)getParam("N",params)
       fb = getParam("fb",params,1.0)
       a = getParam("a",params,1.0)
       filter = new DelayFilter(Block.fs,N,a,fb)
       
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 

input = circuit.nodeMap[inNet]
	
}

public double eval() { 

 if(evaluated) { 
    return value
  }
 

  evaluated = true
  value = getSample() 

  return value
}
 public double getSample() {
  double xin = input.eval()
  double y = filter.evaluate(xin)
 
  return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("N="+N+"\n");
   sb.append("fb="+fb+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
