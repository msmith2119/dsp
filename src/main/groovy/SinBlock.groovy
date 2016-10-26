
import java.util.regex.*

class SinBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double  f
 public phi
 public double T

 public Signal s

  int pos 

 public static Pattern  PATT =   ~/Sin\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public SinBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 

   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Sin" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       f = getParam("f",params)
       phi = getParam("phi",params,0)*Math.PI/180
       ampl = getParam("ampl",params)
      T = getParam("T",params) 
      s = SignalUtils.sin(name,ampl,f,phi,Block.fs,T)
       
       
}
	else { println "no match" }  
  
}
 


  public double getSample() { 

   if(pos >= s.values.length)
   	  pos = 0

   return s.values[pos++]
}

 public Signal getSignal() { return s } 

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("ampl="+ampl+"\n");
   sb.append("f="+f+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}