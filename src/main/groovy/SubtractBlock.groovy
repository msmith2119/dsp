


import java.util.regex.*


class SubtractBlock extends Block { 

public String name
public double gain
public Block input1
public Block input2
public String inNet1
public String inNet2

 public static Pattern  PATT =   ~/Subtract\(([^\(\)]+)\)\s+(\w+)\s+(\w+)\s+(\w+)\s*/ 

 public SubtractBlock () {} 

 public void parse(String line) { 
       Matcher matcher = PATT.matcher(line)
   if(matcher.matches()) {
      name="Subtract" 
      String paramsString =  matcher.group(1)
      inNet1 = matcher.group(2)
      inNet2 = matcher.group(3)
      outNet = matcher.group(4) 
       Map params  = getParams(paramsString)
       gain = getParam("gain",params,1.0)
       
        }
	
}

    public void bind(Circuit circuit) { 
 	input1 = circuit.nodeMap[inNet1]
 	input2 = circuit.nodeMap[inNet2]
	
}


 public double getSample() { 
  
 double x1 = input1.eval()
 double x2 = input2.eval()
 double y =  gain*(x1-x2)
 return y 
}

  public String toString() { 
  StringBuffer sb = new StringBuffer()
   sb.append("name="+name+"\n");
   sb.append("gain="+gain+"\n");
   sb.append("inNet1="+inNet1+"\n");
   sb.append("inNet2="+inNet2+"\n");
   sb.append("outNet="+outNet+"\n");
  return sb.toString() 

}
}