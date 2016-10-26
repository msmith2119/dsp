

import java.util.regex.*

class FmBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double f
 public double pdev
 public double T
 public String src
 public Signal s

  int pos 

 public static Pattern  PATT =   ~/Fm\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public FmBlock() {}


 public void bind(Circuit circuit) {
   Block b = circuit.nodeMap[src]
   s = SignalUtils.fm(name,ampl,f,pdev,b.getSignal().getFunc(),Block.fs,T) 
 } 

 public void  parse(String line) { 

   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Fm" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       ampl = getParam("ampl",params)
       f = getParam("f",params)
       pdev = getParam("pdev",params)
       src = getStringParam("src",params)
      T = getParam("T",params) 
       
}
	else { println "no match" }  
  
}
 


  public double getSample() { 

   if(pos >= s.values.length)
   	  pos = 0

   return s.values[pos++]
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("ampl="+ampl+"\n");
   sb.append("f="+f+"\n");
   sb.append("pdev="+pdev+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}