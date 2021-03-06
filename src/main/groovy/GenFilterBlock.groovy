

import dsp.* 
import java.util.regex.*

class GenFilterBlock extends Block { 


String name 
CustomFilter filter 
Block input
String inNet


 public static Pattern  PATT =   ~/Filter\(([\S]+)\)\s+(\w+)\s+(\w+)\s*/
 

 public GenFilterBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Filter" 
      String paramsString =  matcher.group(1)
      inNet = matcher.group(2)
      outNet = matcher.group(3) 
      Map params  = getParams(paramsString)
      int nx = getIntParam("nx",params,1)
      int ny = getIntParam("ny",params,1)
      String eqn = getStringParam("eqn",params)
       filter = new CustomFilter(Block.fs,nx,ny,eqn)       
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
   sb.append("a="+filter.A+"\n");
   sb.append("b="+filter.B+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
