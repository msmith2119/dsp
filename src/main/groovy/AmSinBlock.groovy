

import java.util.regex.*

class AmSinBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double f
 public double m
 public double fm
 public double T

 public Signal s

  int pos 

 public static Pattern  PATT =   ~/AmSin\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public SinBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 


   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="AmSin" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       ampl = getParam("f",params)
       f = getParam("f",params)
       m = getParam("m",params)
       fm = getParam("fm",params)
      T = getParam("T",params) 
      s = SignalUtils.amsin(name,ampl,f,m,fm,Block.fs,T)
       
       
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
   sb.append("m="+m+"\n");
   sb.append("fm="+fm+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}