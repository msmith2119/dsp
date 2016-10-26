

import dsp.* 
import java.util.regex.*

class DelayBlock extends Block { 


String name 
int N 
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
       filter = new DelayFilter(Block.fs,N)
       
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 

input = circuit.nodeMap[inNet]
	
}

public double eval() { 
 
 if(count == 0 ) {
 	  count++ 
	  circuit.terminals.add(this)
 	  return filter.peek()
          
	  }

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
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
