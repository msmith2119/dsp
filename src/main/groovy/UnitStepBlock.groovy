
import java.util.regex.*

class UnitStepBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double tau
 public double T

 public Signal s

  int pos 

 public static Pattern  PATT =   ~/UnitStep\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public UnitStepBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 

   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="UnitStep" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       tau = getParam("tau",params)
       ampl = getParam("ampl",params)
      T = getParam("T",params) 
      s = SignalUtils.unitStep(name,ampl,tau,Block.fs,T)
       
       
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
   sb.append("tau="+tau+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}