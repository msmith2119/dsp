

import java.util.regex.*

class ConstBlock extends  Block
  { 

 public String name 
 public double  ampl 
 public Signal s

 public static Pattern  PATT =   ~/Const\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public ConstBlock() {}




 public void  parse(String line) { 

   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Const" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       println "ssss params = "+params
       ampl = getParam("ampl",params)
       }
	else { println "no match" }  
  
}
 


  public double getSample() { 

   return ampl

}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("ampl="+ampl+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}