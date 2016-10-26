

import java.util.regex.*

class FmSinBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double f
 public double fm
 public double fdev
 public double T

 public Signal s

  int pos 

 public static Pattern  PATT =   ~/FmSin\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public FmSinBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 


   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="FmSin" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       ampl = getParam("ampl",params)
       f = getParam("f",params)
       fdev = getParam("fdev",params)
       fm = getParam("fm",params)
      T = getParam("T",params) 
      s = SignalUtils.fmsin(name,ampl,f,fdev,fm,Block.fs,T) 
       
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
   sb.append("fdev="+fdev+"\n");
   sb.append("fm="+fm+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}