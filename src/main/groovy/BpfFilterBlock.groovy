

import dsp.* 
import java.util.regex.*

class BpfFilterBlock extends Block { 


String name 
double fc 
double b
int N 
AnalogFilter filter 
Block input
String inNet


 public static Pattern  PATT =   ~/Bpf\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s*/ 

 public BpfFilterBlock() {} 

  public BpfFilterBlock(String name, double fc, double b ,int N) { 

  this.name = name 
  this.fc = fc 
  this.b = b
  this.N = N 
  filter = new FirBPF(Block.fs,fc,b,N)

}

 public void  parse(String line) { 

 
   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Bpf" 
      String paramsString =  matcher.group(1)
      inNet = matcher.group(2)
      outNet = matcher.group(3) 
      Map params  = getParams(paramsString)
       fc = getParam("fc",params)
       b =  getParam("b",params)
       N = (int)getParam("N",params)
       filter = new FirBPF(Block.fs,fc,b,N)
       
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
   sb.append("fc="+fc+"\n");
   sb.append("b="+b+"\n");
   sb.append("N="+N+"\n");
   sb.append("inNet="+inNet+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}
