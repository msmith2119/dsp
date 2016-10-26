

import dsp.* 
import java.util.regex.*

class PDBlock extends Block { 


String name 
Block input1
Block input2

String inNet1
String inNet2

 public static Pattern  PATT =   ~/PD\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s+(\w+)\s*/ 

 public PDBlock() {} 



 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="PD" 
      String paramsString =  matcher.group(1)
      inNet1 = matcher.group(2)
      inNet2 = matcher.group(3)
      outNet = matcher.group(4) 
}
	else { println "no match" }  
  
}

 public void bind(Circuit circuit) { 
 	input1 = circuit.nodeMap[inNet1]
 	input2 = circuit.nodeMap[inNet2]
	
	
}
 public double getSample() { 
  
 double xin1 = input1.eval()
 double xin2 = input2.eval() 
 double pe=  xin1- xin2;
 double p2 = pe + 0.5 
double y = 2*(Math.abs((p2 % 1)) - 1/2); 
 return y
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("inNet1="+inNet1+"\n");
   sb.append("inNet2="+inNet2+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
