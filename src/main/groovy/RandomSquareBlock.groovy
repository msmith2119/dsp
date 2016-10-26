
import java.util.regex.*

class RandomSquareBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public double  tmin
 public tmax
 public double T

 public Signal s

  int pos 

 public static Pattern  PATT =   ~/RandomSquare\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public RandomSquareBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 

   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="RandomSquare" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       tmin = getParam("tmin",params)
       tmax = getParam("tmax",params)
       ampl = getParam("ampl",params)
      T = getParam("T",params) 
      s = SignalUtils.randomSquare(name,ampl,tmin,tmax,Block.fs,T) 
       
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
   sb.append("tmin="+tmin+"\n");
   sb.append("tmax="+tmax+"\n");
   sb.append("T="+T+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}