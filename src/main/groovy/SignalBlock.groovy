

import java.util.regex.*

class SignalBlock extends  Block
  { 

 public String name 
 public String fname
 public String wav 
 String type


 public Signal s

  int pos 

 public static Pattern  PATT =   ~/Signal\(([^\(\)]+)\)\s+(\w+)\s*/ 

  public SignalBlock() {}


 public void bind(Circuit circuit) { } 

 public void  parse(String line) { 


   Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Signal" 
      String paramsString =  matcher.group(1)
      outNet = matcher.group(2) 
      Map params  = getParams(paramsString)
       fname  = getStringParam("fname",params)
       type = getStringParam("type",params)
       if(type == null) { type = "wav" }  
       wav=getStringParam("wav",params)
       if(type == "wav") { 
       Signal[] sigs = Signal.fromWaveFile(wav)
       s = sigs[0]
       }
      else { 
       double T = getParam("T",params,0.0)
       s = SignalUtils.noise(name,Block.fs,T);
      }

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
   sb.append("fname="+fname+"\n");
   sb.append("wav="+wav+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}

}